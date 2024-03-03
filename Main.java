package edu.pro;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.*;
import java.util.function.Function;
import java.util.stream.Collectors;

public class Main {

    public static String cleanText(String path) throws IOException {
        return new String(Files.readAllBytes(Paths.get(path)))
                .replaceAll("[^A-Za-z ]", " ")
                .toLowerCase(Locale.ROOT);
    }

    public static Map<String, Long> countWordFrequencies(String[] words) {
        return Arrays.stream(words)
                .filter(word -> !word.isEmpty())
                .collect(Collectors.groupingBy(Function.identity(), Collectors.counting()));
    }

    public static void main(String[] args) throws IOException {
        LocalDateTime start = LocalDateTime.now();

        String content = cleanText("txt/harry.txt");
        String[] words = content.split(" +");

        Map<String, Long> frequencies = countWordFrequencies(words);

        List<Map.Entry<String, Long>> sortedEntries = frequencies.entrySet().stream()
                .sorted(Map.Entry.comparingByValue(Comparator.reverseOrder()))
                .limit(30)
                .collect(Collectors.toList());

        sortedEntries.forEach(entry -> System.out.println(entry.getKey() + " " + entry.getValue()));

        LocalDateTime finish = LocalDateTime.now();
        System.out.println("------");
        System.out.println(ChronoUnit.MILLIS.between(start, finish));
    }
}
