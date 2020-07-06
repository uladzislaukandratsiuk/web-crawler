package com.webcrawler.reporter_api;

import java.util.List;
import java.util.Map;

public interface WebCrawlerReporter {

    Map<String, List<Integer>> reportLinksWithTermHits(List<String> linkTerms);

    Map<String, List<Integer>> reportLinksWithTopTenTermHits(List<String> linkTerms);
}
