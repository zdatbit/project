/** 
 * @author  zhangdi 	
 * @date 创建时间：2017年8月7日 下午7:38:48  	
 */

package zdatbit.user.dao;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.hibernate.query.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import zdatbit.common.base.BetweenObject;
import zdatbit.common.base.Expressions;


@Transactional
@Repository
public class TemplateDao implements ITemplateDao {
	
	@Autowired
	private SessionFactory sessionFactory;
	private Logger log = LogManager.getLogger(this.getClass().getName());
	/**
	 * 获取单个信息
	 */
	public <T> Object get(Class<T> clazz, Serializable id) {
		if(id==null){
			return null;
		}
		try{
			return this.getSession().get(clazz, id);
		}catch(Exception e){
			log.error("Dao get failed",e);
		}
		return null;
	}

	/**
	 * 获取托管对象
	 */
	public <T> Object obtain(Class<T> clazz, Serializable id) {
		Object instance = null;
		try{
			instance = this.getSession().get(clazz, id);
			this.getSession().evict(instance);
			return instance;
		}catch(Exception e){
			log.error("Dao get failed",e);
		}
		return null;
	}

	/**
	 * 保存方法
	 */
	public boolean save(Object instance) {
		try{
			this.getSession().save(instance);
		}catch(Exception e){
			log.error("Dao save failed", e);
			return false;
		}
		return true;
	}
	/**
	 * 更新
	 * @param instance
	 * @return
	 */
	
	public boolean update(Object instance){
		try{
			this.getSession().saveOrUpdate(instance);
		}catch(Exception e){
			log.error("Dao update failed", e);
			return false;
		}
		return true;
	}
	/**
	 * 更新
	 * @param instance
	 * @return
	 */
	public boolean merge(Object instance){
		try{
			this.getSession().merge(instance);
		}catch(Exception e){
			log.error("Dao update failed", e);
			return false;
		}
		return true;
	}
	/**
	 * 删除方法
	 */
	public boolean delete(Object instance) {
		try{
			this.getSession().delete(instance);
		}catch(Exception e){
			log.error("Dao delete failed", e);
			return false;
		}
		return true;
	}
	/**
	 * 托管
	 */
	public boolean evict(Object instance) {
		try{
			this.getSession().evict(instance);
		}catch(Exception e){
			log.error("Dao evict failed", e);
			return false;
		}
		return true;
	}
	/**
	 * 批量保存
	 */
	public <T> boolean saveBatch(List<T> instances) {
		try{
			for(T t:instances){
				if(!save(t)){
					return false;
				}
			}
		}catch(Exception e){
			log.error("Dao saveBatch failed",e);
			return false;
		}
		return true;
	}
	/**
	 * 批量保存
	 */
	public <T> boolean evictBatch(List<T> instances) {
		try{
			for(T t:instances){
				if(!evict(t)){
					return false;
				}
			}
		}catch(Exception e){
			log.error("Dao evictBatch failed",e);
			return false;
		}
		return true;
	}
	/**
	 * 批量删除
	 */
	public <T> boolean deleteBatch(List<T> instances) {
		try{
			for(T t:instances){
				if(!delete(t)){
					return false;
				}
			}
		}catch(Exception e){
			log.error("Dao saveBatch failed",e);
			return false;
		}
		return true;
	}

