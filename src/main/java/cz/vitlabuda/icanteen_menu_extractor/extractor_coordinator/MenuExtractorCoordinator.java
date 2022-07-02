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
import cz.vitlabuda.icanteen_menu_extractor.menu_extractors.exc.MenuExtractionFailedExceptionBase;
import cz.vitlabuda.icanteen_menu_extractor.menu_extractors.MenuExtractorInterface;
import cz.vitlabuda.icanteen_menu_extractor.menu_extractors.exc.NoMenuPublishedException;
import cz.vitlabuda.icanteen_menu_extractor.menu_extractors.impl.NewFormatMenuExtractor;
import cz.vitlabuda.icanteen_menu_extractor.menu_extractors.impl.OldFormatMenuExtractor;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;

/**
 * This library's default and only implementation of menu extractor coordinator.
 */
public class MenuExtractorCoordinator implements MenuExtractorCoordinatorInterface {
    private static final @NotNull MenuExtractorInterface[] DEFAULT_MENU_EXTRACTOR_SEQUENCE = new MenuExtractorInterface[] {  // Must not be empty!
        new NewFormatMenuExtractor(),
        new OldFormatMenuExtractor()
    };

    private final @NotNull MenuExtractorInterface[] menuExtractorSequence; // Must not be empty!

    /**
     * Constructs a new 'MenuExtractorCoordinator' instance.
     *
     * The default menu extractor sequence will be used.
     */
    public MenuExtractorCoordinator() {
        this.menuExtractorSequence = DEFAULT_MENU_EXTRACTOR_SEQUENCE.clone();
    }

    /**
     * Constructs a new 'MenuExtractorCoordinator' instance.
     *
     * @param nonEmptyMenuExtractorSequence A non-empty array of menu extractors to be used by the instance.
     */
    public MenuExtractorCoordinator(@NotNull MenuExtractorInterface[] nonEmptyMenuExtractorSequence) {
        assert nonEmptyMenuExtractorSequence.length > 0;

        this.menuExtractorSequence = nonEmptyMenuExtractorSequence.clone();
    }

    /**
     * Returns the stored menu extractor sequence.
     *
     * @return The stored menu extractor sequence.
     */
    public @NotNull MenuExtractorInterface[] getMenuExtractorSequence() {
        return menuExtractorSequence.clone();
    }

    /**
     * {@inheritDoc}
     */
    public @NotNull Menu extractMenuFromHtml(@NotNull String htmlString) throws NoMenuPublishedSaysExtractorException, AllExtractorsFailedException {
        List<MenuExtractionFailedExceptionBase> extractorExceptions = new ArrayList<>();

        for(MenuExtractorInterface extractor : menuExtractorSequence) {
            try {
                return extractor.extractMenuFromHtml(htmlString);
            } catch (NoMenuPublishedException e) {
                throw new NoMenuPublishedSaysExtractorException("An extractor says that no menu has been published by the canteen!");
            } catch (MenuExtractionFailedExceptionBase f) {
                extractorExceptions.add(f);
            }
        }

        throw new AllExtractorsFailedException(
            "None of the extractors was able to extract a menu from the supplied HTML string!",
            extractorExceptions.toArray(new MenuExtractionFailedExceptionBase[0])
        );
    }
}
