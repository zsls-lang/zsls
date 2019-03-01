/*
*
* AnalogData.java
* Copyright(C) 2017-2020 
* @date 2019-03-01
*/
package com.zsls.entity;

import com.zsls.base.BaseModel;
import javax.persistence.*;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;


@ApiModel(value = "模拟数据表", description = "模拟数据表")
@Table(name = "analog_data")
public class AnalogData extends BaseModel {
    @ApiModelProperty(value = "")
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @ApiModelProperty(value = "标签名")
    private String one;

    @ApiModelProperty(value = "数据1，今年数据")
    private String two;

    @ApiModelProperty(value = "数据2，明年数据")
    private String three;

    @ApiModelProperty(value = "数据3，后年数据")
    private String four;

    @ApiModelProperty(value = "录入数据的管理员名")
    @Column(name = "user_name")
    private String userName;

    @ApiModelProperty(value = "年")
    @Column(name = "current_year")
    private String currentYear;

    @ApiModelProperty(value = "数据分类标识")
    private String type;

    public AnalogData(Integer id, String one, String two, String three, String four, String userName, String currentYear, String type) {
        this.id = id;
        this.one = one;
        this.two = two;
        this.three = three;
        this.four = four;
        this.userName = userName;
        this.currentYear = currentYear;
        this.type = type;
    }

    public AnalogData() {
        super();
    }

    /**
     * 
     * @return id 
     */
    public Integer getId() {
        return id;
    }

    /**
     * 
     * @param id 
     */
    public void setId(Integer id) {
        this.id = id;
    }

    /**
     * 标签名
     * @return one 标签名
     */
    public String getOne() {
        return one;
    }

    /**
     * 标签名
     * @param one 标签名
     */
    public void setOne(String one) {
        this.one = one == null ? null : one.trim();
    }

    /**
     * 数据1，今年数据
     * @return two 数据1，今年数据
     */
    public String getTwo() {
        return two;
    }

    /**
     * 数据1，今年数据
     * @param two 数据1，今年数据
     */
    public void setTwo(String two) {
        this.two = two == null ? null : two.trim();
    }

    /**
     * 数据2，明年数据
     * @return three 数据2，明年数据
     */
    public String getThree() {
        return three;
    }

    /**
     * 数据2，明年数据
     * @param three 数据2，明年数据
     */
    public void setThree(String three) {
        this.three = three == null ? null : three.trim();
    }

    /**
     * 数据3，后年数据
     * @return four 数据3，后年数据
     */
    public String getFour() {
        return four;
    }

    /**
     * 数据3，后年数据
     * @param four 数据3，后年数据
     */
    public void setFour(String four) {
        this.four = four == null ? null : four.trim();
    }

    /**
     * 录入数据的管理员名
     * @return user_name 录入数据的管理员名
     */
    public String getUserName() {
        return userName;
    }

    /**
     * 录入数据的管理员名
     * @param userName 录入数据的管理员名
     */
    public void setUserName(String userName) {
        this.userName = userName == null ? null : userName.trim();
    }

    /**
     * 年
     * @return current_year 年
     */
    public String getCurrentYear() {
        return currentYear;
    }

    /**
     * 年
     * @param currentYear 年
     */
    public void setCurrentYear(String currentYear) {
        this.currentYear = currentYear == null ? null : currentYear.trim();
    }

    /**
     * 数据分类标识
     * @return type 数据分类标识
     */
    public String getType() {
        return type;
    }

    /**
     * 数据分类标识
     * @param type 数据分类标识
     */
    public void setType(String type) {
        this.type = type == null ? null : type.trim();
    }
}