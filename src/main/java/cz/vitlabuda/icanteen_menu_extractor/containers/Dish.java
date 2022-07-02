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

/**
 * A container class containing a single dish in a day in a food menu.
 */
public class Dish implements ContainerInterface {
    private final @NotNull String dishTitleString;
    private final @NotNull String dishString;

    /**
     * Constructs a new 'Dish' instance.
     *
     * @param dishTitleString A non-empty dish title string, e.g. "Oběd 1" or "Snídaně".
     * @param dishString A non-empty dish "description" string, e.g. "Řízek s bramborem" or "Rohlík s máslem".
     */
    public Dish(@NotNull String dishTitleString, @NotNull String dishString) {
        this.dishTitleString = dishTitleString.trim();
        this.dishString = dishString.trim();

        assert !this.dishTitleString.isEmpty();
        assert !this.dishString.isEmpty();
    }

    /**
     * Returns the stored dish title string.
     *
     * @return The stored dish title string.
     */
    public @NotNull String getDishTitleString() {
        return dishTitleString;
    }

    /**
     * Returns the stored dish "description" string.
     *
     * @return The stored dish "description" string.
     */
    public @NotNull String getDishString() {
        return dishString;
    }
}
