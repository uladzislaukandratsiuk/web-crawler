package com.webcrawler.reporter;

import com.webcrawler.reporter_api.WebCrawlerReporter;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestPropertySource;

import java.util.*;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@TestPropertySource("classpath:crawler.properties")
class WebCrawlerReporterImplTest {

    private static final int LINKS_AMOUNT = 3;

    @Value("${max.visited.pages:20}")
    private int maxVisitedPages;

    @Autowired
    private WebCrawlerReporter reporter;

    @Test
    @Disabled
    void shouldReturnMapOfLinkWithElementHits() {
        Map<String, List<Integer>> linkWithHits = reporter
                .reportLinkWithElementHits(setOfCrawledLinks(), listOfTerms());

        assertNotNull(linkWithHits);
        assertTrue(linkWithHits.size() <= maxVisitedPages);
    }

    @Test
    @Disabled
    void shouldReturnMapOfTopTenLinkWithElementHits() {
        Map<String, List<Integer>> linkWithHits = reporter
                .reportTopTenLinkWithElementHits(setOfCrawledLinks(), listOfTerms());

        assertNotNull(linkWithHits);
        assertTrue(linkWithHits.size() <= maxVisitedPages);
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
        terms.add("Web development");
        return terms;
    }
}