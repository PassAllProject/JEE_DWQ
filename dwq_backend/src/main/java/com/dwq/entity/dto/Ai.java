package com.dwq.entity.dto;
import lombok.Data;

import java.util.Date;

@Data
public class Ai {
    private Integer id;
    private String username;
    private String promote;
    private String answer;
    private Date time;
}
