package cz.vitlabuda.icanteen_menu_extractor;

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
import cz.vitlabuda.icanteen_menu_extractor.extractor_coordinator.MenuExtractorCoordinator;
import cz.vitlabuda.icanteen_menu_extractor.extractor_coordinator.exc.AllExtractorsFailedException;
import cz.vitlabuda.icanteen_menu_extractor.extractor_coordinator.exc.NoMenuPublishedSaysExtractorException;
import cz.vitlabuda.icanteen_menu_extractor.menu_extractors.impl.NewFormatMenuExtractor;
import cz.vitlabuda.icanteen_menu_extractor.menu_extractors.impl.OldFormatMenuExtractor;
import org.jetbrains.annotations.NotNull;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.junit.jupiter.params.provider.ValueSource;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.time.LocalDate;
import java.util.stream.Stream;

class OfflineTest {

    static Stream<Arguments> offlinePositiveTest() {
        return Stream.of(
            Arguments.of(
                    "offline_positive_test_input_1.dat",
                new Menu(
                    new Day[] {
                        new Day(
                            new Dish[] {
                                new Dish("Oběd 1", "Slepičí polévka s masem a rýží, pečené kuřecí stehno, rýže dušená, míchaný salát, čaj, ovocný nápoj"),
                                new Dish("Oběd 2", "Slepičí polévka s masem a rýží, vepřová játra po ševcovsku, hranolky, míchaný salát, čaj, ovocný nápoj")
                            },
                            LocalDate.of(2022, 7, 1)
                        )
                    },
                    NewFormatMenuExtractor.class.getName()
                )
            ),

            Arguments.of(
                    "offline_positive_test_input_2.dat",
                new Menu(
                    new Day[] {
                        new Day(
                            new Dish[] {
                                new Dish("Oběd 1", "Zeleninový vývar s tarhoňou, hovězí pečeně frankfurtská, houskový knedlík - rýže"),
                                new Dish("Oběd 2", "Zeleninový vývar s tarhoňou, francouzské brambory, rajčatový salát")
                            },
                            LocalDate.of(2022, 7, 1)
                        ),
                        new Day(
                            new Dish[] {
                                new Dish("Oběd 1", "Hrachová, rozstřelený ptáček, rýže dušená"),
                                new Dish("Oběd 2", "Hrachová, květák jako mozeček, brambory vařené m.m., kompot")
                            },
                            LocalDate.of(2022, 7, 4)
                        ),
                        new Day(
                            new Dish[] {
                                new Dish("Oběd 1", "Hovězí vývar s těstovinou, vepřová pečeně, bramborové knedlíky, zelí hlávkové"),
                                new Dish("Oběd 2", "Hovězí vývar s těstovinou, smažený sýr, brambory vařené, dresink")
                            },
                            LocalDate.of(2022, 7, 7)
                        ),
                        new Day(
                            new Dish[] {
                                new Dish("Oběd 1", "Květáková polévka, hovězí zadní, rajská omáčka, houskové knedlíky"),
                                new Dish("Oběd 2", "Květáková polévka, soukenický řízek, brambory šťouchané, okurkový salát")
                            },
                            LocalDate.of(2022, 7, 8)
                        ),
                        new Day(
                            new Dish[] {
                                new Dish("Oběd 1", "Čočková, sekaná pečeně, bramborové knedlíky, zelí hlávkové"),
                                new Dish("Oběd 2", "Čočková, těstovinový salát se zeleninou")
                            },
                            LocalDate.of(2022, 7, 11)
                        ),
                        new Day(
                            new Dish[] {
                                new Dish("Oběd 1", "Zeleninový vývar s tarhoňou, smažený květák, brambory vařené, dresink"),
                                new Dish("Oběd 2", "Zeleninový vývar s tarhoňou, vepřové maso na paprice, těstoviny")
                            },
                            LocalDate.of(2022, 7, 12)
                        ),
                        new Day(
                            new Dish[] {
                                new Dish("Oběd 1", "Frankfurtská s bramborem, kynuté knedlíky s borůvkami, ovoce"),
                                new Dish("Oběd 2", "Frankfurtská s bramborem, smažený kuřecí řízek, bramborová kaše m.m., rajčatový salát")
                            },
                            LocalDate.of(2022, 7, 13)
                        ),
                        new Day(
                            new Dish[] {
                                new Dish("Oběd 1", "Česneková s bramborem, masová směs, hranolky"),
                                new Dish("Oběd 2", "Česneková s bramborem, bramborový guláš s uzeninou, chléb")
                            },
                            LocalDate.of(2022, 7, 14)
                        ),
                        new Day(
                            new Dish[] {
                                new Dish("Oběd 1", "Brokolicová, svíčková na smetaně, houskové knedlíky"),
                                new Dish("Oběd 2", "Brokolicová, asijské nudle s kuřecím masem a zeleninou")
                            },
                            LocalDate.of(2022, 7, 15)
                        )
                    },
                    NewFormatMenuExtractor.class.getName()
                )
            ),

            Arguments.of(
                    "offline_positive_test_input_3.dat",
                new Menu(
                    new Day[] {
                        new Day(
                            new Dish[] {
                                new Dish("Oběd 1", "Polévka gulášová, kynuté knedlíky s ovocem sypané mákem, ovoce, nápoje")
                            },
                            LocalDate.of(2022, 7, 1)
                        ),
                        new Day(
                            new Dish[] {
                                new Dish("Oběd 1", "Polévka risi - bisi, vepřové nudličky po valticku, těstoviny, vitamaxima, dezert")
                            },
                            LocalDate.of(2022, 7, 4)
                        ),
                        new Day(
                            new Dish[] {
                                new Dish("Oběd 1", "Polévka kapustová s bramborem, sekaná pečeně, vařené brambory m.m., hlávkový salát, vitamaxima")
                            },
                            LocalDate.of(2022, 7, 7)
                        ),
                        new Day(
                            new Dish[] {
                                new Dish("Oběd 1", "Polévka s krupicovými noky, mexický guláš, rýže jasmínová, vitamaxima, puding")
                            },
                            LocalDate.of(2022, 7, 8)
                        ),
                    },
                    OldFormatMenuExtractor.class.getName()
                )
            ),

            Arguments.of(
                    "offline_positive_test_input_4.dat",
                new Menu(
                    new Day[] {
                        new Day(
                            new Dish[] {
                                new Dish("Oběd 1", "Cibulová polévka s bramborem, Hovězí maso po lovecku, houskový knedlík, čaj")
                            },
                            LocalDate.of(2020, 11, 25)
                        ),
                        new Day(
                            new Dish[] {
                                new Dish("Oběd 1", "Fazolová polévka, Domácí buchty s borůvkovou marmeládou, sladké mlléko, ovoce")
                            },
                            LocalDate.of(2020, 11, 26)
                        ),
                        new Day(
                            new Dish[] {
                                new Dish("Oběd 1", "Hovězí vývar s krupicovými noky, Koprová omáčka s vařeným vejcem a bramborem, čaj"),
                                new Dish("Oběd 2", "Hovězí vývar s krupicovými noky, Smažená krůtí játra, brambor, zeleninová obloha, čaj")
                            },
                            LocalDate.of(2020, 11, 27)
                        ),
                        new Day(
                            new Dish[] {
                                new Dish("Oběd 1", "Zeleninová polévka s drožďovými knedlíčky, Moravský vrabec, bramborový knedlík, dušený špenát, čaj")
                            },
                            LocalDate.of(2020, 11, 30)
                        ),
                        new Day(
                            new Dish[] {
                                new Dish("Oběd 1", "Koprová polévka s bramborem a vejcem, Pečené kuřecí stehno, dušená rýže, mošt"),
                                new Dish("Oběd 2", "Koprová polévka s bramborem a vejcem, Kuskus s kuřecím masem a zeleninou, mošt")
                            },
                            LocalDate.of(2020, 12, 1)
                        ),
                        new Day(
                            new Dish[] {
                                new Dish("Oběd 1", "Bramborová polévka, Čevabčiči s hořčicí a cibulí, brambory, džus"),
                                new Dish("Oběd 2", "Bramborová polévka, Pečený lososvý pstruh filet na citronovém pepři, brambor, džus")
                            },
                            LocalDate.of(2020, 12, 2)
                        ),
                        new Day(
                            new Dish[] {
                                new Dish("Oběd 1", "Hovězí vývar s těstovinou, Hovězí svíčková pečeně, houskový knedlík, čaj")
                            },
                            LocalDate.of(2020, 12, 3)
                        ),
                        new Day(
                            new Dish[] {
                                new Dish("Oběd 1", "Čočková polévka, Zapečené těstoviny s uzeninou, zeleninová obloha, čaj"),
                                new Dish("Oběd 2", "Čočková polévka, Rýžová kaše s jahodami sypaná skořicovými čtverečky, sladké mléko")
                            },
                            LocalDate.of(2020, 12, 4)
                        )
                    },
                    NewFormatMenuExtractor.class.getName()
                )
            )
        );
    }

