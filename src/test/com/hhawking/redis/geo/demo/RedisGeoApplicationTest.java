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
        geoService.testAdd(110.496593,24.778481,"广西省桂林市阳朔县");
        geoService.testAdd(113.34497,23.155366,"华南理工大学五山校区");
    }

    @Test
    public void testGet(){
        geoService.testGeoGet("广西省桂林市阳朔县白沙镇");
    }

    @Test
    public void testGetByXY(){
        geoService.testNearByXY(110.643305,24.633362);//平乐县
        geoService.testNearByXY(110.55836,24.786898);//福利镇
        geoService.testNearByXY(110.462437,24.710278);//高田镇
        geoService.testNearByXY(113.357583,23.15765);//华南农业大学
        geoService.testNearByXY(113.342259,23.170906);//天河客运站
    }
}
