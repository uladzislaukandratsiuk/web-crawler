package com.webcrawler.writer;

import com.webcrawler.reporter_api.WebCrawlerReporter;
import com.webcrawler.writer_api.WebCrawlerCsvWriter;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class WebCrawlerCsvWriterImpl implements WebCrawlerCsvWriter {

    private final WebCrawlerReporter reporter;

    public WebCrawlerCsvWriterImpl(WebCrawlerReporter reporter) {
        this.reporter = reporter;
    }

    @Override
    public void writeLinksWithTermHits(List<String> terms) {

    }

    @Override
    public void writeLinksWithTopTenTermHits(List<String> terms) {

    }
}
