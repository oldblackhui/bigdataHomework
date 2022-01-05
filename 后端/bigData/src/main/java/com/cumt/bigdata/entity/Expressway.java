package com.cumt.bigdata.entity;

import com.alibaba.fastjson.annotation.JSONField;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;

/**
 * @Author: ahui
 * @date: 2021/12/19 - 22:29
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Expressway {
    @TableId(value = "XH")
    private Integer XH; // 序号

    @TableField(value = "RKSJ")
    /*@DateTimeFormat(pattern="yyyy-MM-dd HH:MM")//页面写入数据库时格式化
    @JSONField(format="yyyy-MM-dd HH:MM")*/
    private String RKSJ; // 入口时间

  /*  @TableField(value = "BZ")
    private String BZ; //备注
*/
    @TableField(value = "SFZCKMC")
    private String SFZCKMC; //收费站入口名称

    @TableField(value = "CKSJ")
    /*@DateTimeFormat(pattern="yyyy-MM-dd HH:MM")//页面写入数据库时格式化
    @JSONField(format="yyyy-MM-dd HH:MM")*/
    private String CKSJ; //出口时间

    @TableField(value = "CX")
    private String CX; //车型

    @TableField(value = "SFZRKMC")
    private String SFZRKMC; // 收费站出口名称

    @TableField(value = "CP")
    private String CP; //车牌号



}
