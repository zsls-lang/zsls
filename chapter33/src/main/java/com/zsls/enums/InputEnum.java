/**
 * Company
 * Copyright (C) 2004-2020 All Rights Reserved.
 */
package com.zsls.enums;

/**
 * @author zsls
 * @version $Id InputEnum.java, v 0.1 2020-06-10 17:03  Exp $$
 */
public enum InputEnum {

    ADD("add"),MULTIPLY("mul"),SUBSTARCT("sub");
    private  String message;

    InputEnum(String message) {
        this.message = message;
    }

    public String getMessage() {
        return message;
    }


}