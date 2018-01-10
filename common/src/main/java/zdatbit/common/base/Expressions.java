package zdatbit.common.base;
/**
 * 算式
 * @author Administrator
 *
 */
public enum Expressions {
	/**
	 * 等于
	 */
	Equal,
	/**
	 * 大于等于
	 */
	Gte,
	/**
	 * 大于
	 */
	Gt,
	/**
	 * 小于等于
	 */
	Lte,
	/**
	 * 小于
	 */
	Lt,
	/**
	 * 模糊查询like
	 */
	Like,
	/**
	 * 左侧匹配
	 */
	LikeLeft,
	/**
	 * 右侧匹配
	 */
	LikeRight,
	/**
	 * 区间Between
	 */
	Between,
	/**
	 * 传入匹配值的list或数组
	 */
	In,
	/**
	 * 不等于
	 */
	NotEqual,
	/**
	 * 该字段为空
	 */
	IsNull,
	/**
	 * 该字段不为空
	 */
	NotNull,
	/**
	 * 排序类型
	 * 输入条件时，字段名为需要排序的字段，值为“asc”或“desc”
	 */
	Order,
	/**
	 * 操作语句自己定义，如条件：='a'或>1
	 * 适用于子查询
	 */
	Other,
	/**
	 * 纯执行sql条件
	 */
	Hql,
	/**
	 * 纯hql条件，参数化hql里面的一个参数，
	 * 如propertyNames添加了参数，需要在propertyValues添加，其他为null
	 */
	HqlParam
}
