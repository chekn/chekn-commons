package com.chekn.lang;

import java.io.File;
import java.util.List;

/**
 * 
 * @author CodeFlagAI
 * @date 2016年12月12日-下午5:10:22
 */
public class U {
	
	public static void getSubFiles(List<File> files, File file) {
		if(file.isDirectory()) {
			for(File subFile:file.listFiles())
				U.getSubFiles(files, subFile);
		} else
			files.add(file);
	}

}
