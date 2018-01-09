package zdatbit.common.utils;

import java.util.Date;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * 模板工具类
 * @author Zhoudc
 *
 */
public class ToolTemplate {
	/**
	 * 获取模板内容
	 * @param messageTemplate 模板，{{变量}}代表要替换的参数
	 * @param params 传入参数
	 * @return
	 * @throws Exception
	 */
	public static String getTemplateContent(String content,Map<String, Object> params) throws Exception{
		if (content==null) {
			Tools.throwException("参数错误");
		}
		
		Pattern pattern=Pattern.compile("\\{\\{(.*?)\\}\\}");
		//查找内容替换项
		Matcher matcherContent=pattern.matcher(content);
		Set<String> keys=new HashSet<String>();
		while(matcherContent.find()){
			keys.add(matcherContent.group(1));
		}
		
		if (params!=null && !params.isEmpty()) {
			//处理消息内容替换
			for (String key : keys) {
				if (params.containsKey(key)) {
					Object value=params.get(key);
					if (value!=null && value.getClass().equals(Date.class)) {
						content=content.replaceAll("\\{\\{"+key+"\\}\\}", ToolDateTime.format((Date)value,"yyyy年MM月dd日"));
					}else if(value!=null) {
						content=content.replaceAll("\\{\\{"+key+"\\}\\}", String.valueOf(params.get(key)));
					}else {
						content=content.replaceAll("\\{\\{"+key+"\\}\\}", "");
					}
				}else {
					content=content.replaceAll("\\{\\{"+key+"\\}\\}", "");
				}
			}
		}
		return content;
	}
}
