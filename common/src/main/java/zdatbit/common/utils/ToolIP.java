package zdatbit.common.utils;

import java.io.BufferedReader;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

import net.sf.json.JSONObject;

import org.apache.commons.lang.StringEscapeUtils;

import com.jfinal.log.Logger;

/**
 * IP工具类
 * 
 * @author zwliu
 */
public class ToolIP {

	@SuppressWarnings("unused")
	private static Logger log = Logger.getLogger(ToolIP.class);

	
	/**
	 * 判断ip是否属于中国
	 * @param IP
	 * @return
	 * @throws Exception 
	 */
	public static boolean isCNIp(String IP) throws Exception {
		JSONObject obj = GetAddressByIp(IP);
		if(obj==null){
			Tools.throwException("获取IP异常");
		}
		//{"country":"未分配或者内网IP","country_id":"IANA",
		//{"country":"中国","country_id":"CN","area":"华北","area_id":"100000","region":"北京市","region_id":"110000","city":"北京市","city_id":"110100","county":"","county_id":"-1","isp":"光环新网","isp_id":"100089","ip":"54.222.232.183"}
		String country=obj.get("country").toString();
		String country_id =obj.get("country_id").toString();
		return country.equals("中国")||country.equals("未分配或者内网IP")||country_id.equals("CN")||country_id.equals("IANA");
	}
	/**
	 * 获取国家
	 * @param IP
	 * @return
	 * @throws Exception 
	 */
	public static String GetCountryByIp(String IP) throws Exception {
		JSONObject obj = GetAddressByIp(IP);
		if(obj==null){
			Tools.throwException("获取IP异常");
		}
		return obj.get("country").toString();
	}
	/**
	 *
	 * @param IP
	 * @return
	 * @throws Exception 
	 */
	public static JSONObject GetAddressByIp(String IP) throws Exception {
//		String resout = "";
		JSONObject result = null;
		try {
			String str = getJsonContent("http://ip.taobao.com/service/getIpInfo.php?ip="
					+ IP);
			System.out.println(str);
//			{"code":0,"data":{"country":"\u4e2d\u56fd","country_id":"CN","area":"\u534e\u5317","area_id":"100000","region":"\u5317\u4eac\u5e02","region_id":"110000","city":"\u5317\u4eac\u5e02","city_id":"110100","county":"","county_id":"-1","isp":"\u5149\u73af\u65b0\u7f51","isp_id":"100089","ip":"54.222.232.183"}}
			JSONObject obj = JSONObject.fromObject(str);
			result = JSONObject.fromObject(obj.getString("data"));
			String code = obj.get("code")+"";
			if (!code.equals("0")) {
//				resout = "IP地址有误";
				Tools.throwException("IP地址有误");						
			} 
		} catch (Exception e) {
			e.printStackTrace();
//			resout = "获取IP地址异常：" + e.getMessage();
			Tools.throwException("获取IP地址异常：" + e.getMessage());
		}
//		return resout;
		return result;

	}

	public static String getJsonContent(String urlStr) {
		try {// 获取HttpURLConnection连接对象
			URL url = new URL(urlStr);
			HttpURLConnection httpConn = (HttpURLConnection) url
					.openConnection();
			// 设置连接属性
			httpConn.setConnectTimeout(30000);
			//httpConn.setReadTimeout(10 * 1000);
			httpConn.setDoInput(true);
			httpConn.setRequestMethod("GET");
			/*httpConn.setConnectTimeout(2000);// 设置连接超时时间，单位毫秒
			httpConn.setReadTimeout(2000);// 设置读取数据超时时间，单位毫秒
			httpConn.setDoOutput(true);// 是否打开输出流 true|false
			httpConn.setDoInput(true);// 是否打开输入流true|false
			httpConn.setRequestMethod("POST");// 提交方法POST|GET
			httpConn.setUseCaches(false);// 是否缓存true|false
			httpConn.connect();// 打开连接端口*/
			// 获取相应码
			int respCode = httpConn.getResponseCode();
			if (respCode == 200) {				
				return ConvertStream2Json(httpConn.getInputStream());
			}
		} catch (MalformedURLException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return "";
	}
	@SuppressWarnings("unused")
	private static String ConvertStream2Json2(InputStream inputStream) {
		String jsonStr = "";
		// ByteArrayOutputStream相当于内存输出流
		ByteArrayOutputStream out = new ByteArrayOutputStream();
		byte[] buffer = new byte[1024];
		int len = 0;
		// 将输入流转移到内存输出流中
		try {
			while ((len = inputStream.read(buffer, 0, buffer.length)) != -1) {
				out.write(buffer, 0, len);
			}
			// 将内存流转换为字符串
			jsonStr = new String(out.toByteArray());
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return jsonStr;		
	}
	private static String ConvertStream2Json(InputStream inputStream) {
		String inputline = "";
		String info = "";
		try {
			InputStreamReader inStream = new InputStreamReader(
					inputStream, "UTF-8");
			BufferedReader buffer = new BufferedReader(inStream);
	
			while ((inputline = buffer.readLine()) != null) {
				info += inputline;
			}
		}catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
		return info;
	}
	/**
	 * 方法二
	 * @param ip
	 * @return
	 */
	public static String getIpArea(String ip) {

		String path = "http://ip.taobao.com/service/getIpInfo.php?ip=" + ip;
		String inputline = "";
		String info = "";

		try {
			URL url = new URL(path);
			HttpURLConnection conn = (HttpURLConnection) url.openConnection();
			conn.setReadTimeout(10 * 1000);
			conn.setRequestMethod("GET");

			InputStreamReader inStream = new InputStreamReader(
					conn.getInputStream(), "UTF-8");
			BufferedReader buffer = new BufferedReader(inStream);

			while ((inputline = buffer.readLine()) != null) {
				info += inputline;
			}

		} catch (MalformedURLException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}

		JSONObject jsonob = JSONObject.fromObject((JSONObject.fromObject(info)
				.getString("data")));
		String city = StringEscapeUtils.escapeSql(jsonob.getString("city"));

		return city;
	}
	public static void main(String[] args) {
		System.out.println(getIpArea("183.62.35.197"));

	}

}
