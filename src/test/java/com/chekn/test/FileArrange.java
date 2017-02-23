package com.chekn.test;

import java.io.BufferedOutputStream;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.util.UUID;

public class FileArrange {
	public static final int FULLFILL=0;
	public static final int DELETE=1;
	
	
	public void core(String path, int aeType) throws Exception{
		
		File dir=new File(path);
		File[] files=dir.listFiles();
		
		if(files==null){
			return ;
		}
		
		for(int i=0;i<files.length;i++){
			if(files[i].isDirectory()){
				core(files[i].getAbsolutePath().toLowerCase(),aeType);
			}else{
				if(files[i].getName().equals("Thumbs.db")) continue;
				
				System.out.println("当前处理文件："+files[i].getName());
				
				//this.writeStr(files[i], UUID.randomUUID().toString());
				if(aeType==FileArrange.FULLFILL){
					this.writeBin(files[i], UUID.randomUUID().toString());
				}else if(aeType==FileArrange.DELETE){
					this.destroy(files[i]);
				}
			}
		}
		
	}
	
	public void destroy(File a){
		a.deleteOnExit();
	}
	
	public void writeStr(File a, String generStr){
		try {
			FileWriter fw=new FileWriter(a);
			fw.write(generStr);
		
			//fw.write("\r\ncxs");
			fw.flush();
			fw.close();
		} catch (IOException e) {
			System.err.println(a.getName()+"操作失败");
		}
		
	}
	
	public void writeBin(File a, String s) throws Exception{
		try {
			long oriFileLength=a.length();
			
			DataOutputStream dos=new DataOutputStream(
						new BufferedOutputStream(new FileOutputStream(a))
					);
			
			for(int i=0;i<oriFileLength;i++){
				int writeInt=this.getRandomInt();
				dos.write(writeInt);
			}
			
			dos.flush();
			dos.close();
		} catch (IOException e) {
			System.err.println(a.getName()+"操作失败");
		}
	}
	
	protected int getRandomInt(){
		double ds=Math.random();
		return (int)(ds*1000);
	}
	
	
	
}
