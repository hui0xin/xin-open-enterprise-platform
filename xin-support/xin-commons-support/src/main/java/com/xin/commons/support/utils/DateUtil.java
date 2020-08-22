package com.xin.commons.support.utils;

import org.apache.commons.lang3.time.DateUtils;

import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.*;
import java.util.*;

/**
 * 日期操作类
 * @author: xin
 */
public class DateUtil {

    public static final String FORMAT_SHORTDATETIME = "yyyy-MM-dd";

    //---------------------------时间格式化 开始------------------------------

    public static String formatToGmtTime() {
        return format(new Date(), "yyyy-MM-dd HH:mm:ss");
    }
    public static String formatToGmtTime(Date date) { return format(date, "yyyy-MM-dd HH:mm:ss"); }
    public static String formatToGmtTime(long timestamp) {
        return format(timestamp, "yyyy-MM-dd HH:mm:ss");
    }

    public static String formatToShort() { return format(new Date(), "yyyy-MM-dd"); }
    public static String formatToShort(Date date) {
        return format(date, "yyyy-MM-dd");
    }
    public static String formatToShort(long timestamp) {
        return format(timestamp, "yyyy-MM-dd");
    }

    public static String formatToLongCompact() {
        return format(new Date(), "yyyyMMddHHmmss");
    }
    public static String formatToLongCompact(Date date) {
        return format(date, "yyyyMMddHHmmss");
    }
    public static String formatToLongCompact(long timestamp) {
        return format(timestamp, "yyyyMMddHHmmss");
    }

    public static String formatToShortCompact() {
        return format(new Date(), "yyyyMMdd");
    }
    public static String formatToShortCompact(Date date) {
        return format(date, "yyyyMMdd");
    }
    public static String formatToShortCompact(long timestamp) {
        return format(timestamp, "yyyyMMdd");
    }

    public static Date parseFromGmtTime(String date) throws ParseException { return parse(date, "yyyy-MM-dd HH:mm:ss"); }
    public static Date parseFromLongCompact(String date) throws ParseException { return parse(date, "yyyyMMddHHmmss"); }
    public static Date parseFromShort(String date) throws ParseException { return parse(date, "yyyy-MM-dd"); }
    public static Date parseFromShortCompact(String date) throws ParseException { return parse(date, "yyyyMMdd"); }

    public static String format(Date date, String pattern) {
        return new SimpleDateFormat(pattern).format(date);
    }
    public static String format(long currentTimestampMs, String pattern) {
        return format(new Date(currentTimestampMs), pattern);
    }
    public static Date parse(String date, String pattern) throws ParseException {
        return new SimpleDateFormat(pattern).parse(date);
    }

    //---------------------------时间格式化 结束------------------------------

    /**
     * 获取当前时间的时间戳
     * @return
     */
    public static long currentTimestampMs() {
        return System.currentTimeMillis();
    }
    /**
     * 获取当前时间
     * @return
     */
    public static Date currentDate() {
        return new Date(currentTimestampMs());
    }

    /**
     * 截取日期
     * 例如2018-01-01 12:00:00 返回2018-01-01
     */
    public static Date truncate(Date date) {
        return DateUtils.truncate(date, Calendar.DATE);
    }

    /**
     * 获取今天的日期
     * 返回2018-01-01
     */
    public static Date today() {
        return truncate(currentDate());
    }
    /**
     * 返回明天的日期
     * @return
     */
    public static Date tomorrow() {
        return addDays(today(), 1);
    }
    /**
     * 返回 指定时间的 明天 的日期
     * @return 2018-01-01
     */
    public static Date tomorrow(Date date) {
        return addDays(today(date), 1);
    }
    /**
     * 返回昨天的日期
     * @return 2018-01-01
     */
    public static Date yesterday() {
        return addDays(today(), -1);
    }
    /**
     * 返回指定时间 的昨天 的日期
     * @return 2018-01-01
     */
    public static Date yesterday(Date date) {
        return addDays(today(date), -1);
    }

    /**
     * 返回 date 这个时间的日期
     * @param date 2018-01-01 12：22：22
     * @return 2018-01-01
     */
    public static Date today(Date date) {
        return truncate(date);
    }