	/**
	 * @Description: 将session刷新
	 */
	public void flushSession() {
		try{
			this.getSession().flush();
		}catch(Exception e){
			log.error("Dao flush failed",e);
		}
	}
	/**
	 * @Description: 获取对象集合
	 * @param clazz
	 * @return List<T>
	 */
	@SuppressWarnings("unchecked")
	public <T> List<T> list(Class<T> clazz) {
		try {
			String queryString = "from " + clazz.getName();
			return (List<T>) this.getSession().createQuery(queryString).list();
		} catch (Exception dae) {
			log.error("Dao list failed", dae);
		}
		return null;
	}
	/**
	 * @Description: 根据 属性 获取对象集合
	 * @param clazz
	 * @param propertyName
	 * @param propertyValue
	 * @return <T>
	 */
	@SuppressWarnings("unchecked")
	public <T> List<T> listByProperty(Class<T> clazz, String propertyName,
			Object propertyValue) {
		try {
			String queryString = "from " + clazz.getName()+" ";
			if(StringUtils.isNotBlank(propertyName)){
				queryString+="where "+propertyName+"=:"+getHqlAttrName(propertyName);
			}
			Query query =  this.getSession().createQuery(queryString);
			query.setParameter(getHqlAttrName(propertyName), propertyValue);
			return query.list();
		} catch (Exception dae) {
			log.error("Dao list failed", dae);
		}
		return null;
	}
	/**
	 * @Description: 根据 多属性 获取对象集合
	 * @param clazz
	 * @param propertyNames
	 * @param propertyValues
	 * @return <T>
	 */
	@SuppressWarnings("unchecked")
	public <T> List<T> listByPropertys(Class<T> clazz, String[] propertyNames,
			Object[] propertyValues) {
		try {
			String queryString = "from " + clazz.getName();
			String params = "";
			for(int i=0;i<propertyNames.length;i++){
				params += propertyNames[i] + "=:"+ propertyNames[i] +" and ";
			}
			if(params.length()>0){
				queryString = queryString +" where " + params.substring(0, params.length()-3);
			}
			Query query = this.getSession().createQuery(queryString);
			for(int i=0; i<propertyNames.length;i++){
				query.setParameter(propertyNames[i], propertyValues[i]);
			}
			return (List<T>)query.list();
		} catch (Exception dae) {
			log.error("Dao list failed", dae);
		}
		return null;
	}
	/**
	 * @Description: 获取对象集合数量
	 * @param clazz
	 * @return List<T>
	 */
	public <T> long count(Class<T> clazz) {
		long count = 0;
		try {
			String hql = "select count(*) from " + clazz.getName();
			Query query = this.getSession().createQuery(hql);
			return (Long)query.uniqueResult();
		} catch (Exception dae) {
			log.error("Dao countByProperty failed", dae);
		}
		return count;
	}
	/**
	 * @Description: 根据 属性 获取对象集合数量
	 * @param clazz
	 * @param propertyName
	 * @param propertyValue
	 * @return <T>
	 */
	public <T> long countByProperty(Class<T> clazz, String propertyName,
			Object propertyValue) {
		long count = 0;
		try {
			return countByPropertys(clazz, new String[]{propertyName}, new Object[]{propertyValue});
		} catch (Exception dae) {
			log.error("Dao countByProperty failed", dae);
		}
		return count;
	}
	/**
	 * @Description: 根据 多属性 获取对象集合数量
	 * @param clazz
	 * @param propertyNames
	 * @param propertyValues
	 * @return <T>
	 */
	public <T> long countByPropertys(Class<T> clazz, String[] propertyNames,
			Object[] propertyValues) {
		long count = 0;
		try {
			String hql = "select count(*) from " + clazz.getName()+" where 1=1 ";
			for(int i=0;i<propertyNames.length;i++){
				hql += " and " +propertyNames[i] + "=:"+ propertyNames[i] ;
			}
        	Query query = this.getSession().createQuery(hql);
        	for(int i=0;i<propertyNames.length;i++){
        		query.setParameter(propertyNames[i], propertyValues[i]);
			}
			return (Long)query.uniqueResult();
		} catch (Exception dae) {
			log.error("Dao countByPropertys failed", dae);
		}
		return count;
	}
	/**
	 * @Description: 获取对象集合一部分
	 * @param clazz
	 * @param firstResult
	 * @param maxResults
	 * @return List<T>
	 */
	@SuppressWarnings("unchecked")
	public <T> List<T> page(Class<T> clazz, int firstResult, int maxResults) {
		List<T> list = new ArrayList<T>();
		try {
        	String hql = "from " + clazz.getName();
        	Query query = this.getSession().createQuery(hql);
        	if(firstResult>0)
				query.setFirstResult(firstResult);
			if(maxResults>0)
				query.setMaxResults(maxResults);
			return (List<T>)query.list();
		} catch (Exception e) {
			log.error("Dao page failed", e);
		}
		return list;
	}
	/**
	 * @Description: 根据 属性 获取对象集合一部分
	 * @param clazz
	 * @param propertyName
	 * @param propertyValue
	 * @param firstResult
	 * @param maxResults
	 * @return List<T>
	 */
	public <T> List<T> pageByProperty(Class<T> clazz, String propertyName,
			Object propertyValue, int firstResult, int maxResults) {
		List<T> list = new ArrayList<T>();
		try {
			return pageByPropertys(clazz, new String[]{propertyName}, new Object[]{propertyValue}, firstResult, maxResults);
		} catch (Exception e) {
			log.error("Dao page failed", e);
		}
		return list;
	}
	/**
	 * @Description: 根据 多属性 获取对象集合一部分
	 * @param clazz
	 * @param propertyNames
	 * @param propertyValues
	 * @param firstResult
	 * @param maxResults
	 * @return List<T>
	 */
	@SuppressWarnings("unchecked")
	public <T> List<T> pageByPropertys(Class<T> clazz, String[] propertyNames,
			Object[] propertyValues, int firstResult, int maxResults) {
		List<T> list = new ArrayList<T>();
		try {
        	String hql = "from " + clazz.getName();
        	String params = "";
			for(int i=0;i<propertyNames.length;i++){
				params += propertyNames[i] + "=:"+ propertyNames[i] +" and ";
			}
			if(params.length()>0){
				hql = hql +" where " + params.substring(0, params.length()-3);
			}
        	Query query = this.getSession().createQuery(hql);
        	for(int i=0;i<propertyValues.length;i++){
        		query.setParameter(propertyNames[i], propertyValues[i]);
        	}
        	if(firstResult>0)
				query.setFirstResult(firstResult);
			if(maxResults>0)
				query.setMaxResults(maxResults);
			return (List<T>)query.list();
		} catch (Exception e) {
			log.error("Dao page failed", e);
		}
		return list;
	}
	/**
	 * 根据 多属性条件 获取 全部 对象集合
	 * @param clazz				对象类
	 * @param propertyNames		属性名
	 * @param propertyValues	属性值
	 * @param expressions		条件表达式
	 * @return
	 */
	@SuppressWarnings({"unchecked","rawtypes"})
	public <T> List<T> listByPropertys(Class<T> clazz,
			List<String> propertyNames, List<Object> propertyValues,
			List<Expressions> expressions) {
		List<T> list = new ArrayList<T>();
		try {
        	String hql = "from " + clazz.getName() +" as model where 1=1 ";
        	String orderHql = "";
    		for (int i = 0; i < propertyNames.size(); i++){
    			switch (expressions.get(i)) {
				case Equal:
					hql += " and model." + propertyNames.get(i) + "= :" + getHqlAttrName(propertyNames.get(i));
					break;
				case Lte:
					hql += " and model." + propertyNames.get(i) + "<= :" + getHqlAttrName(propertyNames.get(i));
					break;
				case Lt:
					hql += " and model." + propertyNames.get(i) + "< :" + getHqlAttrName(propertyNames.get(i));
					break;
				case Gte:
					hql += " and model." + propertyNames.get(i) + ">= :" + getHqlAttrName(propertyNames.get(i));
					break;
				case Gt:
					hql += " and model." + propertyNames.get(i) + "> :" + getHqlAttrName(propertyNames.get(i));
					break;
				case Like:
				case LikeLeft:
				case LikeRight:
					hql += " and model." + propertyNames.get(i) + " like :" + getHqlAttrName(propertyNames.get(i));
					break;
				case Between:
					if (propertyValues.get(i) instanceof BetweenObject) {
						hql += " and model." + propertyNames.get(i) + " between :" + getHqlAttrName(propertyNames.get(i))+"Start and :"+getHqlAttrName(propertyNames.get(i))+"End";
					}
					break;
				case NotEqual:
					hql += " and model." + propertyNames.get(i) + "<> :" + getHqlAttrName(propertyNames.get(i));
					break;
				case IsNull:
					hql += " and model." + propertyNames.get(i) + " is null";
					break;
				case NotNull:
					hql += " and model." + propertyNames.get(i) + " is not null";
					break;
				case In:
					hql += " and model." + propertyNames.get(i) + " in( :" + getHqlAttrName(propertyNames.get(i))+")";
					break;
				case Order:
					orderHql+=", model."+propertyNames.get(i) + " " + propertyValues.get(i).toString();
					break;
				case Other:
					hql += " and model." + propertyNames.get(i) + " "+propertyValues.get(i).toString();
					break;
				case Hql:
				case HqlParam:
					hql+=" and "+propertyNames.get(i);
					break;
				default:
					hql += " and model." + propertyNames.get(i) + "= :" + getHqlAttrName(propertyNames.get(i));
					break;
				}    			
    		}
    		if (orderHql.length() != 0) {
    			orderHql=" order by "+orderHql.substring(1);
    		}
			Query query = this.getSession().createQuery(hql + orderHql);
			for (int i = 0; i < propertyNames.size(); i++) {
				switch (expressions.get(i)) {
				case Like:
					query.setParameter(getHqlAttrName(propertyNames.get(i)), "%"+propertyValues.get(i)+"%");
					break;
				case LikeLeft:
					query.setParameter(getHqlAttrName(propertyNames.get(i)), propertyValues.get(i)+"%");
					break;
				case LikeRight:
					query.setParameter(getHqlAttrName(propertyNames.get(i)), "%"+propertyValues.get(i));
					break;
				case Between:
					Object obj=propertyValues.get(i);
					if (obj instanceof BetweenObject) {
						BetweenObject betweenObject=(BetweenObject)obj;
						query.setParameter(getHqlAttrName(propertyNames.get(i))+"Start", betweenObject.getBetweenStartObject());
						query.setParameter(getHqlAttrName(propertyNames.get(i))+"End", betweenObject.getBetweenEndObject());
					}
					
					break;
				case In:
					query.setParameterList(getHqlAttrName(propertyNames.get(i)), (Collection)propertyValues.get(i));
					break;
				case Order:
				case IsNull:
				case NotNull:
				case Other:
				case Hql:
					break;
				case HqlParam:
					Pattern pattern=Pattern.compile(":(\\w+)");
					Matcher matcher=pattern.matcher(propertyNames.get(i));
					if (matcher.find()) {
						if (propertyValues.get(i).getClass().getSimpleName().equals("ArrayList")
								||propertyValues.get(i).getClass().getSimpleName().equals("String[]")
								||propertyValues.get(i).getClass().getSimpleName().equals("Long[]")
								||propertyValues.get(i).getClass().getSimpleName().equals("Integer[]")
								||propertyValues.get(i).getClass().getSimpleName().equals("Double[]")
								||propertyValues.get(i).getClass().getSimpleName().equals("Float[]")) {
							query.setParameterList(matcher.group(1), (Collection)propertyValues.get(i));
						}else {
							query.setParameter(matcher.group(1), propertyValues.get(i));
						}
					}
					break;
				case Equal:
				case NotEqual:
				case Lte:
				case Lt:
				case Gte:
				case Gt:
				
				default:
					query.setParameter(getHqlAttrName(propertyNames.get(i)), propertyValues.get(i));
					break;
				}
			}
			return query.list();
		} catch (Exception e) {
			log.error("Dao listByPropertys failed", e);
		}
		return list;
	}
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
	@SuppressWarnings({"unchecked","rawtypes"})
	public <T> List<T> pageByPropertys(Class<T> clazz,
			List<String> propertyNames, List<Object> propertyValues,
			List<Expressions> expressions, int firstResult, int maxResults) {
		List<T> list = new ArrayList<T>();
		try {
        	String hql = "from " + clazz.getName() +" as model where 1=1 ";
        	String orderHql = "";
    		for (int i = 0; i < propertyNames.size(); i++){
    			switch (expressions.get(i)) {
				case Equal:
					hql += " and model." + propertyNames.get(i) + " = :" + getHqlAttrName(propertyNames.get(i));
					break;
				case Lte:
					hql += " and model." + propertyNames.get(i) + " <= :" + getHqlAttrName(propertyNames.get(i));
					break;
				case Lt:
					hql += " and model." + propertyNames.get(i) + " < :" + getHqlAttrName(propertyNames.get(i));
					break;
				case Gte:
					hql += " and model." + propertyNames.get(i) + " >= :" + getHqlAttrName(propertyNames.get(i));
					break;
				case Gt:
					hql += " and model." + propertyNames.get(i) + " > :" + getHqlAttrName(propertyNames.get(i));
					break;
				case Like:
				case LikeLeft:
				case LikeRight:
					hql += " and model." + propertyNames.get(i) + " like :" + getHqlAttrName(propertyNames.get(i));
					break;
				case Between:
					if (propertyValues.get(i) instanceof BetweenObject) {
						hql += " and model." + propertyNames.get(i) + " between :" + getHqlAttrName(propertyNames.get(i))+"Start and :"+getHqlAttrName(propertyNames.get(i))+"End";
					}
					break;
				case NotEqual:
					hql += " and model." + propertyNames.get(i) + "<> :" + getHqlAttrName(propertyNames.get(i));
					break;
				case IsNull:
					hql += " and model." + propertyNames.get(i) + " is null";
					break;
				case NotNull:
					hql += " and model." + propertyNames.get(i) + " is not null";
					break;
				case In:
					hql += " and model." + propertyNames.get(i) + " in( :" + getHqlAttrName(propertyNames.get(i))+")";
					break;
				case Order:
					orderHql += ", model."+propertyNames.get(i) +" "+propertyValues.get(i).toString();
					break;
				case Other:
					hql += " and model." + propertyNames.get(i) + " "+propertyValues.get(i).toString();
					break;
				case Hql:
				case HqlParam:
					hql+=" and "+propertyNames.get(i);
					break;
				default:
					hql += " and model." + propertyNames.get(i) + " = :" + getHqlAttrName(propertyNames.get(i));
					break;
				}
    			
    		}
    		if(orderHql.length() != 0) {
    			orderHql=" order by "+orderHql.substring(1);
    		}
			Query query = this.getSession().createQuery(hql + orderHql);
			for (int i = 0; i < propertyNames.size(); i++) {
				switch (expressions.get(i)) {
				case Like:
					query.setParameter(getHqlAttrName(propertyNames.get(i)), "%"+propertyValues.get(i)+"%");
					break;
				case LikeLeft:
					query.setParameter(getHqlAttrName(propertyNames.get(i)), propertyValues.get(i)+"%");
					break;
				case LikeRight:
					query.setParameter(getHqlAttrName(propertyNames.get(i)), "%"+propertyValues.get(i));
					break;
				case Between:
					Object obj=propertyValues.get(i);
					if (obj instanceof BetweenObject) {
						BetweenObject betweenObject=(BetweenObject)obj;
						query.setParameter(getHqlAttrName(propertyNames.get(i))+"Start", betweenObject.getBetweenStartObject());
						query.setParameter(getHqlAttrName(propertyNames.get(i))+"End", betweenObject.getBetweenEndObject());
					}
					break;
				case In:
					query.setParameterList(getHqlAttrName(propertyNames.get(i)), (Collection)propertyValues.get(i));
					break;
				case Order:
				case IsNull:
				case NotNull:
				case Other:
				case Hql:
					break;
				case HqlParam:
					Pattern pattern=Pattern.compile(":(\\w+)");
					Matcher matcher=pattern.matcher(propertyNames.get(i));
					if (matcher.find()) {
						if (propertyValues.get(i).getClass().getSimpleName().equals("ArrayList")
								||propertyValues.get(i).getClass().getSimpleName().equals("String[]")
								||propertyValues.get(i).getClass().getSimpleName().equals("Long[]")
								||propertyValues.get(i).getClass().getSimpleName().equals("Integer[]")
								||propertyValues.get(i).getClass().getSimpleName().equals("Double[]")
								||propertyValues.get(i).getClass().getSimpleName().equals("Float[]")) {
							query.setParameterList(matcher.group(1), (Collection)propertyValues.get(i));
						}else {
							query.setParameter(matcher.group(1), propertyValues.get(i));
						}
					}
					break;
				case Equal:
				case NotEqual:
				case Lte:
				case Lt:
				case Gte:
				case Gt:
				default:
					query.setParameter(getHqlAttrName(propertyNames.get(i)), propertyValues.get(i));
					break;
				}
			}
			if(firstResult>0)
				query.setFirstResult(firstResult);
			if(maxResults>0)
				query.setMaxResults(maxResults);
			
			return (List<T>)query.list();
		} catch (Exception e) {
			log.error("Dao pageByPropertys failed", e);
		}
		return list;
	}
	/**
	 * 根据 多属性条件 获取 全部 对象 数量
	 * @param clazz				对象类
	 * @param propertyNames		属性名
	 * @param propertyValues	属性值
	 * @param expressions		条件表达式
	 * @return
	 */
	@SuppressWarnings({"rawtypes"})
	public <T> long countByPropertys(Class<T> clazz,
			List<String> propertyNames, List<Object> propertyValues,
			List<Expressions> expressions) {
		long count = 0;
		try {
        	String hql = "select count(*) from " + clazz.getSimpleName() +" as model where 1=1 ";
        	String orderHql = "";
    		for (int i = 0; i < propertyNames.size(); i++) {
    			switch (expressions.get(i)) {
				case Equal:
					hql += " and model." + propertyNames.get(i) + "= :" + getHqlAttrName(propertyNames.get(i));
					break;
				case Lte:
					hql += " and model." + propertyNames.get(i) + "<= :" + getHqlAttrName(propertyNames.get(i));
					break;
				case Lt:
					hql += " and model." + propertyNames.get(i) + "< :" + getHqlAttrName(propertyNames.get(i));
					break;
				case Gte:
					hql += " and model." + propertyNames.get(i) + ">= :" + getHqlAttrName(propertyNames.get(i));
					break;
				case Gt:
					hql += " and model." + propertyNames.get(i) + "> :" + getHqlAttrName(propertyNames.get(i));
					break;
				case Like:
				case LikeLeft:
				case LikeRight:
					hql += " and model." + propertyNames.get(i) + " like :" + getHqlAttrName(propertyNames.get(i));
					break;
				case Between:
					if (propertyValues.get(i) instanceof BetweenObject) {
						hql += " and model." + propertyNames.get(i) + " between :" + getHqlAttrName(propertyNames.get(i))+"Start and :"+getHqlAttrName(propertyNames.get(i))+"End";
					}
					break;
				case NotEqual:
					hql += " and model." + propertyNames.get(i) + "<> :" + getHqlAttrName(propertyNames.get(i));
					break;
				case IsNull:
					hql += " and model." + propertyNames.get(i) + " is null";
					break;
				case NotNull:
					hql += " and model." + propertyNames.get(i) + " is not null";
					break;
				case In:
					hql += " and model." + propertyNames.get(i) + " in( :" + getHqlAttrName(propertyNames.get(i))+")";
					break;
				case Order:
					orderHql += ", model."+propertyNames.get(i) + " " + propertyValues.get(i).toString();
					break;
				case Other:
					hql += " and model." + propertyNames.get(i) + " "+propertyValues.get(i).toString();
					break;
				case Hql:
				case HqlParam:
					hql+=" and "+propertyNames.get(i);
					break;
				default:
					hql += " and model." + propertyNames.get(i) + "= :" + getHqlAttrName(propertyNames.get(i));
					break;
				}
    			
    		}
    		if(orderHql.length() != 0){
    			orderHql = " order by " + orderHql.substring(1);
    		}
			Query query = this.getSession().createQuery(hql);
			for (int i = 0; i < propertyNames.size(); i++) {
				switch (expressions.get(i)) {
				case Like:
					query.setParameter(getHqlAttrName(propertyNames.get(i)), "%"+propertyValues.get(i)+"%");
					break;
				case LikeLeft:
					query.setParameter(getHqlAttrName(propertyNames.get(i)), propertyValues.get(i)+"%");
					break;
				case LikeRight:
					query.setParameter(getHqlAttrName(propertyNames.get(i)), "%"+propertyValues.get(i));
					break;
				case Between:
					Object obj=propertyValues.get(i);
					if (obj instanceof BetweenObject) {
						BetweenObject betweenObject=(BetweenObject)obj;
						query.setParameter(getHqlAttrName(propertyNames.get(i))+"Start", betweenObject.getBetweenStartObject());
						query.setParameter(getHqlAttrName(propertyNames.get(i))+"End", betweenObject.getBetweenEndObject());
					}
					break;
				case In:
					query.setParameterList(getHqlAttrName(propertyNames.get(i)), (Collection)propertyValues.get(i));
					break;
				case Order:
				case IsNull:
				case NotNull:
				case Other:
				case Hql:
					break;
				case HqlParam:
					Pattern pattern=Pattern.compile(":(\\w+)");
					Matcher matcher=pattern.matcher(propertyNames.get(i));
					if (matcher.find()) {
						if (propertyValues.get(i).getClass().getSimpleName().equals("ArrayList")
								||propertyValues.get(i).getClass().getSimpleName().equals("String[]")
								||propertyValues.get(i).getClass().getSimpleName().equals("Long[]")
								||propertyValues.get(i).getClass().getSimpleName().equals("Integer[]")
								||propertyValues.get(i).getClass().getSimpleName().equals("Double[]")
								||propertyValues.get(i).getClass().getSimpleName().equals("Float[]")) {
							query.setParameterList(matcher.group(1), (Collection)propertyValues.get(i));
						}else {
							query.setParameter(matcher.group(1), propertyValues.get(i));
						}
					}
					break;
				case Equal:
				case NotEqual:
				case Lte:
				case Lt:
				case Gte:
				case Gt:
				default:
					query.setParameter(getHqlAttrName(propertyNames.get(i)), propertyValues.get(i));
					break;
				}
			}
			return (Long)query.uniqueResult();
		} catch (Exception e) {
			log.error("Dao countByPropertys failed", e);
		}
		return count;
	}
	/********************************************************************
	 * 							自定义方法								*
	 ********************************************************************/
	/**
	 * 执行自定义增、删、改的sql语句
	 * @param sql	执行sql语句
	 * @return		变化记录数
	 */
	public int excuteBySql(String sql,Object... params) {
		int result = 0;
		try {
        	Query query = this.getSession().createSQLQuery(sql);
			return query.executeUpdate();
		} catch (Exception e) {
			log.error("Dao excuteBySql failed", e);
		}
		return result;
	}
	
