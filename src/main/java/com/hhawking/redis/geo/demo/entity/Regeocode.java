package com.hhawking.redis.geo.demo.entity;

import java.io.Serializable;

/**
* @title: 地址
* @Author: HH
* @Date: 2019-8-2 14:44
*/
public class Regeocode implements Serializable {
    private static final long serialVersionUID = -2786906570665908137L;

    private String formatted_address;

    public String getFormatted_address() {
        return formatted_address;
    }

    public void setFormatted_address(String formatted_address) {
        this.formatted_address = formatted_address;
    }
}
