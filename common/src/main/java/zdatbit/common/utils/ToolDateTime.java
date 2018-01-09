package zdatbit.common.utils;

import java.sql.Timestamp;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;


/**
 * 日期时间相关
 * 
 * @author 董华健 2012-9-7 下午1:58:46
 */
public class ToolDateTime {

	private static Logger log = Logger.getLogger(ToolDateTime.class);
	
	public static final String pattern_ymd = "yyyy-MM-dd"; // pattern_ymd
	public static final String pattern_ymd2 = "yyyyMMdd"; // pattern_ymd
	public static final String pattern_ymd_hms = "yyyy-MM-dd HH:mm:ss"; // pattern_ymdtime
	public static final String pattern_ymd_hm = "yy-MM-dd HH:mm";
	public static final String pattern_ymd_hms_s = "yyyy-MM-dd HH:mm:ss:SSS"; // pattern_ymdtimeMillisecond
	public static final String pattern_hm = "HH:mm"; // pattern_ymdtimeMillisecond
	public static final String pattern = "yyyy年MM月dd日"; 
	public static final String pattern_chinese_hms = "yyyy年MM月dd日  HH:mm:ss";
	public static final String pattern_yyyyMMddHHmmss="yyyyMMddHHmmss";
	public static final String pattern_yyyyMMddHHmm="yyyyMMddHHmm";
	/**
	 * 主要是给jfinal使用，数据库只认java.sql.*
	 * @param date
	 * @return
	 */
	public static Timestamp getSqlTimestamp(Date date){
		if(null == date){
			date = new Date();
		}
		return getSqlTimestamp(date.getTime());
	}
	
	/**
	 * 将date转换为对应format字符
	 * @param date
	 * @param formatString
	 * @return
	 */
	public static String strFromDate(Date date,String formatString) {
		if (date == null) {
			return null;
		}
		SimpleDateFormat format = new SimpleDateFormat(formatString);
		return format.format(date);
	}
	
	/**
	   * 获取现在时间
	   * 
	   * @return返回字符串格式 yyyy-MM-dd HH:mm:ss
	   */
	public static String getDateTime() {
	   Date currentTime = new Date();
	   SimpleDateFormat formatter = new SimpleDateFormat(pattern_ymd_hms);
	   String dateString = formatter.format(currentTime);
	   return dateString;
	}

	/**
	 * 主要是给jfinal使用，数据库只认java.sql.*
	 * @param time
	 * @return
	 */
	public static Timestamp getSqlTimestamp(long time){
		return new Timestamp(time);
	}
	
	/**
	 * 主要是给jfinal使用，数据库只认java.sql.*
	 * 前台日历控件获取的日期转为java.sql.*格式
	 * @param date
	 * @return
	 * @author liuzw
	 */
	public static Timestamp getSqlTimestamp(String dateStr){
		Date date=parse(dateStr, "yyyy-MM-dd") ;
		return getSqlTimestamp(date);		
	}
	/**
	 * 主要是给jfinal使用，数据库只认java.sql.*
	 * 前台日历控件获取的日期转为java.sql.*格式
	 * @param date
	 * @return
	 * @author sjsun
	 */
	public static Timestamp getSqlTimestamp(String dateStr,String format){
		Date date=parse(dateStr, format) ;
		return getSqlTimestamp(date);		
	}
	/**
	 * 主要是给jfinal使用，数据库只认java.sql.*
	 * 获取某天第一秒 00:00:00
	 * @author liuzw
	 */
	public static Timestamp getFirstSecond(Date date){
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(date);
		calendar.set(Calendar.HOUR_OF_DAY, 0);
	    calendar.set(Calendar.MINUTE, 0);
	    calendar.set(Calendar.SECOND, 0);		
		return 	getSqlTimestamp(calendar.getTime());	
	}
	
	/**
	 * 主要是给jfinal使用，数据库只认java.sql.*
	 * 获取某天最后一秒 23:59:59
	 * @author liuzw
	 */
	public static Timestamp getLastSecond(Date date){
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(date);
		calendar.set(Calendar.HOUR_OF_DAY, 23);
	    calendar.set(Calendar.MINUTE, 59);
	    calendar.set(Calendar.SECOND, 59);
		return 	getSqlTimestamp(calendar.getTime());	
	}
	
	
	/**
	 * 获取当前时间
	 * @return
	 */
	public static Date getDate(){
		return new Date();
	}
	
