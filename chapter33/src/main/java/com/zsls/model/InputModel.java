package com.zsls.model;

public class InputModel {
    private Double first;
    private Double next;
    private String type;//类型

    public static InputModel build() {
        return new InputModel();
    }

    public Double getFirst() {
        return first;
    }

    public void setFirst(Double first) {
        this.first = first;
    }

    public Double getNext() {
        return next;
    }

    public void setNext(Double next) {
        this.next = next;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }
}