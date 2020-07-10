package org.vadtel.crawler;

import org.apache.commons.lang3.StringUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.*;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;


public class Crawler {
    private static final Logger logger = LogManager.getLogger(Crawler.class);

    private final int maxDepth;
    private final int maxUrls;
    private Set<URL> masterList = new HashSet<>();
    private List<Future<CrawlerWorker>> futures = new ArrayList<>();
    private ExecutorService executorService = Executors.newWorkStealingPool();
    private URL start;


    public Crawler() throws MalformedURLException {
        this.maxDepth = PropertiesLoader.MAX_DEPTH;
        this.maxUrls = PropertiesLoader.MAX_URL;
        this.start = new URL(PropertiesLoader.START_URL);
    }

    public void go() throws InterruptedException {
        submitNewURL(start, 0);
        while (checkPages());

        logger.info("Found {} urls", masterList.size());
        System.out.println();

    }

    private boolean checkPages() throws InterruptedException {
        Thread.sleep(1000);
        Set<CrawlerWorker> pageSet = new HashSet<>();
        Iterator<Future<CrawlerWorker>> iterator = futures.iterator();

        while (iterator.hasNext()) {
            Future<CrawlerWorker> future = iterator.next();
            if (future.isDone()) {
                iterator.remove();
                try {
                    pageSet.add(future.get());
                } catch (InterruptedException e) {  // skip pages that load too slow
                } catch (ExecutionException e) {
                }
            }
        }

        for (CrawlerWorker crawlerWorker : pageSet) {
            addNewURLs(crawlerWorker);
        }

        return !futures.isEmpty();
    }

    private void addNewURLs(CrawlerWorker crawlerWorker) {
        for (URL url : crawlerWorker.getUrlList()) {
            if (url.toString().contains("#")) {
                try {
                    url = new URL(StringUtils.substringBefore(url.toString(), "#"));
                } catch (MalformedURLException e) {
                }
            }

            submitNewURL(url, crawlerWorker.getDepth() + 1);
        }
    }

    private void submitNewURL(URL url, int depth) {
        if (shouldVisit(url, depth)) {
            masterList.add(url);
            CrawlerWorker crawlerWorker = new CrawlerWorker(url, depth);
            Future<CrawlerWorker> future = executorService.submit(crawlerWorker);
            futures.add(future);
        }
    }

    private boolean shouldVisit(URL url, int depth) {
        if (masterList.contains(url)) {
            return false;
        }

        String[] extn = {".txt", ".doc", ".pdf", ".jpg", ".gif", ".png"};
        if (Arrays.stream(extn).anyMatch(entry -> url.toString().endsWith(entry))) {
            return false;
        }

        if (depth > maxDepth) {
            return false;
        }

        return masterList.size() < maxUrls;
    }


}


