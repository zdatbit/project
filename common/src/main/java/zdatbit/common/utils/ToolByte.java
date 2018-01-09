package zdatbit.common.utils;

import java.io.UnsupportedEncodingException;

/**
 * Byte工具类
 * @author Liuzw
 */
public class ToolByte {
	/**
	 * byte数组转字符串
	 * @param bytes
	 * @return
	 */
	public static String byte2String(byte bytes[]) {
//        byte bytes[] = new byte[] { 50, 0, -1, 28, -24 };       
        try {
            return new String(bytes, "ISO-8859-1");           
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
		return null;
       
    }
	public static byte[] string2Byte(String string) {
      try {
          return string.getBytes("ISO-8859-1");
      } catch (UnsupportedEncodingException e) {
          e.printStackTrace();
      }
      return null;     
	}
	
	public static void main(String[] args) {
		
	}
}