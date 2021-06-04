package com.union.design.common.util;


import java.text.SimpleDateFormat;
import java.util.*;


public class DateUtils {

    /**
     * 获取SimpleDateFormat
     *
     * @param pattern 日期格式
     * @return SimpleDateFormat对象
     * @throws RuntimeException 异常：非法日期格式
     */
    private static SimpleDateFormat getDateFormat(String pattern) throws RuntimeException {
        return new SimpleDateFormat(pattern);
    }

    /**
     * 获取日期中的某数值。如获取月份
     *
     * @param date     日期
     * @param dateType 日期格式
     * @return 数值
     */
    private static int getInteger(Date date, int dateType) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        return calendar.get(dateType);
    }

    /**
     * 增加日期中某类型的某数值。如增加日期
     *
     * @param date     日期字符串
     * @param dateType 类型
     * @param amount   数值
     * @return 计算后日期字符串
     */
    private static String addInteger(String date, int dateType, int amount) {
        String dateString = null;
        DateStyle dateStyle = getDateStyle(date);
        if (dateStyle != null) {
            Date newDate = stringToDate(date, dateStyle);
            newDate = addInteger(newDate, dateType, amount);
            dateString = dateToString(newDate, dateStyle);
        }
        return dateString;
    }

    /**
     * 增加日期中某类型的某数值。如增加日期
     *
     * @param date     日期
     * @param dateType 类型
     * @param amount   数值
     * @return 计算后日期
     */
    private static Date addInteger(Date date, int dateType, int amount) {
        Date newDate = null;
        if (date != null) {
            Calendar calendar = Calendar.getInstance();
            calendar.setTime(date);
            calendar.add(dateType, amount);
            newDate = calendar.getTime();
        }
        return newDate;
    }

    /**
     * 获取精确的日期
     *
     * @param timestamps 时间long集合
     * @return 日期
     */
    private static Date getAccurateDate(List<Long> timestamps) {
        Date date = null;
        long timestamp = 0;
        Map<Long, long[]> map = new HashMap<>();
        List<Long> absoluteValues = new ArrayList<>();

        if (timestamps != null && timestamps.size() > 0) {
            if (timestamps.size() > 1) {
                for (int i = 0; i < timestamps.size(); i++) {
                    for (int j = i + 1; j < timestamps.size(); j++) {
                        long absoluteValue = Math.abs(timestamps.get(i) - timestamps.get(j));
                        absoluteValues.add(absoluteValue);
                        long[] timestampTmp = {timestamps.get(i), timestamps.get(j)};
                        map.put(absoluteValue, timestampTmp);
                    }
                }

                // 有可能有相等的情况。如2012-11和2012-11-01。时间戳是相等的  
                long minAbsoluteValue = -1;
                if (!absoluteValues.isEmpty()) {
                    // 如果timestamps的size为2，这是差值只有一个，因此要给默认值  
                    minAbsoluteValue = absoluteValues.get(0);
                }
                for (int i = 0; i < absoluteValues.size(); i++) {
                    for (int j = i + 1; j < absoluteValues.size(); j++) {
                        if (absoluteValues.get(i) > absoluteValues.get(j)) {
                            minAbsoluteValue = absoluteValues.get(j);
                        } else {
                            minAbsoluteValue = absoluteValues.get(i);
                        }
                    }
                }

                if (minAbsoluteValue != -1) {
                    long[] timestampsLastTmp = map.get(minAbsoluteValue);
                    if (absoluteValues.size() > 1) {
                        timestamp = Math.max(timestampsLastTmp[0], timestampsLastTmp[1]);
                    } else if (absoluteValues.size() == 1) {
                        // 当timestamps的size为2，需要与当前时间作为参照  
                        long dateOne = timestampsLastTmp[0];
                        long dateTwo = timestampsLastTmp[1];
                        if ((Math.abs(dateOne - dateTwo)) < 100000000000L) {
                            timestamp = Math.max(timestampsLastTmp[0], timestampsLastTmp[1]);
                        } else {
                            long now = new Date().getTime();
                            if (Math.abs(dateOne - now) <= Math.abs(dateTwo - now)) {
                                timestamp = dateOne;
                            } else {
                                timestamp = dateTwo;
                            }
                        }
                    }
                }
            } else {
                timestamp = timestamps.get(0);
            }
        }

        if (timestamp != 0) {
            date = new Date(timestamp);
        }
        return date;
    }

    /**
     * 判断字符串是否为日期字符串
     *
     * @param date 日期字符串
     * @return true or false
     */
    public static boolean isDate(String date) {
        boolean isDate = false;
        if (date != null) {
            if (stringToDate(date) != null) {
                isDate = true;
            }
        }
        return isDate;
    }

    /**
     * 获取日期字符串的日期风格。失敗返回null。
     *
     * @param date 日期字符串
     * @return 日期风格
     */
    public static DateStyle getDateStyle(String date) {
        DateStyle dateStyle;
        Map<Long, DateStyle> map = new HashMap<>();
        List<Long> timestamps = new ArrayList<>();
        for (DateStyle style : DateStyle.values()) {
            Date dateTmp = stringToDate(date, style.getValue());
            if (dateTmp != null) {
                timestamps.add(dateTmp.getTime());
                map.put(dateTmp.getTime(), style);
            }
        }
        dateStyle = map.get(getAccurateDate(timestamps).getTime());
        return dateStyle;
    }

    /**
     * 将日期字符串转化为日期。失败返回null。
     *
     * @param dateString 日期字符串
     * @return 日期
     */
    public static Date stringToDate(String dateString) {
        DateStyle dateStyle = null;
        return stringToDate(dateString, dateStyle);
    }

    /**
     * 将日期字符串转化为日期。失败返回null。
     *
     * @param dateString     日期字符串
     * @param pattern 日期格式
     * @return 日期
     */
    public static Date stringToDate(String dateString, String pattern) {
        Date date = null;
        if (dateString != null) {
            try {
                date = getDateFormat(pattern).parse(dateString);
            } catch (Exception e) {
            }
        }
        return date;
    }

    /**
     * 将日期字符串转化为日期。失败返回null。
     *
     * @param dateString      日期字符串
     * @param dateStyle 日期风格
     * @return 日期
     */
    public static Date stringToDate(String dateString, DateStyle dateStyle) {
        Date date = null;
        if (dateStyle == null) {
            List<Long> timestamps = new ArrayList<>();
            for (DateStyle style : DateStyle.values()) {
                Date dateTmp = stringToDate(dateString, style.getValue());
                if (dateTmp != null) {
                    timestamps.add(dateTmp.getTime());
                }
            }
            date = getAccurateDate(timestamps);
        } else {
            date = stringToDate(dateString, dateStyle.getValue());
        }
        return date;
    }

    /**
     * 将日期转化为日期字符串。失败返回null。
     *
     * @param date     日期
     * @param pattern 日期格式
     * @return 日期字符串
     */
    public static String dateToString(Date date, String pattern) {
        String dateString = null;
        if (date != null) {
            try {
                dateString = getDateFormat(pattern).format(date);
            } catch (Exception e) {
            }
        }
        return dateString;
    }

    /**
     * 将日期转化为日期字符串。失败返回null。
     *
     * @param date      日期
     * @param dateStyle 日期风格
     * @return 日期字符串
     */
    public static String dateToString(Date date, DateStyle dateStyle) {
        String dateString = null;
        if (dateStyle != null) {
            dateString = dateToString(date, dateStyle.getValue());
        }
        return dateString;
    }

    /**
     * 将日期字符串转化为另一日期字符串。失败返回null。
     *
     * @param date     旧日期字符串
     * @param pattern 新日期格式
     * @return 新日期字符串
     */
    public static String stringToString(String date, String pattern) {
        return stringToString(date, null, pattern);
    }

    /**
     * 将日期字符串转化为另一日期字符串。失败返回null。
     *
     * @param date      旧日期字符串
     * @param dateStyle 新日期风格
     * @return 新日期字符串
     */
    public static String stringToString(String date, DateStyle dateStyle) {
        return stringToString(date, null, dateStyle);
    }

    /**
     * 将日期字符串转化为另一日期字符串。失败返回null。
     *
     * @param oldDateString         旧日期字符串
     * @param oldPattern 旧日期格式
     * @param newPattern  新日期格式
     * @return 新日期字符串
     */
    public static String stringToString(String oldDateString, String oldPattern, String newPattern) {
        String newDateString = null;
        if (oldPattern == null) {
            DateStyle style = getDateStyle(oldDateString);
            if (style != null) {
                Date oldDate = stringToDate(oldDateString, style.getValue());
                newDateString = dateToString(oldDate, newPattern);
            }
        } else {
            Date oldDate = stringToDate(oldDateString, oldPattern);
            newDateString = dateToString(oldDate, newPattern);
        }
        return newDateString;
    }

    /**
     * 将日期字符串转化为另一日期字符串。失败返回null。
     *
     * @param date         旧日期字符串
     * @param oldDateStyle 旧日期风格
     * @param newDateStyle 新日期风格
     * @return 新日期字符串
     */
    public static String stringToString(String date, DateStyle oldDateStyle, DateStyle newDateStyle) {
        String dateString = null;
        if (oldDateStyle == null) {
            DateStyle style = getDateStyle(date);
            dateString = stringToString(date, style.getValue(), newDateStyle.getValue());
        } else {
            dateString = stringToString(date, oldDateStyle.getValue(), newDateStyle.getValue());
        }
        return dateString;
    }

    /**
     * 增加日期的年份。失败返回null。
     *
     * @param date       日期
     * @param yearAmount 增加数量。可为负数
     * @return 增加年份后的日期字符串
     */
    public static String addYear(String date, int yearAmount) {
        return addInteger(date, Calendar.YEAR, yearAmount);
    }

    /**
     * 增加日期的年份。失败返回null。
     *
     * @param date       日期
     * @param yearAmount 增加数量。可为负数
     * @return 增加年份后的日期
     */
    public static Date addYear(Date date, int yearAmount) {
        return addInteger(date, Calendar.YEAR, yearAmount);
    }

    /**
     * 增加日期的月份。失败返回null。
     *
     * @param date       日期
     * @param yearAmount 增加数量。可为负数
     * @return 增加月份后的日期字符串
     */
    public static String addMonth(String date, int yearAmount) {
        return addInteger(date, Calendar.MONTH, yearAmount);
    }

    /**
     * 增加日期的月份。失败返回null。
     *
     * @param date       日期
     * @param yearAmount 增加数量。可为负数
     * @return 增加月份后的日期
     */
    public static Date addMonth(Date date, int yearAmount) {
        return addInteger(date, Calendar.MONTH, yearAmount);
    }

    /**
     * 增加日期的天数。失败返回null。
     *
     * @param date      日期字符串
     * @param dayAmount 增加数量。可为负数
     * @return 增加天数后的日期字符串
     */
    public static String addDay(String date, int dayAmount) {
        return addInteger(date, Calendar.DATE, dayAmount);
    }

    /**
     * 增加日期的天数。失败返回null。
     *
     * @param date      日期
     * @param dayAmount 增加数量。可为负数
     * @return 增加天数后的日期
     */
    public static Date addDay(Date date, int dayAmount) {
        return addInteger(date, Calendar.DATE, dayAmount);
    }

    /**
     * 增加日期的小时。失败返回null。
     *
     * @param date
     * @param hourAmount
     * @return 增加小时后的日期字符串
     */
    public static String addHour(String date, int hourAmount) {
        return addInteger(date, Calendar.HOUR_OF_DAY, hourAmount);
    }

    /**
     * 增加日期的小时。失败返回null
     *
     * @param date
     * @param hourAmount
     * @return 增加小时后的日期
     */
    public static Date addHour(Date date, int hourAmount) {
        return addInteger(date, Calendar.HOUR_OF_DAY, hourAmount);
    }

    /**
     * 获取日期的年份。失败返回0。
     *
     * @param date 日期字符串
     * @return 年份
     */
    public static int getYear(String date) {
        return getYear(stringToDate(date));
    }

    /**
     * 获取日期的年份。失败返回0。
     *
     * @param date 日期
     * @return 年份
     */
    public static int getYear(Date date) {
        return getInteger(date, Calendar.YEAR);
    }

    /**
     * 获取日期的月份。失败返回0。
     *
     * @param date 日期字符串
     * @return 月份
     */
    public static int getMonth(String date) {
        return getMonth(stringToDate(date));
    }

    /**
     * 获取日期的月份。失败返回0。
     *
     * @param date 日期
     * @return 月份
     */
    public static int getMonth(Date date) {
        return getInteger(date, Calendar.MONTH);
    }

    /**
     * 获取日期的天数。失败返回0。
     *
     * @param date 日期字符串
     * @return 天
     */
    public static int getDay(String date) {
        return getDay(stringToDate(date));
    }

    /**
     * 获取日期的天数。失败返回0。
     *
     * @param date 日期
     * @return 天
     */
    public static int getDay(Date date) {
        return getInteger(date, Calendar.DATE);
    }

    /**
     * 获取日期的小时。失败返回0。
     *
     * @param date 日期字符串
     * @return 小时
     */
    public static int getHour(String date) {
        return getHour(stringToDate(date));
    }

    /**
     * 获取日期的小时。失败返回0。
     *
     * @param date 日期
     * @return 小时
     */
    public static int getHour(Date date) {
        return getInteger(date, Calendar.HOUR_OF_DAY);
    }

    /**
     * 获取日期的分钟。失败返回0。
     *
     * @param date 日期字符串
     * @return 分钟
     */
    public static int getMinute(String date) {
        return getMinute(stringToDate(date));
    }

    /**
     * 获取日期的分钟。失败返回0。
     *
     * @param date 日期
     * @return 分钟
     */
    public static int getMinute(Date date) {
        return getInteger(date, Calendar.MINUTE);
    }

    /**
     * 获取日期的秒钟。失败返回0。
     *
     * @param date 日期字符串
     * @return 秒钟
     */
    public static int getSecond(String date) {
        return getSecond(stringToDate(date));
    }

    /**
     * 获取日期的秒钟。失败返回0。
     *
     * @param date 日期
     * @return 秒钟
     */
    public static int getSecond(Date date) {
        return getInteger(date, Calendar.SECOND);
    }

    /**
     * 获取日期 。默认yyyy-MM-dd格式。失败返回null。
     *
     * @param date 日期字符串
     * @return 日期
     */
    public static String getDate(String date) {
        return stringToString(date, DateStyle.YYYY_MM_DD);
    }

    /**
     * 获取日期。默认yyyy-MM-dd格式。失败返回null。
     *
     * @param date 日期
     * @return 日期
     */
    public static String getDate(Date date) {
        return dateToString(date, DateStyle.YYYY_MM_DD);
    }

    /**
     * 获取日期的时间。默认HH:mm:ss格式。失败返回null。
     *
     * @param date 日期字符串
     * @return 时间
     */
    public static String getTime(String date) {
        return stringToString(date, DateStyle.HH_MM_SS);
    }

    /**
     * 获取日期的时间。默认HH:mm:ss格式。失败返回null。
     *
     * @param date 日期
     * @return 时间
     */
    public static String getTime(Date date) {
        return dateToString(date, DateStyle.HH_MM_SS);
    }

    /**
     * 获取日期的星期。失败返回null。
     *
     * @param dateString 日期字符串
     * @return 星期
     */
    public static Week getWeek(String dateString) {
        Week week = null;
        DateStyle dateStyle = getDateStyle(dateString);
        if (dateStyle != null) {
            Date date = stringToDate(dateString, dateStyle);
            week = getWeek(date);
        }
        return week;
    }

    /**
     * 获取日期的星期。失败返回null。
     *
     * @param date 日期
     * @return 星期
     */
    public static Week getWeek(Date date) {
        Week week = null;
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        int weekNumber = calendar.get(Calendar.DAY_OF_WEEK) - 1;
        switch (weekNumber) {
            case 0:
                week = Week.SUNDAY;
                break;
            case 1:
                week = Week.MONDAY;
                break;
            case 2:
                week = Week.TUESDAY;
                break;
            case 3:
                week = Week.WEDNESDAY;
                break;
            case 4:
                week = Week.THURSDAY;
                break;
            case 5:
                week = Week.FRIDAY;
                break;
            case 6:
                week = Week.SATURDAY;
                break;
        }
        return week;
    }

    /**
     * 获取两个日期相差的天数
     *
     * @param date      日期字符串
     * @param otherDate 另一个日期字符串
     * @return 相差天数
     */
    public static int getIntervalDays(String date, String otherDate) {
        return getIntervalDays(stringToDate(date), stringToDate(otherDate));
    }

    /**
     * @param date      日期
     * @param otherDate 另一个日期
     * @return 相差天数
     */
    public static int getIntervalDays(Date date, Date otherDate) {
        date = stringToDate(getDate(date));
        long time = Math.abs(date.getTime() - otherDate.getTime());
        return (int) time / (24 * 60 * 60 * 1000);
    }

    /**
     * 根据1970年以来的毫秒数获取日期字符串
     *
     * @param date 1970年以来的ms数
     * @param ds
     * @return 返回指定DateStyle格式的字符串
     */
    public static String longToString(Long date, DateStyle ds) {
        if (date == null || date == 0L) {
            return null;
        }
        if (ds == null) {
            ds = DateStyle.YYYY_MM_DD_HH_MM_SS;
        }
        return getDateFormat(ds.getValue()).format(date);
    }

    /**
     * 根据1970年以来的毫秒数获取日期字符串,默认yyyy-MM-dd HH:mm:ss
     *
     * @param date 1970年以来的ms数
     * @return
     */
    public static String longToString(Long date) {
        return longToString(date, DateStyle.YYYY_MM_DD_HH_MM_SS);
    }

    public enum DateStyle {
        MM_DD("MM-dd"),
        YYYY_MM("yyyy-MM"),
        YYYY_MM_DD("yyyy-MM-dd"),
        YYYYMMDD("yyyyMMdd"),
        MM_DD_HH_MM("MM-dd HH:mm"),
        MM_DD_HH_MM_SS("MM-dd HH:mm:ss"),
        YYYY_MM_DD_HH_MM("yyyy-MM-dd HH:mm"),
        YYYY_MM_DD_HH_MM_SS("yyyy-MM-dd HH:mm:ss"),

        MM_DD_EN("MM/dd"),
        YYYY_MM_EN("yyyy/MM"),
        YYYY_MM_DD_EN("yyyy/MM/dd"),
        MM_DD_HH_MM_EN("MM/dd HH:mm"),
        MM_DD_HH_MM_SS_EN("MM/dd HH:mm:ss"),
        YYYY_MM_DD_HH_MM_EN("yyyy/MM/dd HH:mm"),
        YYYY_MM_DD_HH_MM_SS_EN("yyyy/MM/dd HH:mm:ss"),

        MM_DD_CN("MM月dd日"),
        YYYY_MM_CN("yyyy年MM月"),
        YYYY_MM_DD_CN("yyyy年MM月dd日"),
        MM_DD_HH_MM_CN("MM月dd日 HH:mm"),
        MM_DD_HH_MM_SS_CN("MM月dd日 HH:mm:ss"),
        YYYY_MM_DD_HH_MM_CN("yyyy年MM月dd日 HH:mm"),
        YYYY_MM_DD_HH_MM_SS_CN("yyyy年MM月dd日 HH:mm:ss"),

        HH_MM("HH:mm"),
        HH_MM_SS("HH:mm:ss");

        private String value;

        DateStyle(String value) {
            this.value = value;
        }

        public String getValue() {
            return value;
        }
    }

    public enum Week {
        MONDAY("星期一", "Monday", "Mon.", 1),
        TUESDAY("星期二", "Tuesday", "Tues.", 2),
        WEDNESDAY("星期三", "Wednesday", "Wed.", 3),
        THURSDAY("星期四", "Thursday", "Thur.", 4),
        FRIDAY("星期五", "Friday", "Fri.", 5),
        SATURDAY("星期六", "Saturday", "Sat.", 6),
        SUNDAY("星期日", "Sunday", "Sun.", 7);

        String name_cn;
        String name_en;
        String name_enShort;
        int number;

        Week(String name_cn, String name_en, String name_enShort, int number) {
            this.name_cn = name_cn;
            this.name_en = name_en;
            this.name_enShort = name_enShort;
            this.number = number;
        }

        public String getChineseName() {
            return name_cn;
        }

        public String getName() {
            return name_en;
        }

        public String getShortName() {
            return name_enShort;
        }

        public int getNumber() {
            return number;
        }

    }
}  