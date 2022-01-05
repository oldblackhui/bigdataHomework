package com.cumt.bigdata.service;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.cumt.bigdata.entity.Expressway;
import com.cumt.bigdata.mapper.SimpleMapper;
import com.cumt.bigdata.util.CPTranslate;
import com.cumt.bigdata.util.MapTranslate;
import com.cumt.bigdata.util.SortUtil;
import com.cumt.bigdata.vo.*;
import io.netty.util.internal.StringUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.concurrent.TimeUnit;

/**
 * @Author: ahui
 * @date: 2021/12/20 - 16:08
 */
@Service
@Slf4j
public class SimpleService extends ServiceImpl<SimpleMapper, Expressway> {

    @Autowired
    private SimpleMapper simpleMapper;

    @Autowired
    private RedisTemplate redisTemplate;

    public List<SFZCountAndName> findAllSFZnameIn(){

        List<SFZCountAndName> list = simpleMapper.getAllSFZnameAndCountIn();
        return list;
    }

    public List<SFZCountAndName> findAllSFZnameOut(){

        List<SFZCountAndName> list = simpleMapper.getAllSFZnameAndCountOut();
        return list;
    }

    /*查询规定时间入口使用的收费站名称*/
    public int getDaySFZUseNumForRK(String d1,String d2){
        QueryWrapper<Expressway> queryWrapper = new QueryWrapper<>();
        queryWrapper.select("DISTINCT `SFZRKMC`")
                .between("RKSJ",d1,d2);
        int num = simpleMapper.selectCount(queryWrapper);
        return num;
    }

    /*查询规定时间出口使用的收费站名称*/
    public int getDaySFZUseNumForCK(String d1,String d2){
        QueryWrapper<Expressway> queryWrapper = new QueryWrapper<>();
        queryWrapper.select("DISTINCT `SFZCKMC`")
                .between("CKSJ",d1,d2);
        int num = simpleMapper.selectCount(queryWrapper);
        return num;
    }

    /*查询规定时间内经过入口的数量*/
    public int getDayNumForRK(String d1,String d2) {
        QueryWrapper<Expressway> queryWrapper = new QueryWrapper<>();
        queryWrapper
                .between("RKSJ",d1,d2);
        int num = simpleMapper.selectCount(queryWrapper);
        return num;
    }

    /*查询规定时间内经过出口的数量*/
    public int getDayNumForCK(String d1,String d2) {
        QueryWrapper<Expressway> queryWrapper = new QueryWrapper<>();
        queryWrapper
                .between("CKSJ",d1,d2);
        int num = simpleMapper.selectCount(queryWrapper);
        return num;
    }

    // 查询车流量最大的十个收费站
    public Map<String,Integer> getTop10SFZ(String d1,String d2){
        List<SFZCountAndName> rk = simpleMapper.getTop10SFZForRK(d1, d2);
        List<SFZCountAndName> ck = simpleMapper.getTop10SFZForCK(d1, d2);
        Map<String,Integer> res = new HashMap<>();
        for (SFZCountAndName name : rk) {
            res.put(name.getSFZname(),name.getCountnum());
        }

        for (SFZCountAndName name : ck) {
            if (res.containsKey(name.getSFZname()) && res.get(name.getSFZname())>name.getCountnum()) {
                res.put(name.getSFZname(),name.getCountnum());
            }else {
                res.put(name.getSFZname(),name.getCountnum());
            }
        }
        res = SortUtil.sortByValue(res);
        return res;
    }

    /*
    * @info 规定时间出收费站的车的数量
    */
    public int getMinutesCarNumForSFZCK(String d1,String d2,String sfzname){

        QueryWrapper<Expressway> queryWrapper = new QueryWrapper<>();
        queryWrapper
                .between("CKSJ",d1,d2)
                .eq("SFZCKMC",sfzname);
        int num = simpleMapper.selectCount(queryWrapper);
        return num;
    }

