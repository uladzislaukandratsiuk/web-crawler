package com.webcrawler.crawler_api;

import java.util.List;
import java.util.Map;
import java.util.Set;

public interface WebCrawler {

    Set<String> crawlLink();

    Map<Integer, Set<String>> crawlLinkWithDepth();

    List<Integer> countElementHits(List<String> linkElements);
}