    /**
     * 获取 指定时间 之前/之后 amount 秒的时间
     * @param amount + 之后几秒  - 之前几秒
     */
    public static Date addSeconds(Date date, int amount) {

        return DateUtils.addSeconds(date, amount);
    }
    /**
     * 获取 当前时间 之前/之后 amount 秒的时间
     * @param amount + 之后几秒  - 之前几秒
     */
    public static Date addSeconds(int amount) {

        return addSeconds(currentDate(), amount);
    }

    /**
     * 获取 指定时间 之前/之后 amount 分钟的时间
     * @param amount + 之后几分钟  - 之前几分钟
     */
    public static Date addMinutes(Date date, int amount) {

        return DateUtils.addMinutes(date, amount);
    }
    /**
     * 获取 当前时间 之前/之后 amount 分钟的时间
     * @param amount + 之后几分钟  - 之前几分钟
     */
    public static Date addMinutes(int amount) {

        return addMinutes(currentDate(), amount);
    }

    /**
     * 获取 指定时间 之前/之后 amount 小时的时间
     * @param amount + 之后几小时 - 之前几小时
     */
    public static Date addHours(Date date, int amount) {

        return DateUtils.addHours(date, amount);
    }
    /**
     * 获取 当前时间 之前/之后 amount 小时的时间
     * @param amount + 之后几小时 - 之前几小时
     */
    public static Date addHours(int amount) {

        return addHours(currentDate(), amount);
    }

    /**
     * 获取 指定时间 之前/之后 amount 天的日期
     * amount + 表示之后天数， - 之前的天数
     */
    public static Date addDays(Date date, int amount) {

        return DateUtils.addDays(date, amount);
    }
    /**
     * 获取 当前时间 之前/之后 amount 天的时间
     * amount + 表示之后天数， - 之前的天数
     */
    public static Date addDays(int amount) {

        return DateUtils.addDays(currentDate(), amount);
    }

    /**
     * 获取 指定时间 之前/之后 amount 周的日期
     * amount + 表示之后几周， - 之前的几周
     */
    public static Date addWeeks(Date date, int amount) {

        return DateUtils.addWeeks(date, amount);
    }
    /**
     * 获取 当前时间 之前/之后 amount 周的日期
     * amount + 表示之后几周， - 之前的几周
     */
    public static Date addWeeks(int amount) {

        return DateUtils.addWeeks(currentDate(), amount);
    }

    /**
     * 获取 指定时间 之前/之后 amount 月的日期
     * amount + 表示之后几月， - 之前的几月
     */
    public static Date addMonths(Date date, int amount) {

        return DateUtils.addMonths(date, amount);
    }
    /**
     * 获取 当前时间 之前/之后 amount 月的日期
     * amount + 表示之后几月， - 之前的几月
     */
    public static Date addMonths(int amount) {

        return DateUtils.addMonths(currentDate(), amount);
    }

    /**
     * 获取 指定时间 之前/之后 amount 年的日期
     * amount + 表示之后几年， - 之前的几年
     */
    public static Date addYears(Date date, int amount) {

        return DateUtils.addYears(date, amount);
    }
    /**
     * 获取 当前时间 之前/之后 amount 年的日期
     * amount + 表示之后几年， - 之前的几年
     */
    public static Date addYears(int amount) {

        return DateUtils.addYears(currentDate(), amount);
    }

    /**
     * 比较2个日期先后 正数 是大于，负数是小于
     */
    public static int compare(Date d1, Date d2) {
        return d1.compareTo(d2);
    }

    /**
     * 获取本周日期列表
     */
    public static List<Date> findDates(LocalDate start_time, LocalDate end_time) {
        ZoneId zoneId = ZoneId.systemDefault();
        ZonedDateTime zdt = start_time.atStartOfDay(zoneId);
        ZonedDateTime zdt1 = end_time.atStartOfDay(zoneId);
        Date dBegin = Date.from(zdt.toInstant());
        Date dEnd = Date.from(zdt1.toInstant());
        List lDate = new ArrayList();
        lDate.add(dBegin);
        Calendar calBegin = Calendar.getInstance();
        // 使用给定的 Date 设置此 Calendar 的时间
        calBegin.setTime(dBegin);
        Calendar calEnd = Calendar.getInstance();
        // 使用给定的 Date 设置此 Calendar 的时间
        calEnd.setTime(dEnd);
        // 测试此日期是否在指定日期之后
        while (dEnd.after(calBegin.getTime())) {
            // 根据日历的规则，为给定的日历字段添加或减去指定的时间量
            calBegin.add(Calendar.DAY_OF_MONTH, 1);
            lDate.add(calBegin.getTime());
        }
        return lDate;
    }

