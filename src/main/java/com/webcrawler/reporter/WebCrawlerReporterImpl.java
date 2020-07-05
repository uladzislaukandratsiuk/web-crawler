package com.webcrawler.reporter;

import com.webcrawler.crawler_api.WebCrawler;
import com.webcrawler.reporter_api.WebCrawlerReporter;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;
import java.util.Set;

@Component
public class WebCrawlerReporterImpl implements WebCrawlerReporter {

    private final WebCrawler webCrawler;

    public WebCrawlerReporterImpl(WebCrawler webCrawler) {
        this.webCrawler = webCrawler;
    }

    @Override
    public Map<String, List<Integer>> reportLinkWithElementHits(Set<String> links,
                                                                List<String> linkElements) {
        return null;
    }

    @Override
    public Map<String, List<Integer>> reportTopTenLinkWithElementHits(Set<String> links,
                                                                      List<String> linkElements) {
        return null;
    }
}
