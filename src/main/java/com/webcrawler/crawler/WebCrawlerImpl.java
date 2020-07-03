package com.webcrawler.crawler;

import com.webcrawler.crawler_api.WebCrawler;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Component;

import java.util.*;

@Component
@PropertySource("classpath:crawler.properties")
public class WebCrawlerImpl implements WebCrawler {

    @Value("${max.link.depth:1}")
    private int maxLinkDepth;

    @Value("${max.visited.pages:20}")
    private int maxVisitedPages;

    @Value("${link.name:https://github.com/vladkondratuk}")
    private String linkName;

    @Override
    public Set<String> crawlLink() {
        return null;
    }

    @Override
    public List<Integer> countElementHits(List<String> linkElements) {
        return null;
    }
}
