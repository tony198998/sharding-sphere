package com.wode.util;

import com.google.common.collect.Maps;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.time.DateFormatUtils;
import org.apache.commons.lang3.time.FastDateFormat;
import org.joda.time.DateTime;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.Instant;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.*;
import java.util.concurrent.TimeUnit;


public final class DateUtils {
    private static final Map<Integer, String> WEEK_DAY_CACHE = Maps.newHashMapWithExpectedSize(7);
    private static final Map<Integer, String> WEEK_DAY_CACHE_FORMAT = Maps.newHashMapWithExpectedSize(7);

    private static final String[] PATTERNS = new String[]{
            "yyyy-MM-dd",
            "yyyy-MM-dd HH",
            "yyyy-MM-dd HH:mm",
            "yyyy-MM-dd HH:mm:ss",
            "yyyy-MM-dd mm:ss",
            "yyyy-MM-dd ss",
            "yyMMdd",
            "yyyy年MM月dd日",
    };


    static {
        WEEK_DAY_CACHE.put(0, "周日");
        WEEK_DAY_CACHE.put(1, "周一");
        WEEK_DAY_CACHE.put(2, "周二");
        WEEK_DAY_CACHE.put(3, "周三");
        WEEK_DAY_CACHE.put(4, "周四");
        WEEK_DAY_CACHE.put(5, "周五");
        WEEK_DAY_CACHE.put(6, "周六");

        WEEK_DAY_CACHE_FORMAT.put(0, "星期日");
        WEEK_DAY_CACHE_FORMAT.put(1, "星期一");
        WEEK_DAY_CACHE_FORMAT.put(2, "星期二");
        WEEK_DAY_CACHE_FORMAT.put(3, "星期三");
        WEEK_DAY_CACHE_FORMAT.put(4, "星期四");
        WEEK_DAY_CACHE_FORMAT.put(5, "星期五");
        WEEK_DAY_CACHE_FORMAT.put(6, "星期六");
    }

    private static final FastDateFormat DATE_PARSER = FastDateFormat.getInstance("yyyy-MM-dd");
    private static final FastDateFormat DATE_STR_PARSER = FastDateFormat.getInstance("yyyy年MM月dd日");
    private static final FastDateFormat DATETIME_PARSER = FastDateFormat.getInstance("yyyy-MM-dd HH:mm:ss");
    private static final FastDateFormat DATETIMEMMDDHHmmss_PARSER = FastDateFormat.getInstance("MM月dd日 HH:mm:ss");
    private static final FastDateFormat DATE_MINUTE_TIME_PARSER = FastDateFormat.getInstance("yyyy-MM-dd HH:mm");

    private static final FastDateFormat DATESIMPLE = FastDateFormat.getInstance("yyMMdd");
    private static final FastDateFormat DATESIMPLE_TIME = FastDateFormat.getInstance("yyyyMMdd");
    private static final FastDateFormat DATE_HHmm = FastDateFormat.getInstance("HH:mm");
    private static final FastDateFormat DATE_HHmmss = FastDateFormat.getInstance("HH:mm:ss");

    public static Date getNow(){
        Calendar cal = Calendar.getInstance();
        return cal.getTime();
    }

    public static Date parseDate(String date) throws ParseException {
        return DATE_PARSER.parse(date);
    }

    public static Date parseDateTime(String date) throws ParseException {
        return DATETIME_PARSER.parse(date);
    }

    public static Date parseDateMinuteTime(String date) throws ParseException {
        return DATE_MINUTE_TIME_PARSER.parse(date);
    }

    public static Date parseDateHHmm(String date) throws ParseException {
        return DATE_HHmm.parse(date);
    }

    public static Date parseDateTimeFromStr(String date) {
        try {
            return DATETIME_PARSER.parse(date);
        } catch (ParseException e) {
            //时间转换异常处理
        }
        return null;
    }

    /**
     * 字符串转日期 日期格式 HH:MM:ss
     * @param date
     * @return
     * @throws ParseException
     */
    public static Date parseDateHHmmss(String date) throws ParseException {
        return DATE_HHmmss.parse(date);
    }

    public static Date parseDateyyyyMMdd(String date) throws ParseException {
        return DATESIMPLE_TIME.parse(date);
    }

    /**
     * 日期转字符串 日期格式 HH:MM:ss
     * @param date
     * @return
     * @throws ParseException
     */
    public static String formatDateHHmmss(Date date){
        return DATE_HHmmss.format(date);
    }

