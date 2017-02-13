package com.chekn.lang;

import java.io.BufferedInputStream;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
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
	
	public static Throwable getFinalCause(Throwable cause) {
		if(cause.getCause() !=null ) 
			return getFinalCause(cause.getCause());
		else
			return cause;
	}
	
	public static Thread getThread(long threadId) {
		ThreadGroup group = Thread.currentThread().getThreadGroup();
		while(group != null ){
			Thread[] threads = new Thread[ (int)(group.activeCount() * 1.2)];
			int count = group .enumerate(threads, true);
			for(int i=0; i<count; i++) {
				if(threadId == threads[i].getId())
					return threads[i];
			}
			group =group.getParent();
		}
		return null;
	}

	/**
	 * 大部分IO输入流是无法重复读取的，只能读取一次。再读取时，会抛出IO异常，我们可以使用ByteArrayOutputStream将流数据缓存到内存中，达到多次读取的目的。
	 * 以下就是Java来实现可重复读的IO流：
	 * @param is
	 * @return
	 * @throws IOException
	 */
	public static InputStream cvt2CanRepeatReadStream(InputStream is) throws IOException {
		ByteArrayOutputStream bos = new ByteArrayOutputStream();
		
		//将不可重复读取的IO输入流中读取数据到ByteArrayOutputStream中
		BufferedInputStream br = new BufferedInputStream(is);
		byte[] b = new byte[1024];
		for (int c = 0; (c = br.read(b)) != -1;)
		{
		    bos.write(b, 0, c);
		}
		b = null;
		br.close();
		is.close();
		
		//生成一个新的可重复读取的InputStream
		//这个时候的retInputStream是可以被多次重复读取，close对其无效，但是要注意每次读前，调用retInputStream.reset();方法需要将游标重置到流的头部。
		InputStream retInputStream = new ByteArrayInputStream(bos.toByteArray());
		bos.close();
		return retInputStream;
	}
	
}
