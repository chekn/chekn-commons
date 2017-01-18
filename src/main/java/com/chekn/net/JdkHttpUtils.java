package com.chekn.net;

import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;

import org.apache.commons.io.IOUtils;

public class JdkHttpUtils {

	
	public static String getPageSource(String url, String cookie) throws Exception {
		HttpURLConnection connection= (HttpURLConnection) new URL(url).openConnection();
		connection.setRequestProperty("Cookie", cookie);
		InputStream in = connection.getInputStream();
		String ds=IOUtils.toString(in);
		in.close();
		connection.disconnect();
		return ds;
	}
	
	public static void download(String url, String file) throws Exception {
		InputStream in = new URL(url).openStream();
		FileOutputStream out=  new FileOutputStream(new File(file));
		IOUtils.copy(in,out);
		IOUtils.closeQuietly(in);
		IOUtils.closeQuietly(out);
	}
	
}