    /*
     * @info 规定时间入收费站的车的数量
     */
    public int getMinutesCarNumForSFZRK(String d1,String d2,String sfzname){

        QueryWrapper<Expressway> queryWrapper = new QueryWrapper<>();
        queryWrapper
                .between("RKSJ",d1,d2)
                .eq("SFZRKMC",sfzname);
        int num = simpleMapper.selectCount(queryWrapper);
        return num;

    }


    /*
     * @info 规定时间内出收费站的车来自哪里
     */
    public Map<String,Integer> getMinutesCarFromForSFZCKCP(String d1,String d2){

        QueryWrapper<Expressway> queryWrapper = new QueryWrapper<>();
        queryWrapper.select("CP").between("CKSJ",d1,d2);
        List<Expressway> expressways = simpleMapper.selectList(queryWrapper);
        Map<String,Integer> kinds = new HashMap<>();

        for (Expressway expressway : expressways) {
            String cp = expressway.getCP();
            String tempCP = ""+cp.charAt(0);

            if (kinds.containsKey(tempCP)){
                kinds.put(tempCP,kinds.get(tempCP)+1);
            }else {
                kinds.put(tempCP,1);
            }
        }
        Map<String, Integer> sortByValue = SortUtil.sortByValue(kinds);
        return sortByValue;

    }


    /*
     * @info 规定时间内入收费站的车来自哪里
     */
    public Map<String,Integer> getMinutesCarFromForSFZRKCP(String d1,String d2){
        QueryWrapper<Expressway> queryWrapper = new QueryWrapper<>();
        queryWrapper.select("CP").between("RKSJ",d1,d2);
        List<Expressway> expressways = simpleMapper.selectList(queryWrapper);
        Map<String,Integer> kinds = new HashMap<>();

        for (Expressway expressway : expressways) {
            String cp = expressway.getCP();
            String tempCP = ""+cp.charAt(0);

            if (kinds.containsKey(tempCP)){
                kinds.put(tempCP,kinds.get(tempCP)+1);
            }else {
                kinds.put(tempCP,1);
            }
        }
        Map<String, Integer> sortByValue = SortUtil.sortByValue(kinds);
        return sortByValue;

    }

   /*
   * @info 统计规定时间内收费站 有车辆进入的前十位
   */
    public List<SFZCountAndName> getCarsGetInSFZname10(String d1,String d2){
        List<SFZCountAndName> list = simpleMapper.getCarsGetInSFZname10(d1, d2);
        return list;

    }

    /*
     * @info 统计规定时间内收费站 有车辆离开的前十位
     */
    public List<SFZCountAndName> getCarsGetOutSFZname10(String d1,String d2){
        List<SFZCountAndName> list = simpleMapper.getCarsGetOutSFZname10(d1, d2);
        return list;

    }

