package com.zsls.model.entity;

import lombok.Data;
import lombok.ToString;

@Data
@ToString
public class MyLogInfo {
    /** logtime */
    private String log_insert_time;
    /** ip */
    private String log_origin_ip;
    /** id */
    private String id;
    /** 时间 */
    private String time;
}