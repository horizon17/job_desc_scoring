package com.alexxwhite.jdscoring;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@Component
public class LocationAnalyzer {

    @Autowired
    private TextProcessor textProcessor;

    List<String> states = Arrays.asList(
            "AL", "AK", "AZ", "AR", "CA", "CO", "CT", "DE", "FL", "GA",
            "HI", "ID", "IL", "IN", "IA", "KS", "KY", "LA", "ME", "MD",
            "MA", "MI", "MN", "MS", "MO", "MT", "NE", "NV", "NH", "NJ",
            "NM", "NY", "NC", "ND", "OH", "OK", "OR", "PA", "RI", "SC",
            "SD", "TN", "TX", "UT", "VT", "VA", "WA", "WV", "WI", "WY"
    );

    public List<String> searchLocationList(final String jd) {
        List<String> res = new ArrayList<>();

        return res;
    }

    public String searchLocation(final String jobDesc) {

        List<String> jobDescList = textProcessor.prepareJD(textProcessor.splitTextByLines(jobDesc));

        List<String> locKeyList = new ArrayList<>();
        locKeyList.add("location");
        locKeyList.add("Location");
        List<String> filtered = jobDescList.stream()
                .filter(r -> locKeyList.stream().anyMatch(r::contains))
                .collect(Collectors.toList());

        return filtered.stream().distinct().collect(Collectors.joining());

    }

    public String shortLocation(final String location) {

        List<String> filtered = Arrays.asList(location.split(" ")).stream()
                .filter(r->states.stream().anyMatch(r::contains))
                .collect(Collectors.toList());
        String shortLoc = filtered.stream().distinct().collect(Collectors.joining());
        return shortLoc.replaceAll("REMOTE|HYBRID|COVID|Onsite|Current|", "");
    }
}
