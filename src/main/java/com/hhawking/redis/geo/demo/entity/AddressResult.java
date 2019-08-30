package com.hhawking.redis.geo.demo.entity;

/**
* @title: 高德 API 逆地理编码返回的结果
* @Author: HH
* @Date: 2019-8-2 14:44
*/
public class AddressResult {

    private String status;
    private Regeocode regeocode;
    private String info;
    private String infocode;

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public Regeocode getRegeocode() {
        return regeocode;
    }

    public void setRegeocode(Regeocode regeocode) {
        this.regeocode = regeocode;
    }

    public String getInfo() {
        return info;
    }

    public void setInfo(String info) {
        this.info = info;
    }

    public String getInfocode() {
        return infocode;
    }

    public void setInfocode(String infocode) {
        this.infocode = infocode;
    }
}