    //查询固定时间内入口为某收费站的车辆来自哪里
    public List<MapData> getSFZRKMapInfo(String d1,String d2,String sfzname){
        QueryWrapper<Expressway> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("SFZRKMC",sfzname)
                .select("CP")
                .between("RKSJ",d1,d2);
        List<Expressway> expressways = simpleMapper.selectList(queryWrapper);
        Map<String,Integer> kinds = new HashMap<>();

        for (Expressway expressway : expressways) {
            String cp = expressway.getCP();
            String tempCP = ""+cp.charAt(0)+cp.charAt(1);
            if (kinds.containsKey(tempCP)){
                kinds.put(tempCP,kinds.get(tempCP)+1);
            }else {
                kinds.put(tempCP,1);
            }
        }
        List<MapData> res = new ArrayList<>();
        for(Map.Entry<String, Integer> entry : kinds.entrySet()){
            String mapKey = CPTranslate.CPtoCity(entry.getKey());
            int mapValue = entry.getValue();
            MapData mapData = new MapData();
            mapData.setCityname(mapKey);
            mapData.setNum(mapValue);
            String address = mapToSave(mapKey);
            if (address!=null){
                String[] split = address.split(",");
                mapData.setLng(split[0]);
                mapData.setLat(split[1]);
            } else {
                mapData.setLng("0");
                mapData.setLat("0");
            }
            res.add(mapData);
        }
        Collections.sort(res, new Comparator<MapData>() {
            @Override
            public int compare(MapData o1, MapData o2) {
                return o2.getNum().compareTo(o1.getNum());
            }
        });

        return res;
    }
    //查询固定时间内   出口为某收费站的车辆来自哪里
    public List<MapData> getSFZCKMapInfo(String d1,String d2,String sfzname){
        QueryWrapper<Expressway> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("SFZCKMC",sfzname)
                .select("CP")
                .between("CKSJ",d1,d2);
        List<Expressway> expressways = simpleMapper.selectList(queryWrapper);
        Map<String,Integer> kinds = new HashMap<>();

        for (Expressway expressway : expressways) {
            String cp = expressway.getCP();
            String tempCP = ""+cp.charAt(0)+cp.charAt(1);
            if (kinds.containsKey(tempCP)){
                kinds.put(tempCP,kinds.get(tempCP)+1);
            }else {
                kinds.put(tempCP,1);
            }
        }

        List<MapData> res = new ArrayList<>();
        for(Map.Entry<String, Integer> entry : kinds.entrySet()){
            String mapKey = CPTranslate.CPtoCity(entry.getKey());

            int mapValue = entry.getValue();
            MapData mapData = new MapData();
            mapData.setCityname(mapKey);
            mapData.setNum(mapValue);

            String address = mapToSave(mapKey);

            if (address!=null){
                String[] split = address.split(",");
                mapData.setLng(split[0]);
                mapData.setLat(split[1]);
            } else {
                mapData.setLng("0");
                mapData.setLat("0");
            }
            res.add(mapData);

        }
        Collections.sort(res, new Comparator<MapData>() {
            @Override
            public int compare(MapData o1, MapData o2) {
                return o2.getNum().compareTo(o1.getNum());
            }
        });

        return res;
    }

    // ======================总的
    //查询固定时间内总入口 的车辆来自哪里
    public List<MapData> getSFZRKMapInfoAll(String d1,String d2){
        QueryWrapper<Expressway> queryWrapper = new QueryWrapper<>();
        queryWrapper.select("CP")
                .between("RKSJ",d1,d2);
        List<Expressway> expressways = simpleMapper.selectList(queryWrapper);
        Map<String,Integer> kinds = new HashMap<>();

        for (Expressway expressway : expressways) {
            String cp = expressway.getCP();
            String tempCP = ""+cp.charAt(0)+cp.charAt(1);
            if (kinds.containsKey(tempCP)){
                kinds.put(tempCP,kinds.get(tempCP)+1);
            }else {
                kinds.put(tempCP,1);
            }
        }
        List<MapData> res = new ArrayList<>();
        for(Map.Entry<String, Integer> entry : kinds.entrySet()){
            String mapKey = CPTranslate.CPtoCity(entry.getKey());

            int mapValue = entry.getValue();
            MapData mapData = new MapData();
            mapData.setCityname(mapKey);
            mapData.setNum(mapValue);
            if (StringUtil.isNullOrEmpty(mapKey)){
                System.out.println(entry.getKey()+"mapkey"+mapKey);
            }

            String address = mapToSave(mapKey);
            if (address!=null){
                String[] split = address.split(",");
                mapData.setLng(split[0]);
                mapData.setLat(split[1]);
            } else {
                mapData.setLng("0");
                mapData.setLat("0");
            }
            res.add(mapData);
        }
        Collections.sort(res, new Comparator<MapData>() {
            @Override
            public int compare(MapData o1, MapData o2) {
                return o2.getNum().compareTo(o1.getNum());
            }
        });

        return res;
    }
    //查询固定时间内   出口 的车辆来自哪里
    public List<MapData> getSFZCKMapInfoAll(String d1,String d2){
        QueryWrapper<Expressway> queryWrapper = new QueryWrapper<>();
        queryWrapper.select("CP")
                .between("CKSJ",d1,d2);
        List<Expressway> expressways = simpleMapper.selectList(queryWrapper);
        Map<String,Integer> kinds = new HashMap<>();

        for (Expressway expressway : expressways) {
            String cp = expressway.getCP();
            String tempCP = ""+cp.charAt(0)+cp.charAt(1);
            if (kinds.containsKey(tempCP)){
                kinds.put(tempCP,kinds.get(tempCP)+1);
            }else {
                kinds.put(tempCP,1);
            }
        }

        List<MapData> res = new ArrayList<>();
        for(Map.Entry<String, Integer> entry : kinds.entrySet()){
            String mapKey = CPTranslate.CPtoCity(entry.getKey());
            int mapValue = entry.getValue();
            MapData mapData = new MapData();
            mapData.setCityname(mapKey);
            mapData.setNum(mapValue);
            String address = mapToSave(mapKey);
            if (address!=null){
                String[] split = address.split(",");
                mapData.setLng(split[0]);
                mapData.setLat(split[1]);
            } else {
                mapData.setLng("0");
                mapData.setLat("0");
            }
            res.add(mapData);

        }
        Collections.sort(res, new Comparator<MapData>() {
            @Override
            public int compare(MapData o1, MapData o2) {
                return o2.getNum().compareTo(o1.getNum());
            }
        });

        return res;
    }

