package com.alexxwhite.jdscoring;

import com.opencsv.exceptions.CsvValidationException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class MainComponent {

    @Autowired
    private JDReader jdReader;

    @Autowired
    private ScoreCalculation scoreCalculation;

    public void run() throws CsvValidationException {

        List<EmailDTO> emailDTOS = jdReader.readCSV();

        int maxSc = 0;
        for (EmailDTO emailDTO : emailDTOS) {
            Integer res = scoreCalculation.doCalculation(emailDTO.getWholeBody(), null);
            maxSc = Math.max(res, maxSc);
            System.out.println(emailDTO.getSubject() + "  " + res);
        }
        System.out.println("maxSc  " + maxSc);

    }
}
