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

import cz.vitlabuda.icanteen_menu_extractor.containers.Day;
import cz.vitlabuda.icanteen_menu_extractor.containers.Dish;
import cz.vitlabuda.icanteen_menu_extractor.containers.Menu;
import cz.vitlabuda.icanteen_menu_extractor.menu_extractors.exc.InvalidDayDataException;
import cz.vitlabuda.icanteen_menu_extractor.menu_extractors.exc.InvalidDishDataException;
import cz.vitlabuda.icanteen_menu_extractor.menu_extractors.exc.InvalidMenuDataException;
import org.jetbrains.annotations.NotNull;

import java.time.LocalDate;
import java.util.List;

public class ContainerInstantiationHelper {
    public ContainerInstantiationHelper() {}

    public @NotNull Menu instantiateMenuContainer(@NotNull List<Day> days, @NotNull String extractorClassName) throws InvalidMenuDataException { // DP: Factory
        extractorClassName = extractorClassName.trim();

        if(days.isEmpty())
            throw new InvalidMenuDataException("The supplied list of days is empty!");
        if(extractorClassName.isEmpty())
            throw new InvalidMenuDataException("The supplied extractor class name string is empty!");

        return new Menu(days.toArray(new Day[0]), extractorClassName);
    }

    public @NotNull Day instantiateDayContainer(@NotNull List<Dish> dishes, @NotNull LocalDate date) throws InvalidDayDataException { // DP: Factory
        if(dishes.isEmpty())
            throw new InvalidDayDataException("The supplied list of dishes is empty!");

        return new Day(dishes.toArray(new Dish[0]), date);
    }

    public @NotNull Dish instantiateDishContainer(@NotNull String dishTitleString, @NotNull String dishString) throws InvalidDishDataException { // DP: Factory
        dishTitleString = dishTitleString.trim();
        dishString = dishString.trim();

        if(dishTitleString.isEmpty())
            throw new InvalidDishDataException("The supplied dish title string is empty!");
        if(dishString.isEmpty())
            throw new InvalidDishDataException("The supplied dish string is empty!");

        return new Dish(dishTitleString, dishString);
    }
}
