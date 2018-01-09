package zdatbit.common.utils;

import java.text.DecimalFormat;

public class ToolStringFormat {
	//private static Logger log = Logger.getLogger(ToolStringFormat.class);
	
	public static String stringFormat(String string){
		Double db = Double.parseDouble(string);
		DecimalFormat decimalFormat = new DecimalFormat("###0.00");
		return decimalFormat.format(db);
	}
	
	/**
	 * 手机号中间4位打*
	 * @param phone
	 */
	public static String hidePhoneNum(String phone){
		String str="";
		if(phone==null||phone.equals(""))
			return "";
		str = phone.substring(3, 7);
		str=phone.replace(str, "****");
		return str;
	}
	/**
	 * 银行卡号显示最后4位，其余为*
	 * @param card
	 */
	public static String hideCardNum(String card){
		String str="";
		if(card==null||card.equals(""))
			return "";
//		str = card.substring(4,card.length()-5);
		for(int i=4; i<card.length()-4; i++){
			str=str+"*";
		}
		System.out.println(card.substring(card.length()-4));
		return card.substring(0,4)+str+card.substring(card.length()-4);
	}
	/**
	 * 验证是否是数字字母，并且是6－24位的
	 */
	public static boolean checkString(String str){
		if(str==null)
			return false;
		int length = str.length();
		int mark=0;
		char[] s = str.toCharArray();
		if(length>=6&&length<=24){
			for(int i=0; i<length;i++){
				if(s[i]>='0'&&s[i]<='9' || s[i]>='a'&&s[i]<='z' || s[i]>='A'&&s[i]<='Z'){
					mark++;
					continue;
				}else{
					return false;
				}
			}
		}
		if(mark>=length)
			return true;
		return false;
	}
	public static void main(String[] args){
		String str="99.99999";
		int i=100;
		System.out.println(stringFormat(str));
		System.out.println(stringFormat(String.valueOf(i)).equals(stringFormat(str)));
	}
}
