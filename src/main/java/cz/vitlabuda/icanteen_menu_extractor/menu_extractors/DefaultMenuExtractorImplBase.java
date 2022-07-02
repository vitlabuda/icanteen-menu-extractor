package cz.vitlabuda.icanteen_menu_extractor.menu_extractors;

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
import cz.vitlabuda.icanteen_menu_extractor.dish_filters.DishFilterInterface;
import cz.vitlabuda.icanteen_menu_extractor.dish_filters.impl.BracketsRemovingDishFilter;
import cz.vitlabuda.icanteen_menu_extractor.dish_filters.impl.StrangenessRemovingDishFilter;
import cz.vitlabuda.icanteen_menu_extractor.dish_filters.impl.WhitespaceCleaningDishFilter;
import cz.vitlabuda.icanteen_menu_extractor.menu_extractors.exc.InvalidDayDataException;
import cz.vitlabuda.icanteen_menu_extractor.menu_extractors.exc.InvalidDishDataException;
import cz.vitlabuda.icanteen_menu_extractor.menu_extractors.exc.InvalidMenuDataException;
import cz.vitlabuda.icanteen_menu_extractor.menu_extractors._helpers.ContainerInstantiationHelper;
import cz.vitlabuda.icanteen_menu_extractor.menu_extractors._helpers.DateParsingHelper;
import cz.vitlabuda.icanteen_menu_extractor.menu_extractors._helpers.JsoupAdapter;
import org.jetbrains.annotations.NotNull;

import java.time.LocalDate;
import java.util.List;

/**
 * An abstract base class which is extended by all of this library's built-in menu extractor classes.
 */
public abstract class DefaultMenuExtractorImplBase implements MenuExtractorInterface {
    private static final @NotNull DishFilterInterface[] DEFAULT_DISH_FILTER_SEQUENCE = new DishFilterInterface[] { // May be empty.
        new StrangenessRemovingDishFilter(),
        new BracketsRemovingDishFilter(),
        new WhitespaceCleaningDishFilter()
    };

    private final @NotNull DishFilterInterface[] dishFilterSequence; // May be empty; should be used only in this class's proxy methods.
    private final @NotNull ContainerInstantiationHelper containerInstantiationHelper; // Should be used only in this class's proxy methods.
    protected final @NotNull JsoupAdapter jsoupAdapter; // Should be used by inheritors.
    protected final @NotNull DateParsingHelper dateParsingHelper; // Should be used by inheritors.

    /**
     * Constructs a new 'DefaultMenuExtractorImplBase' instance.
     *
     * The default dish filter sequence will be used.
     */
    public DefaultMenuExtractorImplBase() {
        this.dishFilterSequence = DEFAULT_DISH_FILTER_SEQUENCE.clone();
        this.containerInstantiationHelper = new ContainerInstantiationHelper();
        this.jsoupAdapter = new JsoupAdapter();
        this.dateParsingHelper = new DateParsingHelper();
    }

    /**
     * Constructs a new 'DefaultMenuExtractorImplBase' instance.
     *
     * @param possiblyEmptyDishFilterSequence A possibly empty array of dish filters to be used by the instance.
     */
    public DefaultMenuExtractorImplBase(@NotNull DishFilterInterface[] possiblyEmptyDishFilterSequence) {
        this.dishFilterSequence = possiblyEmptyDishFilterSequence.clone();
        this.containerInstantiationHelper = new ContainerInstantiationHelper();
        this.jsoupAdapter = new JsoupAdapter();
        this.dateParsingHelper = new DateParsingHelper();
    }

    /**
     * Returns the stored dish filter sequence.
     *
     * @return The stored dish filter sequence.
     */
    public @NotNull DishFilterInterface[] getDishFilterSequence() {
        return dishFilterSequence.clone();
    }

    protected @NotNull Menu containerizeMenu(@NotNull List<Day> days, @NotNull String extractorClassName) throws InvalidMenuDataException  {  // DP: Proxy
        // For future extension.
        return containerInstantiationHelper.instantiateMenuContainer(days, extractorClassName);
    }

    protected @NotNull Day containerizeDay(@NotNull List<Dish> dishes, @NotNull LocalDate date) throws InvalidDayDataException {
        // For future extension.
        return containerInstantiationHelper.instantiateDayContainer(dishes, date);
    }

    protected @NotNull Dish filterAndContainerizeDish(@NotNull String dishTitleString, @NotNull String dishString) throws InvalidDishDataException {  // DP: Proxy
        for(DishFilterInterface dishFilter : dishFilterSequence) {
            dishTitleString = dishFilter.filterDishTitleString(dishTitleString);
            dishString = dishFilter.filterDishString(dishString);
        }

        return containerInstantiationHelper.instantiateDishContainer(dishTitleString, dishString);
    }
}
