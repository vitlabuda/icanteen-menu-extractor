package cz.vitlabuda.icanteen_menu_extractor.containers;

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

import org.jetbrains.annotations.NotNull;

import java.time.LocalDate;

/**
 * A container class containing a single day in a food menu.
 */
public class Day implements ContainerInterface {
    private final @NotNull Dish[] dishes;
    private final @NotNull LocalDate date;

    /**
     * Constructs a new 'Day' instance.
     *
     * @param dishes A non-empty array of dishes.
     * @param date A date object.
     */
    public Day(@NotNull Dish[] dishes, @NotNull LocalDate date) {
        this.dishes = dishes.clone();
        this.date = date;

        assert this.dishes.length > 0;
    }

    /**
     * Returns the stored array of dishes.
     *
     * @return The stored array of dishes.
     */
    public @NotNull Dish[] getDishes() {
        return dishes.clone();
    }

    /**
     * Returns the stored date object.
     *
     * @return The stored date object.
     */
    public @NotNull LocalDate getDate() {
        return date;
    }
}
