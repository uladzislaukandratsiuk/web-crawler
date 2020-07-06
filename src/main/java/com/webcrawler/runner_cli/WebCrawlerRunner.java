package com.webcrawler.runner_cli;

import com.webcrawler.reporter_api.WebCrawlerReporter;
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

    private final WebCrawlerReporter reporter;

    public WebCrawlerRunner(WebCrawlerReporter reporter) {
        this.reporter = reporter;
    }

    @Override
    public void run(String... args) throws Exception {

        List<String> terms = Arrays.asList("Product", "Web", "Development", "software", "Full-stack");
        reporter.reportLinksWithElementHits(terms);
        reporter.reportLinksWithTopTenElementHits(terms);
    }
}
