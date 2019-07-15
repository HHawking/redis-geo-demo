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
    public void test(){
        geoService.testAdd();
    }
}
