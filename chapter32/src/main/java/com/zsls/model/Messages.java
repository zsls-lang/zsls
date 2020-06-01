package com.zsls.model;

import lombok.Builder;
import lombok.Data;

import java.util.Date;

@Data
@Builder
public class Messages {
    private Long id;
    private String msg;
    private Date sendTime;

}