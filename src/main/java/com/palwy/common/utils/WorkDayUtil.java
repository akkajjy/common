package com.palwy.common.utils;

import cn.hutool.json.JSONUtil;
import net.objectlab.kit.datecalc.common.DefaultHolidayCalendar;
import net.objectlab.kit.datecalc.joda.LocalDateBackwardHandler;
import net.objectlab.kit.datecalc.joda.LocalDateCalculator;
import net.objectlab.kit.datecalc.joda.LocalDateForwardHandler;
import org.apache.commons.io.IOUtils;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.HttpClientBuilder;
import org.joda.time.LocalDate;
import org.joda.time.LocalDateTime;
import org.joda.time.format.DateTimeFormat;

import java.io.IOException;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.*;

/**
 * 工作日工具类
 */
public class WorkDayUtil {
    public static final String HOLIDAY_DATA_PATTERN = "yyyy-MM-dd";
    /**
     * 节假日
     */
    private static Set<LocalDate> holidayDates = new HashSet<>();
    /**
     * 调休日
     */
    private static Set<LocalDate> recuperationWorkDays = new HashSet<>();

    private static final int DEFAULT_TRADE_END_HOUR = 15;

    private int tradeEndHour = DEFAULT_TRADE_END_HOUR;
    private int currentYear = 0;

    private static final String DEFAULT_URL = "https://trans.magfin.cn/";
    private static final String DEFAULT_RESOURCE = "holiday-%d.json";

    private String dataUrl = DEFAULT_URL + DEFAULT_RESOURCE;

    private static final WorkDayUtil SINGLETON_INSTANCE = new WorkDayUtil();

    private volatile MagfinLocalDateCalculator localDateCalculator;

    private WorkDayUtil() {
    }

    private void setDataUrl(String dataUrl) {
        this.dataUrl = dataUrl;
    }

    /**
     * 初始化
     *
     * @return
     */
    public static WorkDayUtil getInstance() {
        SINGLETON_INSTANCE.initHolidays4year(LocalDate.now().getYear());
        return SINGLETON_INSTANCE;
    }

    public void setCurrentYear(int currentYear) {
        this.currentYear = currentYear;
    }

    public void setTradeEndHour(int endHour) {
        this.tradeEndHour = endHour;
    }

    public void addHolidayDate(LocalDate date) {
        holidayDates.add(date);
    }

    /**
     * 添加调休日
     *
     * @param date
     */
    public void addRecuperationDay(LocalDate date) {
        recuperationWorkDays.add(date);
    }

    public void removeHolidayDate(LocalDate date) {
        holidayDates.remove(date);
    }

    private void initHolidays4year(int year) {
        this.currentYear = year;
        String json;
        try {
            HttpClient client = HttpClientBuilder.create().build();
            HttpGet request = new HttpGet(String.format(dataUrl, year));
            request.addHeader("accept", "application/json");
            HttpResponse response = client.execute(request);
            json = IOUtils.toString(response.getEntity().getContent());
        } catch (IOException e) {
            throw new RuntimeException(String.format("获取%d年的节假日信息错误，请联系管理员", year), e);
        }

        HashMap holidaysDataMap = JSONUtil.toBean(json, HashMap.class);
        List<Map<String, String>> holiday = (List) holidaysDataMap.get("holiday");
        if (holiday == null) {
            throw new RuntimeException(String.format("获取%d年的节假日信息错误，请联系管理员", year));
        }
        holiday.stream().map(i -> i.get("date"))
                .map(d -> LocalDate.parse(d, DateTimeFormat.forPattern(HOLIDAY_DATA_PATTERN)))
                .forEach(this::addHolidayDate);
        List<Map<String, String>> recuperation = (List) holidaysDataMap.get("recuperationDay");
        if (recuperation == null) {
            throw new RuntimeException(String.format("获取%d年的节假日信息错误，请联系管理员", year));
        }

        recuperation.stream().map(i -> i.get("date"))
                .map(d -> LocalDate.parse(d, DateTimeFormat.forPattern(HOLIDAY_DATA_PATTERN)))
                .forEach(this::addRecuperationDay);

    }

    /**
     * 获取从指定的开始日期，推算offsetDays的天数，在这段时间内的工作日日期列表。
     * @param startTime
     * @param offsetDays
     * @return
     */
    public List<java.time.LocalDate> workDays(Date startTime, int offsetDays) {
        LocalDate startDate = getLocalDate(startTime);
        MagfinLocalDateCalculator localDateCalculator = createDateCalculator(startDate, offsetDays, holidayDates);
        List<java.time.LocalDate> result = new ArrayList<>();
        for (int i = 0; i < offsetDays; i++) {
            LocalDate date = startDate.plusDays(i);
            if (!localDateCalculator.isNonWorkingDay(date)) {
                result.add(java.time.LocalDate.of(date.getYear(),date.getMonthOfYear(),date.getDayOfMonth()));
            }
        }
        return result;
    }

