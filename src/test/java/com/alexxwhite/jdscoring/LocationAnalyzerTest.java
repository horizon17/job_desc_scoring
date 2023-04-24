package com.alexxwhite.jdscoring;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import java.util.List;

@SpringBootTest
@ActiveProfiles("test")
public class LocationAnalyzerTest {

    @Autowired
    LocationAnalyzer locationAnalyzer;

    @Autowired
    MainComponent mainComponent;

    @Autowired
    private TextProcessor textProcessor;

    String jd = "Java Backend Engineer – W2 - Hybrid Onsite in Dearborn, MI – Fulltime Direct Hire\n" +
            " \n" +
            "GENERAL INFORMATION:\n" +
            "Salary Range: $120k + benefits Per Year on W2 – Fixed and non-negotiable\n" +
            "Priority: High\n" +
            "Position Type: MSXI\n" +
            "Resource Type: Full Time Employee\n" +
            "Location: Dearborn, MI Local to MI (Weekly 1 or 2 days – work from office)\n" +
            "Work type: Hybrid Model.\n" +
            "Primary Skills: Software Engineer (Backend Java/SpringBoot/GCP). \n" +
            "Visa: Open\n" +
            "Pay-terms: W2 only as this is fulltime hire\n" +
            "Interviews: Telephone and then video call\n" +
            " \n" +
            "Position Description:\n" +
            "-Design and Develop BOM Java API and application by utilizing Java, JavaFX, Spring, Springboot and Hibernate framework. -Optionally will develop AI/ML projects for application infrastructure, licenses and others by using Python, Qliksense, Alteryx and GCP cloud base technologies. -Develop Unit and Functional test cases using JUnit for functional and non-functional requirements. -Reviews integration build status and provide code fixes for any deployment build failures -Participates in an ongoing production software operations review and troubleshoots production issues. -\n" +
            " \n" +
            "Skills Required:\n" +
            "- Java/J2EE, Springboot framework, JavaScript, Web Services,  SQL, Oracle, - Google Cloud Platform (GCP) tools - Agile Development Process\n" +
            " \n" +
            "Experience Required:\n" +
            "-5+ years total experience in Java and API Development utilizing Java/J2EE, JavaFX, Springboot framework, JavaScript, Web Services, Python, GitHub, XML, SQL, Oracle - 4+ years in Agile Development\n" +
            " \n" +
            " \n" +
            "\n" +
            "\n" +
            "\n" +
            "Kevin LENGYEL\n" +
            "\n" +
            "Senior Manager - Recruitments & Talent Acquisition – IntellyK Corporation\n" +
            "\n" +
            "Certified NMSDC | Minority Owned Business Enterprise (MBE) | an E-Verify Company\n" +
            "\n" +
            "9088181632; kevin.lengyel@intellyk.com; Piscataway Township, NJ 08854\n" +
            "\n" +
            "This is not an unsolicited mail. Under Bill 1618 Title III passed by the 105th USA Congress this email cannot be considered as spam as long as we include our contact information and an option to be removed from our emailing list. If you have received this message in error or, are not interested in receiving our emails, please accept our apologies and reply with REMOVE in the subject line. All removal requests will be honored. We sincerely apologize for any inconvenience caused. ";

    String jd1 = "I hope you are doing well!\n" +
            "\n" +
            "I am Avanish Ranjan from Nityo Infotech, We have a new job opening with our client at Given Location. Please go through the job summary and eligibility criteria carefully and let me know if you wish to learn more about this opportunity. I would really appreciate your prompt response. Kindly Share your updated resume or reach me @ Ph: 609-853-0818  Ext: 2376\n" +
            "\n" +
            " \n" +
            "\n" +
            " \n" +
            "\n" +
            "Job Role : Java Developer\n" +
            "\n" +
            "Job Locations : Austin, TX // New Jersey, NJ // Irving, TX // Plano, TX // Columbus, OH // Wilmington, DE\n" +
            "\n" +
            "Job Type :   Fulltime/ Permanent ONLY\n" +
            "\n" +
            " \n" +
            "\n" +
            "Proficient in Java\n" +
            "\n" +
            "Good experience with AWS skills\n" +
            "Solid understanding of object-oriented programming\n" +
            "Familiar with various design and architectural patterns\n" +
            "Skill for writing reusable Java libraries\n" +
            "Knowledge of concurrency patterns in Java\n" +
            "Familiarity with concepts of MVC, JDBC, and RESTful\n" +
            "Experience with both external and embedded databases\n" +
            "Creating database schemas that represent and support business processes";

    @Test
    void searchSalaryTest() {

        String location = locationAnalyzer.searchLocation(jd1);
        System.out.println(location);

        String loc = locationAnalyzer.shortLocation(location);
        System.out.println(loc);

    }


}