    // ==========================================================================

    // 查询规定时间内进入某收费站的车辆  从入口到出口的收费站数据
    public List<RKCKInfo> getRKInfoRKtoCK(String d1,String d2){
        QueryWrapper<Expressway> wrapper = new QueryWrapper<>();
        wrapper.select("SFZRKMC","SFZCKMC")
                .between("RKSJ",d1,d2);
        List<Expressway> expressways = simpleMapper.selectList(wrapper);
        List<RKCKInfo> info = new ArrayList<>();
        for (Expressway expressway : expressways) {
            RKCKInfo rkckInfo = new RKCKInfo();
            String rkmc = expressway.getSFZRKMC();
            String ckmc = expressway.getSFZCKMC();

            String rkmap = mapToSave(rkmc);
            String ckmap = mapToSave(ckmc);


            rkckInfo.setRKMC(rkmc);
            rkckInfo.setCKMC(ckmc);
            if (rkmap!=null){
                rkckInfo.setRKlng(rkmap.split(",")[0]);
                rkckInfo.setRKlat(rkmap.split(",")[1]);
            } else {
                rkckInfo.setRKlng("");
                rkckInfo.setRKlat("");
            }
            if (ckmap!=null){
                rkckInfo.setCKlng(ckmap.split(",")[0]);
                rkckInfo.setCKlat(ckmap.split(",")[1]);
            } else {
                rkckInfo.setCKlng("");
                rkckInfo.setCKlat("");
            }
            info.add(rkckInfo);
        }
        return info;
    }

    // 查询规定时间内离开某收费站的车辆  从入口到出口的收费站数据
    public List<RKCKInfo> getCKInfoRKtoCK(String d1,String d2){
        QueryWrapper<Expressway> wrapper = new QueryWrapper<>();
        wrapper.select("SFZRKMC","SFZCKMC")
                .between("CKSJ",d1,d2);
        List<Expressway> expressways = simpleMapper.selectList(wrapper);
        List<RKCKInfo> info = new ArrayList<>();
        for (Expressway expressway : expressways) {
            RKCKInfo rkckInfo = new RKCKInfo();
            String rkmc = expressway.getSFZRKMC();
            String ckmc = expressway.getSFZCKMC();
            String rkmap = mapToSave(rkmc);
            String ckmap = mapToSave(ckmc);

            rkckInfo.setRKMC(rkmc);
            rkckInfo.setCKMC(ckmc);
            if (rkmap!=null){
                rkckInfo.setRKlng(rkmap.split(",")[0]);
                rkckInfo.setRKlat(rkmap.split(",")[1]);
            } else {
                rkckInfo.setRKlng("");
                rkckInfo.setRKlat("");
            }
            if (ckmap!=null){
                rkckInfo.setCKlng(ckmap.split(",")[0]);
                rkckInfo.setCKlat(ckmap.split(",")[1]);
            } else {
                rkckInfo.setCKlng("");
                rkckInfo.setCKlat("");
            }
            info.add(rkckInfo);
        }
        return info;
    }

