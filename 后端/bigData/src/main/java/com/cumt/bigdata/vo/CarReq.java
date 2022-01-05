package com.cumt.bigdata.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @Author: ahui
 * @date: 2021/12/28 - 23:23
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class CarReq {
    public String date;
    private Integer time;
    private Integer num;
}
