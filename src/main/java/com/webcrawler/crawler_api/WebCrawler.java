package com.webcrawler.crawler_api;

import java.util.List;
import java.util.Set;

public interface WebCrawler {

    Set<String> crawlLink();

    List<Integer> countElementHits(List<String> linkElements);
}
