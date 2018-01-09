/** 
 * @author  zhangdi 	
 * @date 创建时间：2017年7月21日 下午2:04:27  	
 */

package zdatbit.common.utils;

public class ToolsException extends Exception{
	private static final long serialVersionUID = 1L;
	public ToolsException(){
		super();
	}
	public ToolsException(String msg){
		super(msg,new Throwable(msg));
	}
}
