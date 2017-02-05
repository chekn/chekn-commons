package com.chekn.vm.util;

import java.io.StringWriter;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.apache.velocity.Template;
import org.apache.velocity.VelocityContext;
import org.apache.velocity.app.VelocityEngine;
import org.apache.velocity.runtime.RuntimeConstants;
import org.apache.velocity.runtime.resource.loader.ClasspathResourceLoader;

/**
 * 
 * @author CodeFlagAI
 * @date 2017年2月5日-下午12:23:43
 */
public class VelocityUtils {

	/**
	 * demo
	 * http://www.ibm.com/developerworks/cn/java/j-lo-velocity1/
	 * @param args
	 */
	public static void main(String[] args) {
		VelocityEngine ve = new VelocityEngine();
		ve.setProperty(RuntimeConstants.RESOURCE_LOADER, "classpath");
		ve.setProperty("classpath.resource.loader.class", ClasspathResourceLoader.class.getName());
		
		ve.init();
		Template t = ve.getTemplate("hellovelocity.vm");
		VelocityContext ctx = new VelocityContext();
		
		ctx.put("name", "velocity");
		ctx.put("date", (new Date()).toString());
		
		List<String> temp = new ArrayList<String>();
		temp.add("1");
		temp.add("2");
		ctx.put("list", temp);
		
		StringWriter sw = new StringWriter();
		t.merge(ctx, sw);
		System.out.println(sw.toString());
	}
	
}
