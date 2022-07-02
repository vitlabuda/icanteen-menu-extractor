package cz.vitlabuda.icanteen_menu_extractor.utils;

/*
Copyright (c) 2022 Vít Labuda. All rights reserved.

Redistribution and use in source and binary forms, with or without modification, are permitted provided that the
following conditions are met:
 1. Redistributions of source code must retain the above copyright notice, this list of conditions and the following
    disclaimer.
 2. Redistributions in binary form must reproduce the above copyright notice, this list of conditions and the
    following disclaimer in the documentation and/or other materials provided with the distribution.
 3. Neither the name of the copyright holder nor the names of its contributors may be used to endorse or promote
    products derived from this software without specific prior written permission.

THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS "AS IS" AND ANY EXPRESS OR IMPLIED WARRANTIES,
INCLUDING, BUT NOT LIMITED TO, THE IMPLIED WARRANTIES OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR PURPOSE ARE
DISCLAIMED. IN NO EVENT SHALL THE COPYRIGHT HOLDER OR CONTRIBUTORS BE LIABLE FOR ANY DIRECT, INDIRECT, INCIDENTAL,
SPECIAL, EXEMPLARY, OR CONSEQUENTIAL DAMAGES (INCLUDING, BUT NOT LIMITED TO, PROCUREMENT OF SUBSTITUTE GOODS OR
SERVICES; LOSS OF USE, DATA, OR PROFITS; OR BUSINESS INTERRUPTION) HOWEVER CAUSED AND ON ANY THEORY OF LIABILITY,
WHETHER IN CONTRACT, STRICT LIABILITY, OR TORT (INCLUDING NEGLIGENCE OR OTHERWISE) ARISING IN ANY WAY OUT OF THE USE
OF THIS SOFTWARE, EVEN IF ADVISED OF THE POSSIBILITY OF SUCH DAMAGE.
*/

import cz.vitlabuda.icanteen_menu_extractor.ICanteenMenuExtractorConstants;
import cz.vitlabuda.icanteen_menu_extractor.utils.exc.HTTPFetcherException;
import org.jetbrains.annotations.NotNull;

import javax.net.ssl.SSLException;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.SocketTimeoutException;
import java.net.URL;

/**
 * A utility class which may be used to fetch a file (e.g. an HTML document) from an HTTP/HTTPS URL.
 */
public class HTTPFetcher {
    private final int timeoutMilliseconds; // If negative, the value is not used

    /**
     * Constructs a new 'HTTPFetcher' instance.
     *
     * The default timeout of 'HttpURLConnection' is used.
     */
    public HTTPFetcher() {
        this.timeoutMilliseconds = -1;
    }

    /**
     * Constructs a new 'HTTPFetcher' instance.
     *
     * @param timeoutMilliseconds Timeout in milliseconds. If it is negative, the default timeout of 'HttpURLConnection' is used.
     */
    public HTTPFetcher(int timeoutMilliseconds) {
        this.timeoutMilliseconds = timeoutMilliseconds;
    }

    /**
     * Fetches a file from the supplied URL.
     *
     * @param urlString The URL (as a string) to fetch the file from.
     * @return The fetched file as string.
     * @throws HTTPFetcherException If an error occurs while fetching the file, e.g. it is not possible to establish a connection, or the file is not found.
     */
    public @NotNull String fetch(@NotNull String urlString) throws HTTPFetcherException {
        URL urlObject = urlStringToUrlObject(urlString);

        return performHttpRequest(urlObject);
    }

    /**
     * Fetches a file from the supplied URL.
     *
     * @param urlObject The URL (as a URL object) to fetch the file from.
     * @return The fetched file as string.
     * @throws HTTPFetcherException If an error occurs while fetching the file, e.g. it is not possible to establish a connection, or the file is not found.
     */
    public @NotNull String fetch(@NotNull URL urlObject) throws HTTPFetcherException {
        return performHttpRequest(urlObject);
    }

    private @NotNull URL urlStringToUrlObject(@NotNull String urlString) throws HTTPFetcherException {
        try {
            return new URL(urlString);
        } catch (MalformedURLException e) {
            throw new HTTPFetcherException(String.format("The supplied string contains a malformed URL: '%s'", urlString));
        }
    }

    private @NotNull String performHttpRequest(@NotNull URL urlObject) throws HTTPFetcherException {
        checkURLObject(urlObject);

        HttpURLConnection connection = null;
        try {
            connection = (HttpURLConnection) urlObject.openConnection();

            connection.setInstanceFollowRedirects(false);
            connection.setUseCaches(false);
            connection.setDoOutput(false);
            connection.setDoInput(true);

            if (timeoutMilliseconds >= 0) {
                connection.setConnectTimeout(timeoutMilliseconds);
                connection.setReadTimeout(timeoutMilliseconds);
            }

            connection.connect();

            {
                int responseCode = connection.getResponseCode();
                if (responseCode != 200)
                    throw new HTTPFetcherException(String.format("While fetching '%s', the server responded with the following erroneous HTTP response code: %d", urlObject, responseCode));
            }

            try(InputStream inputStream = connection.getInputStream()) {
                return fullyReadInputStreamToString(inputStream);
            }

        } catch (SSLException e) {
            throw new HTTPFetcherException(String.format("Failed to establish a secure connection with '%s'!", urlObject));
        } catch (SocketTimeoutException e) {
            throw new HTTPFetcherException(String.format("The connection to '%s' timed out!", urlObject));
        } catch (IOException e) {
            throw new HTTPFetcherException(String.format("An error occurred while communicating with '%s'! Please check your internet connection!", urlObject));
        } finally {
            if(connection != null)
                connection.disconnect();
        }
    }

    private void checkURLObject(@NotNull URL urlObject) throws HTTPFetcherException {
        String protocol = urlObject.getProtocol();
        if(!("http".equals(protocol)) && !("https".equals(protocol)))
            throw new HTTPFetcherException(String.format("The protocol specified in the supplied URL is not supported: '%s'", protocol));

        String host = urlObject.getHost();
        if(host == null || host.isEmpty())
            throw new HTTPFetcherException("The supplied URL does not contain a hostname!");
    }

    private @NotNull String fullyReadInputStreamToString(@NotNull InputStream inputStream) throws IOException {
        StringBuilder stringBuilder = new StringBuilder();

        try(BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream))) {
            String temp;
            while((temp = reader.readLine()) != null) {
                stringBuilder.append(temp);
                stringBuilder.append(ICanteenMenuExtractorConstants.LINE_SEPARATOR);
            }
        }

        return stringBuilder.toString();
    }
}
