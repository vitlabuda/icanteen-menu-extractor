package cz.vitlabuda.icanteen_menu_extractor.dish_filters.impl;

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

import cz.vitlabuda.icanteen_menu_extractor.dish_filters.DefaultDishFilterImplBase;
import org.jetbrains.annotations.NotNull;

/**
 * A dish filter implementation which "cleans" and normalizes whitespace in input strings.
 */
public class WhitespaceCleaningDishFilter extends DefaultDishFilterImplBase {
    /**
     * Constructs a new 'WhitespaceCleaningDishFilter' instance.
     */
    public WhitespaceCleaningDishFilter() {
        // For future extension.
        super();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public @NotNull String filterDishTitleString(@NotNull String dishTitleString) {
        dishTitleString = dishTitleString.replaceAll("(\\d+)", " $1");

        return cleanWhitespaceInString(dishTitleString);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public @NotNull String filterDishString(@NotNull String dishString) {
        return cleanWhitespaceInString(dishString);
    }

    private @NotNull String cleanWhitespaceInString(@NotNull String string) {
        string = string.replaceAll("\\s*([,:;])\\s*", "$1 ");
        string = string.replaceAll("\\s*(-)\\s*", " $1 ");
        string = string.replaceAll("\\s+", " ");
        string = string.trim();

        return string;
    }
}