    public static String formatDate(Date date) {
        return DATE_PARSER.format(date);
    }

    public static String formatDatetime(Date date) {
        return DATETIME_PARSER.format(date);
    }

    /**
     * 日期转换 格式MM月dd日 HH:mm:ss
     * @param date
     * @return
     */
    public static String formatDatetMMddHHmmss(Date date) {
        return DATETIMEMMDDHHmmss_PARSER.format(date);
    }

    public static String formatDateSimple(Date date) {
        return DATESIMPLE.format(date);
    }

    public static String formatDateToHHMM(Date date) {
        return DATE_HHmm.format(date);
    }


    public static String formatDateToyyyyMMdd(Date date) {
        return DATESIMPLE_TIME.format(date);
    }
    public static String formatDateToyyyyMMddHHmm(Date date) {
        return DATE_MINUTE_TIME_PARSER.format(date);
    }


    public static String formatDateStr(Date date){
        return DATE_STR_PARSER.format(date);
    }

    public static String getDateStrToyyyyMMdd(){
        return formatDateToyyyyMMdd(getNow());
    }

    public static long dateDiff(Date d1, Date d2) {
        if (d1 == null || d2 == null) {
            throw new IllegalArgumentException("date diff use null params");
        }
        return d1.getTime() - d2.getTime();
    }

