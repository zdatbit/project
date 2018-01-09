package zdatbit.user.base;

import java.io.Serializable;

/**
 * between条件
 * @author Zhoudc
 *
 */
public class BetweenObject implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 73229456053943539L;
	/**
	 * 开始值
	 */
	private Object betweenStartObject;
	/**
	 * 结束值
	 */
	private Object betweenEndObject;
	public BetweenObject(){
		
	}
	public BetweenObject(Object betweenStartObject,Object betweenEndObject){
		this.betweenEndObject=betweenEndObject;
		this.betweenStartObject=betweenStartObject;
	}
	public Object getBetweenStartObject() {
		return betweenStartObject;
	}
	public void setBetweenStartObject(Object betweenStartObject) {
		this.betweenStartObject = betweenStartObject;
	}
	public Object getBetweenEndObject() {
		return betweenEndObject;
	}
	public void setBetweenEndObject(Object betweenEndObject) {
		this.betweenEndObject = betweenEndObject;
	}
	
}
