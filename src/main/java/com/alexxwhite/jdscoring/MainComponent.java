package com.alexxwhite.jdscoring;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.opencsv.exceptions.CsvValidationException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
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
public class MainComponent {

    @Autowired
    private JDReader jdReader;

    @Autowired
    private TextProcessor textProcessor;

    @Autowired
    private ScoreCalculation scoreCalculation;

    @Autowired
    private SalaryAnalyzer salaryAnalyzer;

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

    public void run() throws CsvValidationException {

        List<EmailDTO> emailDTOS = jdReader.readCSV();
        List<JoblVO> joblVOS = new ArrayList<>();

        for (EmailDTO emailDTO : emailDTOS) {
            JoblVO joblVO = handlerDto(emailDTO, resume);
            joblVOS.add(joblVO);
        }
        joblVOS = joblVOS.stream()
                .sorted(Comparator.comparing(JoblVO::getScore))
                .collect(Collectors.toList());

        joblVOS.stream()
                .map(jd -> jd.getSubject()
                        + " (score " + jd.getScore()
                        + " date " + jd.getDate() + ")")
                .forEach(System.out::println);

    }

    public JoblVO handlerDto(final EmailDTO emailDTO,
                             final String resume) {

        List<String> jobDescList = textProcessor.prepareJD(textProcessor.splitText(emailDTO.getWholeBody()));

        List<String> relevantList = textProcessor.splitText(resume);

        ObjectMapper om = new ObjectMapper();
        JoblVO joblVO = om.convertValue(emailDTO, JoblVO.class);

        Integer score = scoreCalculation.doCalculation(jobDescList, relevantList);
        joblVO.setScore(score);

        Integer salary = salaryAnalyzer.searchSalary(jobDescList);
        joblVO.setSalary(salary);
        return joblVO;
    }



}
