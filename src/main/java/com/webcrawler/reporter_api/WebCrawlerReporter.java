package com.webcrawler.reporter_api;

import java.util.List;
import java.util.Map;

public interface WebCrawlerReporter {

    Map<String, List<Integer>> reportLinkWithElementHits(List<String> linkElements);

    Map<String, List<Integer>> reportLinkWithTopTenElementHits(List<String> linkElements);
}
