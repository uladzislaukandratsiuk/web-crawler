package com.webcrawler.reporter_api;

import java.util.List;
import java.util.Map;
import java.util.Set;

public interface WebCrawlerReporter {

    Map<String, List<Integer>> reportLinkWithElementHits(Set<String> links,
                                                         List<String> linkElements);

    Map<String, List<Integer>> reportTopTenLinkWithElementHits(Set<String> links,
                                                               List<String> linkElements);
}
