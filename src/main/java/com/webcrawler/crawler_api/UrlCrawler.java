package com.webcrawler.crawler_api;

import java.util.List;
import java.util.Set;

public interface UrlCrawler {

    Set<String> crawlUrl();

    List<Integer> countElementHits(List<String> linkElements);
}
