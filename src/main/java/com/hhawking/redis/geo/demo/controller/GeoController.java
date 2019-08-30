package com.hhawking.redis.geo.demo.controller;

import com.hhawking.redis.geo.demo.service.GeoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class GeoController {

    @Autowired
    private GeoService geoService;
    /**
     * 获取地址
     */
    @RequestMapping("getAddress")
    public String getAddressByRedis(String location) {
        return geoService.getAddress(location);
    }
}
