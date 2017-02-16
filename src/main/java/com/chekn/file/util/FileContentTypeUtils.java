package com.chekn.file.util;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

/**
 * 
 * @author CodeFlagAI
 * @date 2017年2月15日-下午3:09:34
 */
public class FileContentTypeUtils {

	public static String getContentType(String pathToFile) throws IOException {
		Path path = Paths.get(pathToFile);
		return Files.probeContentType(path);
	}
	
	public static void main(String[] args) {
		try {
			System.out.println("File content type is : " + getContentType(""));
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}
