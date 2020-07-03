package com.webcrawler.crawler;

import com.webcrawler.crawler_api.WebCrawler;
import org.jsoup.Jsoup;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestPropertySource;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@TestPropertySource("classpath:crawler.properties")
class WebCrawlerImplTest {

    @Value("${max.visited.pages:20}")
    private int maxVisitedPages;

    private static final String INVALID_LINK = "https://github.com/vladkondratuk/INVALID+LINK";

    private static final int LINKS_AMOUNT = 20;

    @Autowired
    private WebCrawler crawler;

    @Test
    void shouldReturnSetOfCrawledLinks() {
        Set<String> crawledLinks = crawler.crawl();

        assertNotNull(crawledLinks);
        assertTrue(crawledLinks.size() <= maxVisitedPages);
    }

    @Test
    void whenLinkIsInvalid_shouldThrowIOException() {
        IOException exception = assertThrows(IOException.class, () -> {
            Jsoup.connect(INVALID_LINK).get();
        });

        assertEquals("HTTP error fetching URL", exception.getMessage());

        assertTrue(exception.getMessage().contains("HTTP"));
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