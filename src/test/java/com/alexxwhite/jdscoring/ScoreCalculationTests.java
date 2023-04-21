package com.alexxwhite.jdscoring;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.util.Assert;

@SpringBootTest
@ActiveProfiles("test")
class ScoreCalculationTests {

	@Autowired
	ScoreCalculation scoreCalculation;

	String wholeBody = "Subject:\n" +
			"IntelliSearch™ Alert found 26 new jobs, based on your profile\n" +
			"" +
			"C\n" +
			"Java (11)\n" +
			"Kafka:\n" +
			"Spring Boot (framework)\n";

	String myKeys = "Java, kafka, Spring boot";

	@Test
	void prepareJDTest() {

		Integer score = scoreCalculation.doCalculation(wholeBody, myKeys);
		System.out.println(score);

	}

}
