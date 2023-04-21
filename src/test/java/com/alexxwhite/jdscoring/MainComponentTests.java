package com.alexxwhite.jdscoring;

import com.sun.source.tree.AssertTree;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.util.Assert;

import java.util.Date;

@SpringBootTest
@ActiveProfiles("test")
class MainComponentTests {

	@Autowired
	MainComponent mainComponent;

	String wholeBody = "Subject:\n" +
			"IntelliSearch™ Alert found 26 new jobs, based on your profile\n" +
			"From:\n" +
			"\"Dice Job Alert\" <jobs@dice.com>\n" +
			"Date:\n" +
			"4/1/2023, 4:43 AM\n" +
			"To:\n" +
			"anatoliibelov81@gmail.com\n" +
			"\n" +
			"Start applying today\n" +
			" \u200C \u200C \u200C \u200C \u200C \u200C \u200C \u200C \u200C \u200C \u200C \u200C \u200C \u200C \u200C \u200C \u200C \u200C \u200C \u200C \u200C \u200C \u200C \u200C \u200C \u200C \u200C \u200C \u200C \u200C \u200C \u200C \u200C \u200C \u200C \u200C \u200C \u200C \u200C \u200C \u200C \u200C \u200C \u200C \u200C \u200C \u200C \u200C \u200C \u200C \u200C \u200C \u200C \u200C \u200C \u200C \u200C \u200C \u200C \u200C \u200C \u200C \u200C \u200C \u200C \u200C \u200C \u200C \u200C \u200C \u200C \u200C \u200C \u200C \u200C \u200C \u200C \u200C \u200C \u200C \u200C \u200C \u200C \u200C \u200C \u200C \u200C \u200C \u200C \u200C \u200C \u200C \u200C \u200C \u200C \u200C \u200C \u200C \u200C \u200C \u200C \u200C \u200C \u200C \u200C \u200C \u200C \u200C \u200C \u200C \u200C \u200C \u200C \u200C \u200C \u200C \u200C \u200C \u200C \u200C \u200C \u200C \u200C \u200C \u200C \u200C \u200C \u200C \u200C \u200C \u200C \u200C \u200C \u200C \u200C \u200C \u200C \u200C \u200C \u200C \u200C \u200C \u200C \u200C \u200C \u200C \u200C \u200C \u200C \u200C \u200C \u200C \u200C \u200C \u200C \u200C \u200C \u200C \u200C \u200C \u200C \u200C \u200C \u200C \u200C \u200C \u200C \u200C\n" +
			" \n" +
			"Dice Home\n" +
			" \n" +
			"Dice Home\n" +
			"\n" +
			"26 Matching Jobs based on\n" +
			"\n" +
			"26 Matching Jobs based on\n" +
			"My Profile\n" +
			"My\n" +
			"Profile\n" +
			"Sr. Java OR C++ Software Developer\n" +
			"RedRiver Systems L.L.C.                Remote\n" +
			"Posted 182 days ago - updated Yesterday\n" +
			"THIS ROLE IS 100% REMOTE FOR DALLAS AREA RESIDENTS - UNABLE TO SPONSOR Great opportunity to join this growing enterprise network engineering provider who is ...\n" +
			"Sr. Java OR C++ Software Developer\n" +
			"RedRiver Systems L.L.C.\n" +
			"        Remote\n" +
			"Posted 182 days ago - updated Yesterday\n" +
			"THIS ROLE IS 100% REMOTE FOR DALLAS AREA RESIDENTS - UNABLE TO SPONSOR Great opportunity to join this growing enterprise network engineering provider who is ...\n" +
			"Senior Java Developer - Remote (Local to DMV area)\n" +
			"Isoftech Inc                Remote\n" +
			"Posted 35 days ago - updated Yesterday\n" +
			"Senior Java Developer - Remote Top-skills - AWS, Angular, Java Technical Qualifications: (This is a Model JD) . Technically lead and develop full stack engi...\n" +
			"Senior Java Developer - Remote (Local to DMV area)\n" +
			"Isoftech Inc\n" +
			"        Remote\n" +
			"Posted 35 days ago - updated Yesterday\n" +
			"Senior Java Developer - Remote Top-skills - AWS, Angular, Java Technical Qualifications: (This is a Model JD) . Technically lead and develop full stack engi...\n" +
			"Lead / Sr. Java Developer\n" +
			"InfoVision, Inc.                Remote or Dallas, TX, USA\n" +
			"Posted 114 days ago - updated Yesterday\n" +
			"Title: Lead / Sr. Java Developer Location: Dallas, TX Duration: 1 Year + As a Sr. Java Developer you will be required to design, build and test changes to ...\n" +
			"Lead / Sr. Java Developer\n" +
			"InfoVision, Inc.\n" +
			"        Remote or Dallas, TX, USA\n" +
			"Posted 114 days ago - updated Yesterday\n" +
			"Title: Lead / Sr. Java Developer Location: Dallas, TX Duration: 1 Year + As a Sr. Java Developer you will be required to design, build and test changes to ...\n" +
			"Software Developer Sr Advisor (Java/ Oracle with eCommerce Payment Processing exp) - REMOTE\n" +
			"ASD, Inc.                Remote\n" +
			"Posted 14 days ago - updated Yesterday\n" +
			"Software Developer Sr Advisor to augment the USPS Endpoint Innovation team and will support multiple technical projects simultaneously, develop documentation...\n" +
			"Software Developer Sr Advisor (Java/ Oracle with eCommerce Payment Processing exp) - REMOTE\n" +
			"ASD, Inc.\n" +
			"        Remote\n" +
			"Posted 14 days ago - updated Yesterday\n" +
			"Software Developer Sr Advisor to augment the USPS Endpoint Innovation team and will support multiple technical projects simultaneously, develop documentation...\n" +
			"Dynamics AX/D365 Sr. Software Developer\n" +
			"Nigel Frank International                Remote\n" +
			"Posted 6 days ago - updated Yesterday\n" +
			"The Software Developer II will plan, design and develop software programs for internal employees as well as assist with corporate website design and function...\n" +
			"Dynamics AX/D365 Sr. Software Developer\n" +
			"Nigel Frank International\n" +
			"        Remote\n" +
			"Posted 6 days ago - updated Yesterday\n" +
			"The Software Developer II will plan, design and develop software programs for internal employees as well as assist with corporate website design and function...\n" +
			"See all IntelliSearch™ jobs\n" +
			"See all IntelliSearch™ jobs\n" +
			"\n" +
			"Manage Your Job Alert\n" +
			"Facebook        Twitter        Instagram        LinkedIn\n" +
			"googleplay appstore\n" +
			"\n" +
			"You are currently subscribed as anatoliibelov81@gmail.com .\n" +
			"\n" +
			"Manage your alerts or Unsubscribe\n" +
			"\n" +
			"\n" +
			"6465 South Greenwood Plaza Boulevard, Suite 400, Centennial, CO 80111\n" +
			"\n" +
			"Copyright © 1999-2020. All rights reserved. Use is subject to Terms and Conditions.";

	@Test
	void handlerDtoTest() {


		EmailDTO emailDTO = new EmailDTO();
		emailDTO.setWholeBody(wholeBody);
		emailDTO.setDate("4/20/2023");
		JoblVO joblVO = mainComponent.handlerDto(emailDTO);
		Assert.isTrue(joblVO.getScore() > 0, "score is 0");
		System.out.println(joblVO.getScore());

	}

}
