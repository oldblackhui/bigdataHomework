package com.cumt.bigdata.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @Author: ahui
 * @date: 2021/12/25 - 21:22
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class MapData {

    private String cityname;

    private Integer num;

    private String lng;
    private String lat;

}
