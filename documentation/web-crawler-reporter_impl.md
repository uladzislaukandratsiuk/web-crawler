# Web-crawler reporter implementation

### WebCrawlerReporter api 

`WebCrawlerReporter` api have 2 methods: 

   - `reportLinksWithTermHits()` - that returns for all crawled links map of 
   links(key) and list with term hits(value).
   
   - `reportLinksWithTopTenTermHits()` - that returns for top ten by terms total hit crawled links map of 
      links(key) and list with term hits(value).

```java
public interface WebCrawlerReporter {

    Map<String, List<Integer>> reportLinksWithTermHits(List<String> linkTerms);

    Map<String, List<Integer>> reportLinksWithTopTenTermHits(List<String> linkTerms);
}
```

### WebCrawlerReporterImpl

#### WebCrawlerReporterImpl fields

`WebCrawlerImpl` have a field `webCrawler`.

 - `WebCrawler webCrawler` - WebCrawler api dependency that needed for getting set of crawled links.
 
#### WebCrawlerReporterImpl methods
 
 `WebCrawlerReporterImpl` have 6 methods:

 - `public Map<String, List<Integer>> reportLinksWithTermHits(List<String> linkTerms)` - implemented method of 
 WebCrawlerReporter api that gets list of terms and returns for all crawled links map of links(key) and 
 list with term hits(value).
                           
 - `public Map<String, List<Integer>> reportLinksWithTopTenTermHits(List<String> linkTerms)` - implemented method of 
 WebCrawlerReporter api that gets lists of terms returns for top ten by terms total hit crawled links map 
 of links(key) and list with term hits(value).

 - `private Map<String, List<Integer>> getLinksWithTermHits(List<String> linkTerms)` - method that gets list of terms and 
 returns for all crawled links map of links(key) and list with term hits(value).
 
 - `private int countTermHits(List<String> textStrings, String term)` - method that gets list of text string and term
 then count and returns integer number that represents a term hits
 
 - `private int countTotalHits(List<Integer> listOfTermHits)` - method that get list of term hits then counts
 and returns integer number that represents total terms hits
 
 - `private Map<String, List<Integer>> getLinksWithTopTenTermHits(List<String> linkTerms)` - method that get 
 list of terms and returns top ten by terms total hit crawled links map of links(key) and list with term hits(value) 

```java
@Component
public class WebCrawlerReporterImpl implements WebCrawlerReporter {

    private final Logger log = LoggerFactory.getLogger(WebCrawlerReporterImpl.class);

    private final WebCrawler webCrawler;

    public WebCrawlerReporterImpl(WebCrawler webCrawler) {
        this.webCrawler = webCrawler;
    }

    @Override
    public Map<String, List<Integer>> reportLinksWithTermHits(List<String> linkTerms) {
        //...
    }

    @Override
    public Map<String, List<Integer>> reportLinksWithTopTenTermHits(List<String> linkTerms) {
        //...
    }

    private Map<String, List<Integer>> getLinksWithTermHits(List<String> linkTerms) {
        //...
    }

    private int countTermHits(List<String> textStrings, String term) {
        //...
    }

    private int countTotalHits(List<Integer> listOfTermHits) {
        //...
    }

    private Map<String, List<Integer>> getLinksWithTopTenTermHits(List<String> linkTerms) {
        //...
    }
}
```

#### public Map<String, List<Integer>> reportLinksWithTermHits(List<String> linkTerms) - in depth

```java
     @Override
        public Map<String, List<Integer>> reportLinksWithTermHits(List<String> linkTerms) {
            // invoking getLinksWithTermHits method
            return getLinksWithTermHits(linkTerms);
     }
```

#### public Map<String, List<Integer>> reportLinksWithTopTenTermHits(List<String> linkTerms) - in depth

```java
    @Override
    public Map<String, List<Integer>> reportLinksWithTopTenTermHits(List<String> linkTerms) {
        // invoking getLinksWithTopTenTermHits method
        return getLinksWithTopTenTermHits(linkTerms);
    }
```

#### private Map<String, List<Integer>> getLinksWithTermHits(List<String> linkTerms) - in depth

Using `jsoup` library to implement WebCrawlerReporterImpl getLinksWithTermHits method. 

##### jsoup: Java HTML Parser
 
 jsoup is a Java library for working with real-world HTML. It provides a very convenient 
 API for fetching URLs and extracting and manipulating data, using the best of HTML5 DOM 
 methods and CSS selectors.

