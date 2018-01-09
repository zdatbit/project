package zdatbit.common.utils;

import com.jfinal.log.Logger;

public class ToolUTF8 {

	public static Logger log = Logger.getLogger(ToolUTF8.class);
	
	public static String stringFormatToUTF8(String str){
		String formatStr = null;
		try{
			byte[] converStr = str.getBytes("UTF-8");
			formatStr = new String(converStr,"UTF-8");
		}catch(Exception e){
			e.printStackTrace();
		}
		return formatStr;
	}
}
