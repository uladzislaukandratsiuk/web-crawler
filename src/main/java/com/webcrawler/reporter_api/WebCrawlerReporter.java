package com.webcrawler.reporter_api;

import java.util.List;
import java.util.Map;

public interface WebCrawlerReporter {

    Map<String, List<Integer>> reportLinksWithElementHits(List<String> linkElements);

    Map<String, List<Integer>> reportLinksWithTopTenElementHits(List<String> linkElements);
}
