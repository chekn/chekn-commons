package com.chekn.test;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;

import org.apache.commons.codec.digest.DigestUtils;
import org.apache.commons.io.FileUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.chekn.lang.U;

public class Test5 {

	private Logger logger = LoggerFactory.getLogger(this.getClass());
	
	public static void main(String[] args) throws Exception {
		
		FileInputStream fis = new FileInputStream(new File("c:/装箱单1231238.pdf"));
		InputStream rptis = U.cvt2CanRepeatReadStream(fis);
		System.out.println( DigestUtils.md5Hex(rptis) );
		rptis.reset();
		System.out.println( DigestUtils.md5Hex(rptis) );
		rptis.reset();
		FileUtils.copyInputStreamToFile(rptis, new File("c:/cs.pdf"));
	}
	
}
