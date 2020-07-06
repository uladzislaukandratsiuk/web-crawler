package com.webcrawler.writer_api;

import java.util.List;

public interface WebCrawlerCsvWriter {

    void writeLinksWithTermHits(List<String> terms);

    void writeLinksWithTopTenTermHits(List<String> terms);
}
