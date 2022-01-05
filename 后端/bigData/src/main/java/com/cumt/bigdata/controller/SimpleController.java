package com.cumt.bigdata.controller;

import com.cumt.bigdata.service.SimpleService;
import com.cumt.bigdata.vo.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.text.ParseException;
import java.util.List;
import java.util.Map;

/**
 * @Author: ahui
 * @date: 2021/12/24 - 17:53
 */
@RestController
@ResponseBody
public class SimpleController {

    @Autowired
    private SimpleService simpleService;

    @GetMapping("/rk/allin")
    public List<SFZCountAndName> findAllSFZnameIn() {

        return simpleService.findAllSFZnameIn();
    }

    @GetMapping("/ck/allout")
    public List<SFZCountAndName> findAllSFZnameOut() {

        return simpleService.findAllSFZnameOut();
    }

    @PostMapping("/rk/daysfznum")
    public int getDaySFZUseNumForRK(@RequestBody TimeReq timeReq) {
        return simpleService.getDaySFZUseNumForRK(timeReq.d1, timeReq.d2);
    }
    @PostMapping("/ck/daysfznum")
    public int getDaySFZUseNumForCK(@RequestBody TimeReq timeReq) {
        return simpleService.getDaySFZUseNumForCK(timeReq.d1, timeReq.d2);
    }

    @PostMapping("/rk/daycarnum")
    public int getDayNumForRK(@RequestBody TimeReq timeReq) {
        return simpleService.getDayNumForRK(timeReq.d1, timeReq.d2);
    }
    @PostMapping("/ck/daycarnum")
    public int getDayNumForCK(@RequestBody TimeReq timeReq) {
        return simpleService.getDayNumForCK(timeReq.d1, timeReq.d2);
    }
    @PostMapping("/all/topsfz")
    public Map<String,Integer> getTop10SFZ(@RequestBody TimeReq timeReq) {
        return simpleService.getTop10SFZ(timeReq.d1, timeReq.d2);
    }

    @PostMapping("/ck/carnum")
    public int findMinutesCarNumForSFZCK(@RequestBody TimeReq timeReq) {
        return simpleService.getMinutesCarNumForSFZCK(timeReq.d1, timeReq.d2, timeReq.sfzname);
    }

    @PostMapping(("/rk/carnum"))
    public int findMinutesCarNumForSFZRK(@RequestBody TimeReq timeReq) {
        return simpleService.getMinutesCarNumForSFZRK(timeReq.d1, timeReq.d2, timeReq.sfzname);
    }

    @PostMapping("/ck/from")
    public Map<String, Integer> findMinutesCarFromForSFZCKCP(@RequestBody TimeReq timeReq) {
        return simpleService.getMinutesCarFromForSFZCKCP(timeReq.d1, timeReq.d2);
    }

    @PostMapping("/rk/from")
    public Map<String, Integer> findMinutesCarFromForSFZRKCP(@RequestBody TimeReq timeReq) {
        return simpleService.getMinutesCarFromForSFZRKCP(timeReq.d1, timeReq.d2);
    }

    @PostMapping("/rk/sfz")
    public List<SFZCountAndName> findCarsGetInSFZname10(@RequestBody TimeReq timeReq) {
        return simpleService.getCarsGetInSFZname10(timeReq.d1, timeReq.d2);
    }

    @PostMapping("/ck/sfz")
    public List<SFZCountAndName> findCarsGetOutSFZname10(@RequestBody TimeReq timeReq) {
        return simpleService.getCarsGetOutSFZname10(timeReq.d1, timeReq.d2);
    }

    @PostMapping("/rk/fromcity")
    public List<MapData> getSFZRKCarFromWhere(@RequestBody TimeReq timeReq) {
            return simpleService.getSFZRKMapInfo(timeReq.d1, timeReq.d2,timeReq.sfzname);
    }
    @PostMapping("/ck/fromcity")
    public List<MapData> getSFZCKCarFromWhere(@RequestBody TimeReq timeReq) {
        return simpleService.getSFZCKMapInfo(timeReq.d1, timeReq.d2,timeReq.sfzname);
    }

    //总的
    @PostMapping("/rk/allfromcity")
    public List<MapData> getSFZRKCarFromWhereAll(@RequestBody TimeReq timeReq) {
        return simpleService.getSFZRKMapInfoAll(timeReq.d1, timeReq.d2);
    }
    @PostMapping("/ck/allfromcity")
    public List<MapData> getSFZCKCarFromWhereAll(@RequestBody TimeReq timeReq) {
        return simpleService.getSFZCKMapInfoAll(timeReq.d1, timeReq.d2);
    }
    //===

    @PostMapping("/rk/rktock")
    public List<RKCKInfo> getRKInfoRKtoCK(@RequestBody TimeReq timeReq){
        return simpleService.getRKInfoRKtoCK(timeReq.d1,timeReq.d2);
    }

    @PostMapping("/ck/rktock")
    public List<RKCKInfo> getCKInfoRKtoCK(@RequestBody TimeReq timeReq){
        return simpleService.getCKInfoRKtoCK(timeReq.d1,timeReq.d2);
    }

    @PostMapping("/rk/carkind")
    public List<CarInfo> getRkKindsCarNum(@RequestBody TimeReq timeReq){
        return simpleService.getRkKindsCarNum(timeReq.d1,timeReq.d2);
    }
    @PostMapping("/ck/carkind")
    public List<CarInfo> getCkKindsCarNum(@RequestBody TimeReq timeReq){
        return simpleService.getCkKindsCarNum(timeReq.d1,timeReq.d2);
    }

    @PostMapping("/rk/timecar")
    public List<TimeCarInfo> getTimeRKKindsCarNum(@RequestBody CarReq carReq){
        try {
            return simpleService.getTimeRKKindsCarNum(carReq.getDate(),carReq.getTime(),carReq.getNum());
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return null;
    }
    @PostMapping("/ck/timecar")
    public List<TimeCarInfo> getTimeCKKindsCarNum(@RequestBody CarReq carReq){
        try {
            return simpleService.getTimeCKKindsCarNum(carReq.getDate(),carReq.getTime(),carReq.getNum());
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return null;
    }

}