package com.alexxwhite.jdscoring;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;
import java.util.stream.Collectors;

@Component
public class SalaryAnalyzer {

    @Autowired
    private TextProcessor textProcessor;

    public Integer searchSalary(final List<String> jobDescList) {

        List<String> filtered = salaryRows(jobDescList);

        if (filtered.size() != 0) {
            String salary = filtered.get(0).replaceAll("401k|401K","");
            salary = salary.replaceAll("[^\\d$-]","");
            if (salary.split("-").length != 0) {
                String[] salaryRange = salary.split("-");
                salary = salaryRange[salaryRange.length - 1];
            } else if (salary.split("$").length > 2) {
                String[] salaryRange = salary.split("$");
                salary = salaryRange[salaryRange.length - 1];
            }
            salary = salary.replaceAll("[^\\d]","");
            try {
                Integer salr = Integer.valueOf(salary);
                if (salr < 200) {
                    salr = salr * 2000;
                }
                if (salr > 500000) {
                    System.out.println("some super too much " + salary);
                    salr = 200000;
                }
                return salr;
            } catch (Exception e) {
                System.out.println("cant convert " + salary + " to Integer");
            }
        }
        return 0;
    }

    private List<String> salaryRows(final List<String> jobDescList) {

        List<String> salaryHints = new ArrayList<>();
        salaryHints.add("$");
        List<String> filtered = jobDescList.stream().filter(r -> salaryHints.stream().anyMatch(r::contains))
                .collect(Collectors.toList());

        if (filtered.size() != 0) {
           return filtered;
        }
        salaryHints.add("per year");

        filtered = jobDescList.stream().filter(r -> salaryHints.stream().anyMatch(r::contains))
                .collect(Collectors.toList());

        if (filtered.size() != 0) {
            return filtered;
        }
        salaryHints.add("Salary");

        filtered = jobDescList.stream().filter(r -> salaryHints.stream().anyMatch(r::contains))
                .collect(Collectors.toList());

        return filtered;

    }

    public Integer searchSalary(final String jobDesc) {

        List<String> jobDescList = textProcessor.prepareJD(textProcessor.splitTextByLines(jobDesc));

        return searchSalary(jobDescList);
    }

}