    /**
     * 获取当天的开始时间
     * return 2019-02-23 00:00:00
     */
    public static Date getDayBegin() {
        Calendar cal = new GregorianCalendar();
        cal.set(Calendar.HOUR_OF_DAY, 0);
        cal.set(Calendar.MINUTE, 0);
        cal.set(Calendar.SECOND, 0);
        return cal.getTime();
    }

    /**
     * 获取当天的结束时间
     * return 2019-02-23 23:59:59
     */
    public static Date getDayEnd() {
        Calendar cal = new GregorianCalendar();
        cal.set(Calendar.HOUR_OF_DAY, 23);
        cal.set(Calendar.MINUTE, 59);
        cal.set(Calendar.SECOND, 59);
        return cal.getTime();
    }

    /**
     * 获取 昨天/明天 的开始时间
     * amount + 明天时间 - 昨天的时间
     * return 2019-02-23 00:00:00
     */
    public static Date getDayBegin(int amount) {
        Calendar cal = new GregorianCalendar();
        cal.setTime(getDayBegin());
        cal.add(Calendar.DAY_OF_MONTH, amount);
        return cal.getTime();
    }

    /**
     * 获取 昨天/明天 的结束时间
     * amount + 明天时间 - 昨天的时间
     * return 2019-02-23 23:59:59
     */
    public static Date getDayEnd(int amount) {
        Calendar cal = new GregorianCalendar();
        cal.setTime(getDayEnd());
        cal.add(Calendar.DAY_OF_MONTH, amount);
        return cal.getTime();
    }

    // 获取本周的开始时间
    public static Date getBeginDayOfWeek() {
        Date date = new Date();
        if (date == null) {
            return null;
        }
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        int dayofweek = cal.get(Calendar.DAY_OF_WEEK);
        if (dayofweek == 1) {
            dayofweek += 7;
        }
        cal.add(Calendar.DATE, 2 - dayofweek);
        return getDayStartTime(cal.getTime());
    }

    // 获取本周的结束时间
    public static Date getEndDayOfWeek() {
        Calendar cal = Calendar.getInstance();
        cal.setTime(getBeginDayOfWeek());
        cal.add(Calendar.DAY_OF_WEEK, 6);
        Date weekEndSta = cal.getTime();
        return getDayEndTime(weekEndSta);
    }

    // 获取上周的开始时间
    public static Date getBeginDayOfLastWeek() {
        Date date = new Date();
        if (date == null) {
            return null;
        }
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        int dayofweek = cal.get(Calendar.DAY_OF_WEEK);
        if (dayofweek == 1) {
            dayofweek += 7;
        }
        cal.add(Calendar.DATE, 2 - dayofweek - 7);
        return getDayStartTime(cal.getTime());
    }

    // 获取上周的结束时间
    public static Date getEndDayOfLastWeek() {
        Calendar cal = Calendar.getInstance();
        cal.setTime(getBeginDayOfLastWeek());
        cal.add(Calendar.DAY_OF_WEEK, 6);
        Date weekEndSta = cal.getTime();
        return getDayEndTime(weekEndSta);
    }

    // 获取本月的开始时间
    public static Date getBeginDayOfMonth() {
        Calendar calendar = Calendar.getInstance();
        calendar.set(getNowYear(), getNowMonth() - 1, 1);
        return getDayStartTime(calendar.getTime());
    }

    // 获取本月的结束时间
    public static Date getEndDayOfMonth() {
        Calendar calendar = Calendar.getInstance();
        calendar.set(getNowYear(), getNowMonth() - 1, 1);
        int day = calendar.getActualMaximum(5);
        calendar.set(getNowYear(), getNowMonth() - 1, day);
        return getDayEndTime(calendar.getTime());
    }

    // 获取上月的开始时间
    public static Date getBeginDayOfLastMonth() {
        Calendar calendar = Calendar.getInstance();
        calendar.set(getNowYear(), getNowMonth() - 2, 1);
        return getDayStartTime(calendar.getTime());
    }