    /**
     * 获取当月工作日
     * @param startTime 开始日期
     * @return
     */
    public List<java.time.LocalDate> getMonthWorkDays(Date startTime) {
        LocalDate startDate = getLocalDate(startTime);
        MagfinLocalDateCalculator localDateCalculator = createDateCalculator(startDate, 31, holidayDates);
        List<java.time.LocalDate> result = new ArrayList<>();
        for (int i = 0; i < 31; i++) {
            LocalDate date = startDate.plusDays(i);
            if (!localDateCalculator.isNonWorkingDay(date) && startDate.getMonthOfYear() == date.getMonthOfYear()) {
                result.add(java.time.LocalDate.of(date.getYear(),date.getMonthOfYear(),date.getDayOfMonth()));
            }
        }
        return result;
    }

    /**
     * 判断是否工作日
     *
     * @param date
     * @return
     */
    public boolean isWorkingDay(Date date) {
        LocalDate localDate = getLocalDate(date);
        MagfinLocalDateCalculator localDateCalculator = createDateCalculator(localDate, 0, holidayDates);
        return !localDateCalculator.isNonWorkingDay(localDate);
    }

    /**
     * 是否工作日
     * @param localDate
     * @return
     */
    public boolean isWorkingDay(java.time.LocalDate localDate) {
        ZoneId zoneId = ZoneId.systemDefault();
        ZonedDateTime zdt = localDate.atStartOfDay(zoneId);
        Date date = Date.from(zdt.toInstant());
        LocalDate jodaLocalDate = getLocalDate(date);
        MagfinLocalDateCalculator localDateCalculator = createDateCalculator(jodaLocalDate, 0, holidayDates);
        return !localDateCalculator.isNonWorkingDay(jodaLocalDate);
    }

    /**
     * 是否节假日
     * @param date
     * @return
     */
    public boolean isHoliday(Date date) {
        LocalDate localDate = getLocalDate(date);
        MagfinLocalDateCalculator localDateCalculator = createDateCalculator(localDate, 0, holidayDates);
        return localDateCalculator.isNonWorkingDay(localDate);
    }

    private LocalDate getLocalDate(Date startTime) {
        LocalDateTime startDateTime = LocalDateTime.fromDateFields(startTime);
        return startDateTime.toLocalDate();
    }


    private MagfinLocalDateCalculator createDateCalculator(LocalDate startDate, int durationInDays, Set<LocalDate> holidayDates) {
        if (localDateCalculator != null) {
            return localDateCalculator;
        } else {
            localDateCalculator = new MagfinLocalDateCalculator();
        }
        localDateCalculator.setStartDate(startDate);
        if (isForward(durationInDays)) {
            localDateCalculator.setHolidayHandler(new LocalDateForwardHandler());
        } else {
            localDateCalculator.setHolidayHandler(new LocalDateBackwardHandler());
        }
        updateHolidayCalendar(startDate, durationInDays, holidayDates);

        return localDateCalculator;
    }

    private boolean isForward(int durationInDays) {
        return durationInDays >= 0;
    }

    private void updateHolidayCalendar(LocalDate startDate, int durationInDays, Set<LocalDate> holidayDates) {
        List<Integer> approximateYears = getApproximateYears(startDate, durationInDays);
        String yearStart = approximateYears.get(0) + "-01-01";
        Integer integer = approximateYears.get(approximateYears.size() - 1);
        if (this.currentYear > integer) {
            integer = this.currentYear;
        } else {
            initHolidays4year(integer);
        }
        String yearEnd = integer + "-12-31";
        DefaultHolidayCalendar<LocalDate> calendar = new DefaultHolidayCalendar<>(
                holidayDates,
                new LocalDate(yearStart),
                new LocalDate(yearEnd)
        );
        localDateCalculator.setHolidayCalendar(calendar);
    }

    private List<Integer> getApproximateYears(LocalDate startDate, int durationInDays) {
        List<Integer> years = new ArrayList<>();
        int year = startDate.getYear();
        LocalDate localDate = startDate.plusDays(durationInDays);
        int endYear = localDate.getYear();

        for (int i = year; i <= endYear; i++) {
            years.add(i);
        }
        return years;
    }

    private static class MagfinLocalDateCalculator extends LocalDateCalculator {
        @Override
        public boolean isWeekend(LocalDate date) {
            if (recuperationWorkDays.contains(date)) {
                return false;
            }
            return super.isWeekend(date);
        }
    }

}


