<!--
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
-->

# iCanteen Menu Extractor

**iCanteen Menu Extractor** is a Java library for extracting food menus from iCanteen, a Czech food ordering web 
application used by many canteens and school cafeterias in the Czech Republic. 

Since the web app seems not to offer a public API, **the library extracts food menus straight from the HTML code of the 
app's login page.** Since the food menus are publicly available without login, it is not necessary to have 
credentials to the app.



## Disclaimer

This project is not affiliated with *iCanteen* or its author, *Z-WARE s.r.o.* in any way. This library is a non-profit, 
open-source software programmed by an independent developer.



## Usage

In most cases, the only thing you will want to do is to simply fetch and parse a food menu without customizing this
library's functionality using various constructor parameters. 

To fetch the HTML of iCanteen's login page, you may use the 
[`HTTPFetcher`](src/main/java/cz/vitlabuda/icanteen_menu_extractor/utils/HTTPFetcher.java) class, although it is 
certainly not necessary – you may fetch the HTML using any other method or even acquire it from a file, database, 
cache etc.

To extract the food menu from the somehow acquired HTML (in a string), use the `extractMenuFromHtml(String htmlString)` 
method of the
[`MenuExtractorCoordinator`](src/main/java/cz/vitlabuda/icanteen_menu_extractor/extractor_coordinator/MenuExtractorCoordinator.java)
class. If everything goes well, a [`Menu`](src/main/java/cz/vitlabuda/icanteen_menu_extractor/containers/Menu.java) 
instance containing the desired food menu will be returned.

### Documentation

This library's public API and exceptions are documented using _**Doc comments**_ in the source code.

### Example

```java
import cz.vitlabuda.icanteen_menu_extractor.containers.Day;
import cz.vitlabuda.icanteen_menu_extractor.containers.Dish;
import cz.vitlabuda.icanteen_menu_extractor.containers.Menu;
import cz.vitlabuda.icanteen_menu_extractor.extractor_coordinator.MenuExtractorCoordinator;
import cz.vitlabuda.icanteen_menu_extractor.extractor_coordinator.exc.AllExtractorsFailedException;
import cz.vitlabuda.icanteen_menu_extractor.extractor_coordinator.exc.NoMenuPublishedSaysExtractorException;
import cz.vitlabuda.icanteen_menu_extractor.utils.HTTPFetcher;
import cz.vitlabuda.icanteen_menu_extractor.utils.exc.HTTPFetcherException;

public class Example {
    private static final String ICANTEEN_LOGIN_PAGE_URL = "https://strav.nasejidelna.cz/demo/login";

    public static void main(String[] args) {
        // Fetch the login page
        HTTPFetcher fetcher = new HTTPFetcher();
        String html;
        try {
            html = fetcher.fetch(ICANTEEN_LOGIN_PAGE_URL);
        } catch (HTTPFetcherException e) {
            System.err.println(e.toString());
            return;
        }

        // Extract the food menu from the login page
        MenuExtractorCoordinator extractorCoordinator = new MenuExtractorCoordinator();
        Menu menu;
        try {
            menu = extractorCoordinator.extractMenuFromHtml(html);
        } catch (AllExtractorsFailedException | NoMenuPublishedSaysExtractorException e) {
            System.err.println(e.toString());
            return;
        }

        // Display the extracted food menu
        for(Day day : menu.getDays()) {
            System.out.printf("----- %s -----\n", day.getDate().toString());

            for(Dish dish : day.getDishes())
                System.out.printf("%s: %s\n", dish.getDishTitleString(), dish.getDishString());

            System.out.println();
        }
    }
}
```



## Licensing
This project is licensed under the **3-clause BSD license** – see the [LICENSE](LICENSE) file.

In addition, this project uses some third-party open-source components – see the 
[THIRD-PARTY-LICENSES](THIRD-PARTY-LICENSES) file.

Programmed by **[Vít Labuda](https://vitlabuda.cz/)**.
