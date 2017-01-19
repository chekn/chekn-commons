package com.chekn.test;

import java.io.File;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.util.List;

import com.chekn.db.MySQLWorkDb;
import com.fasterxml.jackson.databind.ObjectMapper;

/**
 * 
 * @author CodeFlagAI
 * @date 2017年1月19日-上午10:45:32
 */
public class Test3 {
	
	
	public static void main(String[] args) throws Exception {
		
		
		Connection conn = MySQLWorkDb.getConnection("auto_port_bg_demo");
		

		List<String> notInOss = new ObjectMapper().readValue(new File("C:/Users/CodeFlagAI/Desktop/notInOss.txt"), List.class);
		for(int i=0; i< notInOss.size(); i++) {
			String sql = "insert into t_cert_self values("+ i +", '"+ notInOss.get(i) +"')";
			PreparedStatement ps =conn.prepareStatement(sql);
			ps.executeUpdate();
			ps.close();
			System.out.println(String.format("=>%s", sql));
		}
		
		conn.close();
		System.out.println(String.format("任务执行完成", 1));
	}
	
	
}
