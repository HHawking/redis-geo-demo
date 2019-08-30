package com.hhawking.redis.geo.demo.service;

import com.google.common.base.Splitter;
import com.hhawking.redis.geo.demo.entity.AddressResult;
import com.hhawking.redis.geo.demo.utils.JedisGeo;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.List;

/**
* @title: 经纬度-地址转换服务
* @Author: HH
* @Date: 2019-8-1 15:48
*/
@Service
public class GeoService {

    @Autowired
    private JedisGeo jedisGeo;

    private static final String URL = "https://restapi.amap.com/v3/geocode/regeo?key=${amap.key}&radius=200&location=";

    public  String  getAddress(String location) {
        if (StringUtils.isBlank(location)){
            return "-";
        }
        List<String> split = Splitter.on(",").trimResults().omitEmptyStrings().splitToList(location);
        if (split.size()!=2){
            return "-";
        }
        String s0 = split.get(0);
        String s1 = split.get(1);
        if ("0".equals(s0) && "0".equals(s1)){
            return "-";
        }
        String address = jedisGeo.geoRadius(s0,s1, 20);
        if (StringUtils.isNotBlank(address)){
            return address;
        }else {
            try {
                RestTemplate restTemplate = new RestTemplate();
                AddressResult addressResult = restTemplate.getForObject(URL + location, AddressResult.class);
                if (addressResult != null && "1".equals(addressResult.getStatus())) {
                    String formattedAddress = addressResult.getRegeocode().getFormatted_address();
                    jedisGeo.geoAdd(s0, s1, formattedAddress);
                    return formattedAddress;
                } else {
                    return "-";
                }
            }catch (Exception e){
                return "-";
            }
        }
    }


}