	public static Date getDate(Calendar calendar){
		return calendar.getTime();
	}
	/**
	 * @author zhangdi
	 * 获取几个月后的日期
	 * @param month
	 * @return
	 */
	public static Date getAddMonth(Calendar calendar,int month){
		if(calendar.get(Calendar.MONTH)+month>12){
			calendar.set(Calendar.MONTH,calendar.get(Calendar.MONTH)+month-12);
			calendar.set(Calendar.YEAR, calendar.get(Calendar.YEAR)+(calendar.get(Calendar.MONTH)+month)/12);
		}
		else {
			calendar.set(Calendar.MONTH,calendar.get(Calendar.MONTH)+month);
		}
		
		return calendar.getTime();
	}
	
	/**
	 * 获取某时间加上n个月后的时间
	 * @author liuzw
	 * @param calendar
	 * @param month
	 * @return
	 */
	public static Date getAddMonth(Date date,int month){
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(date);
		calendar.add(Calendar.MONTH, month);
//		if(calendar.get(calendar.MONTH)+month>12){			
//			calendar.set(Calendar.YEAR, calendar.get(calendar.YEAR)+(calendar.get(calendar.MONTH)+month)/12);
//			calendar.set(Calendar.MONTH,calendar.get(calendar.MONTH)+month-12);
//		}
//		else {
//			calendar.set(Calendar.MONTH,calendar.get(calendar.MONTH)+month);
//		}		
		return calendar.getTime();
	}
	/**
	 * 获取某时间加上n秒后的时间
	 * @param date
	 * @param seconds
	 * @return
	 */
	public static Date getAddSeconds(Date date,int seconds){
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(date);
		calendar.add(Calendar.SECOND, seconds);
		return calendar.getTime();
	}
	/**
	 * 获取当前天
	 */
	public static Date getToday(){
		Calendar calendar=Calendar.getInstance();
		calendar.set(Calendar.HOUR_OF_DAY,0);
		calendar.set(Calendar.MINUTE,0);
		calendar.set(Calendar.SECOND,0);
		calendar.set(Calendar.MILLISECOND, 0);
		return calendar.getTime();
	}
	/**
	 * 获取明天
	 */
	public static Date getTomorrow(){
		Calendar calendar=Calendar.getInstance();
		calendar.add(Calendar.DATE, 1);
		calendar.set(Calendar.HOUR_OF_DAY,0);
		calendar.set(Calendar.MINUTE,0);
		calendar.set(Calendar.SECOND,0);
		calendar.set(Calendar.MILLISECOND, 0);
		return calendar.getTime();
	}
	/**
	 * 获取某天
	 */
	public static Date getDate(int day){
		Calendar calendar=Calendar.getInstance();
		calendar.add(Calendar.DATE, day);
		calendar.set(Calendar.HOUR_OF_DAY,0);
		calendar.set(Calendar.MINUTE,0);
		calendar.set(Calendar.SECOND,0);
		calendar.set(Calendar.MILLISECOND, 0);
		return calendar.getTime();
	}
	/**
	 * 获取分钟数后的时间
	 */
	public static Date getDateTimeAfterMiniutes(Date date,int miniutes){
		Calendar calendar=Calendar.getInstance();
		calendar.setTime(date);
		calendar.add(Calendar.MINUTE, miniutes);
		return calendar.getTime();
	}
	/**
	 * 获取分钟数后的时间
	 */
	public static Date getDateTimeAfterMiniutes(int miniutes){
		Calendar calendar=Calendar.getInstance();
		calendar.add(Calendar.MINUTE, miniutes);
		return calendar.getTime();
	}
	/**
	 * 获取当前时间的时间戳
	 * @return
	 */
	public static long getDateByTime(){
		return new Date().getTime();
	}
	
