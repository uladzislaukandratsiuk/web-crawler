package com.webcrawler.crawler;

import com.webcrawler.crawler_api.WebCrawler;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.*;

@Component
@PropertySource("classpath:crawler.properties")
public class WebCrawlerImpl implements WebCrawler {

    private final Logger log = LoggerFactory.getLogger(WebCrawlerImpl.class);

    @Value("${max.link.depth:1}")
    private int maxLinkDepth;

    @Value("${max.visited.pages:15}")
    private int maxVisitedPages;

    @Value("${link.name:https://github.com/vladkondratuk}")
    private String linkName;

    @Override
    public Set<String> crawl() {

        Set<String> links = new HashSet<>();
        Stack<String> linksToVisit = new Stack<>();

        String current;

        linksToVisit.push(linkName);

        while (!linksToVisit.isEmpty()) {

            current = linksToVisit.pop();

            if (!links.contains(current) && links.size() < maxVisitedPages) {
                try {
                    if (links.add(current)) {
                        log.info("visited {}, current {}", links.size(), current);
                    }

                    Document document = Jsoup.connect(linkName).get();
                    Elements internalLinks = document.select("a[href]");

                    for (Element link : internalLinks) {
                        linksToVisit.push(link.attr("abs:href"));
                    }

                } catch (IOException e) {
                   log.error("{}", e.getMessage());
                }
            }
        }

        return links;
    }

    @Override
    public List<Integer> countElementHits(List<String> linkElements) {
        return null;
    }
}
