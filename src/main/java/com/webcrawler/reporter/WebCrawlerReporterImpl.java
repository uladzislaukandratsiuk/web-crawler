package com.webcrawler.reporter;

import com.webcrawler.crawler_api.WebCrawler;
import com.webcrawler.reporter_api.WebCrawlerReporter;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.*;

import static java.util.stream.Collectors.toMap;

@Component
public class WebCrawlerReporterImpl implements WebCrawlerReporter {

    private final Logger log = LoggerFactory.getLogger(WebCrawlerReporterImpl.class);

    private final WebCrawler webCrawler;

    public WebCrawlerReporterImpl(WebCrawler webCrawler) {
        this.webCrawler = webCrawler;
    }

    @Override
    public Map<String, List<Integer>> reportLinksWithTermHits(List<String> linkTerms) {
        return getLinksWithTermHits(linkTerms);
    }

    @Override
    public Map<String, List<Integer>> reportLinksWithTopTenTermHits(List<String> linkTerms) {
        return getLinksWithTopTenTermHits(linkTerms);
    }

    private Map<String, List<Integer>> getLinksWithTermHits(List<String> linkTerms) {

        Map<String, List<Integer>> linkTermHits = new HashMap<>();
        Set<String> links = webCrawler.crawlLinkWithDepth();

        for (String link : links) {
            try {
                List<Integer> listOfTermHits = new ArrayList<>();
                List<String> textStrings = new ArrayList<>();

                int totalHits;
                int termHits;

                for (String term : linkTerms) {

                    Document document = Jsoup.connect(link).get();
                    Elements pageTerms = document.getElementsContainingOwnText(term);

                    pageTerms.forEach(pageTerm -> textStrings.add(pageTerm.text()));

                    termHits = countTermHits(textStrings, term);

                    listOfTermHits.add(termHits);
                }

                totalHits = countTotalHits(listOfTermHits);

                listOfTermHits.add(totalHits);
                linkTermHits.put(link, listOfTermHits);

            } catch (IOException | IllegalArgumentException e) {
                log.error("{}, {}", e.getMessage(), link);
            }
        }

        return linkTermHits;
    }

    private int countTermHits(List<String> textStrings, String term) {
        return (int) textStrings.stream()
                .filter(text -> text
                        .matches(".*\\b(\\w*" + term + "\\w*)\\b.*"))
                .count();
    }

    private int countTotalHits(List<Integer> listOfTermHits) {
        return listOfTermHits.stream().mapToInt(hits -> hits).sum();
    }

    private Map<String, List<Integer>> getLinksWithTopTenTermHits(List<String> linkTerms) {

        Map<String, List<Integer>> linksAndHits =
                getLinksWithTermHits(linkTerms);

        linksAndHits = linksAndHits
                .entrySet()
                .stream()
                .sorted((hit1, hit2) ->
                        (hit2.getValue().get(hit2.getValue().size() - 1))
                                .compareTo((hit1.getValue().get(hit1.getValue().size() - 1))))
                .limit(10)
                .collect(toMap(Map.Entry::getKey, Map.Entry::getValue,
                        (k, v) -> v, LinkedHashMap::new));

        log.info("Top Ten Links With Terms Hits:");
        linksAndHits.forEach((link, termHits) ->
                log.info("{} {}", link, termHits));

        return linksAndHits;
    }
}
