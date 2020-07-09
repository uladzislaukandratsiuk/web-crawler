# Web-crawler csv writer implementation

### WebCrawlerCsvWriter api 

`WebCrawlerCsvWriter` api have 2 methods `writeLinksWithTermHits(List<String> terms)` and 
`writeLinksWithTopTenTermHits(List<String> terms)` that gets list of terms then write statistics
to csv files for all cases and top ten terms total hits.

```java
public interface WebCrawlerCsvWriter {

    void writeLinksWithTermHits(List<String> terms);

    void writeLinksWithTopTenTermHits(List<String> terms);
}
```

### WebCrawlerCsvWriterImpl

WebCrawlerCsvWriterImpl implements WebCrawler api. Using `Apache Commons CSV library` library.

#### Apache Commons CSV library
 
 The Apache Commons CSV library provides a simple interface for reading and writing CSV files of various types.

#### WebCrawlerCsvWriterImpl fields

`WebCrawlerImpl` have a field `reporter`.

- `WebCrawlerReporter reporter` - WebCrawlerReporter api dependency that needed for getting maps reports for all
crawled links and top ten terms total hits terms.
 
#### WebCrawlerCsvWriterImpl methods
 
 `WebCrawlerImpl` have 5 methods:

 - `public void writeLinksWithTermHits(List<String> terms)` - gets list of terms and write to csv file statistics for all 
 crawled links.
 
 - `public void writeLinksWithTopTenTermHits(List<String> terms)` - gets list of terms and write to csv file statistics for
 top ten terms total hits for crawled links.
 
 - `private void invokeCreateCsvFile(Map<String, List<Integer>> linksWithHits, List<String> terms, String fileName)` - 
 gets map of links(key) and list of term hits(value), list of terms, file name then invoke `createCsvFile` method
 
 - `private void createCsvFile(Map<String, List<Integer>> linksWithHits, List<String> terms, String filePath)` - 
 gets map of links(key) and list of term hits(value), list of terms, file name then create csv file with 
 statistics for links and terms hits
 
 - `private String[] getHeaders(List<String> terms)` - gets list of terms and return headers for csv file
 
```java
@Component
public class WebCrawlerCsvWriterImpl implements WebCrawlerCsvWriter {

    private static final Logger log = LoggerFactory
            .getLogger(WebCrawlerCsvWriterImpl.class);

    private final WebCrawlerReporter reporter;

    public WebCrawlerCsvWriterImpl(WebCrawlerReporter reporter) {
        this.reporter = reporter;
    }

    @Override
    public void writeLinksWithTermHits(List<String> terms) {
        //...
    }

    @Override
    public void writeLinksWithTopTenTermHits(List<String> terms) {
        //...
    }

    private void invokeCreateCsvFile(Map<String, List<Integer>> linksWithHits,
                                     List<String> terms, String fileName) {
        //...
    }

    private void createCsvFile(Map<String, List<Integer>> linksWithHits,
                               List<String> terms, String filePath) throws IOException {

        //...
    }

    private String[] getHeaders(List<String> terms) {
        //...
    }
}
``` 

#### public void writeLinksWithTermHits(List<String> terms) - in depth

```java
        @Override
        public void writeLinksWithTermHits(List<String> terms) {
    
            String fileName = "LinksWithTermHits.csv";
    
            /*
                Map<String, List<Integer>> linksWithTermsHits - represents links(key) and list of hits(value)                    
                
                linksWithTermsHits - references to map report for all crawled links   
             */
            Map<String, List<Integer>> linksWithTermsHits =
                    reporter.reportLinksWithTermHits(terms);
    
            invokeCreateCsvFile(linksWithTermsHits, terms, fileName);
        }
```

#### public void writeLinksWithTopTenTermHits(List<String> terms) - in depth

```java
        @Override
        public void writeLinksWithTopTenTermHits(List<String> terms) {
    
            String fileName = "LinksWithTopTenTermHits.csv";
    
            /*
                Map<String, List<Integer>> linksWithTermsHits - represents links(key) and list of hits(value)                    
                
                linksWithTermsHits - references to map report for top ten total terms hits crawled links   
             */
            Map<String, List<Integer>> linksWithTermsHits =
                    reporter.reportLinksWithTermHits(terms);
    
            invokeCreateCsvFile(linksWithTermsHits, terms, fileName);
        }
```

#### private void invokeCreateCsvFile(Map<String, List<Integer>> linksWithHits, List<String> terms, String fileName) - in depth

```java
        private void invokeCreateCsvFile(Map<String, List<Integer>> linksWithHits,
                                     List<String> terms, String fileName) {
        try {
            /* 
                creating file path: statistics - directory, file separator - slash 
                generating for different os, file name - file name with extension
            */ 
            String filePath = "statistics" + File.separator + fileName;

            // invoking createCsvFile() method 
            createCsvFile(linksWithHits, terms, filePath);
        } catch (IOException e) {
            log.error("{}", e.getMessage());
        }
    }
```

#### private void createCsvFile(Map<String, List<Integer>> linksWithHits, List<String> terms, String filePath) - in depth


```java
        private void createCsvFile(Map<String, List<Integer>> linksWithHits,
                               List<String> terms, String filePath) throws IOException {
    
        //Convenience class for writing character files. from java.io
        FileWriter out = new FileWriter(filePath);

        /*
            CSVPrinter - prints values in a CSV format. 
        */
        try (CSVPrinter printer = new CSVPrinter(out, CSVFormat.DEFAULT
                .withHeader(getHeaders(terms)))) {

            /*
                List<String> records - records to print into csv consists of link and each 
                term hit value in string representation
            */
            List<String> records = new ArrayList<>();

            // loop runs foreach value in map of links(key) and list of term hits(value)
            linksWithHits.forEach((link, termHits) -> {

                // add link to records list
                records.add(link);
                // add each term hits from list in string representation
                termHits.forEach(hit -> records.add(hit.toString()));

                try {
                    // prints list of records like one row into csv file
                    printer.printRecords(Collections.singletonList(records));
                } catch (IOException e) {
                    log.error("{}", e.getMessage());
                }

                // clear records 
                records.clear();
            });
        }
    }
```

#### private String[] getHeaders(List<String> terms) - in depth

```java
        private String[] getHeaders(List<String> terms) {
    
        // list of headers for csv file
        List<String> headers = new ArrayList<>();

        if (terms != null && !terms.isEmpty()) {
            headers.add("link");
            headers.addAll(terms);
            headers.add("total");
        }

        return headers.toArray(new String[0]);
    }
```

[Back to README](../README.md)