    // 获取上月的结束时间
    public static Date getEndDayOfLastMonth() {
        Calendar calendar = Calendar.getInstance();
        calendar.set(getNowYear(), getNowMonth() - 2, 1);
        int day = calendar.getActualMaximum(5);
        calendar.set(getNowYear(), getNowMonth() - 2, day);
        return getDayEndTime(calendar.getTime());
    }

    // 获取本年的开始时间
    public static Date getBeginDayOfYear() {
        Calendar cal = Calendar.getInstance();
        cal.set(Calendar.YEAR, getNowYear());
        cal.set(Calendar.MONTH, Calendar.JANUARY);
        cal.set(Calendar.DATE, 1);
        return getDayStartTime(cal.getTime());
    }

    // 获取本年的结束时间
    public static java.util.Date getEndDayOfYear() {
        Calendar cal = Calendar.getInstance();
        cal.set(Calendar.YEAR, getNowYear());
        cal.set(Calendar.MONTH, Calendar.DECEMBER);
        cal.set(Calendar.DATE, 31);
        return getDayEndTime(cal.getTime());
    }

    // 获取某个日期的开始时间
    public static Timestamp getDayStartTime(Date d) {
        Calendar calendar = Calendar.getInstance();
        if (null != d)
            calendar.setTime(d);
        calendar.set(calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH),
                calendar.get(Calendar.DAY_OF_MONTH), 0, 0, 0);
        calendar.set(Calendar.MILLISECOND, 0);
        return new Timestamp(calendar.getTimeInMillis());
    }

    // 获取某个日期的结束时间
    public static Timestamp getDayEndTime(Date d) {
        Calendar calendar = Calendar.getInstance();
        if (null != d)
            calendar.setTime(d);
        calendar.set(calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH),
                calendar.get(Calendar.DAY_OF_MONTH), 23, 59, 59);
        calendar.set(Calendar.MILLISECOND, 999);
        return new Timestamp(calendar.getTimeInMillis());
    }

    // 获取今年是哪一年
    public static Integer getNowYear() {
        Date date = new Date();
        GregorianCalendar gc = (GregorianCalendar) Calendar.getInstance();
        gc.setTime(date);
        return Integer.valueOf(gc.get(1));
    }

    // 获取本月是哪一月
    public static int getNowMonth() {
        Date date = new Date();
        GregorianCalendar gc = (GregorianCalendar) Calendar.getInstance();
        gc.setTime(date);
        return gc.get(2) + 1;
    }

    // 两个日期相减得到的天数
    public static int getDiffDays(Date beginDate, Date endDate) {
        if (beginDate == null || endDate == null) {
            throw new IllegalArgumentException("getDiffDays param is null!");
        }
        long diff = (endDate.getTime() - beginDate.getTime())
                / (1000 * 60 * 60 * 24);
        int days = new Long(diff).intValue();
        return days;
    }

    // 两个日期相减得到的毫秒数
    public static long dateDiff(Date beginDate, Date endDate) {
        long date1ms = beginDate.getTime();
        long date2ms = endDate.getTime();
        return date2ms - date1ms;
    }


    public static int differentDays(long d1,long d2){

        return (int)(Math.abs(d1-d2)/(1000*3600*24));
    }

    /**
     * 获取两个时间秒差
     * @param d1
     * @param d2
     * @return
     */
    public static int differentSeconds(long d1,long d2){

        return (int)(Math.abs(d1-d2)/(1000));
    }

    // 获取两个日期中的最大日期
    public static Date max(Date beginDate, Date endDate) {
        if (beginDate == null) {
            return endDate;
        }
        if (endDate == null) {
            return beginDate;
        }
        if (beginDate.after(endDate)) {
            return beginDate;
        }
        return endDate;
    }

    // 获取两个日期中的最小日期
    public static Date min(Date beginDate, Date endDate) {
        if (beginDate == null) {
            return endDate;
        }
        if (endDate == null) {
            return beginDate;
        }
        if (beginDate.after(endDate)) {
            return endDate;
        }
        return beginDate;
    }

    // 返回某月该季度的第一个月
    public static Date getFirstSeasonDate(Date date) {
        final int[] SEASON = { 1, 1, 1, 2, 2, 2, 3, 3, 3, 4, 4, 4 };
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        int sean = SEASON[cal.get(Calendar.MONTH)];
        cal.set(Calendar.MONTH, sean * 3 - 3);
        return cal.getTime();
    }

    // 返回某个日期下几天的日期
    public static Date getNextDay(Date date, int i) {
        Calendar cal = new GregorianCalendar();
        cal.setTime(date);
        cal.set(Calendar.DATE, cal.get(Calendar.DATE) + i);
        return cal.getTime();
    }

    // 返回某个日期前几天的日期
    public static Date getFrontDay(Date date, int i) {
        Calendar cal = new GregorianCalendar();
        cal.setTime(date);
        cal.set(Calendar.DATE, cal.get(Calendar.DATE) - i);
        return cal.getTime();
    }

    // 获取某年某月到某年某月按天的切片日期集合(间隔天数的集合)
    public static List getTimeList(int beginYear, int beginMonth, int endYear,
                                   int endMonth, int k) {
        List list = new ArrayList();
        if (beginYear == endYear) {
            for (int j = beginMonth; j <= endMonth; j++) {
                list.add(getTimeList(beginYear, j, k));
            }
        } else {
            {
                for (int j = beginMonth; j < 12; j++) {
                    list.add(getTimeList(beginYear, j, k));
                }
                for (int i = beginYear + 1; i < endYear; i++) {
                    for (int j = 0; j < 12; j++) {
                        list.add(getTimeList(i, j, k));
                    }
                }
                for (int j = 0; j <= endMonth; j++) {
                    list.add(getTimeList(endYear, j, k));
                }
            }
        }
        return list;
    }

    // 获取某年某月按天切片日期集合(某个月间隔多少天的日期集合)
    public static List getTimeList(int beginYear, int beginMonth, int k) {
        List list = new ArrayList();
        Calendar begincal = new GregorianCalendar(beginYear, beginMonth, 1);
        int max = begincal.getActualMaximum(Calendar.DATE);
        for (int i = 1; i < max; i = i + k) {
            list.add(begincal.getTime());
            begincal.add(Calendar.DATE, k);
        }
        begincal = new GregorianCalendar(beginYear, beginMonth, max);
        list.add(begincal.getTime());
        return list;
    }

    /**
     * 时间戳转格式化
     *
     * @param timestamp
     * @return
     */
    public static Date timestamp2DateTime(long timestamp) {
        Instant instant = Instant.ofEpochMilli(timestamp);
        ZoneId zone = ZoneId.systemDefault();
        LocalDateTime localDateTime = LocalDateTime.ofInstant(instant, zone);
        Date date = localDateTime2Date(localDateTime);
        return date;
    }

    /**
     * LocalDateTime转换为Date
     *
     * @param localDateTime
     */
    public static Date localDateTime2Date(LocalDateTime localDateTime) {
        ZoneId zoneId = ZoneId.systemDefault();
        // Combines this date-time with a time-zone to create a ZonedDateTime.
        ZonedDateTime zdt = localDateTime.atZone(zoneId);
        return Date.from(zdt.toInstant());
    }

    /**
     * 获取今天的开始时间戳
     * @return
     */
    public static long getTodayStartTimestamp() {
        LocalDateTime currentDateTime = LocalDateTime.of(LocalDate.now(), LocalTime.MIN);
        long startTimestamp = currentDateTime.toInstant(ZoneOffset.of("+8")).toEpochMilli();
        return startTimestamp;
    }

    /**
     * 获取距离当前时间n天的时间戳
     *
     * @param timeMills
     * @param days
     * @return
     */
    public static long getTimestampByDays(long timeMills, int days) {
        Instant instant = Instant.ofEpochMilli(timeMills);
        ZoneId zone = ZoneId.systemDefault();
        LocalDateTime currentDateTime = LocalDateTime.ofInstant(instant, zone);
        LocalDateTime finalDateTime;
        if (days > 0) {
            finalDateTime = currentDateTime.plusDays(days);
        } else {
            finalDateTime = currentDateTime.minusDays(-days);
        }
        long finalTimestamp = finalDateTime.atZone(zone).toInstant().toEpochMilli();
        return finalTimestamp;
    }

    public static void main(String[] args) {

        System.out.println(getDayBegin().getTime());
        System.out.println(getTodayStartTimestamp());
    }

}


