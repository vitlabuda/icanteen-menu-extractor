package cz.vitlabuda.icanteen_menu_extractor.menu_extractors._helpers;

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

import cz.vitlabuda.icanteen_menu_extractor.menu_extractors.exc.InvalidHTMLException;
import org.jetbrains.annotations.NotNull;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

public class JsoupAdapter { // DP: Adapter
    public JsoupAdapter() {}

    public @NotNull Document parseDocument(@NotNull String htmlString) {
        return Jsoup.parse(htmlString);
    }

    public @NotNull Element selectFirstElement(@NotNull Element selectFrom, @NotNull String cssQuery) throws InvalidHTMLException {
        Element element = selectFrom.selectFirst(cssQuery);
        if(element == null)
            throw generateNoElementMatchesCSSQueryException(cssQuery);

        return element;
    }

    public @NotNull Elements selectAllElements(@NotNull Element selectFrom, @NotNull String cssQuery) throws InvalidHTMLException {
        Elements elements = selectFrom.select(cssQuery);
        if(elements.isEmpty())
            throw generateNoElementMatchesCSSQueryException(cssQuery);

        return elements;
    }

    public @NotNull String getAttributeValueOfElement(@NotNull Element element, @NotNull String attributeName) throws InvalidHTMLException {
        String attribute = element.attr(attributeName).trim();
        if(attribute.isEmpty())
            throw new InvalidHTMLException(String.format("The HTML element has no attribute named '%s'!", attributeName));

        return attribute;
    }

    public @NotNull String getInnerTextOfElement(@NotNull Element element) throws InvalidHTMLException {
        String text = element.ownText().trim();
        if(text.isEmpty())
            throw new InvalidHTMLException("The inner text of the HTML element is empty!");

        return text;
    }

    public @NotNull String getInnerTextOfElementAndItsChildren(@NotNull Element element) throws InvalidHTMLException {
        String text = element.text().trim();
        if(text.isEmpty())
            throw new InvalidHTMLException("The inner text of the HTML element and its children is empty!");

        return text;
    }

    public @NotNull Elements getChildrenOfElement(@NotNull Element element) throws InvalidHTMLException {
        Elements children = element.children();
        if(children.isEmpty())
            throw new InvalidHTMLException("The HTML element has no children!");

        return children;
    }

    public @NotNull Element getElementAtIndex(@NotNull Elements elements, int index) throws InvalidHTMLException {
        try {
            return elements.get(index);
        } catch (IndexOutOfBoundsException e) {
            throw new InvalidHTMLException(String.format("The HTML element collection does not contain an element at index %d!", index));
        }
    }

    private @NotNull InvalidHTMLException generateNoElementMatchesCSSQueryException(@NotNull String cssQuery) {  // DP: Factory
        return new InvalidHTMLException(String.format("No HTML element matches the following CSS query: '%s'", cssQuery));
    }
}
