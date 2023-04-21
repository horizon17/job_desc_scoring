package com.alexxwhite.jdscoring;

import com.opencsv.CSVReader;
import com.opencsv.exceptions.CsvValidationException;
import org.hibernate.annotations.Comment;
import org.springframework.stereotype.Component;

import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@Component
public class JDReader {

    public List<EmailDTO> readCSV() throws CsvValidationException {
        List<EmailDTO> dtos = new ArrayList<>();
        try {
            CSVReader reader = new CSVReader(new FileReader("c:\\temp\\messages-4.csv"));
            String[] line;
            while ((line = reader.readNext()) != null) {
                EmailDTO emailDTO = new EmailDTO();
                if (line.length == 1) {
                    emailDTO.setWholeBody(line[0]);
                } else if (line.length == 6) {
                    emailDTO.setSubject(line[0]);
                    emailDTO.setFrom(line[1]);
                    emailDTO.setTo(line[2]);
                    emailDTO.setDate(line[3]);
                    emailDTO.setWholeBody(line[5]);
                }
                dtos.add(emailDTO);
                //System.out.println(col1 + col2 + col3 + col5);
            }
            reader.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return dtos;
    }
}

