package com.webcrawler.crawler_api;

import java.util.Set;

public interface WebCrawler {

    Set<String> crawlLink();

    Set<String> crawlLinkWithDepth();
}
