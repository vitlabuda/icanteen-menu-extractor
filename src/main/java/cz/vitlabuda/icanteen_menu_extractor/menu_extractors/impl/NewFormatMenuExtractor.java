package cz.vitlabuda.icanteen_menu_extractor.menu_extractors.impl;

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

import cz.vitlabuda.icanteen_menu_extractor.containers.Day;
import cz.vitlabuda.icanteen_menu_extractor.containers.Dish;
import cz.vitlabuda.icanteen_menu_extractor.containers.Menu;
import cz.vitlabuda.icanteen_menu_extractor.menu_extractors.DefaultMenuExtractorImplBase;
import cz.vitlabuda.icanteen_menu_extractor.menu_extractors.exc.InvalidHTMLException;
import cz.vitlabuda.icanteen_menu_extractor.menu_extractors.exc.MenuExtractionFailedExceptionBase;
import cz.vitlabuda.icanteen_menu_extractor.dish_filters.DishFilterInterface;
import cz.vitlabuda.icanteen_menu_extractor.menu_extractors.exc.NoMenuPublishedException;
import org.jetbrains.annotations.NotNull;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

/**
 * A menu extractor implementation which is able to extract food menus from iCanteen HTML documents featuring the "new format".
 *
 * <a href="https://web.archive.org/web/20210614172216/https://strav.nasejidelna.cz/0051/login">An example of a "new format" HTML document</a>
 */
public class NewFormatMenuExtractor extends DefaultMenuExtractorImplBase {
    /**
     * Constructs a new 'NewFormatMenuExtractor' instance.
     *
     * The default dish filter sequence will be used.
     */
    public NewFormatMenuExtractor() {
        // For future extension.
        super();
    }

    /**
     * Constructs a new 'NewFormatMenuExtractor' instance.
     *
     * @param possiblyEmptyDishFilterSequence A possibly empty array of dish filters to be used by the instance.
     */
    public NewFormatMenuExtractor(@NotNull DishFilterInterface[] possiblyEmptyDishFilterSequence) {
        super(possiblyEmptyDishFilterSequence);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public @NotNull Menu extractMenuFromHtml(@NotNull String htmlString) throws MenuExtractionFailedExceptionBase {
        Document document = jsoupAdapter.parseDocument(htmlString);

        throwIfNoMenuHasBeenPublished(document);

        return extractMenu(document);
    }

    private void throwIfNoMenuHasBeenPublished(@NotNull Document document) throws NoMenuPublishedException {
        try {
            jsoupAdapter.selectFirstElement(document, ".noMenu");
        } catch (InvalidHTMLException e) {
            return; // If the '.noMenu' element could not be found, it means that there is a menu present
        }

        // If the '.noMenu' element could be found, it means that there is no menu present
        throw new NoMenuPublishedException("No menu has been published by the canteen!");
    }

    private @NotNull Menu extractMenu(@NotNull Document document) throws MenuExtractionFailedExceptionBase {
        List<Day> days = new ArrayList<>();

        {
            Element jidelnicek = jsoupAdapter.selectFirstElement(document, ".jidelnicek");
            for(Element jidelnicekDen : jsoupAdapter.selectAllElements(jidelnicek, ".jidelnicekDen"))
                days.add(extractDay(jidelnicekDen));
        }

        return containerizeMenu(days, getClass().getName());
    }

    private @NotNull Day extractDay(@NotNull Element jidelnicekDen) throws MenuExtractionFailedExceptionBase {
        LocalDate date;
        {
            Element jidelnicekTop = jsoupAdapter.selectFirstElement(jidelnicekDen, ".jidelnicekTop");
            String dateString = jsoupAdapter.getAttributeValueOfElement(jidelnicekTop, "id");
            date = dateParsingHelper.parseICanteenIdAttrDateString(dateString);
        }

        List<Dish> dishes = new ArrayList<>();
        {
            Element article = jsoupAdapter.selectFirstElement(jidelnicekDen, "article");
            for(Element container : jsoupAdapter.selectAllElements(article, ".container"))
                dishes.add(extractDish(container));
        }

        return containerizeDay(dishes, date);
    }

    private @NotNull Dish extractDish(@NotNull Element container) throws MenuExtractionFailedExceptionBase {
        String dishTitleString, dishString;
        {
            Elements jidelnicekItems = jsoupAdapter.selectAllElements(container, ".jidelnicekItem");

            Element dishTitleElement = jsoupAdapter.getElementAtIndex(jidelnicekItems, 0);
            Element dishElement = jsoupAdapter.getElementAtIndex(jidelnicekItems, 1);

            dishTitleString = jsoupAdapter.getInnerTextOfElementAndItsChildren(dishTitleElement); // e.g. "Oběd 1"
            dishString = jsoupAdapter.getInnerTextOfElementAndItsChildren(dishElement); // e.g. "Polévka květáková"
        }

        return filterAndContainerizeDish(dishTitleString, dishString);
    }
}
