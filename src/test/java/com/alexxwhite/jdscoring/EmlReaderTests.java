package com.alexxwhite.jdscoring;

import lombok.SneakyThrows;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import java.awt.*;
import java.io.IOException;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

@SpringBootTest
@ActiveProfiles("test")
class EmlReaderTests {

	@Autowired
	EMLReader emlReader;

	@Test
	@SneakyThrows
	void prepareJDTest() {

		String filePath = "c:\\temp\\ab81_and_dev\\davai jgi otez!-20240105-1563.eml";
		EmailDTO emailDTO = emlReader.readFileByName(filePath);

	}

	@Test
	void Test2() {

		try {
			// Replace with the path to your EML file
			String filePath = "c:\\temp\\ab81_and_dev\\Hybrid onsite role -- Role_ Sr. Java Developer -- Location_ Coral Springs, FL _ Berkeley Heights, NJ -- Duration_ Contract __ Fulltime-20240105-9773.eml";
			// Command to open the EML file with the default program
			String command = "cmd /c start \"\" \"" + filePath + "\"";

			Runtime.getRuntime().exec(command);
		} catch (IOException e) {
			e.printStackTrace();
		}

	}


}
