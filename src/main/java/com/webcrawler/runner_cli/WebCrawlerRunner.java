package com.webcrawler.runner_cli;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Component
public class WebCrawlerRunner implements CommandLineRunner {

    private static final Logger log = LoggerFactory
            .getLogger(WebCrawlerRunner.class);

    @Override
    public void run(String... args) throws Exception {
    }
}
