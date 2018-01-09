package zdatbit.common.utils;

import java.io.UnsupportedEncodingException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.KeyGenerator;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;


import com.jfinal.log.Logger;

public class ToolAES {

	private static Logger logger = Logger.getLogger(ToolAES.class);

	/**
	 * 密钥算法
	 */
	public static final String KEY_ALGORITHM = "AES";

	/** 
     * 加密 
     *  
     * @param content 需要加密的内容 
     * @param password  加密密码 
     * @return 
     */  
    public static byte[] encrypt(String content, String password) {  
        try {             
            KeyGenerator kgen = KeyGenerator.getInstance("AES");  
            kgen.init(128, new SecureRandom(password.getBytes()));  
            SecretKey secretKey = kgen.generateKey();  
            byte[] enCodeFormat = secretKey.getEncoded();  
            SecretKeySpec key = new SecretKeySpec(enCodeFormat, "AES");  
            Cipher cipher = Cipher.getInstance("AES");// 创建密码器  
            byte[] byteContent = content.getBytes("utf-8");  
            cipher.init(Cipher.ENCRYPT_MODE, key);// 初始化  
            byte[] result = cipher.doFinal(byteContent);  
            return result; // 加密  
        } catch (NoSuchAlgorithmException e) {  
            e.printStackTrace();  
        } catch (NoSuchPaddingException e) {  
            e.printStackTrace();  
        } catch (InvalidKeyException e) {  
            e.printStackTrace();  
        } catch (UnsupportedEncodingException e) {  
            e.printStackTrace();  
        } catch (IllegalBlockSizeException e) {  
            e.printStackTrace();  
        } catch (BadPaddingException e) {  
            e.printStackTrace();  
        }  
        return null;  
    }
    /* AES加密 
     *  
     * @param content 需要加密的内容 
     * @param password  加密密码 
     * @return 
     */  
    public static String encrypt3(String content, String password) {  
        try {             
            KeyGenerator kgen = KeyGenerator.getInstance("AES");  
            kgen.init(128, new SecureRandom(password.getBytes()));  
            SecretKey secretKey = kgen.generateKey();  
            byte[] enCodeFormat = secretKey.getEncoded();  
            SecretKeySpec key = new SecretKeySpec(enCodeFormat, "AES");  
            Cipher cipher = Cipher.getInstance("AES");// 创建密码器  
            byte[] byteContent = content.getBytes("utf-8");  
            cipher.init(Cipher.ENCRYPT_MODE, key);// 初始化  
            byte[] result = cipher.doFinal(byteContent);  
            return parseByte2HexStr(result); // 加密结果转字符串并返回  
        } catch (NoSuchAlgorithmException e) {  
            e.printStackTrace();  
        } catch (NoSuchPaddingException e) {  
            e.printStackTrace();  
        } catch (InvalidKeyException e) {  
            e.printStackTrace();  
        } catch (UnsupportedEncodingException e) {  
            e.printStackTrace();  
        } catch (IllegalBlockSizeException e) {  
            e.printStackTrace();  
        } catch (BadPaddingException e) {  
            e.printStackTrace();  
        }  
        return null;  
    }

    /**解密 
     * @param content  待解密内容 
     * @param password 解密密钥 
     * @return 
     */  
    public static byte[] decrypt(byte[] content, String password) {
    	logger.error("AES解密开始");
        try {        	
        	KeyGenerator kgen = KeyGenerator.getInstance("AES");         	
        	SecureRandom random=SecureRandom.getInstance("SHA1PRNG");
        	random.setSeed(password.getBytes());
        	kgen.init(128, random);
//          kgen.init(128, new SecureRandom(password.getBytes()));
            SecretKey secretKey = kgen.generateKey();
            byte[] enCodeFormat = secretKey.getEncoded();
            SecretKeySpec key = new SecretKeySpec(enCodeFormat, "AES");
            Cipher cipher = Cipher.getInstance("AES");// 创建密码器             
            cipher.init(Cipher.DECRYPT_MODE, key);// 初始化 
            byte[] result = cipher.doFinal(content); 
        	logger.error("解密结束,解密后result="+result);
            return result; // 加密  
        } catch (NoSuchAlgorithmException e) { 
        	logger.error("异常NoSuchAlgorithmException"+e);
        	e.printStackTrace();  
        } catch (NoSuchPaddingException e) { 
        	logger.error("异常NoSuchPaddingException"+e);
            e.printStackTrace();  
        } catch (InvalidKeyException e) {  
        	logger.error("异常InvalidKeyException"+e);
            e.printStackTrace();  
        } catch (IllegalBlockSizeException e) { 
        	logger.error("异常IllegalBlockSizeException"+e);
            e.printStackTrace();  
        } catch (BadPaddingException e) {
        	logger.error("异常BadPaddingException"+e);
            e.printStackTrace();  
        }  
        return null;  
    }
    /**将二进制转换成16进制 
     * @param buf 
     * @return 
     */  
    public static String parseByte2HexStr(byte buf[]) {  
        StringBuffer sb = new StringBuffer();  
        for (int i = 0; i < buf.length; i++) {  
            String hex = Integer.toHexString(buf[i] & 0xFF);  
            if (hex.length() == 1) {  
               hex = '0' + hex;  
            }  
            sb.append(hex.toUpperCase());  
        }  
        return sb.toString();  
    }
    /**将16进制转换为二进制 
     * @param hexStr 
     * @return 
     */  
    public static byte[] parseHexStr2Byte(String hexStr) {
    	logger.error("将16进制转换为二进制--hexStr==="+hexStr);
        if (hexStr.length() < 1)  
            return null;  
        byte[] result = new byte[hexStr.length()/2];  
        for (int i = 0;i< hexStr.length()/2; i++) {        	
        	int high = Integer.parseInt(hexStr.substring(i*2, i*2+1), 16);  
            int low = Integer.parseInt(hexStr.substring(i*2+1, i*2+2), 16);
        	result[i] = (byte) (high * 16 + low);
        } 
        logger.error("将16进制转换为二进制--result==="+result);
        return result;
    }
    
    
    
