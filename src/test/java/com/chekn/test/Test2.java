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
public class Test2 {
	
	private static Logger logger=LoggerFactory.getLogger(Test2.class);
	
	public static OSSClient getOssClient(){
		Map<String, String> props =Resources.init("ali");
		String endPoint =props.get("ali.oss.endpoint");
		String keyId = props.get("ali.oss.access.key.id");
		String keySecret = props.get("ali.oss.access.key.secret");
		OSSClient ossClient = OssClientFactory.getOssClient(endPoint, keyId, keySecret);
		return ossClient;
	}
	
	public static void main(String[] args) throws SQLException {
	
		
		OSSClient ossClient = getOssClient();
		///bakBucket(ossClient, "uploadall", "uploadall-bak");
		clearBucket(ossClient, "uploadall-bak");
		ossClient.shutdown();
	}
	
	public static void bakBucket(OSSClient ossClient , String sourceBucket, String bakBucket) {
		final int maxKeys = 100;
		String nextMarker = null;
		
		/*
		Date now = new Date();
		int upTotal=0;
		logger.info("new img task start -> marker: {} ...", DateFormatUtils.format(now, "yyyy-MM-dd HH:mm:ss.SSS"));
		Date endTime = new Date();
		logger.info("new img task done -> find: {}, spend time: {} s, marker: {}...", upTotal, (endTime.getTime() - now.getTime()) /1000, DateFormatUtils.format(endTime, "yyyy-MM-dd HH:mm:ss.SSS") );
		*/
		ObjectListing listing; 
		do{
			//区全部内容条件拼装
			ListObjectsRequest listObjectsRequest= new ListObjectsRequest(sourceBucket);
			if(nextMarker!=null)
				listObjectsRequest.withMarker(nextMarker);
			listing = ossClient.listObjects(listObjectsRequest.withMaxKeys(maxKeys));
			List<OSSObjectSummary> dataUnit= listing.getObjectSummaries();
			
			for(OSSObjectSummary mDataUnit: dataUnit) {
				//Date lastModifiedDate= mDataUnit.getLastModified();
				String imagePath=mDataUnit.getKey();
				//备份
				ossClient.copyObject(sourceBucket, imagePath, bakBucket, imagePath);
				System.out.print(".");
			}

			System.out.println();
			nextMarker=listing.getNextMarker();
			//logger.info("current arrive nextMarker->{}...", nextMarker);
		} while(listing.isTruncated()) ;
		
		System.out.println(String.format("%s-%s bak opeation finish.", 1));
	}

	
	public static void clearBucket(OSSClient ossClient, String bucketName) {
		final int maxKeys = 100;
		String nextMarker = null;
		
		/*
		Date now = new Date();
		int upTotal=0;
		logger.info("new img task start -> marker: {} ...", DateFormatUtils.format(now, "yyyy-MM-dd HH:mm:ss.SSS"));
		Date endTime = new Date();
		logger.info("new img task done -> find: {}, spend time: {} s, marker: {}...", upTotal, (endTime.getTime() - now.getTime()) /1000, DateFormatUtils.format(endTime, "yyyy-MM-dd HH:mm:ss.SSS") );
		*/
		ObjectListing listing; 
		do{
			//区全部内容条件拼装
			ListObjectsRequest listObjectsRequest= new ListObjectsRequest(bucketName);
			if(nextMarker!=null)
				listObjectsRequest.withMarker(nextMarker);
			listing = ossClient.listObjects(listObjectsRequest.withMaxKeys(maxKeys));
			List<OSSObjectSummary> dataUnit= listing.getObjectSummaries();
			
			for(OSSObjectSummary mDataUnit: dataUnit) {
				//Date lastModifiedDate= mDataUnit.getLastModified();
				String imagePath=mDataUnit.getKey();
				//备份
				ossClient.deleteObject(bucketName, imagePath);
				//
				System.out.println(imagePath);
			}

			System.out.println();
			nextMarker=listing.getNextMarker();
			//logger.info("current arrive nextMarker->{}...", nextMarker);
		} while(listing.isTruncated()) ;
		
		System.out.println(String.format("%s bak opeation finish.", 1));	//%s 能少，不能多
	}

	
}
