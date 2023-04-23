package com.alexxwhite.jdscoring;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@Component
public class SalaryAnalyzer {

    @Autowired
    private TextProcessor textProcessor;

    public Integer searchSalary(final List<String> jobDescList) {

        List<String> salaryHints = Arrays.asList("$", "Salary", "per year");
        List<String> filtered = jobDescList.stream().filter(r -> salaryHints.stream().anyMatch(r::contains))
                .collect(Collectors.toList());

        if (filtered.size() != 0) {
            String salary = filtered.get(0).replaceAll("[^\\d]","");
            try {
                return Integer.valueOf(salary);
            } catch (Exception e) {
                System.out.println("cant convert " + salary + " to Integer");
            }
        }
        return 0;
    }

    public Integer searchSalary(final String jobDesc) {

        List<String> jobDescList = textProcessor.prepareJD(textProcessor.splitTextByLines(jobDesc));

        return searchSalary(jobDescList);
    }

}
