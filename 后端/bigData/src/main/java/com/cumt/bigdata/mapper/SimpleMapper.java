package com.cumt.bigdata.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.cumt.bigdata.entity.Expressway;
import com.cumt.bigdata.vo.SFZCountAndName;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @Author: ahui
 * @date: 2021/12/19 - 22:30
 */
@Repository
public interface SimpleMapper extends BaseMapper<Expressway> {
    /*自定义查询语句*/

    IPage<Expressway> getOneMinute(IPage page,@Param("xh") String xh,@Param("bz") String bz);

    // 统计规定时间内收费站 有车辆进入的前十位
    List<SFZCountAndName> getCarsGetInSFZname10(@Param("d1") String d1, @Param("d2") String d2);

    // 统计规定时间内收费站 有车辆离开的前十位
    List<SFZCountAndName> getCarsGetOutSFZname10(@Param("d1") String d1, @Param("d2") String d2);

    List<SFZCountAndName> getAllSFZnameAndCountIn();

    List<SFZCountAndName> getAllSFZnameAndCountOut();

    List<SFZCountAndName> getTop10SFZForRK(@Param("d1") String d1, @Param("d2") String d2);
    List<SFZCountAndName> getTop10SFZForCK(@Param("d1") String d1, @Param("d2") String d2);
}