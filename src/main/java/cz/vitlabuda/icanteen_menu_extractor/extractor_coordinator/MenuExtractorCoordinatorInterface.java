package cz.vitlabuda.icanteen_menu_extractor.extractor_coordinator;

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

import cz.vitlabuda.icanteen_menu_extractor.containers.Menu;
import cz.vitlabuda.icanteen_menu_extractor.extractor_coordinator.exc.AllExtractorsFailedException;
import cz.vitlabuda.icanteen_menu_extractor.extractor_coordinator.exc.NoMenuPublishedSaysExtractorException;
import org.jetbrains.annotations.NotNull;

/**
 * An interface implemented by this library's menu extractor coordinator classes.
 */
public interface MenuExtractorCoordinatorInterface {
    /**
     * Extracts a food menu from the supplied HTML document using the coordinator's collection of menu extractors.
     *
     * @param htmlString The HTML document to extract the food menu from.
     * @return A 'Menu' instance containing the extracted food menu.
     * @throws NoMenuPublishedSaysExtractorException When a menu extractor used by the coordinator finds out that the HTML document it is trying to parse contains a note that the canteen has not published a food menu.
     * @throws AllExtractorsFailedException When all menu extractors used by the coordinator fail to extract a food menu from the HTML document.
     */
    @NotNull Menu extractMenuFromHtml(@NotNull String htmlString) throws NoMenuPublishedSaysExtractorException, AllExtractorsFailedException;
}
