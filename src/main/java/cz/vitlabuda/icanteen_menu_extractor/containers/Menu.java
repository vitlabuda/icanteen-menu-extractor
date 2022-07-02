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
 * A container class containing a food menu.
 */
public class Menu implements ContainerInterface {
    private final @NotNull Day[] days;
    private final @NotNull String extractorClassName;

    /**
     * Constructs a new 'Menu' instance.
     *
     * @param days A non-empty array of days.
     * @param extractorClassName A non-empty string containing the name of the extractor class which extracted the food menu from an HTML document.
     */
    public Menu(@NotNull Day[] days, @NotNull String extractorClassName) {
        this.days = days.clone();
        this.extractorClassName = extractorClassName.trim();

        assert this.days.length > 0;
        assert !this.extractorClassName.isEmpty();
    }

    /**
     * Returns the stored array of days.
     *
     * @return The stored array of days.
     */
    public @NotNull Day[] getDays() {
        return days.clone();
    }

    /**
     * Returns the stored extractor class name string.
     *
     * @return The stored extractor class name string.
     */
    public @NotNull String getExtractorClassName() {
        return extractorClassName;
    }
}
