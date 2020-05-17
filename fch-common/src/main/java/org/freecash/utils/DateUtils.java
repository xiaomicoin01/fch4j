package org.freecash.utils;

import org.apache.commons.lang3.StringUtils;

import java.text.SimpleDateFormat;
import java.util.*;

/**
 * 日期转换工具类
 * 使用joda-time 避免使用jdk自带的SimpleDateFormat，该类存在并发问题
 * @ClassName: DateUtils
 *
 */
public class DateUtils {

    public static final String DEFAULT_PATTERN = "yyyy-MM-dd";

    public static final String CN_PATTERN = "yyyy年MM月dd日";

    public static final String DIR_PATTERN = "yyyy/MM/dd";

    public static final String TIMESTAMP_PATTERN = "yyyy-MM-dd HH:mm:ss";

    public static final String TIMESTAMP_PATTERN_MINUTE = "yyyy-MM-dd HH:mm";

    public static final String TIMES_PATTERN = "HH:mm:ss";

    public static final String NOCHAR_PATTERN = "yyyyMMddHHmmss";

    public static final String GMT8_PATTERN = "yyMMddHHmmss";

    public static final String TIMES_NOCHAR_PATTERN = "HHmmss";

    public static final String DEFAULT_NOCHAR_PATTERN = "yyyyMMdd";

    public static final String MUTI_PATTERN = "yyyy-MM-dd'T'HH:mm:ss.SSS'Z'";

    public static final String CST_PATTERN = "EEE MMM dd HH:mm:ss zzz yyyy";

    /**
     * 转换为日期
     * @param date
     * @param datePattern
     * @return null or date
     * @author huangwei
     */
    public static Date parseDate(String date, String datePattern) throws Exception{
        if(StringUtils.isBlank(date) || StringUtils.isBlank(datePattern)){
            return null;
        }
        SimpleDateFormat format = new SimpleDateFormat(datePattern);
        return format.parse(date);
    }

    /**
     * 获得当前日期
     *
     * @return 日期
     */
    public static Date getNow() {
        Calendar cal = Calendar.getInstance();
        Date currDate = cal.getTime();
        return currDate;
    }

    /**
     * 日期转换为字符串 格式自定义
     *
     * @param date 日期
     * @param f 格式
     * @return 返回格式化的字符串
     */
    public static String dateStr(Date date, String f) {
        if (date == null) {
            return "";
        }
        SimpleDateFormat format = new SimpleDateFormat(f);
        String str = format.format(date);
        return str;
    }

    /**
     * 天数加减
     *
     * @param date 日期
     * @param num  天数
     * @return 返回加减后的天数
     */
    public static Date subDay(Date date, int num) {
        Calendar calendar1 = Calendar.getInstance();
        calendar1.setTime(date);
        calendar1.add(Calendar.DAY_OF_MONTH, num);
        return calendar1.getTime();
    }

    /**
     * 设置时分秒
     * @param date 时间
     * @param hour 小时
     * @param minute 分钟
     * @param second 秒
     * @return 返回修改后的日期
     */
    public static Date setTime(Date date, int hour, int minute, int second) {
        Calendar calendar1 = Calendar.getInstance();
        calendar1.setTime(date);
        calendar1.set(Calendar.HOUR_OF_DAY, hour);
        calendar1.set(Calendar.MINUTE, minute);
        calendar1.set(Calendar.SECOND, second);
        calendar1.set(Calendar.MILLISECOND, 0);
        Date end = calendar1.getTime();
        return end;
    }

    /**
     * 获取当前年的第一天
     * @return 日期
     */
    public static Date getCurYearFirstDay() {
        Calendar currCal = Calendar.getInstance();
        int currentYear = currCal.get(Calendar.YEAR);
        return getYearFirst(currentYear);
    }

    /**
     * 获取某年第一天日期
     * @param year 年份
     * @return Date
     */
    public static Date getYearFirst(int year){
        Calendar calendar = Calendar.getInstance();
        calendar.clear();
        calendar.set(Calendar.YEAR, year);
        Date currYearFirst = calendar.getTime();
        return currYearFirst;
    }

    /**
     * 获取又有的月份，并根据format格式化所有的时间
     * @param format
     * @return
     */
    public static List<String> getNowYearAllMonth(String format) {
        List<String> res = new ArrayList<>();
        Calendar calendar1 = Calendar.getInstance();
        int month = calendar1.get(Calendar.MONTH);
        for (int i = 0; i <= month; i++) {
            calendar1.set(Calendar.MONTH, i);
            String temp = dateStr(calendar1.getTime(), format);
            res.add(temp);
        }
        return res;
    }

    /**
     * 字符串转Date
     *
     * @param dateStr 日期字符串
     * @param format  日期字符串的格式
     * @return 日期
     * @throws Exception 异常
     */
    public static Date parse(String dateStr, String format) throws Exception {
        SimpleDateFormat f = new SimpleDateFormat(format);
        return f.parse(dateStr);
    }

    /**
     * 获取某个月的第一天
     * @param date
     * @return
     */
    public static Date getMonthFirstDay(Date date) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        calendar.set(Calendar.DAY_OF_MONTH, 1);
        Date temp = calendar.getTime();
        return setTime(temp,0,0,0);
    }

    /**
     * 获取某个月的最后一天
     * @param date
     * @return
     */
    public static Date getMonthLastDay(Date date) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        calendar.set(Calendar.DAY_OF_MONTH, calendar.getActualMaximum(Calendar.DAY_OF_MONTH));
        Date temp = calendar.getTime();
        return setTime(temp,23,59,59);
    }

    /**
     * 变化日期格式
     * @param dateStr 日志字符串
     * @param from  dateStr的格式
     * @param to  变换后的格式
     * @return 变换后的字符串
     */
    public static String changeFormat(String dateStr,String from,String to) throws Exception{
        Date d = DateUtils.parseDate(dateStr,from);
        String res = DateUtils.dateStr(d,to);
        return res;
    }

    public static Date parseSecondToDate(Long second){
        return new Date(second*1000);
    }
}
