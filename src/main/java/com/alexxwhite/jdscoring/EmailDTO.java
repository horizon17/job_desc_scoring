package com.alexxwhite.jdscoring;

import lombok.Data;

@Data
public class EmailDTO {

    private String subject;

    private String from;

    private String to;

    private String date;

    private String wholeBody; // all included

}
