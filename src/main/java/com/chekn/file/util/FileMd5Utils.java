package com.chekn.file.util;
import java.io.File;
import java.io.FileInputStream;

import org.apache.commons.codec.digest.DigestUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 文件校验工具
 * @author CHEKN
 *
 */
public class FileMd5Utils {

	private Logger logger = LoggerFactory.getLogger(this.getClass());
	
	/**
	 * 计算MD5 值
	 * @param file
	 * @return
	 * @throws Exception
	 */
	public static String computeMd5Hex(File file) throws Exception {
		FileInputStream is = new FileInputStream(file);
	    String fmd5= DigestUtils.md5Hex(is);
	    is.close();
	    return fmd5;
	}
	
	
	
}
