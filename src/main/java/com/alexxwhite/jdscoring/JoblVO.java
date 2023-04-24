package com.alexxwhite.jdscoring;

import lombok.Data;

@Data
public class JoblVO {

    private String subject;

    private String from;

    private String to;

    //@JsonDeserialize(using = CustomDateDeserializer.class) // todo wait for DB
    private String date;

    private String wholeBody; // all included

    private Integer score;

    private String location;

    private String loc;

    private Integer salary;

    private String type; // remote, onsite, hybrid

    private String guid;

}
