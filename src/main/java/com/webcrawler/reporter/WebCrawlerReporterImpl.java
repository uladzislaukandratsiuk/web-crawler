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

@Component
public class WebCrawlerReporterImpl implements WebCrawlerReporter {

    private final Logger log = LoggerFactory.getLogger(WebCrawlerReporterImpl.class);

    private final WebCrawler webCrawler;

    public WebCrawlerReporterImpl(WebCrawler webCrawler) {
        this.webCrawler = webCrawler;
    }

    @Override
    public Map<String, List<Integer>> reportLinkWithElementHits(List<String> linkElements) {
        return countLinkElementHits(linkElements);
    }

    @Override
    public Map<String, List<Integer>> reportLinkWithTopTenElementHits(List<String> linkElements) {
        return null;
    }

    private Map<String, List<Integer>> countLinkElementHits(List<String> linkElements) {

        Map<String, List<Integer>> linkElementHits = new HashMap<>();
        Set<String> links = webCrawler.crawlLinkWithDepth();

        for (String link : links) {

            int totalHits;
            int termHits = 0;

            List<Integer> elementHits = new ArrayList<>();

            try {
                List<String> textStrings = new ArrayList<>();

                for (String element : linkElements) {

                    Document document = Jsoup.connect(link).get();
                    Elements pageElements = document.getElementsContainingOwnText(element);

                    pageElements.forEach(pageElement -> textStrings.add(pageElement.text()));

                    termHits += textStrings.stream()
                            .filter(text ->
                                    text.matches(".*\\b(\\w*" + element + "\\w*)\\b.*"))
                            .count();

                    elementHits.add(termHits);
                    termHits = 0;
                }

            } catch (IOException | IllegalArgumentException e) {
                log.error("{}", e.getMessage());
            }

            totalHits = elementHits.stream().mapToInt(hits -> hits).sum();
            elementHits.add(totalHits);
            linkElementHits.put(link, elementHits);
        }

        linkElementHits.forEach((link, elementHits) ->
                log.info("{} {}", link, elementHits));

        return linkElementHits;
    }
}
