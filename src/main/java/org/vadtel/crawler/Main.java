package org.vadtel.crawler;

import java.io.IOException;

public class Main {


    public static void main(String[] args) throws IOException, InterruptedException {
        new Crawler().go();
        Statistics.writeAll();
        Statistics.writeTenFirst();
    }
}
