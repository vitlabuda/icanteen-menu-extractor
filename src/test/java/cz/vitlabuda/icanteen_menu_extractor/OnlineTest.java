package cz.vitlabuda.icanteen_menu_extractor;

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

import cz.vitlabuda.icanteen_menu_extractor.extractor_coordinator.MenuExtractorCoordinator;
import cz.vitlabuda.icanteen_menu_extractor.extractor_coordinator.exc.AllExtractorsFailedException;
import cz.vitlabuda.icanteen_menu_extractor.extractor_coordinator.exc.NoMenuPublishedSaysExtractorException;
import cz.vitlabuda.icanteen_menu_extractor.utils.HTTPFetcher;
import cz.vitlabuda.icanteen_menu_extractor.utils.exc.HTTPFetcherException;
import org.jetbrains.annotations.NotNull;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

class OnlineTest {

    @ParameterizedTest
    @ValueSource(strings = {
        // Completely random instances found through a Google search.
        "https://strav.nasejidelna.cz/demo/login",
        "https://strav.nasejidelna.cz/0117/login",
        "https://strav.nasejidelna.cz/0051/login",
        "https://strav.nasejidelna.cz/0211/login",
        "https://strav.nasejidelna.cz/0072/login",
        "https://web.archive.org/web/20200804115747/https://strav.nasejidelna.cz/demo/login",
        "https://web.archive.org/web/20210614172216/https://strav.nasejidelna.cz/0051/login",
        "https://web.archive.org/web/20210725001310/https://strav.nasejidelna.cz/0072/login",
        "https://intr.dmvm.cz:8443/login",
        "http://jidelna.ssis.cz/login",
        "https://strava.sps-chrudim.cz/faces/login.jsp",
        "http://icanteen.spst.cz/faces/login.jsp",
        "http://jidelna.epol.cz:8090/faces/login.jsp",
        "http://90.179.28.190:8080/faces/login.jsp",
        "https://objednavky.jidelnasokolska.cz/faces/login.jsp",
        "https://jidelna.geology.cz/faces/login.jsp",
        "https://web.archive.org/web/20220515232500/http://jidelna.ssis.cz/login",
        "https://web.archive.org/web/20160512211226/http://jidelna.epol.cz:8090/faces/login.jsp"
    })
    void onlinePositiveTest(@NotNull final String testedUrl) throws HTTPFetcherException, AllExtractorsFailedException {
        final String htmlString = new HTTPFetcher().fetch(testedUrl);
        final MenuExtractorCoordinator coordinator = new MenuExtractorCoordinator();

        // The test succeeds if the menu is parsed successfully, i.e. if no exception is thrown.
        try {
            coordinator.extractMenuFromHtml(htmlString);
        } catch (NoMenuPublishedSaysExtractorException ignored) {}  // However, it may happen that there is currently no menu published by the canteen.
    }

    @ParameterizedTest
    @ValueSource(strings = {
        "https://vitlabuda.cz/",
        "https://cs.wikipedia.org/wiki/Hlavn%C3%AD_strana",
        "https://en.wikipedia.org/wiki/Main_Page",
        "https://jsonplaceholder.typicode.com/posts",  // Does not contain HTML!
        "https://upload.wikimedia.org/wikipedia/commons/3/35/Tux.svg",  // Does not contain HTML!
        "https://cs.wikipedia.org/static/images/project-logos/cswiki.png"  // Does not contain HTML!
    })
    void onlineNegativeTest(@NotNull final String testedUrl) throws HTTPFetcherException {
        final String htmlString = new HTTPFetcher().fetch(testedUrl);
        final MenuExtractorCoordinator coordinator = new MenuExtractorCoordinator();

        Assertions.assertThrows(AllExtractorsFailedException.class, () -> coordinator.extractMenuFromHtml(htmlString));
    }
}
