package com.palwy.common;

import cn.hutool.json.JSONUtil;
import com.alibaba.fastjson.JSON;
import com.palwy.common.utils.WorkDayUtil;
import org.junit.Test;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.Date;
import java.util.List;

public class BaseTest {

    @Test
    public void test1(){
        WorkDayUtil workDayUtil = WorkDayUtil.getInstance();
        String dateStr = "2025-08-03";
        System.out.println("是否工作日:" + workDayUtil.isWorkingDay(LocalDate.parse(dateStr)));
    }

    @Test
    public void test2(){
        WorkDayUtil workDayUtil = WorkDayUtil.getInstance();
        List<LocalDate> localDates = workDayUtil.workDays(new Date(),10);

        List<LocalDate> localDates1 = workDayUtil.workDays(new Date(),10);

        List<LocalDate> localDates2 = workDayUtil.workDays(new Date(),10);

        System.out.println("是否工作日:" + JSON.toJSONString(localDates));
        System.out.println("是否工作日:" + JSON.toJSONString(localDates1));
        System.out.println("是否工作日:" + JSON.toJSONString(localDates2));

    }

    @Test
    public void test3(){
        String startTime =  "08:00:00";
        String endTime =  "21:00:00";
        LocalTime nowTime = LocalTime.parse("21:00:01");
        System.out.println(nowTime.compareTo(LocalTime.parse(startTime)) >= 0 && nowTime.compareTo(LocalTime.parse(endTime)) <= 0);
    }
}
