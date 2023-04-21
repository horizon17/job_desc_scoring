package com.alexxwhite.jdscoring;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.opencsv.exceptions.CsvValidationException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

@Component
public class MainComponent {

    @Autowired
    private JDReader jdReader;

    @Autowired
    private ScoreCalculation scoreCalculation;

    public void run() throws CsvValidationException {

        List<EmailDTO> emailDTOS = jdReader.readCSV();
        List<JoblVO> joblVOS = new ArrayList<>();

        for (EmailDTO emailDTO : emailDTOS) {
            JoblVO joblVO = handlerDto(emailDTO);
            joblVOS.add(joblVO);
        }
        joblVOS = joblVOS.stream()
                .sorted(Comparator.comparing(JoblVO::getScore))
                .collect(Collectors.toList());

        joblVOS.stream()
                .map(jd -> jd.getSubject()
                        + " (score " + jd.getScore()
                        + " date " + jd.getDate() + ")")
                .forEach(System.out::println);

    }

    public JoblVO handlerDto(final EmailDTO emailDTO) {
        Integer res = scoreCalculation.doCalculation(emailDTO.getWholeBody(), null);
        ObjectMapper om = new ObjectMapper();
        JoblVO joblVO = om.convertValue(emailDTO, JoblVO.class);
        joblVO.setScore(res);
        return joblVO;
    }

}