    // 查询规定时间入口收费站 六种货车数量的变化
    public List<CarInfo> getRkKindsCarNum(String d1,String d2) {
        QueryWrapper<Expressway> wrapper = new QueryWrapper<>();
        wrapper.select("CX")
                .between("RKSJ",d1,d2);
        List<Expressway> expressways = simpleMapper.selectList(wrapper);
        int a=0,b=0,c=0,d=0,e=0,f=0;
        for (Expressway expressway : expressways) {
            String cx = expressway.getCX().substring(0,3);
            if (cx.equals("一型车")){
                a +=1;
            } else if (cx.equals("二型车")){
                b+=1;
            } else if (cx.equals("三型车")) {
                c+=1;
            } else if (cx.equals("四型车")) {
                d+=1;
            } else if (cx.equals("五型车")) {
                e+=1;
            } else if (cx.equals("六型车")) {
                f+=1;
            }

        }
        List<CarInfo> res = new ArrayList<>();
        res.add(new CarInfo("一型车",a));
        res.add(new CarInfo("二型车",b));
        res.add(new CarInfo("三型车",c));
        res.add(new CarInfo("四型车",d));
        res.add(new CarInfo("五型车",e));
        res.add(new CarInfo("六型车",f));
        return res;
    }

    // 查询规定时间出口收费站 六种货车数量的变化
    public List<CarInfo> getCkKindsCarNum(String d1,String d2) {
        QueryWrapper<Expressway> wrapper = new QueryWrapper<>();
        wrapper.select("CX")
                .between("CKSJ",d1,d2);
        List<Expressway> expressways = simpleMapper.selectList(wrapper);
        int a=0,b=0,c=0,d=0,e=0,f=0;
        for (Expressway expressway : expressways) {
            String cx = expressway.getCX().substring(0,3);
            if (cx.equals("一型车")){
                a +=1;
            } else if (cx.equals("二型车")){
                b+=1;
            } else if (cx.equals("三型车")) {
                c+=1;
            } else if (cx.equals("四型车")) {
                d+=1;
            } else if (cx.equals("五型车")) {
                e+=1;
            } else if (cx.equals("六型车")) {
                f+=1;
            }

        }
        List<CarInfo> res = new ArrayList<>();
        res.add(new CarInfo("一型车",a));
        res.add(new CarInfo("二型车",b));
        res.add(new CarInfo("三型车",c));
        res.add(new CarInfo("四型车",d));
        res.add(new CarInfo("五型车",e));
        res.add(new CarInfo("六型车",f));
        return res;
    }

