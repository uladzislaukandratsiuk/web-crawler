package com.webcrawler.runner_cli;

import com.webcrawler.writer_api.WebCrawlerCsvWriter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@PropertySource("classpath:crawler.properties")
public class WebCrawlerRunner implements CommandLineRunner {

    @Value("${terms.to.find:default,terms,list}")
    private List<String> terms;

    private final WebCrawlerCsvWriter writer;

    public WebCrawlerRunner(WebCrawlerCsvWriter writer) {
        this.writer = writer;
    }

    @Override
    public void run(String... args) throws Exception {
        writer.writeLinksWithTermHits(terms);
        writer.writeLinksWithTopTenTermHits(terms);
    }
}