    @ParameterizedTest
    @MethodSource
    void offlinePositiveTest(@NotNull final String resourceName, @NotNull final Menu correctMenu) throws IOException, AllExtractorsFailedException, NoMenuPublishedSaysExtractorException {
        final Menu extractedMenu = new MenuExtractorCoordinator().extractMenuFromHtml(readResourceFileToString(resourceName));
        Assertions.assertEquals(correctMenu.getExtractorClassName(), extractedMenu.getExtractorClassName());

        final Day[] extractedDays = extractedMenu.getDays(), correctDays = correctMenu.getDays();
        Assertions.assertEquals(correctDays.length, extractedDays.length);
        for(int i = 0; i < correctDays.length; i++) {
            final Day extractedDay = extractedDays[i], correctDay = correctDays[i];
            Assertions.assertEquals(correctDay.getDate(), extractedDay.getDate());

            final Dish[] extractedDishes = extractedDay.getDishes(), correctDishes = correctDay.getDishes();
            Assertions.assertEquals(correctDishes.length, extractedDishes.length);
            for(int j = 0; j < correctDishes.length; j++) {
                final Dish extractedDish = extractedDishes[j], correctDish = correctDishes[j];
                Assertions.assertEquals(correctDish.getDishTitleString(), extractedDish.getDishTitleString());
                Assertions.assertEquals(correctDish.getDishString(), extractedDish.getDishString());
            }
        }
    }

