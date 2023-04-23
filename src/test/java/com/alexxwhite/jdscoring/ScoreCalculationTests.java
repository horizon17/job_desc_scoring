package com.alexxwhite.jdscoring;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.util.Assert;

import java.util.List;

@SpringBootTest
@ActiveProfiles("test")
class ScoreCalculationTests {

	@Autowired
	ScoreCalculation scoreCalculation;

	@Autowired
	private TextProcessor textProcessor;

	String jd = "Subject:\n" +
			"IntelliSearch™ Alert found 26 new jobs, based on your profile\n" +
			"" +
			"C\n" +
			"Java (11)\n" +
			"Kafka:\n" +
			"Spring Boot (framework)\n";

	String resume = "Java, kafka, Spring boot";

	@Test
	void prepareJDTest() {

		List<String> jobDescList = textProcessor.prepareJD(textProcessor.splitText(jd));
		List<String> relevantList = textProcessor.splitText(resume);

		Integer score = scoreCalculation.doCalculation(jobDescList, relevantList);
		System.out.println(score);

	}

}
