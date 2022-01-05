package com.cumt.bigdata;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.cumt.bigdata.entity.Expressway;
import com.cumt.bigdata.mapper.SimpleMapper;
import com.cumt.bigdata.service.SimpleService;

import com.cumt.bigdata.vo.MapData;
import org.apache.tomcat.jni.Time;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.auditing.CurrentDateTimeProvider;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @Author: ahui
 * @date: 2021/12/25 - 21:01
 */
@SpringBootTest
public class BigDataApplicationTests {

    @Autowired
    SimpleService simpleService;

    @Autowired
    SimpleMapper simpleMapper;

    @Test
    void test() throws ParseException {
        String d1 = "2020-12-20 00:00";
        String d2 = "2020-12-28 00:00";
        String address = "广东罗田主线站";
        List<MapData> sfzckMapInfo = simpleService.getSFZRKMapInfoAll(d1, d2);
        for (MapData mapData : sfzckMapInfo) {
            System.out.println(mapData);
        }
        System.out.println(sfzckMapInfo.size()+"======");

    }
    @Test
    void test1(){

    }

}
