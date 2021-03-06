/** 
 * @author  zhangdi 	
 * @date 创建时间：2017年8月4日 上午11:28:13  	
 */

package zdatbit.common.base;

import org.springframework.web.servlet.view.freemarker.FreeMarkerView;

import javax.servlet.http.HttpServletRequest;
import java.util.Map;

public class ContextFreeMarkerView extends FreeMarkerView {  
    private static final String CONTEXT_PATH = "zdatbit/common/base";
    @Override  
    protected void exposeHelpers(Map<String, Object> model,HttpServletRequest request) throws Exception {  
        model.put(CONTEXT_PATH, request.getContextPath());  
        super.exposeHelpers(model, request);  
    }  
}  
