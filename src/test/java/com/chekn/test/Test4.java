package com.chekn.test;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.chekn.lang.CommonComparator;

public class Test4 {

	private Logger logger = LoggerFactory.getLogger(this.getClass());
	
	public static void main(String[] args) {
		
		System.out.println("阻塞的".matches("\\w*"));
		
	    CommonComparator<String, String> cctor = new CommonComparator<String, String>();
	    cctor.addComparVal("1", "b中国");
	    cctor.addComparVal("4", "a美讯");
	    cctor.addComparVal("9", "e行驶");
	    cctor.addComparVal("8", "c多少");
	    cctor.addComparVal("6", "f都是");
	    cctor.addComparVal("5", "e味全");

	    List<String> comparRes = cctor.sort(true);
	    System.out.println( comparRes );
		
	}
	
}
