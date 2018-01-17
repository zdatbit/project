package zdatbit.log.service.base;

import com.jfinal.plugin.activerecord.Page;
import zdatbit.common.base.Expressions;

import java.io.Serializable;
import java.util.List;

@SuppressWarnings("rawtypes")
public interface ITemplateService {
	
	/********************************************************************
	 * 							获取 持久态 对象							*
	 ********************************************************************/
	/**
	 * @Description: 根据 主键 获取对象
	 * @param id
	 */
	public Object get(Class clazz, Serializable id);
	
	/********************************************************************
	 * 							获取 托管态 对象							*
	 ********************************************************************/
	/**
	 * @Description: 根据 主键 获取对象
	 * @param id
	 */
	public Object obtain(Class clazz, Serializable id);
	
	/********************************************************************
	 * 							保存与删除对象							*
	 ********************************************************************/
	/**
	 * @Description: 保存 对象
	 * @param instance 
	 * @return boolean
	 */
	public String save(Object instance);
	/**
	 * @Description: 保存 对象
	 * @param instance 
	 * @return boolean
	 */
	public String update(Object instance);
	/**
	 * @Description: 保存 对象
	 * @param instance 
	 * @return boolean
	 */
	public String merge(Object instance);
	/**
	 * @Description: 删除 对象
	 * @return boolean
	 */
	public String deleteByID(Class clazz, Serializable id);
	/**
	 * @Description: 删除 对象
	 * @param instance 
	 * @return boolean
	 */
	public String delete(Object instance);
	/**
	 * @Description: 托管 对象
	 * @param instance
	 * @return boolean
	 */
	public String evict(Object instance);
	
	/********************************************************************
	 * 							保存与删除对象集合							*
	 ********************************************************************/
	/**
	 * @Description: 批量 保存 
	 * @param instances 
	 * @return boolean
	 */
	public String saveBatch(List<Object> instances);
	/**
	 * @Description: 批量 删除
	 * @param instances 
	 * @return boolean
	 */
	public <T> String deleteBatch(List<T> instances);
	/**
	 * @Description: 批量 删除
	 * @return boolean
	 */
	public String deleteBatch(Class clazz, String ids);

	/********************************************************************
	 * 							hibernate session commit				*
	 ********************************************************************/
	/**
	 * @Description: 将session刷新
	 */
	public void flushSession();
	
	/********************************************************************
	 * 							高级条件过滤								*
	 ********************************************************************/
	/**
	 * 根据 多属性条件 获取 全部 对象集合
	 * @param propertyNames		属性名
	 * @param propertyValues	属性值
	 * @param expressions		条件表达式
	 * @return
	 */
	public <T> List<T> listByPropertys(Class clazz, List<String> propertyNames, List<Object> propertyValues, List<Expressions> expressions);
	/**
	 * 根据 多属性条件 获取 部分 对象集合（用于分页）
	 * @param propertyNames		属性名
	 * @param propertyValues	属性值
	 * @param expressions		条件表达式
	 * @param firstResult		首条记录位置
	 * @param maxResults		记录总条数
	 * @return
	 */
	public <T> List<T> pageByPropertys(Class clazz, List<String> propertyNames, List<Object> propertyValues, List<Expressions> expressions, int firstResult, int maxResults);
	/**
	 * 根据 多属性条件 获取 部分 对象集合（用于分页）
	 * @param propertyNames		属性名
	 * @param propertyValues	属性值
	 * @param expressions		条件表达式
	 * @param firstResult		首条记录位置
	 * @param maxResults		记录总条数
	 * @return
	 */
	public <T> Page<T> pagition(Class clazz, List<String> propertyNames, List<Object> propertyValues, List<Expressions> expressions, int firstResult, int maxResults);
	/**
	 * 根据 多属性条件 获取 全部 对象 数量
	 * @param propertyNames		属性名
	 * @param propertyValues	属性值
	 * @param expressions		条件表达式
	 * @return
	 */
	public long countByPropertys(Class clazz, List<String> propertyNames, List<Object> propertyValues, List<Expressions> expressions);
	
	
}
