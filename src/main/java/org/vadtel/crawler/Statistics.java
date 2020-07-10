package org.vadtel.crawler;

import java.io.IOException;
import java.util.Comparator;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.stream.Collectors;

public class Statistics {
    public static final List<StatisticsRecord> RECORDS = new CopyOnWriteArrayList<>();

    public static List<StatisticsRecord> getTenFirst() {

        return RECORDS.stream()
                .sorted(Comparator.comparingInt(StatisticsRecord::getTotalHits)
                        .reversed())
                .limit(10)
                .collect(Collectors.toList());
    }

    public static void writeTenFirst() throws IOException {
        getTenFirst().stream().map(StatisticsRecord::getShortStatistics).forEach(System.out::println);
        CSVWriter.write(getTenFirst(), "out10.csv");
    }


    public static void writeAll() throws IOException {
        CSVWriter.write(RECORDS, "out.csv");
    }

}