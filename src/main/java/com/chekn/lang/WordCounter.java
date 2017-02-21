package com.chekn.lang;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import org.apache.commons.lang.time.DateUtils;

public class WordCounter {
	
	/**
	 * 统计元素，倒序排列
	 * @param clts
	 * @return
	 */
	public static <T> Map<T,Integer> statisItemSortCountDesc(List<T> clts) {
		Map<T, Integer> map = new HashMap<T, Integer>();// 用于统计各个单词的个数，排序
		for(T clt: clts) {
			if (map.containsKey(clt)) { // HashMap不允许重复的key，所以利用这个特性，去统计单词的个数
				int count = map.get(clt);
				map.put(clt, count + 1); // 如果HashMap已有这个单词，则设置它的数量加1
			} else
				map.put(clt, 1); // 如果没有这个单词，则新填入，数量为1
		}
		return sort(map); // 调用排序的方法，排序并输出！
	}

	public static <T> Map<T,Integer> sort(Map<T, Integer> map) {
		List<Map.Entry<T, Integer>> infoIds = new ArrayList<Map.Entry<T, Integer>>(map.entrySet());
		Collections.sort(infoIds, new Comparator<Map.Entry<T, Integer>>() {
			public int compare(Map.Entry<T, Integer> o1, Map.Entry<T, Integer> o2) {
				return (o2.getValue() - o1.getValue());
			}
		}); // 排序
		
		Map<T,Integer> revMap = new LinkedHashMap<T, Integer>();
		for (int i = 0; i < infoIds.size(); i++) { 
			Entry<T, Integer> id = infoIds.get(i);
			revMap.put(id.getKey(), id.getValue());
		}
		return revMap;
	}
	
	/**
	 * 使用示例
	 * @param args
	 * @throws ParseException
	 */
	@SuppressWarnings("rawtypes")
	public static void main(String[] args) throws ParseException {
		
		
		for(Entry unit: statisItemSortCountDesc(Arrays.asList("123","12","12313","12323","123", "12", "12")).entrySet()) {
			System.out.println(unit);
		}
		
		System.out.println("\n\n");
		for(Entry unit: statisItemSortCountDesc(Arrays.asList(1,1,12,12,12,123,123,123,123,123)).entrySet()) {
			System.out.println(unit);
		}
		
		System.out.println("\n\n");
		Date date1=DateUtils.parseDate("2017-02-21", new String[]{"yyyy-MM-dd"});
		Date date2=DateUtils.parseDate("2017-02-21", new String[]{"yyyy-MM-dd"});
		Date date3=DateUtils.parseDate("2017-02-21", new String[]{"yyyy-MM-dd"});
		Date date4=DateUtils.parseDate("2017-02-20", new String[]{"yyyy-MM-dd"});
		for(Entry unit: statisItemSortCountDesc(Arrays.asList(date1, date2, date3, date4)).entrySet()) {
			System.out.println(unit);
		}
		
		
	}
	
}
