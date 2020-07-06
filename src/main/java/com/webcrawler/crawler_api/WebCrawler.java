package com.webcrawler.crawler_api;

import java.util.List;
import java.util.Map;
import java.util.Set;

public interface WebCrawler {

    Set<String> crawlLink();

    Set<String> crawlLinkWithDepth();

    Map<String, List<Integer>> countLinkElementHits(Set<String> links,
                                                    List<String> linkElements);
}
