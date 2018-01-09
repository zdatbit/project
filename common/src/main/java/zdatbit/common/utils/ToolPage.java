package zdatbit.common.utils;

import com.jfinal.log.Logger;

public class ToolPage {

	@SuppressWarnings("unused")
	private static Logger logger = Logger.getLogger(ToolPage.class);

	/**
	 * 根据总行数和每页大小获取总页数
	 * @param totalRow
	 * @param pageSize
	 * @return
	 */
	public static int getTotalPage(int totalRow,int pageSize){
		if(totalRow==0){
			return 0;
		}
		if (totalRow < pageSize){
            return 1;
        }else{
            return (totalRow/pageSize + (totalRow%pageSize==0?0:1));
        }
    }
}
   