	/**
	 * 获取下个月的今天
	 * @author liuzw
	 */
	public static Date nextMonthToday() {
        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.DAY_OF_MONTH,calendar.get(Calendar.DAY_OF_MONTH));
        calendar.add(Calendar.MONTH, 1);
        return calendar.getTime();
    }
	/**
	 * 获取上个月的今天
	 * eg:今天是2015.09.07则上个月的今天是2015.08.07
	 * @author liuzw
	 */
	public static Date lastMonthToday() {
        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.DAY_OF_MONTH,calendar.get(Calendar.DAY_OF_MONTH));
        calendar.add(Calendar.MONTH, -1);
        return calendar.getTime();        
    }
	/**
	 * 获取从今天开始一个月的日期
	 * eg:[2015年8月3号......2015年9月2号]
	 * @return
	 */
	public static List<String> getDaysOfOneMonth(){		
		//获取可面询的日期[eg:2015年8月3号......2015年9月3号]
		List<String> days = ToolDateTime.getDaySpaceDate(ToolDateTime.getDate(), ToolDateTime.nextMonthToday());
		//移除最后一个日期[eg:2015年8月3号......2015年9月2号]
		days.remove(days.size()-1);	
		return days;
	}	

	/**
	 * 设置某一天对应的星期的第一天为星期一，按中国的习惯一个星期的第一天是星期一 
	 * @author liuzw
	 * @param time
	 * @return
	 */
    public static Calendar getMondayOfWeek(Date date) {        
         Calendar cal = Calendar.getInstance();
         cal.setTime(date);
         //判断要计算的日期是否是周日，如果是则减一天计算周六的，否则会出问题，计算到下一周去了
         int dayWeek = cal.get(Calendar.DAY_OF_WEEK);//获得当前日期是一个星期的第几天
         if(1 == dayWeek) {
             cal.add(Calendar.DAY_OF_MONTH, -1);
         }        
         cal.setFirstDayOfWeek(Calendar.MONDAY);//设置一个星期的第一天，按中国的习惯一个星期的第一天是星期一 
         return cal;
    }
    /**
     * 根据指定日期计算所在周的周一
     * @param date
     */
    public static Date getFirstDateOfWeek(Date date) {    	
    	Calendar cal = getMondayOfWeek(date);    	
        int day = cal.get(Calendar.DAY_OF_WEEK);//获得当前日期是一个星期的第几天 
        cal.add(Calendar.DATE, cal.getFirstDayOfWeek()-day);//根据日历的规则，给当前日期减去星期几与一个星期第一天的差值 
        return cal.getTime();
    }
    /**
     * 根据指定日期计算所在周的周日
     * @param date
     */
    public static Date getLastDateOfWeek(Date date) {
    	Calendar cal = getMondayOfWeek(date);    	
        int day = cal.get(Calendar.DAY_OF_WEEK);//获得当前日期是一个星期的第几天 
        cal.add(Calendar.DATE, cal.getFirstDayOfWeek()-day+6);
        return cal.getTime();        
    }
    /**
     * 获取指定日期是一个星期的第几天
     * @param date
     */
    public static int getDayOfWeek(Date date) {
    	Calendar cal = getMondayOfWeek(date);  
    	cal.setFirstDayOfWeek(Calendar.MONDAY);
    	int dayWeek = cal.get(Calendar.DAY_OF_WEEK)-1;//获得当前日期是一个星期的第几天
        if(dayWeek==0) {
        	dayWeek=7;
        }        
        return dayWeek;
    }
	/**
	 * 格式化
	 * @param date
	 * @param pattern
	 * @return
	 */
	public static String format(Date date, String pattern) {
		DateFormat format = new SimpleDateFormat(pattern);
		return format.format(date);
	}

	/**
	 * 格式化
	 * @param date
	 * @param parsePattern
	 * @param returnPattern
	 * @return
	 */
	public static String format(String date, String parsePattern, String returnPattern) {
		return format(parse(date, parsePattern), returnPattern);
	}
	
	/**
	 * 解析
	 * @param date
	 * @param pattern
	 * @return
	 */
	public static Date parse(String date, String pattern) {
		SimpleDateFormat format = new SimpleDateFormat(pattern);
		try {
			return format.parse(date);
		} catch (ParseException e) {
			log.error("ToolDateTime.parse异常：date值" + date + "，pattern值" + pattern);
			return null;
		}
	}
	/**
	 * 解析
	 * @param dateStr
	 * @return
	 */
	public static Date parse(String dateStr) {
		Date date = null;
		try {
			date = DateFormat.getDateTimeInstance().parse(dateStr);
		} catch (ParseException e) {
			log.error("ToolDateTime.parse异常：date值" + date);
			return null;
		}
		return date;
	}
	
	/**
	 * 两个日期的时间差，返回"X天X小时X分X秒"
	 * 
	 * @param begin
	 * @param end
	 * @return
	 */
	public static String getBetween(Date begin, Date end) {
		long between = (end.getTime() - begin.getTime()) / 1000;// 除以1000是为了转换成秒
		long day = between / (24 * 3600);
		long hour = between % (24 * 3600) / 3600;
		long minute = between % 3600 / 60;
		long second = between % 60 / 60;

		StringBuilder sb = new StringBuilder();
		sb.append(day);
		sb.append("天");
		sb.append(hour);
		sb.append("小时");
		sb.append(minute);
		sb.append("分");
		sb.append(second);
		sb.append("秒");

		return sb.toString();
	}
	/**
	 * 两个日期的时间差，返回"hh:mm:ss"(前提是两个日期只差不超过一天)
	 * @param begin
	 * @param end
	 * @return
	 */
	public static String getBetweenTime(Date begin, Date end) {
		long between = (end.getTime() - begin.getTime()) / 1000;// 除以1000是为了转换成秒
//		long day = between / (24 * 3600);
		long hour = between % (24 * 3600) / 3600;
		long minute = between % 3600 / 60;
		long second = between % 60 / 60;

		StringBuilder sb = new StringBuilder();
		sb.append(hour);
		sb.append(":");
		sb.append(minute);
		sb.append(":");
		sb.append(second);
		return sb.toString();
	}
	/**
	 * 返回两个日期之间隔了多少分钟
	 * 
	 * @param start 支持pattern_ymd_hms = "yyyy-MM-dd HH:mm:ss"格式
	 * @param end
	 * @return
	 */
	public static int getDateMinuteSpace(String start, String end) {		
		int miniutes = (int) ((parse(start, pattern_ymd_hms).getTime() - parse(end, pattern_ymd_hms).getTime()) / 60 / 1000);
		return miniutes;
	}
	/**
	 * 返回两个日期之间隔了多少分钟
	 * 
	 * @param date1
	 * @param end
	 * @return
	 */
	public static int getDateMinuteSpace(Date start, Date end) {
		int miniutes = (int) ((start.getTime() - end.getTime()) / 60 / 1000);
		return miniutes;
	}
	/**
	 * 返回两个日期之间隔了多少秒
	 * 
	 * @param date1
	 * @param end
	 * @return
	 */
	public static int getDateSecondsSpace(Date start, Date end) {
		int seconds = (int) ((start.getTime() - end.getTime()) / 1000);
		return Math.abs(seconds);
	}
	/**
	 * 返回两个日期之间隔了多少小时
	 * 
	 * @param date1
	 * @param end
	 * @return
	 */
	public static int getDateHourSpace(Date start, Date end) {
		int hour = (int) ((start.getTime() - end.getTime()) / 3600 / 1000);
		return Math.abs(hour);
	}
	/**
	 * 返回两个日期之间隔了多少天
	 *eg:1号和2号差1天,1号和3号差2天
	 * @author liuzw
	 * @param date1
	 * @param end
	 * @return
	 */
	 public static int getBetweenDay(Date date1, Date date2) {  
        Calendar d1 = new GregorianCalendar();  
        d1.setTime(date1);  
        Calendar d2 = new GregorianCalendar();  
        d2.setTime(date2);  
        int days = d2.get(Calendar.DAY_OF_YEAR)- d1.get(Calendar.DAY_OF_YEAR);  
        int y2 = d2.get(Calendar.YEAR);  
        if (d1.get(Calendar.YEAR) != y2) {  
//	          d1 = (Calendar) d1.clone();  
            do {  
                days += d1.getActualMaximum(Calendar.DAY_OF_YEAR);  
                d1.add(Calendar.YEAR, 1);  
            } while (d1.get(Calendar.YEAR) != y2);  
        }  
        return days;  
    }
	
	/**
	 * 返回两个日期之间隔了多少天
	 * eg:1号和3号中间隔了1天
	 * @param date1
	 * @param end
	 * @return
	 */
	public static int getDateDaySpace(Date start, Date end) {
		int day = (int) (getDateHourSpace(start, end) / 24);
		return day;
	}
	/**
	 * 返回两个日期之间差了多少天
	 * eg:1号和3号差2天
	 * @param date1
	 * @param end
	 * @return
	 */
	public static int getDateDaySubtract(Date start, Date end) {
		return getDateDaySpace(start,end);
	}
	
	/**
	 * 得到某一天是星期几
	 * @param strDate 日期字符串
	 * @return String 星期几
	 */
	@SuppressWarnings("static-access")
	public static String getDateInWeek(Date date) {
		String[] weekDays = {"星期日", "星期一", "星期二", "星期三", "星期四", "星期五", "星期六"};
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(date);
		int dayIndex = calendar.get(calendar.DAY_OF_WEEK) - calendar.SUNDAY;
		if (dayIndex < 0){
			dayIndex = 0;
		}
		return weekDays[dayIndex];
	}
	/**
	 * 得到某一天是星期几
	 * @param strDate 日期字符串
	 * @return String 星期几
	 */
	@SuppressWarnings("static-access")
	public static String getDateOfWeek(Date date) {
		String[] weekDays = {"周日", "周一", "周二", "周三", "周四", "周五", "周六"};
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(date);
		int dayIndex = calendar.get(calendar.DAY_OF_WEEK) - calendar.SUNDAY;
		if (dayIndex < 0){
			dayIndex = 0;
		}
		return weekDays[dayIndex];
	}
	/**
	 * 获取指定日期加num天后的日期
	 * @param date
	 * @param num 正数代表加,负数代表减
	 * @return
	 */
	public static Date getDateAddDate(Date date,int num){		
	    Calendar calendar = Calendar.getInstance();
	    calendar.setTime(date); 
	    calendar.add(Calendar.DATE,num);//把日期往后增加一天.整数往后推,负数往前移动 
	    return calendar.getTime();   //这个时间就是日期往后推一天的结果 
	}
	/**
	 * 获取指定日期加hour小时,minute分钟后的时间
	 * @param date
	 * @param hour 正数代表加,负数代表减
	 * @return
	 */
	public static Date getDateAddHourMinute(Date date,int hour,int minute){		
	    Calendar calendar = Calendar.getInstance();
	    calendar.setTime(date); 
	    calendar.add(Calendar.HOUR,hour);
	    calendar.add(Calendar.MINUTE,minute);
	    return calendar.getTime();   
	}
	/**
	 * 日期加上多少个小时
	 * @param date
	 * @param hourCount	多少个小时
	 * @return
	 */
	public static Date getDateAddHour(Date date, long hourCount){
		long time = date.getTime() + 3600 * 1000 * hourCount;
		Date dateTemp = new Date();
		dateTemp.setTime(time);
		return dateTemp;
	}
	/**
	 * 日期减去多少个小时
	 * @param date
	 * @param hourCount	多少个小时
	 * @return
	 */
	public static Date getDateReduceHour(Date date, long hourCount){
		long time = date.getTime() - 3600 * 1000 * hourCount;
		Date dateTemp = new Date();
		dateTemp.setTime(time);
		return dateTemp;
	}
	
	/**
	 * 日期区间分割
	 * @param start
	 * @param end
	 * @param splitCount
	 * @return
	 */
	public static List<Date> getDateSplit(Date start, Date end, long splitCount){
		long startTime = start.getTime();
		long endTime = end.getTime();
		long between = endTime - startTime;

		long count = splitCount - 1l;
		long section = between / count;
		
		List<Date> list = new ArrayList<Date>();
		list.add(start);
		
		for (long i = 1l ; i < count; i++) {
			long time = startTime + section * i;
			Date date = new Date();
			date.setTime(time);
			list.add(date);
		}
		
		list.add(end);
		
		return list;
	}
	
	/**
	 * 返回两个日期之间隔了多少天，包含开始、结束时间
	 * @param start
	 * @param end
	 * @return
	 */
	public static List<String> getDaySpaceDate(Date start, Date end) {
		Calendar fromCalendar = Calendar.getInstance();
		fromCalendar.setTime(start);
		fromCalendar.set(Calendar.HOUR_OF_DAY, 0);
		fromCalendar.set(Calendar.MINUTE, 0);
		fromCalendar.set(Calendar.SECOND, 0);
		fromCalendar.set(Calendar.MILLISECOND, 0);

		Calendar toCalendar = Calendar.getInstance();
		toCalendar.setTime(end);
		toCalendar.set(Calendar.HOUR_OF_DAY, 0);
		toCalendar.set(Calendar.MINUTE, 0);
		toCalendar.set(Calendar.SECOND, 0);
		toCalendar.set(Calendar.MILLISECOND, 0);

		List<String> dateList = new LinkedList<String>();

		long dayCount = (toCalendar.getTime().getTime() - fromCalendar.getTime().getTime()) / (1000 * 60 * 60 * 24);
		if(dayCount < 0){
			return dateList;
		}

		dateList.add(format(fromCalendar.getTime(), pattern));
		
		for (int i = 0; i < dayCount; i++) {
			fromCalendar.add(Calendar.DATE, 1);// 增加一天
			dateList.add(format(fromCalendar.getTime(), pattern));
		}

		return dateList;
	}
	/**
	 * 返回两个日期之间隔了多少秒，包含开始、结束时间
	 * @param start
	 * @param end
	 * @return
	 */
	public static Long getDiffSeconds(Date start, Date end) {
		if (start==null || end==null) {
			return null;
		}
		Calendar fromCalendar = Calendar.getInstance();
		fromCalendar.setTime(start);

		Calendar toCalendar = Calendar.getInstance();
		toCalendar.setTime(end);

		//List<String> dateList = new LinkedList<String>();

		long count =Math.abs((toCalendar.getTime().getTime() - fromCalendar.getTime().getTime()) / (1000));
		return count;
	}
	/**
	 * 返回两个日期之间隔了多少天，包含开始、结束时间
	 * @param start
	 * @param end
	 * @return
	 */
	public static Long getDiffDay(Date start, Date end) {
		if (start==null || end==null) {
			return null;
		}
		Calendar fromCalendar = Calendar.getInstance();
		fromCalendar.setTime(start);
		fromCalendar.set(Calendar.HOUR_OF_DAY, 0);
		fromCalendar.set(Calendar.MINUTE, 0);
		fromCalendar.set(Calendar.SECOND, 0);
		fromCalendar.set(Calendar.MILLISECOND, 0);

		Calendar toCalendar = Calendar.getInstance();
		toCalendar.setTime(end);
		toCalendar.set(Calendar.HOUR_OF_DAY, 0);
		toCalendar.set(Calendar.MINUTE, 0);
		toCalendar.set(Calendar.SECOND, 0);
		toCalendar.set(Calendar.MILLISECOND, 0);

		//List<String> dateList = new LinkedList<String>();

		long dayCount =Math.abs((toCalendar.getTime().getTime() - fromCalendar.getTime().getTime()) / (1000 * 60 * 60 * 24));
		return dayCount;
	}
	/**
	 * 获取开始时间
	 * @param start
	 * @param end
	 * @return
	 */
	public static Date startDateByDay(Date start, int end){
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(start);
		calendar.add(Calendar.DATE, end);// 明天1，昨天-1
		calendar.set(Calendar.HOUR_OF_DAY, 0);   
		calendar.set(Calendar.MINUTE, 0);   
		calendar.set(Calendar.SECOND, 0);   
		calendar.set(Calendar.MILLISECOND, 0);   
		Date date = calendar.getTime();
		return date;
	}
	/**
	 * 获取开始时间
	 * @param start
	 * @param end
	 * @return
	 */
	public static Date startDateByDay(Date start){
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(start);
		calendar.set(Calendar.HOUR_OF_DAY, 0);   
		calendar.set(Calendar.MINUTE, 0);   
		calendar.set(Calendar.SECOND, 0);   
		calendar.set(Calendar.MILLISECOND, 0);   
		Date date = calendar.getTime();
		return date;
	}
	/**
	 * 获取结束时间
	 * @param start
	 * @param end
	 * @return
	 */
	public static Timestamp endSqlTimestamp(Timestamp start){
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(start);
		calendar.set(Calendar.HOUR_OF_DAY, 23);   
		calendar.set(Calendar.MINUTE, 59);   
		calendar.set(Calendar.SECOND, 59);   
		calendar.set(Calendar.MILLISECOND, 999);   
		Date date = calendar.getTime();
		return getSqlTimestamp(date);
	}
	/**
	 * 获取结束时间
	 * @param start
	 * @param end
	 * @return
	 */
	public static Date endDateByDay(Date start){
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(start);
		calendar.set(Calendar.HOUR_OF_DAY, 23);   
		calendar.set(Calendar.MINUTE, 59);   
		calendar.set(Calendar.SECOND, 59);   
		calendar.set(Calendar.MILLISECOND, 999);   
		Date date = calendar.getTime();
		return date;
	}

	/**
	 * 获取开始时间
	 * @param start
	 * @param end
	 * @return
	 */
	public static Date startDateByHour(Date start, int end){
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(start); 
		calendar.set(Calendar.MINUTE, end);   
		Date date = calendar.getTime();
		return date;
	}
	
	/**
	 * 获取结束时间
	 * @param start
	 * @param end
	 * @return
	 */
	public static Date endDateByHour(Date end){
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(end); 
		calendar.set(Calendar.SECOND, 59);   
		calendar.set(Calendar.MILLISECOND, 999);   
		Date date = calendar.getTime();
		return date;
	}
	
	/**
	 * 根据年份和周得到周的开始和结束日期
	 * @param year
	 * @param week
	 * @return
	 */
	public static Map<String, Date> getStartEndDateByWeek(int year, int week){
		Calendar weekCalendar = new GregorianCalendar();
		weekCalendar.set(Calendar.YEAR, year);
		weekCalendar.set(Calendar.WEEK_OF_YEAR, week);
		weekCalendar.set(Calendar.DAY_OF_WEEK, weekCalendar.getFirstDayOfWeek());
		
		Date startDate = weekCalendar.getTime(); // 得到周的开始日期

		weekCalendar.roll(Calendar.DAY_OF_WEEK, 6);
		Date endDate = weekCalendar.getTime(); // 得到周的结束日期
		
		// 开始日期往前推一天
		Calendar startCalendar = Calendar.getInstance();
		startCalendar.setTime(startDate);
		startCalendar.add(Calendar.DATE, 1);// 明天1，昨天-1
		startCalendar.set(Calendar.HOUR_OF_DAY, 0);   
		startCalendar.set(Calendar.MINUTE, 0);   
		startCalendar.set(Calendar.SECOND, 0);   
		startCalendar.set(Calendar.MILLISECOND, 0);   
		startDate = startCalendar.getTime();

		// 结束日期往前推一天
		Calendar endCalendar = Calendar.getInstance();
		endCalendar.setTime(endDate);
		endCalendar.add(Calendar.DATE, 1);// 明天1，昨天-1
		endCalendar.set(Calendar.HOUR_OF_DAY, 23);   
		endCalendar.set(Calendar.MINUTE, 59);   
		endCalendar.set(Calendar.SECOND, 59);   
		endCalendar.set(Calendar.MILLISECOND, 999);   
		endDate = endCalendar.getTime();
		
		Map<String, Date> map = new HashMap<String, Date>();
		map.put("start", startDate);
		map.put("end", endDate);
		return map;
	}
	
	/**
	 * 根据日期月份，获取月份的开始和结束日期
	 * @param date
	 * @return
	 */
	public static Map<String, Date> getMonthDate(Date date){
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(date);
		calendar.set(Calendar.HOUR_OF_DAY, 0);   
		calendar.set(Calendar.MINUTE, 0);   
		calendar.set(Calendar.SECOND, 0);   
		calendar.set(Calendar.MILLISECOND, 0);
		
		// 得到前一个月的第一天
		calendar.set(Calendar.DAY_OF_MONTH, calendar.getActualMinimum(Calendar.DAY_OF_MONTH));
		Date start = calendar.getTime();
		
		// 得到前一个月的最后一天
		calendar.set(Calendar.DAY_OF_MONTH, calendar.getActualMaximum(Calendar.DAY_OF_MONTH));
		Date end = calendar.getTime();
		
		Map<String, Date> map = new HashMap<String, Date>();
		map.put("start", start);
		map.put("end", end);
		return map;
	}

	/**
	 * 获取指定日期
	 * @param date
	 * @param hour
	 * @param minute
	 * @param second
	 * @param millisecond
	 * @return
	 */
	public static Date getDate(int date, int hour, int minute, int second, int millisecond) {
		Calendar calendar = Calendar.getInstance();
		calendar.add(Calendar.DATE, date); 
		calendar.set(Calendar.HOUR_OF_DAY, hour);   
		calendar.set(Calendar.MINUTE, minute);   
		calendar.set(Calendar.SECOND, second);   
		calendar.set(Calendar.MILLISECOND, millisecond);  
		return calendar.getTime();
	}
	/**
	 * 获取指定日期
	 * @param date
	 * @param hour
	 * @param minute
	 * @param second
	 * @param millisecond
	 * @return
	 */
	public static Date getDate(int hour, int minute, int second) {
		Calendar calendar = Calendar.getInstance();
		calendar.set(Calendar.HOUR_OF_DAY, hour);   
		calendar.set(Calendar.MINUTE, minute);   
		calendar.set(Calendar.SECOND, second);   
		return calendar.getTime();
	}
	private static final long ONE_MINUTE = 60000L;  
    private static final long ONE_HOUR = 3600000L;  
    private static final long ONE_DAY = 86400000L;  
    private static final long ONE_WEEK = 604800000L;  
  
    private static final String ONE_SECOND_AGO = "秒前";  
    private static final String ONE_MINUTE_AGO = "分钟前";  
    private static final String ONE_HOUR_AGO = "小时前";  
    private static final String ONE_DAY_AGO = "天前";  
    private static final String ONE_MONTH_AGO = "月前";  
    private static final String ONE_YEAR_AGO = "年前";  
    /**
     * 个性化时间，如几秒前，几分前
     * @param date
     * @return
     */
    public static String formatPersonnal(Date date) {  
        long delta = new Date().getTime() - date.getTime();  
        if (delta < 1L * ONE_MINUTE) {  
            long seconds = toSeconds(delta);  
            return (seconds <= 0 ? 1 : seconds) + ONE_SECOND_AGO;  
        }  
        if (delta < 45L * ONE_MINUTE) {  
            long minutes = toMinutes(delta);  
            return (minutes <= 0 ? 1 : minutes) + ONE_MINUTE_AGO;  
        }  
        if (delta < 24L * ONE_HOUR) {  
            long hours = toHours(delta);  
            return (hours <= 0 ? 1 : hours) + ONE_HOUR_AGO;  
        }  
        if (delta < 48L * ONE_HOUR) {  
            return "昨天";  
        }  
        if (delta < 30L * ONE_DAY) {  
            long days = toDays(delta);  
            return (days <= 0 ? 1 : days) + ONE_DAY_AGO;  
        }  
        if (delta < 12L * 4L * ONE_WEEK) {  
            long months = toMonths(delta);  
            return (months <= 0 ? 1 : months) + ONE_MONTH_AGO;  
        } else {  
            long years = toYears(delta);  
            return (years <= 0 ? 1 : years) + ONE_YEAR_AGO;  
        }  
    }  
    /**
     * 获取人性化时间
     * @param miniutes
     * @return
     */
    public static String getPersonalTimeStr(Integer miniutes){
    	if (miniutes==null) {
			return "";
		}
    	if (miniutes<60) {
			return miniutes+"分钟";
		}else if (miniutes>1440) {
			if (miniutes%1440==0) {
				return (miniutes/1440)+"天";
			}else {
				return (miniutes/1440)+"天"+((miniutes%1440)/60)+"小时";
			}
			
		}else {
			if (miniutes%60==0) {
				return (miniutes/60)+"小时";
			}else {
				return (miniutes/60)+"小时"+(miniutes%60)+"分钟";
			}
			
		}
    }
    private static long toSeconds(long date) {  
        return date / 1000L;  
    }  
  
    private static long toMinutes(long date) {  
        return toSeconds(date) / 60L;  
    }  
  
    private static long toHours(long date) {  
        return toMinutes(date) / 60L;  
    }  
  
    private static long toDays(long date) {  
        return toHours(date) / 24L;  
    }  
  
    private static long toMonths(long date) {  
        return toDays(date) / 30L;  
    }  
  
    private static long toYears(long date) {  
        return toMonths(date) / 365L;  
    }  
    
	@SuppressWarnings("deprecation")
	public static void main(String[] args) throws ParseException{
//		System.out.println(format("2013-07-01", pattern_ymd, "MM-dd"));
		
//		Date start = parse("2013-07-01 01:00:00", pattern_ymdtime);
//		Date end = parse("2013-07-01 12:00:00", pattern_ymdtime);
//		long splitCount = 12l;
//		List<Date> list = getDateSplit(start, end, splitCount);
//		for (Date date : list) {
//			System.out.println(format(date, pattern_ymdtime));
//		}
		
//		Date start = parse("2013-07-01 01:00:00", pattern_ymdtime);
//		Date end = parse("2013-07-05 12:00:00", pattern_ymdtime);
//		List<String> list = getDaySpaceDate(start, end);
//		for (String str : list) {
//			System.out.println(str);
//		}
		
//		Date start = parse("2013-07-01 01:00:00", pattern_ymdtime);
//		Date end = endDate(start, 7);
//		System.out.println(format(end, pattern_ymdtime));

//		Date endDate = ToolDateTime.endDate(new Date());
//		Date startDate = ToolDateTime.startDate(endDate, -14);
//		System.out.println(format(startDate, pattern_ymdtimeMillisecond));
//		System.out.println(format(endDate, pattern_ymdtimeMillisecond));
		
//		Date endDate = ToolDateTime.endDateByHour(new Date());
//		Date startDate = ToolDateTime.startDateByHour(endDate, -24);
//		
//		System.out.println(format(startDate, pattern_ymdtimeMillisecond));
//		System.out.println(format(endDate, pattern_ymdtimeMillisecond));
		
		System.out.println(format(new Date(), pattern_yyyyMMddHHmmss));
		System.out.println(parse("2015-11-14 18:03:44", pattern_ymd_hms));
		Date date=new Date();
		date.setMonth(0);
		date.setDate(31);
		System.out.println(date.toLocaleString());
		System.out.println(ToolDateTime.getAddMonth(date, 1).toLocaleString());
		
	}

}
