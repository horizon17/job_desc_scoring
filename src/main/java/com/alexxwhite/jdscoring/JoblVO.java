package com.alexxwhite.jdscoring;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import lombok.Data;

import java.util.Date;

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

}
