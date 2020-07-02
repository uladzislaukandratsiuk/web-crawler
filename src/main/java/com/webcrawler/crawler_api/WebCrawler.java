package com.webcrawler.crawler_api;

import java.util.List;
import java.util.Map;

public interface WebCrawler<K, V> {

    Map<K, List<V>> crawlLink(String linkName);
}
