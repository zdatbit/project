package zdatbit.common.utils;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import org.apache.log4j.Logger;

public class ToolMessage {
	
	public static Logger log= Logger.getLogger(ToolMessage.class);
	/**
	 * 群发短信
	 * @param mobiles
	 * @param Content
	 * @param stime
	 * @param rrid
	 * @return
	 */
	public static String MessageSend(List<String> mobiles, String contents, Calendar stime, Object rrid){
		String mobile = mobiles.get(0);
		String content = contents + "[关心万家]";
		content = ToolUTF8.stringFormatToUTF8(content);
		String sendMessage="";
		for(int i=1; i<mobiles.size(); i++){
			mobile+=","+mobiles.get(i);
		}
		if(stime==null){
			if(rrid!=null){
				sendMessage="http://sdk2.entinfo.cn:8061/mdsmssend.ashx?sn=SDK-666-010-01581&pwd=FA59BF85B9D62FF59F0071B6FF450BF9&mobile="+mobile+"&content="+content+"&ext=&stime=&rrid="+rrid+"&msgfmt=";
			}else{
				sendMessage="http://sdk2.entinfo.cn:8061/mdsmssend.ashx?sn=SDK-666-010-01581&pwd=FA59BF85B9D62FF59F0071B6FF450BF9&mobile="+mobile+"&content="+content+"&ext=&stime=&rrid=&msgfmt=";
			}
		}else{
			if(rrid!=null){
				sendMessage="http://sdk2.entinfo.cn:8061/mdsmssend.ashx?sn=SDK-666-010-01581&pwd=FA59BF85B9D62FF59F0071B6FF450BF9&mobile="+mobile+"&content="+content+"&ext=&stime="+stime+"&rrid="+rrid+"&msgfmt=";
			}else{
				sendMessage="http://sdk2.entinfo.cn:8061/mdsmssend.ashx?sn=SDK-666-010-01581&pwd=FA59BF85B9D62FF59F0071B6FF450BF9&mobile="+mobile+"&content="+content+"&ext=&stime="+stime+"&rrid=&msgfmt=";
			}
		}
		return sendMessage;
	}
	/**
	 * 发送给指定手机号码短信
	 * @param mobile
	 * @param contents
	 * @return
	 */
	public static String MessageSend(String mobile, String contents){
		List<String> mobiles=new ArrayList<String>();
		mobiles.add(mobile);
		return MessageSend(mobiles,contents);
	}
	public static String MessageSend(List<String> mobiles, String contents){
		return MessageSend(mobiles,contents,null,null);
	}
	
	public static boolean privateMessage(String mobile,String content) throws Exception{
		List<String> mobiles = new ArrayList<String>();
		mobiles.add(mobile);
//		String str=MessageSend(mobiles,content,null,1);
//		if(getReturnData(str).equals("1")){
//			return true;
//		}
//		else{
//			return false;
//		}
		return true;
	}
	/**
	 * 发送个性短信
	 * @param mobiles
	 * @param contents
	 * @param stime
	 * @param rrid
	 * @return
	 */
	public static String specificMessageSend(List<String> mobiles, List<String> contents, Calendar stime, Object rrid){
		String mobile = mobiles.get(0);
		String content = ToolUTF8.stringFormatToUTF8(contents.get(0)+"[关心万家]");
		String sendMessage="";
		for(int i=1; i<mobiles.size(); i++){
			mobile+=","+mobiles.get(i);
		}
		for(int i=1; i<contents.size(); i++){
			content+=","+ToolUTF8.stringFormatToUTF8(contents.get(i)+"[关心万家]");
		}
		if(stime==null){
			if(rrid!=null){
				sendMessage="http://sdk2.entinfo.cn:8061/mdgxsend.ashx?sn=SDK-666-010-01581&pwd=FA59BF85B9D62FF59F0071B6FF450BF9&mobile="+mobile+"&content="+content+"&ext=&stime=&rrid="+rrid+"&msgfmt=";
			}else{
				sendMessage="http://sdk2.entinfo.cn:8061/mdgxsend.ashx?sn=SDK-666-010-01581&pwd=FA59BF85B9D62FF59F0071B6FF450BF9&mobile="+mobile+"&content="+content+"&ext=&stime=&rrid=&msgfmt=";
			}
		}else{
			if(rrid!=null){
				sendMessage="http://sdk2.entinfo.cn:8061/mdgxsend.ashx?sn=SDK-666-010-01581&pwd=FA59BF85B9D62FF59F0071B6FF450BF9&mobile="+mobile+"&content="+content+"&ext=&stime="+stime+"&rrid="+rrid+"&msgfmt=";
			}else{
				sendMessage="http://sdk2.entinfo.cn:8061/mdgxsend.ashx?sn=SDK-666-010-01581&pwd=FA59BF85B9D62FF59F0071B6FF450BF9&mobile="+mobile+"&content="+content+"&ext=&stime="+stime+"&rrid=&msgfmt=";
			}
		}
		return sendMessage;
	}
	
	public static String specificMessageSend(List<String> mobiles, List<String> contents){
		return specificMessageSend(mobiles, contents, null, null);
	}
	
	public static String getReturnData(String urlString) throws Exception {
		String res = ""; 
		try { 
			URL url = new URL(urlString);
			HttpURLConnection conn = (HttpURLConnection)url.openConnection();
			conn.setDoOutput(true);
			conn.setDoInput(true);
			conn.setRequestMethod("GET");
			conn.connect();
			BufferedReader in = new BufferedReader(new InputStreamReader(conn.getInputStream(),"UTF-8"));
			String line;
			while ((line = in.readLine()) != null) {
				res += line;
			}
			in.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
//		System.out.println(res);
		return res;
	}
	
	public static void index(String url) throws Exception{
		String str = "http://172.20.19.179:10080/solr/collection1/dataimport?command=full-import&clean=true&commit=true&optimize=true&wt=json&entity=cap_doctor&indent=true&verbose=true&debug=false";
		getReturnData(str);
	}
	
	public static void main(String[] args) throws Exception{
//		List<String> mobiles = new ArrayList<String>();
		String mobiles="15652998191";
//		mobiles.add("18810992306");
//		List<String> contents = new ArrayList<String>();
		String contents="张迪123456465456[关心万家]";
//		contents.add("高志龙[关心万家]");
		boolean urlString=privateMessage(mobiles, contents);
//		String str=getReturnData(urlString);
		System.out.println(urlString);
//		index("");
//		CommonService.service.docFullIndex();
	}
}
 