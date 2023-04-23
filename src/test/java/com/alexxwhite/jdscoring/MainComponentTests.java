package com.alexxwhite.jdscoring;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.util.Assert;

@SpringBootTest
@ActiveProfiles("test")
class MainComponentTests {

    @Autowired
    MainComponent mainComponent;

    String wholeBody = "Subject:\n" +
            "URGENT!! | Java Developer\n" +
            "From:\n" +
            "Deependra Kumar <deependra.s@nityo.com>\n" +
            "Date:\n" +
            "4/20/2023, 2:20 PM\n" +
            "To:\n" +
            "anatoliibelov81@gmail.com\n" +
            "\n" +
            "CEIPAL ATS - Simplified Recruiting and Staffing\n" +
            "Hello Anatolii Belov\n" +
            " \n" +
            "My name is Deependra Kumar and I am a Staffing Specialist at Nityo Infotech. I am reaching out to you on an exciting job opportunity with one of our clients.\n" +
            " \n" +
            "\n" +
            "Role: Java Developer\n" +
            "\n" +
            "Client: Albertsons\n" +
            "\n" +
            "Location: Plano TX, or Pleasanton, CA or Arizona, CA- will be require to be onsite 2 time a week local\n" +
            "\n" +
            "Duration: 6 month CTH\n" +
            "\n" +
            "Interviews: 1-2 rounds\n" +
            "\n" +
            "GC,USC,H4 AND OPT\n" +
            "\n" +
            "Linkedin Must have\n" +
            "\n" +
            "Requirements:\n" +
            "\n" +
            "    Kafka\n" +
            "    Java (8)\n" +
            "    API\n" +
            "    Spring\n" +
            "    React\n" +
            "    Microservices\n" +
            "    Ability to API’s from scratch.\n" +
            "\n" +
            " \n" +
            "\n" +
            "Key Responsibilities include, but are not limited to:\n" +
            "\n" +
            "•            An expert in building digital solutions and you know your way around both server side and client-side code\n" +
            "\n" +
            "•            Curious and excited to learn new things and solve tricky issues in the simplest possible way.\n" +
            "\n" +
            "•            Motivated by taking a new feature all the way from inception, through research, design, development, and deployment up until it's being used by our customers.\n" +
            "\n" +
            "•            Participate in the implementation and enhancement of our digital experience platform\n" +
            "\n" +
            "•            Facilitate the implementation and adoption of new frameworks, tools and technologies.\n" +
            "\n" +
            "•            You also strive to help your colleagues when they need it\n" +
            "\n" +
            "•            Has a mindset and believes in CI/CD, Automation in developing modern software\n" +
            "\n" +
            " \n" +
            "\n" +
            "Qualification:\n" +
            "\n" +
            "•            4-year degree (Computer Science, Information Systems or relational functional field) and/or equivalent combination of education or work experience\n" +
            "\n" +
            "•            8+ years of hands-on programming and integration experience in Java (11 and above), Spring Framework (5 and above), building REST & enabled highly scalable and resilient APIs on Cloud based Distributed Systems is a must-have.\n" +
            "\n" +
            "•            Experienced in designing and developing Microservices applying Reactive patterns.\n" +
            "\n" +
            "•            Experience in No-SQL DBs like MongoDB or Azure Cosmos DB is a must have.\n" +
            "\n" +
            "•            Experience in Microsoft Azure or GCP is required. Additional experience in application containerization with Dockers/Kubernetes is preferred.\n" +
            "\n" +
            "•            Experience with Linux/UNIX systems and the best practices for deploying applications to those stacks.\n" +
            "\n" +
            "•            Extensive experience with Web-Services (SOAP/RESTful) web service or micro services using Spring or Spring Boot\n" +
            "\n" +
            "•            Significant experience with Agile/Scrum methodologies is required.\n" +
            "\n" +
            "•            Strong emphasis on Testing, Quality and Automation best practices.\n" +
            "\n" +
            "•            Strong organization skills with good interpersonal skills and a customer service-oriented attitude.\n" +
            "\n" +
            "•            Strong analytical, problem-solving, and decision-making skills. Ability to communicate and drive highly complex technology solutions to broad audiences within and outside of Technology and Engineering\n" +
            "\n" +
            "•            Ability to define solutions from very high-level business ideas and can work independently as needed.\n" +
            "\n" +
            " \n" +
            "\n" +
            " \n" +
            "\n" +
            " \n" +
            "\n" +
            "Thanks & Regards\n" +
            "Deependra Kumar\n" +
            "Technical Recruiter\n" +
            "Nityo Infotech Corp.\n" +
            "609-853-0818 Ext- 2416\n" +
            "Email: deependra.s@nityo.com\n" +
            "Disclaimer: http://www.nityo.com/Email_Disclaimer.html\n" +
            "\n" +
            " \n" +
            "\n" +
            "To unsubscribe from future emails or to update your email preferences click here .";


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

    @Test
    void handlerDtoTest() {

        EmailDTO emailDTO = new EmailDTO();
        emailDTO.setWholeBody(wholeBody);
        emailDTO.setDate("4/20/2023");
        JoblVO joblVO = mainComponent.handlerDto(emailDTO, resume);
        Assert.isTrue(joblVO.getScore() > 0, "score is 0");
        System.out.println(joblVO.getScore());

    }

}
