package com.alexxwhite.jdscoring;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.*;
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

    List<String> statesFull = new ArrayList<>(Arrays.asList(
            "Alabama",
            "Alaska",
            "Arizona",
            "Arkansas",
            "California",
            "Colorado",
            "Connecticut",
            "Delaware",
            "Florida",
            "Georgia",
            "Hawaii",
            "Idaho",
            "Illinois",
            "Indiana",
            "Iowa",
            "Kansas",
            "Kentucky",
            "Louisiana",
            "Maine",
            "Maryland",
            "Massachusetts",
            "Michigan",
            "Minnesota",
            "Mississippi",
            "Missouri",
            "Montana",
            "Nebraska",
            "Nevada",
            "New Hampshire",
            "New Jersey",
            "New Mexico",
            "New York",
            "North Carolina",
            "North Dakota",
            "Ohio",
            "Oklahoma",
            "Oregon",
            "Pennsylvania",
            "Rhode Island",
            "South Carolina",
            "South Dakota",
            "Tennessee",
            "Texas",
            "Utah",
            "Vermont",
            "Virginia",
            "Washington",
            "West Virginia",
            "Wisconsin",
            "Wyoming"
    ));

    static HashMap<String, String> statesMap;

    static {
        statesMap = new HashMap<>();
        statesMap.put("Alabama", "AL");
        statesMap.put("Alaska", "AK");
        statesMap.put("Arizona", "AZ");
        statesMap.put("Arkansas", "AR");
        statesMap.put("California", "CA");
        statesMap.put("Colorado", "CO");
        statesMap.put("Connecticut", "CT");
        statesMap.put("Delaware", "DE");
        statesMap.put("Florida", "FL");
        statesMap.put("Georgia", "GA");
        statesMap.put("Hawaii", "HI");
        statesMap.put("Idaho", "ID");
        statesMap.put("Illinois", "IL");
        statesMap.put("Indiana", "IN");
        statesMap.put("Iowa", "IA");
        statesMap.put("Kansas", "KS");
        statesMap.put("Kentucky", "KY");
        statesMap.put("Louisiana", "LA");
        statesMap.put("Maine", "ME");
        statesMap.put("Maryland", "MD");
        statesMap.put("Massachusetts", "MA");
        statesMap.put("Michigan", "MI");
        statesMap.put("Minnesota", "MN");
        statesMap.put("Mississippi", "MS");
        statesMap.put("Missouri", "MO");
        statesMap.put("Montana", "MT");
        statesMap.put("Nebraska", "NE");
        statesMap.put("Nevada", "NV");
        statesMap.put("New Hampshire", "NH");
        statesMap.put("New Jersey", "NJ");
        statesMap.put("New Mexico", "NM");
        statesMap.put("New York", "NY");
        statesMap.put("North Carolina", "NC");
        statesMap.put("North Dakota", "ND");
        statesMap.put("Ohio", "OH");
        statesMap.put("Oklahoma", "OK");
        statesMap.put("Oregon", "OR");
        statesMap.put("Pennsylvania", "PA");
        statesMap.put("Rhode Island", "RI");
        statesMap.put("South Carolina", "SC");
        statesMap.put("South Dakota", "SD");
        statesMap.put("Tennessee", "TN");
        statesMap.put("Texas", "TX");
        statesMap.put("Utah", "UT");
        statesMap.put("Vermont", "VT");
        statesMap.put("Virginia", "VA");
        statesMap.put("Washington", "WA");
        statesMap.put("West Virginia", "WV");
        statesMap.put("Wisconsin", "WI");
        statesMap.put("Wyoming", "WY");
    }


    public List<String> searchLocationList(final String jd) {

        List<String> res = new ArrayList<>();

        return res;
    }

    public String searchLocation(final String jobDesc) {

        List<String> jobDescList = textProcessor.prepareJD(textProcessor.splitTextByLines(jobDesc));

        List<String> locKeyList = new ArrayList<>();
        locKeyList.add("LOCATION");
        locKeyList.add("Location");
        locKeyList.add("location");
        locKeyList.add("LOCATED");
        locKeyList.add("Located");
        locKeyList.add("located");
        List<String> filtered = jobDescList.stream()
                .filter(r -> locKeyList.stream().anyMatch(r::contains))
                .collect(Collectors.toList());

        String finalLoc = filtered.stream().distinct().collect(Collectors.joining());

        if (finalLoc.contains("email")
                || finalLoc.contains("http")
                || finalLoc.contains("setting")
                || finalLoc.contains(".com")) {
            return "NA";
        }
        return finalLoc;
    }

    public String shortLocation(final String location) {
        if (location.isEmpty()) {
            return "";
        }
        List<String> filtered = Arrays.asList(location.split(" ")).stream()
                .filter(r -> states.stream().anyMatch(r::contains))
                .collect(Collectors.toList());

        if (filtered.size() == 0) {
            filtered = Arrays.asList(location.split(" ")).stream()
                    .filter(r -> statesFull.stream().anyMatch(r::contains))
                    .collect(Collectors.toList());
            if (filtered.size() == 0) {
                return "";
            }

            filtered = filtered.stream()
                    .map(s -> {
                        for (Map.Entry<String, String> substring : statesMap.entrySet()) {
                            if (s.contains(substring.getKey())) {
                                return substring.getValue();
                            }
                        }
                        return s;
                    })
                    .collect(Collectors.toList());
        }
        String shortLoc = filtered.stream().distinct().collect(Collectors.joining());
        return shortLoc.replaceAll("REMOTE|HYBRID|COVID|Onsite|ONSITE|CURRENT|Current|", "");
    }
}
