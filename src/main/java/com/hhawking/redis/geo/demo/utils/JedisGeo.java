package com.hhawking.redis.geo.demo.utils;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import redis.clients.jedis.GeoCoordinate;
import redis.clients.jedis.GeoRadiusResponse;
import redis.clients.jedis.GeoUnit;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.exceptions.JedisException;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
* @title: JedisGeo
* @Author: HH
* @Date: 2019-8-1 16:22
*/
@Component
public class JedisGeo {
    private final static Logger logger = LoggerFactory.getLogger(JedisGeo.class);
    private static final String GEO_KEY = "rxd-geo";

    public long geoAddbat(String list){
        return geoAddbat(GEO_KEY, list);
    }

    private long geoAddbat(final String key, final String list){
        long res = 0;
        Map<String, GeoCoordinate> addmap = new HashMap<>();
        for(String str : list.split(";")){
            String[] s=str.split(",");
            if(s.length < 3) {
                continue;
            }
            addmap.put(s[2], new GeoCoordinate(Double.parseDouble(s[0]), Double.parseDouble(s[1])));
        }

        if(addmap.size() > 0){
            try(Jedis jedis = RedisUtils.getJedis()) {

                res = jedis.geoadd(key, addmap);
            } catch (JedisException e) {
                logger.error("", e);
            }
            return res;
        }
        else {
            return 0;
        }
    }

    public long geoAdd(String longitude, String latitude, final String member) throws NumberFormatException{
        return geoAdd(GEO_KEY, Double.parseDouble(longitude), Double.parseDouble(latitude), member);
    }

    public long geoAdd(double longitude, double latitude, final String member){
        return geoAdd(GEO_KEY, longitude, latitude, member);
    }

    private long geoAdd(final String key, double longitude, double latitude, String member){
        long res = 0;
        if(member==null || member.length()<1) {
            return 0;
        }
        try(Jedis jedis = RedisUtils.getJedis()) {
            if(isMessyCode(member)) {
                logger.info("----geoAdd---longitude={},latitude={},address={}",longitude, latitude,member);
            } else{
                member = String.format("%s#**#%f-%f", member, longitude, latitude);
                res = jedis.geoadd(String.format("%s_%d_%d", key, (int)longitude, (int)latitude), longitude, latitude, member);
            }
        } catch (JedisException e) {
            logger.error("", e);
        }
        return res;
    }

    public long geoDel(String longitude, String latitude, final String member) throws NumberFormatException{
        return geoDel(GEO_KEY, Double.parseDouble(longitude), Double.parseDouble(latitude), member);
    }

    public long geoDel(double longitude, double latitude, final String member){
        return geoDel(GEO_KEY, longitude, latitude, member);
    }

    private long geoDel(final String key, double longitude, double latitude, String member){
        if(StringUtils.isBlank(member)) {
            return 0;
        }
        long res = 0;
        try(Jedis jedis = RedisUtils.getJedis()) {
            res = jedis.zrem(String.format("%s_%d_%d", key, (int)longitude, (int)latitude), member);
            if(res==0){
                member = String.format("%s#**#%f-%f", member, longitude, latitude);
                res = jedis.zrem(String.format("%s_%d_%d", key, (int)longitude, (int)latitude), member);
            }
        } catch (JedisException e) {
            logger.error("", e);
        }
        return res;
    }

    /**
     * 通过经纬度与范围获取地址
     * @param longitude 经度
     * @param latitude 纬度
     * @param radius 范围
     * @return
     */
    public String geoRadius(String longitude, String latitude, double radius){
        return geoRadius(GEO_KEY, Double.parseDouble(longitude), Double.parseDouble(latitude), radius);
    }

    /**
     * 通过经纬度与范围获取地址
     * @param longitude 经度
     * @param latitude 纬度
     * @param radius 范围
     * @return
     */
    public String geoRadius(double longitude, double latitude, double radius){
        return geoRadius(GEO_KEY, longitude, latitude, radius);
    }

    private String geoRadius(final String key, double longitude, double latitude, double radius) {
        List<GeoRadiusResponse> value;
        String result = null;
        double dis = radius;
        try(Jedis jedis = RedisUtils.getJedis()) {
            value = jedis.georadius(String.format("%s_%d_%d", key, (int)longitude, (int)latitude), longitude, latitude, radius, GeoUnit.M);
            if(value==null||value.isEmpty()){
                return null;
            }
            for (GeoRadiusResponse tmp : value) {
                if(dis > tmp.getDistance()) {
                    dis  = tmp.getDistance();
                    result = tmp.getMemberByString();
                }
            }
        } catch (JedisException e) {
            logger.error("", e);
        }
        if (StringUtils.isBlank(result)){
            return null;
        }
        int index = result.indexOf("#**#");
        if(index > 0) {
            result = result.substring(0, index);
        }
        return result;
    }


    /**
     * 判断字符串是否乱码
     * 当从Unicode编码向某个字符集转换时，如果在该字符集中没有对应的编码，则得到0x3f(即问号字符?)
     * 从其他字符集向Unicode编码转换时，如果这个二进制数在该字符集中没有标识任何的字符，则得到的结果是0xfffd
     * @param str
     * @return true存在乱码,false不存在乱码
     */
    private boolean isMessyCode(String str) {
        for (int i = 0; i < str.length(); i++) {
            char c = str.charAt(i);
            if ((int) c == 0xfffd) {
                return true;
            }
        }
        return false;
    }
}
