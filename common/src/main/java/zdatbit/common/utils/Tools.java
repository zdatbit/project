package zdatbit.common.utils;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.util.UUID;


public class Tools {
	
	/**
	 * 手动抛出错误，上层需要抓取错误
	 * @param message 错误信息
	 * @throws Exception
	 */
	public static void throwException(String message) throws ToolsException{
		ToolsException ex=new ToolsException(message);
		throw ex;
	}
	/**
	 * 从异常中抽取错误信息
	 * @param e
	 * @return
	 */
	public static String getExceptionMsg(Exception e){
		String message="";
		if (e.getMessage()!=null) {
			return e.getMessage();
		}else {
			Throwable th=e.getCause();
			while(th!=null){
				if (th.getMessage()!=null) {
					/*if (th instanceof ActiveRecordException) {
						if (th.getMessage().contains("Data too long")) {
							return "The length of content limit is exceeded, please fill in again.";
						}else {
							return "Your request has been blocked.";
						}
						
					}else {
						return th.getMessage();
					}*/
					return th.getMessage();
					
				}else {
					th=th.getCause();
				}
			}
		}
		if (ToolString.isEmpty(message)) {
			message="Error, please retry.";
		}
		return message;
	}
	/**
	 * 获取异常详细
	 * @param e
	 * @return
	 */
	public static String getExceptionDetail(Exception e){
		ByteArrayOutputStream baos = new ByteArrayOutputStream();  
    	e.printStackTrace(new PrintStream(baos));  
    	String exception = baos.toString();  
        return exception;
	}
	/**
	 * 获取UUID by jdk
	 * @author 董华健    2012-9-7 下午2:22:18
	 * @return
	 */
	public static String getUuidByJdk(boolean is32bit){
		String uuid = UUID.randomUUID().toString();
		if(is32bit){
			return uuid.toString().replace("-", ""); 
		}
		return uuid;
	}
}
