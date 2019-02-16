package com.zsls.base;


import org.apache.commons.lang3.builder.ToStringBuilder;

import java.io.Serializable;

public class BaseModel implements Serializable {
    private static final long serialVersionUID = 3187674196466183366L;

    public BaseModel() {
    }

    @Override
    public String toString() {
        return ToStringBuilder.reflectionToString(this);
    }

}