```java
        private Map<String, List<Integer>> getLinksWithTermHits(List<String> linkTerms) {

        /*
           Map<String, List<Integer>> linkTermHits - represents links(key) and list of hits(value)       
        */
        Map<String, List<Integer>> linkTermHits = new HashMap<>();

        /*
            Set<String> links - gets reference to set of crawled links with depth
        */
        Set<String> links = webCrawler.crawlLinkWithDepth();

        for (String link : links) {
            try {
                /*
                    List<Integer> listOfTermHits - list of hits for terms
                */
                List<Integer> listOfTermHits = new ArrayList<>();

                /*
                    List<String> textStrings - list of text strings from html page
                */
                List<String> textStrings = new ArrayList<>();

                int totalHits;
                int termHits;

                for (String term : linkTerms) {

                    /*
                        Document document - represent specific html page

                        Jsoup.connect(linkName).get() - jsoup connects to link and gets html page    
                    */
                    Document document = Jsoup.connect(link).get();

                    /*
                        Elements internalLinks - implements ArrayList<> and represents
                        list of elements from html page

                        document.getElementsContainingOwnText(term) - selects element on html page 
                        that contains specific term
                    */
                    Elements pageTerms = document.getElementsContainingOwnText(term);

                    // adding pageTerm text from element to textString list
                    pageTerms.forEach(pageTerm -> textStrings.add(pageTerm.text()));

                    /*
                        invoke countTermHits(textStrings, term) takes list of text string and term then 
                        count and return integer number that represents term hits for all text stings
                    */
                    termHits = countTermHits(textStrings, term);

                    // adding counted term hits to list of term hits
                    listOfTermHits.add(termHits);
                }

                /*
                    invoke countTotalHits(listOfTermHits) takes list of term hits then 
                    count and return integer number that represents total term hits
                */
                totalHits = countTotalHits(listOfTermHits);

                // adding counted total term hits to list of term hits
                listOfTermHits.add(totalHits);

                // putting link(key) and list of counted term hits(value)
                linkTermHits.put(link, listOfTermHits);

            } catch (IOException | IllegalArgumentException e) {
                log.error("{}, {}", e.getMessage(), link);
            }
        }

        return linkTermHits;
    }
```

#### private int countTermHits(List<String> textStrings, String term) - in depth

```java
    private int countTermHits(List<String> textStrings, String term) {
        
        /*
            textStrings.stream() - scan list of text stings and counts matches
            for a word or phrase(term) 
        */
        return (int) textStrings.stream()
                .filter(text -> text
                        .matches(".*\\b(\\w*" + term + "\\w*)\\b.*"))
                .count();
    }
```

#### private int countTotalHits(List<Integer> listOfTermHits) - in depth


```java
    private int countTotalHits(List<Integer> listOfTermHits) {
    
        /*
            listOfTermHits.stream() - scan list of term hits and counts
            sum of all list values 
        */
        return listOfTermHits.stream().mapToInt(hits -> hits).sum();
    }
```

#### private Map<String, List<Integer>> getLinksWithTopTenTermHits(List<String> linkTerms) - in depth

```java
        private Map<String, List<Integer>> getLinksWithTopTenTermHits(List<String> linkTerms) {

        /*
            Map<String, List<Integer>> linksAndHits - map of links(key) and list with term hits(value)
            by top ten by terms total hit crawled links
        */
        Map<String, List<Integer>> linksAndHits =
                getLinksWithTermHits(linkTerms);


        /*
            linksAndHits.entrySet().stream() - gets sorted top ten total hits 
            LinkedHashMap<String, List<Integer>> linked map with links(key) and list with term hits(value)
        */
        linksAndHits = linksAndHits
                .entrySet()
                .stream()
                .sorted((hit1, hit2) ->
                        (hit2.getValue().get(hit2.getValue().size() - 1))
                                .compareTo((hit1.getValue().get(hit1.getValue().size() - 1))))
                .limit(10)
                .collect(toMap(Map.Entry::getKey, Map.Entry::getValue,
                        (k, v) -> v, LinkedHashMap::new));
        
        /*
            linksAndHits.forEach((link, termHits) - get each value 
            and prints to cli log.info("\n{} {}", link, termHits)) 
        */
        log.info("\nTop Ten Links With Terms Hits:");
        linksAndHits.forEach((link, termHits) ->
                log.info("\n{} {}", link, termHits));

        return linksAndHits;
    }
```

[Back to README](../README.md)