    @ParameterizedTest
    @ValueSource(strings = {
            "offline_nomenu_test_input_1.dat"
    })
    void offlineNoMenuTest(@NotNull final String resourceName) throws IOException {
        final String htmlString = readResourceFileToString(resourceName);
        final MenuExtractorCoordinator coordinator = new MenuExtractorCoordinator();

        Assertions.assertThrows(NoMenuPublishedSaysExtractorException.class, () -> coordinator.extractMenuFromHtml(htmlString));
    }

    @ParameterizedTest
    @ValueSource(strings = {
            "offline_negative_test_input_1.dat"
    })
    void offlineNegativeTest(@NotNull final String resourceName) throws IOException {
        final String htmlString = readResourceFileToString(resourceName);
        final MenuExtractorCoordinator coordinator = new MenuExtractorCoordinator();

        Assertions.assertThrows(AllExtractorsFailedException.class, () -> coordinator.extractMenuFromHtml(htmlString));
    }

    private @NotNull String readResourceFileToString(@NotNull final String resourceName) throws IOException {
        final StringBuilder stringBuilder = new StringBuilder();

        try(final InputStream inputStream = getClass().getClassLoader().getResourceAsStream(resourceName)) {
            assert inputStream != null;

            try(final BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream))) {
                String temp;
                while((temp = bufferedReader.readLine()) != null) {
                    stringBuilder.append(temp);
                    stringBuilder.append(ICanteenMenuExtractorConstants.LINE_SEPARATOR);
                }
            }
        }

        return stringBuilder.toString();
    }
}
