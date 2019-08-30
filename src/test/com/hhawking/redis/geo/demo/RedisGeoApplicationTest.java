package com.hhawking.redis.geo.demo;

import com.hhawking.redis.geo.demo.service.GeoService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest
public class RedisGeoApplicationTest {

    @Autowired
    private GeoService geoService;

    @Test
    public void testAdd(){
        String address = geoService.getAddress("-10,-10");
        System.out.println(address);
//        geoService.testAdd(110.496593,24.778481,"广西省桂林市阳朔县");
//        geoService.testAdd(113.34497,23.155366,"华南理工大学五山校区");
    }
}
