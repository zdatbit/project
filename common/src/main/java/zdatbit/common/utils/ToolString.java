package zdatbit.common.utils;

import java.io.UnsupportedEncodingException;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.regex.Pattern;

import org.apache.commons.codec.binary.Base64;
import org.apache.log4j.Logger;


/**
 * 字符串处理常用方法
 */
public abstract class ToolString {

	@SuppressWarnings("unused")
	private static Logger log = Logger.getLogger(ToolString.class);

	/**
	 * 常用正则表达式
	 */
	public final static String regExp_integer_1 = "^\\d+$"; // 匹配非负整数（正整数 + 0）
	public final static String regExp_integer_2 = "^[0-9]*[1-9][0-9]*$"; // 匹配正整数
	public final static String regExp_integer_3 = "^((-\\d+) ?(0+))$"; // 匹配非正整数（负整数  + 0）
	public final static String regExp_integer_4 = "^-[0-9]*[1-9][0-9]*$"; // 匹配负整数
	public final static String regExp_integer_5 = "^-?\\d+$"; // 匹配整数

	public final static String regExp_float_1 = "^\\d+(\\.\\d+)?$"; // 匹配非负浮点数（正浮点数 + 0）
	public final static String regExp_float_2 = "^(([0-9]+\\.[0-9]*[1-9][0-9]*) ?([0-9]*[1-9][0-9]*\\.[0-9]+) ?([0-9]*[1-9][0-9]*))$"; // 匹配正浮点数
	public final static String regExp_float_3 = "^((-\\d+(\\.\\d+)?) ?(0+(\\.0+)?))$"; // 匹配非正浮点数（负浮点数 + 0）
	public final static String regExp_float_4 = "^(-(([0-9]+\\.[0-9]*[1-9][0-9]*) ?([0-9]*[1-9][0-9]*\\.[0-9]+) ?([0-9]*[1-9][0-9]*)))$"; // 匹配负浮点数
	public final static String regExp_float_5 = "^(-?\\d+)(\\.\\d+)?$"; // 匹配浮点数

	public final static String regExp_letter_1 = "^[A-Za-z]+$";// 匹配由26个英文字母组成的字符串
	public final static String regExp_letter_2 = "^[A-Z]+$";// 匹配由26个英文字母的大写组成的字符串
	public final static String regExp_letter_3 = "^[a-z]+$";// 匹配由26个英文字母的小写组成的字符串
	public final static String regExp_letter_4 = "^[A-Za-z0-9]+$";// 匹配由数字和26个英文字母组成的字符串
	public final static String regExp_letter_5 = "^\\w+$";// 匹配由数字、26个英文字母或者下划线组成的字符串

	public final static String regExp_email = "^[\\w-]+(\\.[\\w-]+)*@[\\w-]+(\\.[\\w-]+)+$"; // 匹配email地址
	
	public final static String regExp_url_1 = "^[a-zA-z]+://(\\w+(-\\w+)*)(\\.(\\w+(-\\w+)*))*(\\?\\S*)?$"; // 匹配url
	public final static String regExp_url_2 = "[a-zA-z]+://[^\\s]*"; // 匹配url
		
	public final static String regExp_chinese_1 = "[\\u4e00-\\u9fa5]"; // 匹配中文字符
	public final static String regExp_chinese_2 = "[^\\x00-\\xff]"; // 匹配双字节字符(包括汉字在内)

	public final static String regExp_line = "\\n[\\s ? ]*\\r"; // 匹配空行：

	public final static String regExp_html_1 = "/ <(.*)>.* <\\/\\1> ? <(.*) \\/>/"; // 匹配HTML标记
	public final static String regExp_startEndEmpty = "(^\\s*) ?(\\s*$)"; // 匹配首尾空格

	public final static String regExp_accountNumber = "^[a-zA-Z][a-zA-Z0-9_]{4,15}$"; //匹配帐号是否合法(字母开头，允许5-16字节，允许字母数字下划线)
	
	public final static String regExp_telephone = "\\d{3}-\\d{8} ?\\d{4}-\\d{7}"; //匹配国内电话号码，匹配形式如 0511-4405222 或 021-87888822 
	public final static String regExp_Phone = "^1\\d{10}$"; //匹配国内电话号码，匹配形式如 13900000000 
	public static final String regExp_Email = "^([a-z0-9A-Z]+[-|\\.]?)+[a-z0-9A-Z]@([a-z0-9A-Z]+(-[a-z0-9A-Z]+)?\\.)+[a-zA-Z]{2,}$";//匹配邮箱
	public final static String regExp_qq = "[1-9][0-9]{4,}"; // 腾讯QQ号, 腾讯QQ号从10000开始
	
