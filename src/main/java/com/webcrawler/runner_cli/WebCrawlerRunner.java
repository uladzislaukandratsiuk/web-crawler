package com.webcrawler.runner_cli;

import com.webcrawler.writer_api.WebCrawlerCsvWriter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.util.Arrays;
import java.util.List;

@Component
public class WebCrawlerRunner implements CommandLineRunner {

    private static final Logger log = LoggerFactory
            .getLogger(WebCrawlerRunner.class);

    private final WebCrawlerCsvWriter writer;

    public WebCrawlerRunner(WebCrawlerCsvWriter writer) {
        this.writer = writer;
    }

    @Override
    public void run(String... args) throws Exception {

        List<String> terms = Arrays
                .asList("Product", "Web",
                        "Development", "software",
                        "web app", "Full-stack");
        writer.writeLinksWithTermHits(terms);
        writer.writeLinksWithTopTenTermHits(terms);
    }
}
