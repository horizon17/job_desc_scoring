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

    final static String jobDesc = "";

    final static String resume = " Platforms/Frameworks: Java, JEE/J2EE, Spring/Boot Framework, Quarkus\n" +
            "        Security: Spring Security, OAuth, OpenID, Keycloak\n" +
            "        DB: Hibernate, JPA, JDBC for Oracle, MS SQL, MySQL, PostgreSQL, MongoDB\n" +
            "        API: REST, SOAP\n" +
            "        CI/CD: Docker, OpenShift, Kubernetes\n" +
            "        Cloud: AWS, Google Cloud (including Cloud API, Cloud PubSub, GKE, Cloud Storage BigQuery, Cloud Spanner, BigTable, Cloud Logging, Stackdriver, App Engine)\n" +
            "        Git, Gradle, Maven (including plugin development)\n" +
            "        Kafka, Apache Ignite, React, Node JS\n" +
            "        Deep understanding and use of Object-Oriented, Functional and Aspect-Oriented programming paradigms\n" +
            "        JUnit, Mockito, TDD\n" +
            "        Linux/Unix";

    final static String irrelevantResumeKeys = "Python Azure C# C++ ";

    final static int defaultPositiveScore = 10;
    final static int defaultNegativeScore = 10;


    public Integer doCalculation(final String jobDesc,
                              final String parResume) {

        List<String> jobDescList = prepareJD(splitText(jobDesc));

        HashMap<String, Integer> scoreMap = new HashMap<>();

        // positive
        List<String> relevantList = splitText(resume);
        if (parResume != null) {
            relevantList = splitText(parResume);
        }

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

    private List<String> splitText(final String bitText) {
        return Arrays.asList(bitText.split("\\s*[/,;:.\n]\\s*"));
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

    private List<String> prepareJD(List<String> jdSource) {

        jdSource = jdSource.stream()
                .map(r->r.replace("(", ""))
                .map(r->r.replace(")", ""))
                .collect(Collectors.toList());

        jdSource.removeIf(e->e.isEmpty() || e == null || e.length() == 1);

        return jdSource;
    }


}
