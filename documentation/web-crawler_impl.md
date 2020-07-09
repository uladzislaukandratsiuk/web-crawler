# Web-crawler implementation

### WebCrawler api 

`WebCrawler` api have a method `crawlLinkWithDepth()` that returns set of crawled links with depth.

```java
public interface WebCrawler {

    Set<String> crawlLinkWithDepth();
}
```

### WebCrawlerImpl

WebCrawlerImpl implements WebCrawler api. Using `jsoup` library.

#### jsoup: Java HTML Parser
 
 jsoup is a Java library for working with real-world HTML. It provides a very convenient 
 API for fetching URLs and extracting and manipulating data, using the best of HTML5 DOM 
 methods and CSS selectors.

#### WebCrawlerImpl fields

`WebCrawlerImpl` have 4 fields `maxLinkDepth`, `maxVisitedPages`, `rootLink`, `visitedPages`.

 - `int visitedPages` - integer number that represent visited pages in specific moment.

 Predefined fields with values from `crawler.properties` file:      
 - `int maxLinkDepth` - integer number that represent how deep crawler can traverse link. 
 Depth starts with `0(zero)` value that represent a `root.link`, `1`- depth for internals links 
 that was found for `root.link` and so on.
 - `int maxVisitedPages` - integer number that represent how much pages will be visited by a crawler. 
 `Visited pages` counts all visited links. Links can be repeated if, so they will not be 
 traversed but will be counted as `visited`. 
 - `rootLink` - string of characters that represent root link for a crawler to start traversing.
 
#### WebCrawlerImpl methods
 
 `WebCrawlerImpl` have 2 methods `crawlLinkWithDepth`, `getInternalLinks`.

 - `public Set<String> crawlLinkWithDepth()` - implemented method of WebCrawler api that returns 
 set of crawled links with depth.
 
 - `private Set<String> getInternalLinks(String linkName)` - method that returns set of internal links 
 for a specific link(page).
 
```java
@Component
@PropertySource("classpath:crawler.properties")
public class WebCrawlerImpl implements WebCrawler {

    
    @Value("${max.link.depth:1}")
    private int maxLinkDepth;

    @Value("${max.visited.pages:15}")
    private int maxVisitedPages;

    @Value("${root.link:https://github.com/vladkondratuk}")
    private String rootLink;

    private int visitedPages = 0;

    @Override
    public Set<String> crawlLinkWithDepth() {

        //...
    }

    private Set<String> getInternalLinks(String linkName) {

        //...
    }
}
```

#### public Set<String> crawlLinkWithDepth() - in depth

```java
        @Override
        public Set<String> crawlLinkWithDepth() {
        
        /*
            String currentLink references to predefined link value  
        */
        String currentLink = rootLink;
        int currentLinkDepth = 0;

        /*
            Set<String> crawledLinksWithDepth - represent set of crawled links with 
            depth that less than max depth
        */
        Set<String> crawledLinksWithDepth = new HashSet<>();

        while (currentLinkDepth < maxLinkDepth) {

            /*
                Set<String> crawledLinks - represents set of links for current depth     
                
                crawledLinks - references to set of internal links for specific link(page)  
            */
            Set<String> crawledLinks = getInternalLinks(currentLink);

            /*
                crawledLinksWithDepth.addAll(crawledLinks) - adding set of links for current depth
                to crawledLinksWithDepth set for all depth levels sets that scanned or will be scanned      
            */
            crawledLinksWithDepth.addAll(crawledLinks);

            currentLinkDepth++;

            /*
                loop foreach for link in set of crawledLinks in concrete depth level
            */
            for (String crawledLink : crawledLinks) {

                currentLink = crawledLink;

                /*
                    crawledLinks - references to set of internal links for specific link(page)
                */
                crawledLinks = getInternalLinks(currentLink);

                /*
                    crawledLinksWithDepth.addAll(crawledLinks) - adding set of links for current depth
                    to crawledLinksWithDepth set for all depth levels sets that scanned or will be scanned  
                */
                crawledLinksWithDepth.addAll(crawledLinks);
            }
        }

        visitedPages = 0;

        return crawledLinksWithDepth;
    }
```

#### private Set<String> getInternalLinks(String linkName) - in depth

```java
        private Set<String> getInternalLinks(String linkName) {

        /*
            Set<String> links - represent set of internal links for specific link(page).
        */
        Set<String> links = new HashSet<>();

        /*
            Stack<String> linksToVisit - represent stack represents all internal link 
            for specific link(page).
        */
        Stack<String> linksToVisit = new Stack<>();

        linksToVisit.push(linkName);

        while (!linksToVisit.isEmpty()) {

            String current = linksToVisit.pop();
            visitedPages++;

            if (!links.contains(current) && visitedPages < maxVisitedPages) {
                try { 
                    
                    /*
                        adding current unique link to set
                    */             
                    links.add(current);

                    /*
                        Document document - represent specific html page

                        Jsoup.connect(linkName).get() - jsoup connects to link and gets html page    
                    */
                    Document document = Jsoup.connect(linkName).get();

                    /*
                        Elements internalLinks - implements ArrayList<> and represents
                        list of elements from html page

                        document.select("a[href]") - selects link on html page and adds
                        them to internalLinks list 
                    */    
                    Elements internalLinks = document.select("a[href]");

                    /*
                        internalLinks.stream() - gets all absolute links and pushes
                        them into linksToVisit stack
                    */
                    internalLinks.stream()
                            .map(link -> link.attr("abs:href"))
                            .forEach(linksToVisit::push);

                } catch (IOException | IllegalArgumentException e) {
                    log.error("{}, {}", e.getMessage(), linkName);
                }
            }
        }

        return links;
    }
```

[Back to README](../README.md)