package com.chekn.data;

import java.io.InputStream;
import java.io.StringWriter;
import java.net.URL;
import java.net.URLConnection;

import org.apache.commons.io.IOUtils;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.dataformat.xml.XmlMapper;

/**
 * 
 * @author CodeFlagAI
 * @date 2016年12月29日-下午1:07:16
 */
public class JsonXmlCvt {
	
	private static XmlMapper xmlMapper = new XmlMapper();
    private static ObjectMapper objectMapper = new ObjectMapper();
   
   
   //这种方式的转换存在 bug,会把本应该装成 数组的数据, 转成具有一堆相同属性的对象
   public static void main(String[] args) throws Exception {
       URLConnection conn=new URL("http://imagecv.autoport.com.cn/3106916100000027/636104871623404310.xml").openConnection() ;
       InputStream in = conn.getInputStream();
       
       String xml = IOUtils.toString(in,"UTF-8");
       StringWriter w = new StringWriter();  
       JsonParser jp;
       
       try {
           jp = xmlMapper.getFactory().createParser(xml);
           JsonGenerator jg = objectMapper.getFactory().createGenerator(w);  
           while (jp.nextToken() != null) {  
               jg.copyCurrentEvent(jp);  
           }  
           jp.close();  
           jg.close();
       } catch (Exception e) {
    	   throw new RuntimeException(e);
       }  
        
      System.out.println(w.toString());
   }
   
}
