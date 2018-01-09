/** 
 * @author  zhangdi 	
 * @date 创建时间：2017年8月8日 上午11:15:23  	
 */

package zdatbit.user.dao;

import zdatbit.user.base.Expressions;

import java.io.Serializable;
import java.util.List;


public interface ITemplateDao {
	/********************************************************************
	 * 							获取 持久态 对象							*
	 ********************************************************************/
	/**
	 * @Description: 根据 主键 获取对象
	 * @param clazz
	 * @param id
	 * @return <T>
	 */
	public <T> Object get(final Class<T> clazz, final Serializable id);
	
	/********************************************************************
	 * 							获取 托管态 对象							*
	 ********************************************************************/
	/**
	 * @Description: 根据 主键 获取对象
	 * @param clazz
	 * @param id
	 * @return <T>
	 */
	public <T> Object obtain(final Class<T> clazz, final Serializable id);
	
	/********************************************************************
	 * 							保存与删除对象							*
	 ********************************************************************/
	/**
	 * @Description: 保存 对象
	 * @param instance 
	 * @return boolean
	 */
	public boolean save(Object instance);
	/**
	 * @Description: 保存 对象
	 * @param instance 
	 * @return boolean
	 */
	public boolean update(Object instance);
	/**
	 * @Description:更新操作
	 * @param instance
	 * @return boolean
	 */
	public boolean merge(Object instance);
	/**
	 * @Description: 删除 对象
	 * @param instance 
	 * @return boolean
	 */
	public boolean delete(Object instance);
	/**
	 * @Description: 托管 对象
	 * @param instance
	 * @return boolean
	 */
	public boolean evict(Object instance);
	
	/********************************************************************
	 * 							保存与删除对象集合							*
	 ********************************************************************/
	/**
	 * @Description: 批量 保存 
	 * @param instances 
	 * @return boolean
	 */
	public <T> boolean saveBatch(List<T> instances);
	/**
	 * @Description: 批量 删除
	 * @param instances 
	 * @return boolean
	 */
	public <T> boolean deleteBatch(List<T> instances);
	/**
	 * @Description: 托管 对象
	 * @param instance
	 * @return boolean
	 */
	public <T> boolean evictBatch(List<T> instance);

	/********************************************************************
	 * 							hibernate session commit				*
	 ********************************************************************/
	/**
	 * @Description: 将session刷新
	 */
	public void flushSession();
	
	/********************************************************************
	 * 							获取 持久态 对象集合						*
	 ********************************************************************/
	/**
	 * @Description: 获取对象集合
	 * @param clazz
	 * @return List<T>
	 */
	public <T> List<T> list(final Class<T> clazz);
	/**
	 * @Description: 根据 属性 获取对象集合
	 * @param clazz
	 * @param propertyName
	 * @param propertyValue
	 * @return <T>
	 */
	public <T> List<T> listByProperty(final Class<T> clazz, final String propertyName, final Object propertyValue);
	/**
	 * @Description: 根据 多属性 获取对象集合
	 * @param clazz
	 * @param propertyNames
	 * @param propertyValues
	 * @return <T>
	 */
	public <T> List<T> listByPropertys(final Class<T> clazz, final String[] propertyNames, final Object[] propertyValues);
	/**
	 * @Description: 获取对象集合数量
	 * @param clazz
	 * @return List<T>
	 */
	public <T> long count(final Class<T> clazz);
	/**
	 * @Description: 根据 属性 获取对象集合数量
	 * @param clazz
	 * @param propertyName
	 * @param propertyValue
	 * @return <T>
	 */
	public <T> long countByProperty(final Class<T> clazz, final String propertyName, final Object propertyValue);
	/**
	 * @Description: 根据 多属性 获取对象集合数量
	 * @param clazz
	 * @param propertyNames
	 * @param propertyValues
	 * @return <T>
	 */
	public <T> long countByPropertys(final Class<T> clazz, final String[] propertyNames, final Object[] propertyValues);
	
	/********************************************************************
	 * 							简单分页查询								*
	 ********************************************************************/
	/**
	 * @Description: 获取对象集合一部分
	 * @param clazz
	 * @param firstResult
	 * @param maxResults
	 * @return List<T>
	 */
	public <T> List<T> page(final Class<T> clazz, final int firstResult, final int maxResults);
	/**
	 * @Description: 根据 属性 获取对象集合一部分
	 * @param clazz
	 * @param propertyName
	 * @param propertyValue
	 * @param firstResult
	 * @param maxResults
	 * @return List<T>
	 */
	public <T> List<T> pageByProperty(final Class<T> clazz, final String propertyName, final Object propertyValue, final int firstResult, final int maxResults);
	/**
	 * @Description: 根据 多属性 获取对象集合一部分
	 * @param clazz
	 * @param propertyNames
	 * @param propertyValues
	 * @param firstResult
	 * @param maxResults
	 * @return List<T>
	 */
	public <T> List<T> pageByPropertys(final Class<T> clazz, final String[] propertyNames, final Object[] propertyValues, final int firstResult, final int maxResults);
	

	/********************************************************************
	 * 							高级条件过滤								*
	 ********************************************************************/
	/**
	 * 根据 多属性条件 获取 全部 对象集合
	 * @param clazz				对象类
	 * @param propertyNames		属性名
	 * @param propertyValues	属性值
	 * @param expressions		条件表达式
	 * @return
	 */
	public <T> List<T> listByPropertys(final Class<T> clazz, final List<String> propertyNames, final List<Object> propertyValues, final List<Expressions> expressions);
	/**
	 * 根据 多属性条件 获取 部分 对象集合（用于分页）
	 * @param clazz
	 * @param propertyNames		属性名
	 * @param propertyValues	属性值
	 * @param expressions		条件表达式
	 * @param firstResult		首条记录位置
	 * @param maxResults		记录总条数
	 * @return
	 */
	public <T> List<T> pageByPropertys(final Class<T> clazz, final List<String> propertyNames, final List<Object> propertyValues, final List<Expressions> expressions, final int firstResult, final int maxResults);
	/**
	 * 根据 多属性条件 获取 全部 对象 数量
	 * @param clazz				对象类
	 * @param propertyNames		属性名
	 * @param propertyValues	属性值
	 * @param expressions		条件表达式
	 * @return
	 */
	public <T> long countByPropertys(final Class<T> clazz, final List<String> propertyNames, final List<Object> propertyValues, final List<Expressions> expressions);
	
	
	/********************************************************************
	 * 							其他高级查询								*
	 ********************************************************************/
	/**
	 * 执行自定义增、删、改的sql语句
	 * @param sql	执行sql语句
	 * @return		变化记录数
	 */
	public int excuteBySql(final String sql, Object... objects);
	/**
	 * 执行自定义增、删、改的hql语句
	 * @param hql	执行hql语句
	 * @return		变化记录数
	 */
	public int excuteByHql(final String hql, Object... objects);
	/**
	 * @Description   自定义sql查询方法
	 * @param sql     sql语法
	 * @param params  参数
	 * @return
	 */
	public <T> List<T> listBySql(String sql, Object... params);
	/**
	 * @Description   自定义hql查询方法
	 * @param hql     hql语法
	 * @param params  参数
	 * @return
	 */
	public <T> List<T> listByHql(String hql, Object... params);
	
}
