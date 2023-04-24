package com.alexxwhite.jdscoring;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.opencsv.exceptions.CsvValidationException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.core.io.ResourceLoader;
import org.springframework.stereotype.Component;

import java.io.FileWriter;
import java.io.IOException;
import java.net.URISyntaxException;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Path;
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

    @Autowired
    ResourceLoader resourceLoader;

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

    public void run() throws CsvValidationException, IOException, URISyntaxException {

        List<EmailDTO> emailDTOS = jdReader.readCSV();
        List<JoblVO> joblVOS = new ArrayList<>();

        for (EmailDTO emailDTO : emailDTOS) {
            JoblVO joblVO = handlerDto(emailDTO, resume);
            joblVOS.add(joblVO);
        }
        joblVOS = joblVOS.stream()
                .sorted(Comparator.comparing(JoblVO::getScore))
                .collect(Collectors.toList());

        //  { x: 63.4, y: 51.8, z: 15.4, name: 'PT', country: 'Portugal' },
        //  { x: 64, y: 82.9, z: 31.3, name: 'NZ', country: 'New Zealand' }
        int i = 0;
        StringBuilder sb = new StringBuilder();
        for (JoblVO joblVO : joblVOS) {
            sb.append("{ x: " + joblVO.getScore() + ", "
                    + "y: " + joblVO.getSalary() + ", "
                    + "z: 30" + ", "
                    + " name: " + "'JD' " + ", "  // joblVO.getSubject()
                    + " country: " + " 'location CA'" + "}" + "\n");
            if (i != joblVOS.size() - 1) {
                sb.append(",");
            }
        }

        Resource resource = resourceLoader.getResource("classpath:static/index.html");
        if (resource.exists()) {
            URL url = resource.getURL();
            List<String> fileRowsList = getFromFile(url);

//            fileRowsList = fileRowsList.stream()
//                    .peek(r->r.replace("replace_me", sb.toString()))
//                    .collect(Collectors.toList());


            String fileName = "c:\\temp\\index.html";

            try (FileWriter writer = new FileWriter(fileName)) {
                for (String line : fileRowsList) {
                    if (line.contains("replace_me")) {
                        writer.write(sb.toString() + System.lineSeparator());
                        continue;
                    }
                    writer.write(line + System.lineSeparator());
                }
            } catch (IOException e) {
                System.err.format("IOException: %s%n", e);
            }
        }

        joblVOS.stream()
                .map(jd -> jd.getSubject()
                        + " (score " + jd.getScore()
                        + " salary ================ " + jd.getSalary()
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

        Integer salary = salaryAnalyzer.searchSalary(emailDTO.getWholeBody());
        joblVO.setSalary(salary);
        return joblVO;
    }

    private List<String> getFromFile(URL url) throws IOException, URISyntaxException {

        return  Files.readAllLines(Path.of(url.toURI()));

    }



}
