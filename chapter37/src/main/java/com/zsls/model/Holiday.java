package com.zsls.model;

import java.io.Serializable;
import java.util.Date;

public class Holiday implements Serializable {
    private static final long serialVersionUID = 1L;
    private Integer id;
    private String holidayName; //申请人
    private Date startDate; //开始时间
    private Date endDate;   //结束时间
    private Integer num;    //请假天数
    private String reason;  //请假事由
    private String type;    //请假类型

    public Integer getId() {

        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getHolidayName() {
        return holidayName;
    }

    public void setHolidayName(String holidayName) {
        this.holidayName = holidayName;
    }

    public Date getStartDate() {
        return startDate;
    }

    public void setStartDate(Date startDate) {
        this.startDate = startDate;
    }

    public Date getEndDate() {
        return endDate;
    }

    public void setEndDate(Date endDate) {
        this.endDate = endDate;
    }

    public Integer getNum() {
        return num;
    }

    public void setNum(Integer num) {
        this.num = num;
    }

    public String getReason() {
        return reason;
    }

    public void setReason(String reason) {
        this.reason = reason;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }
}
