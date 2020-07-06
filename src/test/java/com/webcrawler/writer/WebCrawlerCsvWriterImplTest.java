package com.webcrawler.writer;

import com.webcrawler.writer_api.WebCrawlerCsvWriter;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.nio.file.Path;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@SpringBootTest
class WebCrawlerCsvWriterImplTest {

    @Autowired
    private WebCrawlerCsvWriter writer;

    @TempDir
    static Path directory;

    @Test
    @Disabled
    void shouldWriteLinksWithTermHitsToFile() {

    }

    @Test
    @Disabled
    void shouldWriteLinksWithTopTenTermHits() {

    }

    private Map<String, List<Integer>> mapOfLinksWithTermsHits() {
        List<Integer> hits = Arrays.asList(1, 2, 3, 6);
        Map<String, List<Integer>> linksWithTermsHits = new HashMap<>();
        linksWithTermsHits.put("https://link/page/1", hits);
        linksWithTermsHits.put("https://link/page/2", hits);
        return linksWithTermsHits;
    }
}