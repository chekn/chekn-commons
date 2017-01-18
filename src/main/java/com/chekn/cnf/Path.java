package com.chekn.cnf;

import java.io.File;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class Path {
	
	private static Logger logger= LoggerFactory.getLogger(Path.class);
	private static Os sysOs=Os.gtBySeparator(File.separator);
	
	private static Os pathOs;
	private static String middlePath;
	
	public static String toLocalPath(String prostr) {
		logger.error("x [cnf2com] current sys is "+ sysOs.name());
		
		//判断给的格式
		pathOs=Os.gtBySeparator(prostr.contains("/") ? "/":"\\" );
		middlePath= StringUtils.removeStart(prostr, pathOs.getRootDir());
		if (pathOs != sysOs) {
			middlePath=middlePath.replace(pathOs.getSepartor(), sysOs.getSepartor());
		} 
		
		return sysOs.getRootDir()+middlePath;
	}
	
	public static void main (String[] args) {
		System.out.println(Path.toLocalPath("/cksl/clsdks"));;
	}
	
	enum Os {
		
		WINDOWS("c:\\","\\"),LINUX("/","/");
		
		String rootDir;
		String separtor;
		
		private Os(String rootDir, String separtor) {
			this.rootDir = rootDir;
			this.separtor = separtor;
		}
		
		public static Os gtBySeparator(String separator) {
			
			for(Os os: Os.values()) {
				if(os.getSepartor().equals(separator)) 
					return os;
			}
			return null;
		}
		
		public String getRootDir() {
			return rootDir;
		}
		public void setRootDir(String rootDir) {
			this.rootDir = rootDir;
		}
		public String getSepartor() {
			return separtor;
		}
		public void setSepartor(String separtor) {
			this.separtor = separtor;
		}
		
	}
	

}
