package zdatbit.common.utils;

import java.math.BigDecimal;
import java.text.DecimalFormat;

import com.jfinal.log.Logger;

public class ToolDouble {
	@SuppressWarnings("unused")
	private static Logger log = Logger.getLogger(ToolDouble.class);
	
	/**
	 * double精度调整
	 * @param doubleValue 需要调整的值123.454
	 * @param format 目标样式".##"
	 * @return
	 */
	public static String decimalFormatToString(double doubleValue, String format){
		DecimalFormat myFormatter = new DecimalFormat(format);  
		String formatValue = myFormatter.format(doubleValue);
		return formatValue;
	}
	/** 
     * 提供精确的加法运算。 
     * @param value1 被加数 
     * @param value2 加数 
     * @return 两个参数的和 
     */  
    public static Double add(Double value1, Double value2) {  
        BigDecimal b1 = new BigDecimal(Double.toString(value1));  
        BigDecimal b2 = new BigDecimal(Double.toString(value2));  
        return b1.add(b2).doubleValue();  
    } 
}
