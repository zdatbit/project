package zdatbit.common.utils;

import java.math.BigInteger;
import java.util.Random;

import com.jfinal.log.Logger;

/**
 * 随机数类
 */
public class ToolRandoms {

	@SuppressWarnings("unused")
	private static Logger log = Logger.getLogger(ToolRandoms.class);

	private static final Random random = new Random();

	// 定义验证码字符.去除了O、I、l、、等容易混淆的字母
	public static final char authCode[] = { 
		'A', 'B', 'C', 'D', 'E', 'F', 'G', 'H', 'G', 'K', 'M', 'N', 'P', 'Q', 'R', 'S', 'T', 'U', 'V', 'W', 'X', 'Y', 
		'a', 'c', 'd', 'e', 'f', 'g', 'h', 'k', 'm', 'n', 'p', 'r', 's', 't', 'u', 'v', 'w', 'x', 'y', 
		'3', '4', '5', '7', '8' };
	
	public static final int length = authCode.length;
	
	/**
	 * 生成验证码
	 * @return
	 */
	public static char getAuthCodeChar() {
		return authCode[number(0, length)];
	}
	
	/**
	 * 生成验证码
	 * @return
	 */
	public static String getAuthCode(int length) {
		StringBuilder sb = new StringBuilder();
		for (int i = 0; i < length; i++) {
			sb.append(authCode[number(0, length)]);
		}
		return sb.toString();
	}
	
	/**
	 * 产生两个数之间的随机数
	 * @param min 小数
	 * @param max 比min大的数
	 * @return int 随机数字
	 */
	public static int number(int min, int max) {
		return min + random.nextInt(max - min);
	}
	
	/**
	 * 产生两个数之间的随机数
	 * @param min 小数
	 * @param max 比min大的数
	 * @return long 随机数字
	 */
	public static long number(long min, long max) {
		long result;
		do{
			result=min + random.nextLong();
		}while(result<min||result>max);
		return result;
	}
	/**
	 * 产生16位随机数，介于1000000000000000-9999999999999999之间
	 * @return long 随机数字
	 */
	public static BigInteger number()
	{
		BigInteger result;
		long number1=number(10000000,99999999);
		long number2=number(0,99999999);
		StringBuffer str=new StringBuffer(16);
		str.append(String.valueOf(number1));
		String str2=String.valueOf(number2);
		for(int i=0;i<8-str2.length();i++)
		{
			str.append(0);
		}
		str.append(str2);		
		result=new BigInteger(str.toString());
		return result;
	}

	/**
	 * 产生12位随机数，介于100000000000-999999999999之间
	 * @return long 随机数字
	 */
	public static BigInteger number12()
	{
		BigInteger result;
		long number1=number(100000,999999);
		long number2=number(0,999999);
		StringBuffer str=new StringBuffer(12);
		str.append(String.valueOf(number1));
		String str2=String.valueOf(number2);
		for(int i=0;i<6-str2.length();i++)
		{
			str.append(0);
		}
		str.append(str2);
		result=new BigInteger(str.toString());
		return result;
	}
	/**
	 * @author Liuzw
	 * 产生8位随机数，介于10000000-99999999之间
	 * @return  随机数字
	 */
	public static BigInteger number8(){
		BigInteger result;
		long number1=number(1000,9999);
		long number2=number(0,9999);
		StringBuffer str=new StringBuffer(8);
		str.append(String.valueOf(number1));
		String str2=String.valueOf(number2);
		for(int i=0;i<4-str2.length();i++)
		{
			str.append(0);
		}
		str.append(str2);
		result=new BigInteger(str.toString());
		return result;
	}
	/**
	 * @author Liuzw
	 * 产生6位随机数，介于100000-999999之间
	 * @return  随机数字
	 */
	public static BigInteger number6(){
		BigInteger result;
		long number1=number(100,999);
		long number2=number(0,999);
		StringBuffer str=new StringBuffer(6);
		str.append(String.valueOf(number1));
		String str2=String.valueOf(number2);
		for(int i=0;i<3-str2.length();i++)
		{
			str.append(0);
		}
		str.append(str2);
		result=new BigInteger(str.toString());
		return result;
	}
	/**
	 * @author Liuzw
	 * 产生4位随机数，介于1000-9999之间
	 * @return  随机数字
	 */
	public static BigInteger number4(){
		BigInteger result;
		long number1=number(10,99);
		long number2=number(0,99);
		StringBuffer str=new StringBuffer(4);
		str.append(String.valueOf(number1));
		String str2=String.valueOf(number2);
		for(int i=0;i<2-str2.length();i++)
		{
			str.append(0);
		}
		str.append(str2);
		result=new BigInteger(str.toString());
		return result;
	}
	/**
	 * 产生0--number的随机数,不包括num
	 * @param number   数字
	 * @return int 随机数字
	 */
	public static int number(int number) {
		return random.nextInt(number);
	}

	/**
	 * 生成RGB随机数
	 * @return
	 */
	public static int[] getRandomRgb() {
		int[] rgb = new int[3];
		for (int i = 0; i < 3; i++) {
			rgb[i] = random.nextInt(255);
		}
		return rgb;
	}
	
}
