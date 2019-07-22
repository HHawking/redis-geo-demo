package com.hhawking.redis.geo.demo.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.geo.*;
import org.springframework.data.redis.connection.RedisGeoCommands;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class GeoService {

    @Autowired
    private RedisTemplate redisTemplate;

    private final String cityGeoKey = "geo";

    public void testAdd(double x,double y,String member){
        Long addedNum = redisTemplate.opsForGeo().add(cityGeoKey,new Point(x,y),member);
        System.out.println(addedNum);
    }
    
    public void testGeoGet(String members){
        List<Point> points = redisTemplate.opsForGeo().position(cityGeoKey,members);
        System.out.println(points);
    }
    
    public void testDist(){
        Distance distance = redisTemplate.opsForGeo()
                .distance(cityGeoKey,"北京","上海", RedisGeoCommands.DistanceUnit.KILOMETERS);
        System.out.println(distance);
    }
    
    public void testNearByXY(double x,double y){
        //longitude,latitude
        Circle circle = new Circle(x,y, Metrics.KILOMETERS.getMultiplier());
        RedisGeoCommands.GeoRadiusCommandArgs args = RedisGeoCommands.GeoRadiusCommandArgs.newGeoRadiusArgs().includeDistance().includeCoordinates().sortAscending().limit(5);
        GeoResults<RedisGeoCommands.GeoLocation<String>>  results = redisTemplate.opsForGeo()
                .radius(cityGeoKey,circle,args);
        System.out.println(results);
    }
    
    public void testNearByPlace(){
        Distance distance = new Distance(5,Metrics.KILOMETERS);
        RedisGeoCommands.GeoRadiusCommandArgs args = RedisGeoCommands.GeoRadiusCommandArgs.newGeoRadiusArgs().includeDistance().includeCoordinates().sortAscending().limit(5);
        GeoResults<RedisGeoCommands.GeoLocation<String>>  results = redisTemplate.opsForGeo()
                .radius(cityGeoKey,"北京",distance,args);
        System.out.println(results);
    }
    
    public void testGeoHash(){
        List<String> results = redisTemplate.opsForGeo()
                .hash(cityGeoKey,"北京","上海","深圳");
        System.out.println(results);
    }
}
