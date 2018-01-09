package zdatbit.common.utils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import com.jfinal.plugin.activerecord.Record;

public class ToolCollection {
	/**
	 * 将map转换为list
	 * @param map
	 * @return
	 */
	@SuppressWarnings("rawtypes")
	public static List<Record> convertMapToList(HashMap map){
		List<Record> records=new ArrayList<Record>();
		if(map==null){
			return null;
		}
		for (Object object : map.keySet()) {
			Record record=new Record();
			record.set("value", object);
			record.set("text", map.get(object));
			records.add(record);
		}
		return records;
	}
	/**
	 * 生成数字序列
	 * @param min
	 * @param max
	 * @return
	 */
	public static List<Integer> createList(Integer min,Integer max){
		List<Integer> list=new ArrayList<Integer>();
		for (int i = min; i <=max; i++) {
			list.add(i);
		}
		return list;
	}
}
