package com.alexxwhite.jdscoring;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.util.Assert;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

@SpringBootTest
@ActiveProfiles("test")
class ScoreCalculationTests {

	@Autowired
	ScoreCalculation scoreCalculation;

	@Autowired
	private TextProcessor textProcessor;

	@Autowired
	KeyWordsSrc keyWordsSrc;

	String jd = "Subject:\n" +
			"IntelliSearch™ Alert found 26 new jobs, based on your profile\n" +
			"" +
			"C\n" +
			"Java (11)\n" +
			"Kafka:\n" +
			"Spring Boot (framework)\n";

	String jd1 = "Hi,\n" +
			"\n" +
			" \n" +
			"\n" +
			"Hope you are doing well !!\n" +
			"\n" +
			" \n" +
			"\n" +
			"Nice talking to you.\n" +
			"\n" +
			" \n" +
			"\n" +
			"Please confirm the Salary $65/Hr on W2 for below opportunity.\n" +
			"\n" +
			" \n" +
			"\n" +
			"Also, please confirm the below right to represent.\n" +
			"\n" +
			" \n" +
			"\n" +
			"I (……………) authorize VBeyond Corp. to submit my profile for the position of Lead Full stack Java Developer ll San Francisco, CA ( Onsite-Hybrid ) with S3.\n" +
			"\n" +
			"I also confirm that I have not been submitted to this position through any other Vendor.\n" +
			"\n" +
			" \n" +
			"\n" +
			"Please share your one photo ID, you can hide your confidential details.\n" +
			"\n" +
			" \n" +
			"\n" +
			"Full Legal Name\n" +
			"\n" +
			" \n" +
			"\n" +
			"Contact Number\n" +
			"\n" +
			" \n" +
			"\n" +
			"Current location\n" +
			"\n" +
			" \n" +
			"\n" +
			"Work Authorization /Visa Status\n" +
			"\n" +
			"LinkedIn ID\n" +
			"\n" +
			" \n" +
			"\n" +
			"Last 4 digits of SSN\n" +
			"\n" +
			" \n" +
			"\n" +
			"MM/DD\n" +
			"\n" +
			" \n" +
			"\n" +
			"Reason for change\n" +
			"\n" +
			" \n" +
			"\n" +
			"Availability for interview (3 time slots)\n" +
			"\n" +
			" \n" +
			"\n" +
			" \n" +
			"\n" +
			"Role: Lead Full stack Java Developer\n" +
			"\n" +
			"Location: San Francisco Bay Area - 2-3 days hybrid onsite required (In-Person Interview )\n" +
			"\n" +
			"Duration :  12+ Months\n" +
			"\n" +
			" \n" +
			"\n" +
			"Open to locations such as San Leandro, Fremont, Concord and San Francisco\n" +
			"\n" +
			" \n" +
			"\n" +
			"Required Skills:\n" +
			"\n" +
			"Lead Full stack Java Developer\n" +
			"mongo DB and Kafka experience STRONGLY preferred.\n" +
			"Must have 2+ years as a Lead.\n" +
			"Strong Java Full stack development experience with Enterprise Java frameworks\n" +
			"Hands on experience with Spring boot, Spring JPA with Micro services development\n" +
			"Experience with MongoDB as datastore and Event Driven application development.\n" +
			"Experience with development tools such as GitHub, Gradle, Maven, Jenkins, UDeploy, AppDynamics, GeneOS, Splunk, JUnit .\n" +
			"Experienced with Agile development methodology and CI/CD";

	String resume = "Java, kafka, Spring boot";

	@Test
	void prepareJDTest() {

		List<String> jobDescList = textProcessor.prepareJD(textProcessor.splitText(jd1));
		//List<String> relevantList = textProcessor.splitText(resume);

		Integer score = scoreCalculation.doCalculation(jobDescList);
		System.out.println(score);

	}

	@Test
	void fillScoreTest() {

		List<String> irrelevantList = Arrays.asList(keyWordsSrc.getIrrelevantResumeKeys().split(" "));

		HashMap<String, Integer> scoreMap = new HashMap<>();

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
		scoreMap = scoreCalculation.fillScoreMap(scoreMap, irrelevantList, ScoreCalculation.defaultNegativeScore);

		System.out.println(scoreMap);

	}

}