	public final static String regExp_postbody = "[1-9]\\d{5}(?!\\d)"; // 匹配中国邮政编码
	
	public final static String regExp_idCard = "\\d{15} ?\\d{18}"; // 匹配身份证, 中国的身份证为15位或18位

	public final static String regExp_ip = "\\d+\\.\\d+\\.\\d+\\.\\d+";//IP
	
	/**
	 * 字符编码
	 */
	public final static String encoding = "UTF-8";

	/**
	 * Url Base64编码
	 * 
	 * @param data
	 *            待编码数据
	 * @return String 编码数据
	 * @throws Exception
	 */
	public static String encode(String data) throws Exception {
		// 执行编码
		byte[] b = Base64.encodeBase64(data.getBytes(encoding));

		return new String(b, encoding);
	}

	/**
	 * Url Base64解码
	 * 
	 * @param data
	 *            待解码数据
	 * @return String 解码数据
	 * @throws Exception
	 */
	public static String decode(String data) throws Exception {
		// 执行解码
		byte[] b = Base64.decodeBase64(data.getBytes(encoding));

		return new String(b, encoding);
	}

	/**
	 * URL编码（utf-8）
	 * 
	 * @param source
	 * @return
	 */
	public static String urlEncode(String source) {
		String result = source;
		try {
			result = java.net.URLEncoder.encode(source, encoding);
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
		return result;
	}

	/**
	 * 根据内容类型判断文件扩展名
	 * 
	 * @param contentType
	 *            内容类型
	 * @return
	 */
	public static String getFileExt(String contentType) {
		String fileExt = "";
		if ("image/jpeg".equals(contentType))
			fileExt = ".jpg";
		else if ("audio/mpeg".equals(contentType))
			fileExt = ".mp3";
		else if ("audio/amr".equals(contentType))
			fileExt = ".amr";
		else if ("video/mp4".equals(contentType))
			fileExt = ".mp4";
		else if ("video/mpeg4".equals(contentType))
			fileExt = ".mp4";
		return fileExt;
	}

	/**
	 * 获取bean名称
	 * 
	 * @param bean
	 * @return
	 */
	public static String beanName(Object bean) {
		String fullClassName = bean.getClass().getName();
		String classNameTemp = fullClassName.substring(fullClassName.lastIndexOf(".") + 1, fullClassName.length());
		return classNameTemp.substring(0, 1) + classNameTemp.substring(1);
	}
	
	public final static Pattern referer_pattern = Pattern.compile("@([^@^\\s^:]{1,})([\\s\\:\\,\\;]{0,1})");//@.+?[\\s:]
	 
	/**
	 * 处理提到某人 @xxxx
	 * @param msg  传入的文本内容
	 * @param referers 传出被引用到的会员名单
	 * @return 返回带有链接的文本内容
	 *//*
	public static String userLinks(String contents, List<String> userReferers) {
	    StringBuilder html = new StringBuilder();
	    int lastIdx = 0;
	    Matcher matchr = referer_pattern.matcher(contents);
	    while (matchr.find()) {
	        String origion_str = matchr.group();
	        //System.out.println("-->"+origion_str);
	        String userName = origion_str.substring(1, origion_str.length()).trim();
	        //char ch = str.charAt(str.length()-1);
	        //if(ch == ':' || ch == ',' || ch == ';')
	        //  str = str.substring(0, str.length()-1);
	        //System.out.println(str);
	        html.append(contents.substring(lastIdx, matchr.start()));
	         
	        User user = null;
			Object userObj = User.dao.cacheGet(userName);
			if (null != userObj) {
				user = (User) userObj;
			} else {
				Map<String, Object> param = new HashMap<String, Object>();
				param.put("column", "username");
				String sql = ToolSqlXml.getSql("platform.user.column", param);
				List<User> userList = User.dao.find(sql, userName);
				if (userList.size() == 1) {
					user = userList.get(0);
				}
			}
	        
	        if(user != null){
	            html.append("<a href='http://www.xx.com/"+user.getStr("username")+"' class='referer' target='_blank'>@");
	            html.append(userName.trim());
	            html.append("</a> ");
	            if(userReferers != null && !userReferers.contains(user.getPKValue())){
	            	userReferers.add(user.getPKValue());
	            }
	        } else {
	            html.append(origion_str);
	        }
	        lastIdx = matchr.end();
	        //if(ch == ':' || ch == ',' || ch == ';')
	        //  html.append(ch);
	    }
	    html.append(contents.substring(lastIdx));
	    return html.toString();
	}*/

	/**
	 * 首字母转小写
	 * @param s
	 * @return
	 */
	public static String toLowerCaseFirstOne(String s) {
        if(Character.isLowerCase(s.charAt(0)))
            return s;
        else
            return (new StringBuilder()).append(Character.toLowerCase(s.charAt(0))).append(s.substring(1)).toString();
    }
	
	/**
	 * 首字母转大写
	 * @param s
	 * @return
	 */
    public static String toUpperCaseFirstOne(String s) {
        if(Character.isUpperCase(s.charAt(0)))
            return s;
        else
            return (new StringBuilder()).append(Character.toUpperCase(s.charAt(0))).append(s.substring(1)).toString();
    }
    
    /**
	 * 判断字符串是否为空
	 * @param str
	 * @return
	 */
	public static Boolean isEmpty(String str){
		if (str==null || str.length()==0) {
			return true;
		}
		return false;
	}
	/**
	 * 字符串是否存在于字符串数组中
	 * @param str
	 * @param strs
	 * @return
	 */
    public static Boolean isInStrs(String str,String[] strs){
    	for (String s : strs) {
			if (s.equalsIgnoreCase(str)) {
				return true;
			}
		}
    	return false;
    }
    /**
	 * 是否存在于字符串数组中
	 * @param str
	 * @param strs
	 * @return
	 */
    public static Boolean isInLongs(Long id,Long[] ids){
    	for (Long i : ids) {
			if (i.equals(id)) {
				return true;
			}
		}
    	return false;
    }
    /**
     * 判断一个字符串是否在另一个字符串中，以英文逗号","分隔
     * @param str
     * @param strs
     * @return
     */
    public static Boolean isInStrs(String str,String strs){
    	return isInStrs(str, strs,",");
    }
    /**
     * 判断一个字符串是否在另一个字符串中，以separator分隔
     * @param str 要判断的字符串
     * @param strs 以分隔符分割的字符串
     * @param separator 分隔符
     * @return
     */
    public static Boolean isInStrs(String str,String strs,String separator){
    	if(isEmpty(str)||isEmpty(strs)){
    		return false;
    	}
    	return isInStrs(str, strs.split(separator));
    }
    /**
     * 生成随机字符串，长度最大不超过35位
     * @param num 指定字符串长度
     * @return
     * @throws Exception
     */
    public static String generateWord(int num) throws Exception{
    	String[] beforeShuffle=new String[]{"0","1","2","3","4","5","6","7",
			"8","9","A","B","C","D","E","F","G","H","I","J",
			"K","L","M","N","O","P","Q","R","S","T","U","V",
			"W","X","Y","Z"};
    	if (beforeShuffle.length<num) {
			Tools.throwException("生成随机字符串失败，原因为超过最大位数！");
		}
    	List<String> list=Arrays.asList(beforeShuffle);
    	Collections.shuffle(list);
    	StringBuilder sb=new StringBuilder();
    	for (int i = 0; i < list.size(); i++) {
			sb.append(list.get(i));
		}
    	String afterShuffle=sb.toString();
    	String result=afterShuffle.substring(0,num);
    	return result;
    }
    /**
     * 生成随机字符串，长度最大不超过10位
     * @param num 指定字符串长度
     * @return
     * @throws Exception
     */
    public static String generateNumberStr(int num) throws Exception{
    	String[] beforeShuffle=new String[]{"0","1","2","3","4","5","6","7",
			"8","9"};
    	if (beforeShuffle.length<num) {
			Tools.throwException("生成随机字符串失败，原因为超过最大位数！");
		}
    	List<String> list=Arrays.asList(beforeShuffle);
    	Collections.shuffle(list);
    	StringBuilder sb=new StringBuilder();
    	for (int i = 0; i < list.size(); i++) {
			sb.append(list.get(i));
		}
    	String afterShuffle=sb.toString();
    	String result=afterShuffle.substring(0,num);
    	return result;
    }
    /**
     * 生成带日期分钟的随机数，可用于订单
     * @param num 除日期分钟外随机码位数
     * @return 201612091251+num位随机码
     * @throws Exception
     */
    public static String generateNumberStrByDateMiniute(int num) throws Exception{
    	Date now=new Date();
    	return ToolDateTime.format(now, ToolDateTime.pattern_yyyyMMddHHmm)+generateNumberStr(num);
    }
    /**
     * 以指定分隔符连接字符串
     * @param objs
     * @param separator
     * @return
     */
    public static String contact(Object[] objs,String separator){
    	if (objs==null || objs.length==0) {
			return "";
		}
    	if (isEmpty(separator)) {
			separator="";
		}
    	StringBuilder sb=new StringBuilder();
    	boolean isFirst=true;
    	for (Object obj : objs) {
    		if (!isFirst) {
    			sb.append(separator);
			}else {
				isFirst=false;
			}
    		sb.append(obj.toString());
		}
    	return sb.toString();
    }
    /**
     * 以指定分隔符连接字符串
     * @param objs
     * @param separator
     * @return
     */
    @SuppressWarnings("rawtypes")
	public static String contact(List objs,String separator){
    	if (objs==null || objs.size()==0) {
			return "";
		}
    	if (isEmpty(separator)) {
			separator="";
		}
    	StringBuilder sb=new StringBuilder();
    	boolean isFirst=true;
    	for (Object obj : objs) {
    		if (!isFirst) {
    			sb.append(separator);
			}else {
				isFirst=false;
			}
    		sb.append(obj.toString());
		}
    	return sb.toString();
    }
    /**
     * 获取重复字符串
     * @param str 要重复的字符串
     * @param repeatTimes 重复次数
     * @return
     */
    public static String getRepeatStr(String str,int repeatTimes){
    	StringBuffer sb=new StringBuffer();
    	for (int i = 0; i < repeatTimes; i++) {
			sb.append(str);
		}
    	return sb.toString();
    }
    /**
     * 获取替换字符串中间值
     * @param str
     * @param replaceStr 如 ***
     * @param len
     * @return
     */
    public static String getReplaceStr(String str,String replaceStr,int len){
    	if (ToolString.isEmpty(str)) {
			return "";
		}
    	if(str.length()<3){
    		return str;
    	}
    	if(str.length()>3 && str.length()<=len){
    		len=str.length()-2;
    	}
		str=str.substring(0,len/2)+replaceStr+str.substring(str.length()-len/2,str.length());
		return str;
    }
    /**
     * 返回分隔列表
     * @param strs
     * @param separator
     * @return
     */
    public static List<Integer> getSplitListToInt(String strs,String separator){
    	List<Integer> ts=new ArrayList<Integer>();
    	if (ToolString.isEmpty(strs) || ToolString.isEmpty(separator)) {
			return ts;
		}
    	String[] ss=strs.split(separator);
    	for (String s : ss) {
    		if (ToolString.isDigetStr(s)) {
    			ts.add(Integer.parseInt(s));
			}
			
		}
    	return ts;
    }
    /**
     * 返回分隔列表
     * @param strs
     * @param separator
     * @return
     */
    public static List<Integer> getSplitListToInteger(String strs,String separator){
    	List<Integer> ts=new ArrayList<Integer>();
    	if (ToolString.isEmpty(strs) || ToolString.isEmpty(separator)) {
			return ts;
		}
    	String[] ss=strs.split(separator);
    	for (String s : ss) {
			ts.add(Integer.parseInt(s));
		}
    	return ts;
    }
    /**
     * 整数转字符串并补全
     * @param num 整数值
     * @param place 位数
     * @param ch 补全的字符
     * @return
     */
    public static String getFillStrFromInt(int num,Integer place,String ch){
    	return String.format("%"+ch+place+"d",num);
    }
    /**
     * 获取字符串前len个字符
     * @param str
     * @param len
     * @return
     */
    public static String getSubStr(String str,int len){
    	if (ToolString.isEmpty(str)) {
			return "";
		}
    	if (str.length()<len) {
			return str;
		}
    	return str.substring(0, len);
    }
    /**
     * 判断字符串是否为数字字符串
     * @param str
     * @return
     */
    public static Boolean isDigetStr(String str){
    	if (ToolString.isEmpty(str)) {
			return false;
		}
    	for(int i=0;i<str.length();i++){
    		char c=str.charAt(i);
    		int k=(char)c;
    		if (k>=48 && k<=57) {
				continue;
			}else {
				return false;
			}
    	}
    	return true;
    }
    /** 
     * 将emoji表情替换成* 
     *  
     * @param source 
     * @return 过滤后的字符串 
     */  
    public static String filterEmoji(String source) {  
        if(!isEmpty(source)){  
            return source.replaceAll("[\\ud800\\udc00-\\udbff\\udfff\\ud800-\\udfff]", "[表情]");  
        }else{  
            return source;  
        }  
    }  
    /**
     * 在字符串数组中是否存在str开始的字符串
     * @param str
     * @param strs
     * @return
     */
    public static boolean strInStartStrs(String str,String[] strs){
    	if (ToolString.isEmpty(str)||strs==null || strs.length==0) {
			return false;
		}
    	for (String s : strs) {
			if (s.startsWith(str)) {
				return true;
			}
		}
    	return false;
    }
    /**
     * 校验手机号
     * 
     * @param mobile
     * @return 校验通过返回true，否则返回false
     */
    public static boolean isMobile(String phone) {
    	if (isEmpty(phone)) {
			return false;
		}
        return Pattern.matches(regExp_Phone, phone);
    }
    
    /**
     * 校验邮箱
     * 
     * @param email
     * @return 校验通过返回true，否则返回false
     */
    public static boolean isEmail(String email) {
    	if (isEmpty(email)) {
			return false;
		}
        return Pattern.matches(regExp_email, email);
    }
    /**
     * 获取文件大小
     * @param fileS
     * @return
     */
    public static String GetFileSizeStr(Long fileS){
    	if(fileS==null || fileS.equals(0L)){
    		return "0B";
    	}
        String size = ""; 
        DecimalFormat df = new DecimalFormat("#.00"); 
       if (fileS < 1024) {
           size = df.format((double) fileS) + "B";
       } else if (fileS < 1048576) {
           size = df.format((double) fileS / 1024) + "KB";
       } else if (fileS < 1073741824) {
           size = df.format((double) fileS / 1048576) + "MB";
       } else {
           size = df.format((double) fileS / 1073741824) +"GB";
       }
        return size;
       }
    /**
     * 通过时间来获取cron表达式
     * 主要是通过某个人的生日，获取每年定时发送信息的cron表达式
     * @param date
     */
    public static String getCron(Date date){
    	String result = "";
    	if(date!=null){
	    	String strDate = ToolDateTime.format(date, "yyyy-MM-dd");
	    	String monthStr = strDate.substring(5, 7);
	    	String dayStr = strDate.substring(8);
	    	result = "0 0 8 "+Integer.parseInt(dayStr)+" "+Integer.parseInt(monthStr)+" ? *";
    	}
    	return result;
    }
    /**
     * 获取精准时间
     * @param date
     * @return
     */
    public static String getExactCron(Date date){
    	String result = "";
    	if(date!=null){
	    	String strDate = ToolDateTime.format(date, "yyyy-MM-dd");
	    	String monthStr = strDate.substring(5, 7);
	    	String dayStr = strDate.substring(8);
	    	result = "0 0 8 "+Integer.parseInt(dayStr)+" "+Integer.parseInt(monthStr)+" ? *";
    	}
    	return result;
    }
    /**
     * 是否只包括数字
     * @param str
     * @return
     */
    public static Boolean containNum(String str){
    	if(ToolString.isEmpty(str)){
    		return false;
    	}
    	for(int i=0; i<str.length();i++){
    		char temp = str.charAt(i);
    		if(temp>='0'||temp<='9'){
    			continue;
    		}else{
    			return false;
    		}
    	}
    	return true;
    }
    /**
     * 是否只包括字母
     * @param str
     * @return
     */
    public static Boolean containChar(String str){
    	if(ToolString.isEmpty(str)){
    		return false;
    	}
    	if(containNum(str)){
    		return false;
    	}
    	for(int i=0; i<str.length();i++){
    		char temp = str.charAt(i);
    		if(temp>='a'||temp<='z'||temp>='A'||temp<='Z'){
    			continue;
    		}else{
    			return false;
    		}
    	}
    	return true;
    }
    /**
     * 是否包含字母和数字之外的字符
     * true为只包括字母和数字
     * @param str
     * @return
     */
    public static Boolean containCharAndNum(String str){
    	if(ToolString.isEmpty(str)){
    		return false;
    	}
    	for(int i=0; i<str.length();i++){
    		char temp = str.charAt(i);
    		if(temp>='a'||temp<='z'||temp>='A'||temp<='Z'){
    			continue;
    		}else if(temp>='0'||temp<='9'){
    			continue;
    		}else{
    			return false;
    		}
    	}
    	return true;
    }
	public static void main(String[] args){
//		try {
//			System.out.println(ToolString.generateNumberStr(6));
//		} catch (Exception e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}		
		System.out.println(getReplaceStr("13146336832", "***", 6));
		System.out.println("a【】".indexOf("【"));
	}
	
}
