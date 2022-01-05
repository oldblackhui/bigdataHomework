package com.cumt.bigdata.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

/**
 * @Author: ahui
 * @date: 2021/12/28 - 21:38
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class TimeCarInfo {
    private String time;
    private String car;
    private Integer num;
}