    //=====================================第二种加密方法=================================//
    /** 
     * 加密 
     * 这种加密方式有两种限制
     *  ①密钥必须是16位的
     *  ②待加密内容的长度必须是16的倍数，如果不是16的倍数，就会出如下异常：javax.crypto.IllegalBlockSizeException: Input length not multiple of 16 bytes
         at com.sun.crypto.provider.SunJCE_f.a(DashoA13*..)
         at com.sun.crypto.provider.SunJCE_f.b(DashoA13*..)
         at com.sun.crypto.provider.SunJCE_f.b(DashoA13*..)
         at com.sun.crypto.provider.AESCipher.engineDoFinal(DashoA13*..)
         at javax.crypto.Cipher.doFinal(DashoA13*..)
     * 要解决如上异常，可以通过补全传入加密内容等方式进行避免。(参见ToolSecurityAES工具类)
     * @param content 需要加密的内容 
     * @param password  加密密码 
     * @return 
     */  
    public static byte[] encrypt2(String content, String password) {  
	    try {  
            SecretKeySpec key = new SecretKeySpec(password.getBytes(), "AES");  
            Cipher cipher = Cipher.getInstance("AES/ECB/NoPadding");  
            byte[] byteContent = content.getBytes("utf-8");  
            cipher.init(Cipher.ENCRYPT_MODE, key);// 初始化  
            byte[] result = cipher.doFinal(byteContent);  
            return result; // 加密  
	    } catch (NoSuchAlgorithmException e) {  
            e.printStackTrace();  
	    } catch (NoSuchPaddingException e) {  
            e.printStackTrace();  
	    } catch (InvalidKeyException e) {  
            e.printStackTrace();  
	    } catch (UnsupportedEncodingException e) {  
            e.printStackTrace();  
	    } catch (IllegalBlockSizeException e) {  
            e.printStackTrace();  
	    } catch (BadPaddingException e) {  
            e.printStackTrace();  
	    }  
	    return null;  
    }
   
    public static void main(String args[]) throws UnsupportedEncodingException {
//	 	String content = "test";  
//	    String password = "12345678";  
//	    //加密  
//	    System.out.println("加密前：" + content);  
//	    byte[] encryptResult = encrypt(content, password);
//	    String encryptResultStr=new String(encryptResult,"ISO-8859-1");
//	    System.out.println("加密后：" + encryptResultStr);
//	    //解密前,将字符串,转为byte
//	    byte[] encryptResultByte=encryptResultStr.getBytes("ISO-8859-1");
//	    System.out.println("解密前,将字符串,转为byte：" + encryptResultByte);
//	    //解密  
////	    byte[] decryptResult = decrypt(encryptResult,password); 
//	    byte[] decryptResult = decrypt(encryptResultByte,password); 
//	    System.out.println("解密后：" + new String(decryptResult));
	    
	    String content = "123456";
//	    String password = "123456";
	    String password = "cctvnews_sz";
	    //加密  
	    System.out.println("加密前：" + content);  
	    byte[] encryptResult = encrypt(content, password);  
	    String encryptResultStr = parseByte2HexStr(encryptResult);  
	    System.out.println("加密后：" + encryptResultStr);  
	    //解密  
	    byte[] decryptFrom = parseHexStr2Byte(encryptResultStr);  
	    byte[] decryptResult = decrypt(decryptFrom,password);  
	    System.out.println("解密后：" + new String(decryptResult));  
    }
}
   
