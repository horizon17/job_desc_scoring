package com.alexxwhite.jdscoring;

import org.springframework.beans.factory.annotation.Autowired;
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

    final static int defaultPositiveScore = 10;
    final static int defaultNegativeScore = -10;

    @Autowired
    KeyWordsSrc keyWordsSrc;

    /**
     *
     * @param jobDescList current job vacation
     * @return
     */
    public Integer doCalculation(final List<String> jobDescList) {

        //List<String> relevantList = textProcessor.splitText(resume);
        List<String> relevantList = Arrays.asList(keyWordsSrc.getResume().split(" "));

        // negative
        List<String> irrelevantList = Arrays.asList(keyWordsSrc.getIrrelevantResumeKeys().split(" "));

        HashMap<String, Integer> scoreMap = new HashMap<>();

        // special ++ scores for some keys from relevantList
        scoreMap.put("Java", 25);
        scoreMap.put("Spring Boot", 25);
        scoreMap.put("Kafka", 20);
        scoreMap.put("aws certification", 20);
        scoreMap.put("aws certificate", 20);
        scoreMap.put("AWS Developer Certification", 20);
        scoreMap.put("AWS", 20);
        scoreMap.put("aws", 20);
        scoreMap.put("microservices", 20);
        scoreMap = fillScoreMap(scoreMap, relevantList, defaultPositiveScore);

        // special -- scores
        scoreMap.put("Angular", -50);
        scoreMap.put("Nodejs", -50);
        scoreMap.put("Node.js", -50);
        scoreMap.put("React", -50);
        scoreMap.put("Lead developer", -20);
        scoreMap.put("Azure", -10);
        scoreMap.put("Full stack", -50);
        scoreMap.put("Full stack developer", -50);
        scoreMap.put("Lead Full", -50);
        scoreMap.put("C#", -50);
        scoreMap.put(".Net", -50);
        scoreMap.put("C# .Net Developer", -50);
        scoreMap = fillScoreMap(scoreMap, irrelevantList, defaultNegativeScore);

        return getScore(jobDescList, scoreMap);

    }

    public HashMap<String, Integer> fillScoreMap(HashMap<String, Integer> scoreMap,
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
                            final HashMap<String, Integer> scoreMap) {

        int sum = scoreMap.entrySet().stream()
                .filter(entry -> ifContainAny(jDkeyWords, entry.getKey()))
                .mapToInt(Map.Entry::getValue)
                //.peek(System.out::println)
                .sum();
        System.out.println(sum);
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
