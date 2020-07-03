package com.webcrawler.crawler;

import com.webcrawler.crawler_api.WebCrawler;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class WebCrawlerImplTest {

    private static final int LINKS_AMOUNT = 3;

    @Autowired
    private WebCrawler crawler;

    @Test
    @Disabled
    void shouldReturnSetOfCrawledLinks() {
        Set<String> crawledLinks = crawler.crawlLink();

        assertNotNull(crawledLinks);
        assertEquals(LINKS_AMOUNT, crawledLinks.size());
    }

    @Test
    @Disabled
    void shouldReturnListOfElementHits() {
        List<Integer> elementHits = crawler.countElementHits(listOfTerms());

        assertNotNull(elementHits);
        assertEquals(LINKS_AMOUNT, elementHits.size());
    }

    private Set<String> setOfCrawledLinks() {
        Set<String> crawledLinks = new HashSet<>();
        for (int i = 0; i < LINKS_AMOUNT; i++) {
            crawledLinks.add("https://github.com/page/" + i);
        }
        return crawledLinks;
    }

    private List<String> listOfTerms() {
        List<String> terms = new ArrayList<>();
        terms.add("Java");
        terms.add("Spring");
        return terms;
    }
}