	/**
	 * 执行自定义增、删、改的hql语句
	 * @param hql	执行hql语句
	 * @return		变化记录数
	 */
	public int excuteByHql(String hql,Object... params) {
		int result = 0;
		try {
        	Query query = this.getSession().createQuery(hql);
        	for(int i=0; i<params.length;i++){
        		query.setParameter(i, params[i]);
    		}
			return query.executeUpdate();
		} catch (Exception e) {
			log.error("Dao excuteBySql failed", e);
		}
		return result;
	}
	/**
	 * @Description  自定义sql查询方法
	 * @param sql    sql语法
	 * @param params 参数
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public <T> List<T> listBySql(String sql,Object... params){
		try {
        	Query query = this.getSession().createSQLQuery(sql);
        	for(int i=0; i<params.length;i++){
        		query.setParameter(i, params[i]);
    		}
			return (List<T>)query.list();
		} catch (Exception e) {
			log.error("Dao excuteBySql failed", e);
		}
		return null;
	}
	/**
	 * @Description   自定义hql查询方法
	 * @param hql     hql语法，带？
	 * @param params  参数
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public <T> List<T> listByHql(String hql,Object... params){
		try {
        	Query query = this.getSession().createQuery(hql);
        	for(int i=0; i<params.length;i++){
        		query.setParameter(i, params[i]);
    		}
			return (List<T>)query.list();
		} catch (Exception e) {
			log.error("Dao excuteBySql failed", e);
		}
		return null;
	}
	/**
	 * @Description   自定义hql查询方法
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public <T> List<T> listByHqlLimit(Class<T> clazz, String where,Integer firstResult, Integer maxResults){
		try {
			String hql = "from "+clazz.getSimpleName()+" "+where;
        	Query query = this.getSession().createQuery(hql);
        	if(firstResult>0)
				query.setFirstResult(firstResult);
			if(maxResults>0)
				query.setMaxResults(maxResults);
			return (List<T>)query.list();
		} catch (Exception e) {
			log.error("Dao excuteBySql failed", e);
		}
		return null;
	}
	/**
	 * 根据条件获取所有记录数
	 * @param clazz
	 * @param where
	 * @return
	 */
	public <T> Long countByhql(Class<T> clazz,String where){
		try{
			String hql = "select count(*) from "+clazz.getSimpleName() +" "+where;
			Query query = this.getSession().createQuery(hql);
			return (Long)query.uniqueResult();
		} catch (Exception e) {
			log.error("Dao excuteBySql failed", e);
		}
		return null;
	}
	/**
	 * @Description   自定义hql查询方法
	 * @param hql     hql语法，带？
	 * @param params  参数
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public <T> T listFirstByHql(String hql,Object... params){
		try {
        	Query query = this.getSession().createQuery(hql);
        	for(int i=0; i<params.length;i++){
        		query.setParameter(i, params[i]);
    		}
			return (T) query.uniqueResult();
		} catch (Exception e) {
			log.error("Dao excuteBySql failed", e);
		}
		return null;
	}
	/********************************************************************
	 * 							内部私有方法								*
	 ********************************************************************/
	private String getHqlAttrName(String name){
		return name.replace(".", "_");
	}
	/**
	 * hql变量名转换
	 * @param table 表名
	 * @param name 
	 * @return
	 */
	@SuppressWarnings("unused")
	private String getHqlAttrName(String table,String name){
		return table+"_"+name.replace(".", "_");
	}	
	/********************************************************************
	 * 							get set方法								*
	 ********************************************************************/
	public SessionFactory getSessionFactory() {
		return sessionFactory;
	}

	public void setSessionFactory(SessionFactory sessionFactory) {
		this.sessionFactory = sessionFactory;
	}
	public Session getSession(){
		return sessionFactory.getCurrentSession();
	}
}
