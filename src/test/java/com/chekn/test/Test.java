package com.chekn.test;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.time.DateFormatUtils;
import org.apache.commons.lang.time.DateUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.aliyun.oss.OSSClient;
import com.aliyun.oss.model.ListObjectsRequest;
import com.aliyun.oss.model.OSSObjectSummary;
import com.aliyun.oss.model.ObjectListing;
import com.chekn.cnf.Resources;
import com.chekn.db.MySQLWorkDb;
import com.chekn.lang.U;
import com.chekn.oss.OssClientFactory;

/**
 * 
 * @author CodeFlagAI
 * @date 2017年1月17日-下午4:08:56
 */
public class Test {
	
	private static Logger logger=LoggerFactory.getLogger(Test.class);
	
	public static OSSClient getOssClient(){
		Map<String, String> props =Resources.init("ali");
		String endPoint =props.get("ali.oss.endpoint");
		String keyId = props.get("ali.oss.access.key.id");
		String keySecret = props.get("ali.oss.access.key.secret");
		OSSClient ossClient = OssClientFactory.getOssClient(endPoint, keyId, keySecret);
		return ossClient;
	}
	
	public static Connection getConnection() {
		Map<String, String> props =Resources.init("jdbc");
		String url =props.get("jdbc_url");
		String u = props.get("jdbc_username");
		String p = props.get("jdbc_password");
		return MySQLWorkDb.getConnection(url, u, p);
	}
	
	public static void main(String[] args) throws SQLException {
		
		String sql ="select sImagePath from t_image where tUploadTime <  timestamp('2017-01-09 19:45:59') ";
		Connection conn = getConnection();
		PreparedStatement ps =conn.prepareStatement(sql);
		
		ResultSet rs = ps.executeQuery();
		List<String> allPath = new ArrayList<String>();
		while(rs.next()) {
			String sPath = StringUtils.removeStart(rs.getString("sImagePath"), "/");
			allPath.add(sPath);
			//System.out.println("...."+ sPath);
		}
		
		System.out.println("sImagePath end!");
		
		
		List<String> notInOss = new ArrayList<String>();
		
		String bucketName ="uploadall";
		OSSClient ossClient = getOssClient();
		/**for(String path: allPath) {
			if(!ossClient.doesObjectExist(bucketName, path)) {
				System.out.println();
				System.out.println(String.format("%s not exist in oss", path));
				notInOss.add(path);
			} else 
				System.out.print(".");
		}
		
		System.out.println(notInOss);
		System.out.println("ossClient end!");*/
		getAllOssContent(ossClient, bucketName, null, allPath);
		//ObjectListing objectListing = ossClient.listObjects("autoport-test", "SH");
		//List<OSSObjectSummary> sums = objectListing.getObjectSummaries();
		//int i=0;
		/*if(sums != null)
			for(OSSObjectSummary sum: sums) {
				System.out.println((i++) + "。" + sum.getKey());
				//ossClient.deleteObject("autoport-test", sum.getKey());
				//if(i++ >10)
				//	break;
				// ossClient.copyObject("autoport-test", "cdd", "autoport-test", sum.getKey()+"cc");	//确认如果 copy 不存在会报异常，程序会中断
			}
		*/

		//System.out.println(ossClient.listBuckets());
		ossClient.shutdown();
	}
	
	public static void getAllOssContent(OSSClient ossClient , String bucket, String nextMarker2, List<String> allInDB) {
		final int maxKeys = 100;
		String nextMarker = nextMarker2 == null? null: nextMarker2;
		
		/*
		Date now = new Date();
		int upTotal=0;
		logger.info("new img task start -> marker: {} ...", DateFormatUtils.format(now, "yyyy-MM-dd HH:mm:ss.SSS"));
		Date endTime = new Date();
		logger.info("new img task done -> find: {}, spend time: {} s, marker: {}...", upTotal, (endTime.getTime() - now.getTime()) /1000, DateFormatUtils.format(endTime, "yyyy-MM-dd HH:mm:ss.SSS") );
		*/
		List<String> noInOss = new ArrayList<String>();
		
		ObjectListing listing; 
		do{
			//区全部内容条件拼装
			ListObjectsRequest listObjectsRequest= new ListObjectsRequest(bucket);
			if(nextMarker!=null)
				listObjectsRequest.withMarker(nextMarker);
			listing = ossClient.listObjects(listObjectsRequest.withMaxKeys(maxKeys));
			List<OSSObjectSummary> dataUnit= listing.getObjectSummaries();
			
			for(OSSObjectSummary mDataUnit: dataUnit) {
				//Date lastModifiedDate= mDataUnit.getLastModified();
				String imagePath=mDataUnit.getKey();
				
				if(!allInDB.contains(imagePath)){
					noInOss.add(imagePath);
				} else
					;//System.out.println(imagePath);
			}
				
			nextMarker=listing.getNextMarker();
			//logger.info("current arrive nextMarker->{}...", nextMarker);
		} while(listing.isTruncated()) ;
		
		//for(String oss: )
		System.out.println(noInOss);
	}

}
