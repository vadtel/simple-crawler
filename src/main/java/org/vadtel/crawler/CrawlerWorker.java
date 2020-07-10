package org.vadtel.crawler;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.HashSet;
import java.util.Set;
import java.util.concurrent.Callable;

public class CrawlerWorker implements Callable<CrawlerWorker> {

    public static final int TIMEOUT = 60000;
    private static final Logger logger = LogManager.getLogger(CrawlerWorker.class);
    private URL url;
    private int depth;
    private Set<URL> urlList = new HashSet<>();

    public CrawlerWorker(URL url, int depth) {
        this.url = url;
        this.depth = depth;
    }

    @Override
    public CrawlerWorker call() {
        logger.info("Visiting ({}): {} ---- {}", depth, url, Thread.currentThread().getName());

        Document doc = null;
        try {
            doc = Jsoup.parse(url, TIMEOUT);
        } catch (IOException e) {

            logger.error("Error parsing {}", url);
        }
        if (doc != null) {
            doc.select("a[href]").forEach(link -> {
                        String href = link.attr("abs:href");
                        if (!href.isBlank() && !href.startsWith("#") && !href.startsWith("javascript")) {
                            try {
                                URL nextUrl = new URL(url, href);
                                urlList.add(nextUrl);
                            } catch (MalformedURLException e) {
                                logger.error("Bad URL: {}", href);
                            }
                        }
                    }
            );
        }

        new TermsStaticticParser(url).collect(doc);

        return this;
    }

    public Set<URL> getUrlList() {
        return urlList;
    }

    public int getDepth() {
        return depth;
    }


}
