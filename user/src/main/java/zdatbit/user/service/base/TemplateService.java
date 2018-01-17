/** 
 * @author  zhangdi 	
 * @date 创建时间：2017年8月7日 下午7:44:52  	
 */

package zdatbit.user.service.base;

import java.io.Serializable;
import java.net.URLDecoder;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang.StringUtils;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.session.Session;
import org.apache.shiro.subject.Subject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import zdatbit.common.utils.HtmlFilter;
import zdatbit.common.base.Expressions;
import zdatbit.user.dao.TemplateDao;
import zdatbit.common.base.Globals;


import com.jfinal.plugin.activerecord.Page;
import zdatbit.user.domain.User;

@SuppressWarnings({"rawtypes","unchecked"})
@Service(value="TemplateService")
@Transactional
public class TemplateService implements ITemplateService{
	@Autowired
	private TemplateDao dao;

	@Override
	public Object get(Class clazz,Serializable id) {
		return dao.get(clazz, id);
	}
	
	@Override
	public Object obtain(Class clazz,Serializable id) {
		return dao.obtain(clazz, id);
	}

	@Override
	public String save(Object instance) {
		if(dao.save(instance)) return "";
		return "【"+instance.toString()+"】保存失败<br>";
	}
	@Override
	public String merge(Object instance) {
		if(dao.merge(instance)) return "";
		return "【"+instance.toString()+"】更新失败<br>";
	}
	@Override
	public String update(Object instance) {
		if(dao.update(instance)) return "";
		return "【"+instance.toString()+"】更新失败<br>";
	}
	@Override
	public String deleteByID(Class clazz,Serializable id) {
		Object instance = dao.get(clazz, id);
		if(dao.delete(instance)) return "";
		return "【"+instance.toString()+"】删除失败<br>";
	}
	@Override
	public String delete(Object instance) {
		if(dao.delete(instance)) return "";
		return "【"+instance.toString()+"】删除失败<br>";
	}
	@Override
	public String evict(Object instance) {
		if(dao.evict(instance)) return "";
		return "【"+instance.toString()+"】托管失败<br>";
	}

	@Override
	public String saveBatch(List<Object> instances) {
		if(dao.saveBatch(instances)) return "";
		return "批量保存失败<br>";
	}

	@Override
	public <T> String deleteBatch(List<T> instances) {
		if(dao.deleteBatch(instances)) return "";
		return "批量删除失败<br>";
	}
	@Override
	public String deleteBatch(Class clazz,String ids) {
		String msg="";
		String[] idstr=ids.split(",");
		for (String id : idstr) {
			msg+=deleteByID(clazz,Integer.parseInt(id));
		}
		if(msg.length()<=0){
			return "";
		}
		return "批量删除失败<br>";
	}
	@Override
	public void flushSession() {
		dao.flushSession();
		
	}
	public <T> List<T> list(Class clazz){
		return dao.list(clazz);
	}
	@Override
	public <T> List<T> listByPropertys(Class clazz,List<String> propertyNames,
			List<Object> propertyValues, List<Expressions> expressions) {
		return dao.listByPropertys(clazz, propertyNames, propertyValues, expressions);
	}

	@Override
	public <T> List<T> pageByPropertys(Class clazz,List<String> propertyNames,
			List<Object> propertyValues, List<Expressions> expressions,
			int page, int rows) {
		return dao.pageByPropertys(clazz, propertyNames, propertyValues, expressions, (page-1)*rows, rows);
	}
	
	@Override
	public <T> Page<T> pagition(Class clazz,List<String> propertyNames,
			List<Object> propertyValues, List<Expressions> expressions,
			int pageNumber, int pageSize) {
		List<T> list = dao.pageByPropertys(clazz, propertyNames, propertyValues, expressions, (pageNumber-1)*pageSize, pageSize);
		
		long totalRow = 0;
		int totalPage = 0;
		
		long count = dao.countByPropertys(clazz, propertyNames, propertyValues,expressions);
		if (count >= 1)
			totalRow = count;		
		else
			return new Page<T>(list, pageNumber, pageSize, 0, 0);	// totalRow = 0;
		
		totalPage = (int) (totalRow / pageSize);
		if (totalRow % pageSize != 0) {
			totalPage++;
		}
		
		if (pageNumber > totalPage)
			return new Page<T>(list, pageNumber, pageSize, totalPage, (int)totalRow);
		
		return new Page<T>(list, pageNumber, pageSize, totalPage, (int)totalRow);
		
	}
	/**
	 * 通过hql来查询
	 * @param clazz
	 * @param where
	 * @param pageNumber
	 * @param pageSize
	 * @return
	 */
	public <T> Page<T> pageByHql(Class clazz,String where,Integer pageNumber,Integer pageSize){
		List<T> list = dao.listByHqlLimit(clazz,where, (pageNumber-1)*pageSize, pageSize);
		long count = dao.countByhql(clazz, where);
		
		long totalRow = 0;
		int totalPage = 0;
		if (count >= 1)
			totalRow = count;		
		else
			return new Page<T>(list, pageNumber, pageSize, 0, 0);	// totalRow = 0;
		
		totalPage = (int) (totalRow / pageSize);
		if (totalRow % pageSize != 0) {
			totalPage++;
		}
		
		if (pageNumber > totalPage)
			return new Page<T>(list, pageNumber, pageSize, totalPage, (int)totalRow);
		
		return new Page<T>(list, pageNumber, pageSize, totalPage, (int)totalRow);
	}
	@Override
	public long countByPropertys(Class clazz,List<String> propertyNames,
			List<Object> propertyValues, List<Expressions> expressions) {
		return dao.countByPropertys(clazz, propertyNames, propertyValues, expressions);
	}
	/**
	 * 获取当前用户
	 * @return
	 */
	public User getCurrentUser(){
		Subject subject=SecurityUtils.getSubject();
		Session session=subject.getSession();
		return (User) session.getAttribute(Globals.SessionUserInfoField);
	}

	/**
	 * 分页带参方法
	 * @param request
	 * @return
	 */
	@SuppressWarnings("deprecation")
	public String setPaginationQuery(HttpServletRequest request){
		String pageQueryStr="_page";
		
		Map<String, String[]> map=request.getParameterMap();
		for (String key : map.keySet()) {
			String[] values=map.get(key);
			//解析出键值 
				if(!(key.equals(Globals.PageNumberField) || key.equals(Globals.PageSizeField) || key.equals("_page"))){
					for (String value : values) {
						if (StringUtils.isNotEmpty(value)) {
							pageQueryStr+="&"+key+"="+ HtmlFilter.escapeHtml(URLDecoder.decode(value));
						}
						
					}
					
				}
		}
		return pageQueryStr;
	}
}