    // 入口按照t(时间间隔) 一次性发送 n个时间的数据
    public List<TimeCarInfo> getTimeRKKindsCarNum(String d1,int t,int n) throws ParseException {
        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm");
        Date parse = df.parse(d1);

        List<TimeCarInfo> allTime = new ArrayList<>();
        int a=0,b=0,c=0,d=0,e=0,f=0;
        for (int i = 1; i <= n; i++) {
            Date date1 = new Date(parse.getTime() + (long) t * (i-1) *60 * 1000);
            Date date2 = new Date(parse.getTime() + (long) t * i *60 * 1000);
            String format1 = df.format(date1);
            String format2 = df.format(date2);

            QueryWrapper<Expressway> wrapper = new QueryWrapper<>();
            wrapper.select("CX","RKSJ").between("RKSJ",format1,format2);
            List<Expressway> expressways = simpleMapper.selectList(wrapper);

            for (Expressway onecar : expressways) {
                String cx = onecar.getCX().substring(0,3);
                if (cx.equals("一型车")){
                    a+=1;
                } else if (cx.equals("二型车")){
                    b+=1;
                } else if (cx.equals("三型车")) {
                    c+=1;
                } else if (cx.equals("四型车")) {
                    d+=1;
                } else if (cx.equals("五型车")) {
                    e+=1;
                } else if (cx.equals("六型车")) {
                    f+=1;
                }
            }

            allTime.add(new TimeCarInfo(String.valueOf(t*i),"一型车",a));
            allTime.add(new TimeCarInfo(String.valueOf(t*i),"二型车",b));
            allTime.add(new TimeCarInfo(String.valueOf(t*i),"三型车",c));
            allTime.add(new TimeCarInfo(String.valueOf(t*i),"四型车",d));
            allTime.add(new TimeCarInfo(String.valueOf(t*i),"五型车",e));
            allTime.add(new TimeCarInfo(String.valueOf(t*i),"六型车",f));

        }

        return allTime;
    }

    // 出口按照t(时间间隔) 一次性发送 n个时间的数据
    public List<TimeCarInfo> getTimeCKKindsCarNum(String d1,int t,int n) throws ParseException {
        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm");
        Date parse = df.parse(d1);

        List<TimeCarInfo> allTime = new ArrayList<>();
        int a=0,b=0,c=0,d=0,e=0,f=0;

        for (int i = 1; i <= n; i++) {

            Date date1 = new Date(parse.getTime() + (long) t * (i-1) *60 * 1000);
            Date date2 = new Date(parse.getTime() + (long) t * i *60 * 1000);
            String format1 = df.format(date1);
            String format2 = df.format(date2);

            QueryWrapper<Expressway> wrapper = new QueryWrapper<>();
            wrapper.select("CX","CKSJ").between("CKSJ",format1,format2);
            List<Expressway> expressways = simpleMapper.selectList(wrapper);

            for (Expressway onecar : expressways) {
                String cx = onecar.getCX().substring(0,3);
                if (cx.equals("一型车")){
                    a+=1;
                } else if (cx.equals("二型车")){
                    b+=1;
                } else if (cx.equals("三型车")) {
                    c+=1;
                } else if (cx.equals("四型车")) {
                    d+=1;
                } else if (cx.equals("五型车")) {
                    e+=1;
                } else if (cx.equals("六型车")) {
                    f+=1;
                }
            }

            allTime.add(new TimeCarInfo(String.valueOf(t*i),"一型车",a));
            allTime.add(new TimeCarInfo(String.valueOf(t*i),"二型车",b));
            allTime.add(new TimeCarInfo(String.valueOf(t*i),"三型车",c));
            allTime.add(new TimeCarInfo(String.valueOf(t*i),"四型车",d));
            allTime.add(new TimeCarInfo(String.valueOf(t*i),"五型车",e));
            allTime.add(new TimeCarInfo(String.valueOf(t*i),"六型车",f));

        }

        return allTime;
    }




        public String mapToSave(String address){

            String map = (String) redisTemplate.opsForValue().get(address);
            if (StringUtil.isNullOrEmpty(map)){
                System.out.println("map没有走redis"+address);
                String coordinate = MapTranslate.getCoordinate(address);
                if (StringUtil.isNullOrEmpty(coordinate)){
                    coordinate = MapTranslate.getCoordinate("广州");
                }
                redisTemplate.opsForValue().set(address,coordinate,20, TimeUnit.DAYS);
                return coordinate;
            } else {
                return map;
            }
        }
}