    public static Date getDate(Date date, int field, int value) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);

        calendar.add(field, value);

        return calendar.getTime();
    }

    public static boolean sameToCurrentDayOfYear(long time) {
        Calendar calendar = Calendar.getInstance();
        int currentDayOfYear = calendar.get(Calendar.DAY_OF_YEAR);

        calendar.setTimeInMillis(time);
        int compared = calendar.get(Calendar.DAY_OF_YEAR);

        return currentDayOfYear == compared;
    }

    public static Date getStartOfDay(Date date) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        calendar.set(Calendar.HOUR_OF_DAY, 0);
        calendar.set(Calendar.MINUTE, 0);
        calendar.set(Calendar.SECOND, 0);
        return calendar.getTime();
    }

    public static Date getLastOfDay(Date date) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        calendar.set(Calendar.HOUR_OF_DAY, 23);
        calendar.set(Calendar.MINUTE, 59);
        calendar.set(Calendar.SECOND, 59);
        return calendar.getTime();
    }

    public static Date getNextFirstWeekOfDay(Date date, int hour, int minites, int seconds) {
        Calendar calendar = Calendar.getInstance();
        calendar.setFirstDayOfWeek(Calendar.SATURDAY);
        calendar.set(Calendar.DAY_OF_WEEK, calendar.getFirstDayOfWeek() + 7);
        calendar.set(Calendar.HOUR_OF_DAY, hour);
        calendar.set(Calendar.MINUTE, minites);
        calendar.set(Calendar.SECOND, seconds);

        if (calendar.getTime().getTime() < date.getTime()) {
            calendar.add(Calendar.DATE, 7);
        }

        return calendar.getTime();
    }

    /**
     * 获取日期 上周一
     * @return
     */
    public static Date getLastWeekMonday() {
        Calendar cal = Calendar.getInstance();
        cal.setTime(getStartOfThisWeek());
        cal.add(Calendar.DATE, -7);
        return cal.getTime();
    }

    /**
     * 得到本周周一
     */
    public static Date getStartOfThisWeek() {
        Calendar calendar = Calendar.getInstance();
        calendar.setFirstDayOfWeek(Calendar.MONDAY);
        calendar.set(Calendar.DAY_OF_WEEK, Calendar.MONDAY);
        calendar.set(Calendar.HOUR_OF_DAY, 0);
        calendar.set(Calendar.MINUTE, 0);
        calendar.set(Calendar.SECOND, 0);
        return calendar.getTime();
    }

    /**
     * 获取本月开始日期
     * @return String
     * **/
    public static Date getMonthStart(){
        Calendar cal=Calendar.getInstance();
        cal.add(Calendar.MONTH, 0);
        cal.set(Calendar.DAY_OF_MONTH, 1);
        Date time=cal.getTime();
        return time;
    }

    /**
     * 得到本周周日
     */
    public static Date getEndOfThisWeek() {
        Calendar calendar = Calendar.getInstance();
        calendar.setFirstDayOfWeek(Calendar.MONDAY);
        calendar.set(Calendar.DAY_OF_WEEK, Calendar.SUNDAY);
        calendar.set(Calendar.HOUR_OF_DAY, 23);
        calendar.set(Calendar.MINUTE, 59);
        calendar.set(Calendar.SECOND, 59);
        return calendar.getTime();
    }

    public static Date getNextWeekTodayLast() {
        Date date = new Date();
        LocalDate localDate = date.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
        Date nextWeekToday = java.sql.Date.valueOf(localDate.plusDays(7));
        return getLastOfDay(nextWeekToday);
    }

    /**
     * 计算当天剩余时间 秒
     * @return
     */
    public static long todaySurplusTime(){
        Date now = getNow();
        Date lastOfDay = getLastOfDay(now);
        long seconds = DateUtils.dateDiff(lastOfDay, now) / 1000;
        return seconds;
    }

    public static Optional<Date> parseDateWithPatterns(String dataStr) {
        if (StringUtils.isBlank(dataStr)) {
            return Optional.empty();
        }
        try {
            return Optional.ofNullable(org.apache.commons.lang3.time.DateUtils.parseDate(dataStr, PATTERNS));
        } catch (Exception e) {
            return Optional.empty();
        }
    }

    public static String dateConvert(Date date) {
        DateTime today = DateTime.now().withMillisOfDay(0);
        DateTime dateTime = new DateTime(date).withMillisOfDay(0);
        if(today.getDayOfYear() == dateTime.getDayOfYear()) {
            return "今天";
        } else if (today.getDayOfYear() + 1 == dateTime.getDayOfYear()) {
            return "明天";
        } else if (today.getDayOfYear() + 2 == dateTime.getDayOfYear()) {
            return "后天";
        }

        // xx月xx日 周x
        return DateFormatUtils.format(date, "MM月dd日 ") + new DateTime(date).dayOfWeek().getAsShortText(Locale.CHINESE);
    }

    /**
     * 获取月和日
     * @param date
     * @return
     */
    public static String getNowDateMMdd(Date date) {
        // xx月xx日
        String time=DateFormatUtils.format(date, "MM月dd日 ");
        if (time.startsWith("0")){
            return time.substring(1);
        }else {
            DateFormatUtils.format(date, "MM月dd日 ");
        }
        return DateFormatUtils.format(date, "MM月dd日 ");
    }

    /**
     * 进行日期 加减天数
     * @param date
     * @param num
     * @return
     */
    public static Date dateToSubDays(Date date,Integer num){
        Calendar calendar1=Calendar.getInstance();
        calendar1.setTime(date);
        calendar1.add(Calendar.DAY_OF_MONTH,num);
        return calendar1.getTime();
    }

    /**
     * 计算两个时间相隔天数
     * @param curDate
     * @param befDate
     * @return
     */
    public static int calTimeIntervalDays(Date curDate, Date befDate) {
        Instant instant = curDate.toInstant();
        Instant instant1 = befDate.toInstant();
        ZoneId zone = ZoneId.systemDefault();
        LocalDateTime localDateTime = LocalDateTime.ofInstant(instant, zone);
        LocalDateTime localDateTime1 = LocalDateTime.ofInstant(instant1, zone);
        LocalDate localDate = localDateTime.toLocalDate();
        LocalDate localDate1 = localDateTime1.toLocalDate();
        return (int) (localDate.toEpochDay()-localDate1.toEpochDay());
    }

    /**
     * 时间 + hours
     * @param date
     * @param hours
     * @return
     */
    public static Date dateAddHours(Date date,int hours){
        Calendar calendar1=Calendar.getInstance();
        calendar1.setTime(date);
        calendar1.add(Calendar.HOUR_OF_DAY,hours);
        return calendar1.getTime();
    }
    /**
     * 时间 + Minutes
     * @param date
     * @param minutes
     * @return
     */
    public static Date dateAddMinutes(Date date,int minutes){
        Calendar calendar1=Calendar.getInstance();
        calendar1.setTime(date);
        calendar1.add(Calendar.MINUTE,minutes);
        return calendar1.getTime();
    }

    /**
     * 根据每周的第几天获取周几
     * @param dayOfWeek 每周的第几天
     * @return String
     */
    public static String getWeekDayStrByDayOfWeek(int dayOfWeek) {
        return WEEK_DAY_CACHE.get(dayOfWeek);
    }

    /**
     * 根据当前日期获取星期几
     * @param date
     * @return String
     */
    public static String getWeekDayStrByDate(Date date) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        int res = calendar.get(Calendar.DAY_OF_WEEK)-1;
        return WEEK_DAY_CACHE_FORMAT.get(res);
    }

    /**
     * 将毫秒值转为时分秒
     * @param seconds
     * @return
     */
    public static String secondToTime(long seconds) {
        long m = seconds/1000;
        if(m < 60) {//秒

            return NumFormat(0) + ":" + NumFormat(m);
        }

        if(m < 3600) {//分

            return NumFormat(m / 60) + ":" + NumFormat(m % 60);
        }

        if(m < 3600 * 24) {//时

            return NumFormat(m / 60 / 60) + ":" + NumFormat(m / 60 % 60) + ":" + NumFormat(m % 60);
        }

        if(m >= 3600 * 24) {//天

            return NumFormat(m / 60 / 60 /24) + "天" +NumFormat(m / 60 / 60 % 24) + ":" + NumFormat(m / 60 % 60) + ":" + NumFormat(m % 60);
        }

        return "--";
    }

    public static String NumFormat(long i) {
        if(String.valueOf(i).length() < 2) {
            return "0"+i;
        }else {
            return String.valueOf(i);
        }
    }

    /**
     * 获取当前属于年度周次
     * @return
     */
    public static int getWeekOfYear(){
        Calendar calendar = Calendar.getInstance();
        calendar.setFirstDayOfWeek(Calendar.MONDAY);
        return calendar.get(Calendar.WEEK_OF_YEAR);
    }

    /**
     * 获取某时间属于年度周次
     * @return
     */
    public static int getWeekOfYearByDate(Date date){
        Calendar calendar = Calendar.getInstance();
        calendar.setFirstDayOfWeek(Calendar.MONDAY);
        calendar.setTime(date);
        return calendar.get(Calendar.WEEK_OF_YEAR);
    }

    public static int getDayOfWeek(){
        Calendar calendar = Calendar.getInstance();
        return calendar.get(Calendar.DAY_OF_WEEK);
    }

    /**
     * 将时间格式化为 *小时*分
     * @param unit
     * @param time
     * @return
     */
    public static String formatTime(TimeUnit unit,long time){
        long hours = 0;
        long totalMinutes = unit.toMinutes(time);
        long minutes = totalMinutes;

        if (totalMinutes > 0) {
            hours = unit.toHours(time);
        }
        if (hours > 0){
            minutes = totalMinutes - (TimeUnit.HOURS.toMinutes(hours));
        }

        return String.format("%d小时%d分",hours,minutes);
    }

    /**
     * 获取两个日期之间的日期列表
     * @param startTime yyyyMMdd
     * @param endTime yyyyMMdd
     * @return
     */
    public static List<String> getListDiffDaysToyyyyMMdd(String startTime, String endTime) {

        // 返回的日期集合
        List<String> days = new ArrayList<String>();

        DateFormat dateFormat = new SimpleDateFormat("yyyyMMdd");
        try {
            Date start = dateFormat.parse(startTime);
            Date end = dateFormat.parse(endTime);
            end = dateToSubDays(end,-1);
            Calendar tempStart = Calendar.getInstance();
            tempStart.setTime(start);
//            tempStart.add(Calendar.DATE, +1);// 日期加1(包含结束)

            Calendar tempEnd = Calendar.getInstance();
            tempEnd.setTime(end);
            tempEnd.add(Calendar.DATE, +1);// 日期加1(包含结束)
            while (tempStart.before(tempEnd)) {
                days.add(dateFormat.format(tempStart.getTime()));
                tempStart.add(Calendar.DAY_OF_YEAR, 1);
            }

        } catch (ParseException e) {
            e.printStackTrace();
        }

        return days;
    }

    public static void main(String[] args) {


//        String start = "20200901";
//        String end = "20200912";
//        List<String> list = getListDiffDaysToyyyyMMdd(start,end);
//        System.out.println(list.toString());
        String endDate = null;
        try {
            endDate = DateUtils.formatDateToyyyyMMdd(DateUtils.dateToSubDays(DateUtils.parseDateyyyyMMdd("20200908"),-10));
        } catch (ParseException e) {
        }

        String start = endDate;
        String end = "20200908";
        List<String> list = getListDiffDaysToyyyyMMdd(start,end);
        System.out.println(list);
    }

}
