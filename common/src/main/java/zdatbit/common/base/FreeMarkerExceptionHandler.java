/** 
 * @author  zhangdi 	
 * @date 创建时间：2017年8月23日 下午4:04:34  	
 */

package zdatbit.common.base;

import freemarker.core.Environment;
import freemarker.template.TemplateException;
import freemarker.template.TemplateExceptionHandler;

import java.io.Writer;

public class FreeMarkerExceptionHandler implements TemplateExceptionHandler{


	public void handleTemplateException(TemplateException te, Environment env,
			Writer out) throws TemplateException {
	}

}
