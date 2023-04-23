package com.alexxwhite.jdscoring;

import org.springframework.stereotype.Component;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * steps:
 *  1. read JD, get uniq words
 *  2. get scores and calculate
 *  3. build chart
 */

@Component
public class ScoreCalculation {

    final static String irrelevantResumeKeys = "Python Azure C# C++ ";

    final static int defaultPositiveScore = 10;
    final static int defaultNegativeScore = 10;


    public Integer doCalculation(final List<String> jobDescList,
                              final List<String> relevantList) {

        HashMap<String, Integer> scoreMap = new HashMap<>();

        // special ++ scores
        scoreMap.put("Java", 25);
        scoreMap.put("Spring Boot", 25);
        scoreMap.put("Kafka", 20);
        scoreMap = fillScoreMap(scoreMap, relevantList, defaultPositiveScore);


        // negative
        List<String> irrelevantList = Arrays.asList(irrelevantResumeKeys.split(" "));

        // special -- scores
        scoreMap.put("Angular", -20);
        scoreMap.put("aws certification", -20);
        scoreMap.put("aws certificate", -20);
        scoreMap.put("AWS Developer Certification", -20);
        scoreMap = fillScoreMap(scoreMap, irrelevantList, defaultNegativeScore);

        // locations:
        //scoreMap.put("Java", 25);

        return getScore(jobDescList, scoreMap);

    }

    private HashMap<String, Integer> fillScoreMap(HashMap<String, Integer> scoreMap,
                                                    final List<String> keyList,
                                                    final int defaultScore) {
        for (String nextKey : keyList) {
            if (scoreMap.containsKey(nextKey)) {
                continue;
            }
            scoreMap.put(nextKey, defaultScore);
        }
        return scoreMap;
    }

    public Integer getScore(final List<String> jDkeyWords,
                            final HashMap<String, Integer> positiveScore) {

        int sum = positiveScore.entrySet().stream()
                .filter(entry -> ifContainAny(jDkeyWords, entry.getKey()))
                .mapToInt(Map.Entry::getValue)
                .sum();

        return sum;
    }

    private boolean ifContainAny(final List<String> jDkeyWords,
                                 final String key) {
        for (String nextJd : jDkeyWords) {
            if (nextJd.contains(key) || key.contains(nextJd)) {
                return true;
            }
        }
        return false;
    }



}
