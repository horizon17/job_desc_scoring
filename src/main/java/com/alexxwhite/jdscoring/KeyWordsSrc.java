package com.alexxwhite.jdscoring;

import org.springframework.stereotype.Component;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

/**
 * location
 * salary
 *
 * graph: horiz - score, vert - salary, size - same
 *
 */
@Component
public class KeyWordsSrc {

    final static String irrelevantResumeKeys = "Python Azure C# C++ ";

    final static String resume = " Platforms/Frameworks: Java, aws, JEE, J2EE, Spring Boot, Spring Framework, Quarkus\n" +
            "        Spring Security, OAuth, OpenID, Keycloak\n" +
            "        Hibernate, JPA, JDBC for Oracle, MS SQL, MySQL, PostgreSQL, MongoDB\n" +
            "        REST api, SOAP\n" +
            "        CI/CD: Docker, OpenShift, Kubernetes\n" +
            "        Cloud: Google Cloud, Cloud API, Cloud PubSub, GKE, Cloud Storage BigQuery, Cloud Spanner, BigTable, Cloud Logging, Stackdriver, App Engine)\n" +
            "        Git, Gradle, Maven (including plugin development)\n" +
            "        Kafka, Apache Ignite, React, Node JS\n" +
            "        Deep understanding and use of Object-Oriented, Functional and Aspect-Oriented programming paradigms\n" +
            "        JUnit, Mockito, TDD\n" +
            " Micro services, Containers, K8, Streaming, " +
            "        Linux/Unix";

    public String getResume() {
        return resume;
    }

    public String getIrrelevantResumeKeys() {
        return irrelevantResumeKeys;
    }

}
