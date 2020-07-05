package com.webcrawler.runner_cli;

import com.webcrawler.crawler_api.WebCrawler;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.util.Arrays;
import java.util.List;
import java.util.Set;

@Component
public class WebCrawlerRunner implements CommandLineRunner {

    private static final Logger log = LoggerFactory
            .getLogger(WebCrawlerRunner.class);

    private final WebCrawler webCrawler;

    public WebCrawlerRunner(WebCrawler webCrawler) {
        this.webCrawler = webCrawler;
    }

    @Override
    public void run(String... args) throws Exception {

        List<String> terms = Arrays.asList("Product", "Web", "Development", "software");
        Set<String> links = webCrawler.crawlLink();
        webCrawler.crawlLinkWithDepth();
        webCrawler.countLinkElementHits(links, terms);
    }
}
