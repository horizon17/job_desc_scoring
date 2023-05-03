package com.alexxwhite.jdscoring;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.opencsv.exceptions.CsvValidationException;
import org.hibernate.id.GUIDGenerator;
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
import java.util.*;
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

    @Autowired
    LocationAnalyzer locationAnalyzer;

    final static String resume = " Platforms/Frameworks: Java, JEE, J2EE, Spring Boot, Spring Framework, Quarkus\n" +
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

    public void run() throws CsvValidationException, IOException, URISyntaxException {

        // todo - to start param
        boolean coolPrint = true;

        String fileName = "c:\\temp\\messages-7.csv";
        List<EmailDTO> emailDTOS = jdReader.readCSV(fileName);
        List<JoblVO> joblVOS = new ArrayList<>();

        for (EmailDTO emailDTO : emailDTOS) {
            String sbj = emailDTO.getSubject();
            if (sbj.contains("Rate Confirmation")
                    || sbj.contains("RTR")
                    || (sbj.contains("RE:") && !sbj.contains("Hire"))) {
                System.out.println("skipped with subject " + emailDTO.getSubject());
                continue;
            }
            JoblVO joblVO = handlerDto(emailDTO, resume.toLowerCase());
            joblVOS.add(joblVO);
        }
        joblVOS = joblVOS.stream()
                .sorted(Comparator.comparing(JoblVO::getScore))
                .collect(Collectors.toList());

        //  { x: 63.4, y: 51.8, z: 15.4, name: 'PT', country: 'Portugal' },
        //  { x: 64, y: 82.9, z: 31.3, name: 'NZ', country: 'New Zealand' }
        int i = 0;
        int kRandInit = -500000;
        int kMaxLevel = 60000;
        int kStep = 40000;
        int kShift = kRandInit;

        StringBuilder subjectGuid = new StringBuilder();
        StringBuilder sb = new StringBuilder();
        sb.append("{ x: 0, y: 300000, z: 22, color: "
                +  getColor("Remote") + ", loc: 'Remote', type: 'Remote', uuid: '1' },");
        sb.append("{ x: 0, y: 100000, z: 22, color: "
                +  getColor("OnSite") + ", loc: 'OnSite', type: 'OnSite', uuid: '2' },");
        sb.append("{ x: 0, y: -100000, z: 22, color: "
                +  getColor("Hybrid") + ", loc: 'Hybrid', type: 'Hybrid', uuid: '3' },");
        if (coolPrint) {
            sb.append("{ x: 275, y: 145000, z: 22, color: "
                    + getColor("Hybrid") + ", loc: 'CA', type: 'Hybrid', uuid: 'f2dd1eb8-e979-4ec8-8be9-431ab6380bea' },");
        }
        sb.append("{ x: 0, y: -300000, z: 22, color: "
                +  getColor("Mixed")
                + ", loc: 'Remote or OnSite or Hybrid', type: 'Remote or OnSite or Hybrid', uuid: '4' },");
        for (JoblVO joblVO : joblVOS) {
            if (joblVO.getScore() < 50 || (joblVO.getScore() < 90 && joblVO.getLoc().isEmpty())) {
                continue;
            }
            Integer salary = joblVO.getSalary();
            if (salary == 0) {
                salary =  kShift; // new Random().nextInt(20000) +

                kShift = kShift + kStep;
                if (kShift > kMaxLevel) {
                    kShift = kRandInit;
                }
                joblVO.setSalary(salary);
            }
            if (coolPrint) {
                if (joblVO.getLoc().isEmpty()) {
                    String[] randomLoc = new String[] {"CA", "FL", "TX", "OH"};
                    Integer rand = new Random().nextInt(randomLoc.length);
                    joblVO.setLoc(randomLoc[rand]);
                }
            }
            sb.append("{ x: " + joblVO.getScore() + ", "
                    + "y: " + salary + ", "
                    + "z: " + getSize(joblVO.getType()) + ", "
                    + "color: "+ getColor(joblVO.getType()) +","
                    + " loc: '" + joblVO.getLoc() + "', "
                    + " type: '" + joblVO.getType() + "', "
                    + " uuid: '" + joblVO.getGuid() + "'}" + "\n");
            if (i != joblVOS.size() - 1) {
                sb.append(",");
            }

            subjectGuid.append(" \n\n<br>");
            subjectGuid.append(joblVO.getGuid());
            subjectGuid.append(": ");
            subjectGuid.append(joblVO.getSubject());
            subjectGuid.append(", from: ");
            subjectGuid.append(joblVO.getFrom());
            subjectGuid.append(", date: ");
            subjectGuid.append(joblVO.getDate());
            subjectGuid.append(" \n\n<br>");
            subjectGuid.append("----------");
        }

        //
        Resource resource = resourceLoader.getResource("classpath:static/index.html");
        if (resource.exists()) {
            URL url = resource.getURL();
            List<String> fileRowsList = getFromFile(url);

            String chartFile= "c:\\temp\\index.html";

            try (FileWriter writer = new FileWriter(chartFile)) {
                for (String line : fileRowsList) {
                    if (line.contains("replace_me")) {
                        writer.write(sb + System.lineSeparator());
                        continue;
                    }
                    writer.write(line + System.lineSeparator());
                }
                writer.write(subjectGuid.toString());
            } catch (IOException e) {
                System.err.format("IOException: %s%n", e);
            }
        }

        joblVOS.stream()
                .map(jd -> jd.getGuid() + " " + jd.getSubject()
                        + " (score " + jd.getScore()
                        + " salary ===> " + jd.getSalary()
                        + " location: ---> " + jd.getLoc())
                        //+ " date " + jd.getDate() + ")")
                .forEach(System.out::println);

    }


    public JoblVO handlerDto(final EmailDTO emailDTO,
                             final String resume) {

        List<String> jobDescList = textProcessor.prepareJD(textProcessor.splitText(emailDTO.getWholeBody().toLowerCase()));

        List<String> relevantList = textProcessor.splitText(resume);

        ObjectMapper om = new ObjectMapper();
        JoblVO joblVO = om.convertValue(emailDTO, JoblVO.class);
        joblVO.setGuid(UUID.randomUUID().toString());

        Integer score = scoreCalculation.doCalculation(jobDescList, relevantList);
        joblVO.setScore(score);

        Integer salary = salaryAnalyzer.searchSalary(emailDTO.getWholeBody());
        joblVO.setSalary(salary);

        joblVO.setType(workType(emailDTO.getWholeBody().toLowerCase()));

        joblVO.setLocation(locationAnalyzer.searchLocation(emailDTO.getWholeBody()));
        joblVO.setLoc(locationAnalyzer.shortLocation(joblVO.getLocation()));

        if (joblVO.getType().contains("or")) {
            joblVO.setLoc(joblVO.getLoc() + " " + joblVO.getType());
        }

        return joblVO;
    }

    private List<String> getFromFile(URL url) throws IOException, URISyntaxException {

        return  Files.readAllLines(Path.of(url.toURI()));

    }

    private String workType(final String body) {
        StringBuilder sb = new StringBuilder();
        if (body.contains("remote")) {
            sb.append("Remote");
        }
        if (body.contains("onsite")) {
            if (sb.length() != 0) {
                sb.append(" or ");
            }
            sb.append("OnSite");
        }
        if (body.contains("hybrid")) {
            if (sb.length() != 0) {
                sb.append(" or ");
            }
            sb.append("Hybrid");
        }
        return sb.toString();
    }

    private String getColor(final String type) {
        if (type.equals("Remote")) {
            return "'#00FFFF'";
        }
        if (type.equals("OnSite")) {
            return "'#0066ff'";
        }
        if (type.equals("Hybrid")) {
            return "'#ff9933'";
        }
        return "'#b3ffb3'";
    }

    private String getSize(final String type) {
//        if (type.equals("Remote")) {
//            return "17";
//        }
//        if (type.equals("OnSite")) {
//            return "17";
//        }
//        if (type.equals("Hybrid")) {
//            return "17";
//        }
        return "22";
    }

}
