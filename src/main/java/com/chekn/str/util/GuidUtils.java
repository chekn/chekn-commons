package com.chekn.str.util;

import java.util.UUID;

/**
 * 
 * @author CodeFlagAI
 * @date 2016年12月12日-下午4:42:12
 */
public class GuidUtils {
	
	public static String getGuid() {
		return UUID.randomUUID().toString().replace("-", "");
	}

}
