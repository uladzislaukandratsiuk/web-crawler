package com.webcrawler.crawler;

import com.webcrawler.crawler_api.WebCrawler;
import org.jsoup.Jsoup;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestPropertySource;

import java.io.IOException;
import java.util.*;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@TestPropertySource("classpath:crawler.properties")
class WebCrawlerImplTest {

    private static final String MALFORMED_LINK = "malformed link";
    private static final String INVALID_LINK = "https://github.com/INVALID+LINK";

    @Value("${max.visited.pages:20}")
    private int maxVisitedPages;

    @Autowired
    private WebCrawler crawler;

    @Test
    void shouldReturnSetOfCrawledLinks() {
        Set<String> crawledLinks = crawler.crawlLink();

        assertNotNull(crawledLinks);
        assertTrue(crawledLinks.size() <= maxVisitedPages);
    }

    @Test
    void shouldReturnSetOfCrawledLinksWithDepth() {
        Set<String> linksWithDepth = crawler.crawlLinkWithDepth();

        assertNotNull(linksWithDepth);
        assertTrue(linksWithDepth.size() <= maxVisitedPages);
    }

    @Test
    void whenLinkIsInvalid_shouldThrowIOException() {
        IOException exception =
                assertThrows(IOException.class,
                        () -> Jsoup.connect(INVALID_LINK).get());

        assertEquals("HTTP error fetching URL", exception.getMessage());

        assertTrue(exception.getMessage().contains("HTTP"));
    }

    @Test
    void whenLinkIsMalformed_shouldThrowIllegalArgumentException() {
        IllegalArgumentException exception =
                assertThrows(IllegalArgumentException.class,
                        () -> Jsoup.connect(MALFORMED_LINK).get());

        assertEquals("Malformed URL: " + MALFORMED_LINK, exception.getMessage());

        assertTrue(exception.getMessage().contains("Malformed"));
    }
}