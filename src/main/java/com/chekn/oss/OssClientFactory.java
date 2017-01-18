package com.chekn.oss;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.aliyun.oss.OSSClient;

public class OssClientFactory {

	private static final Logger LOG = LoggerFactory.getLogger(OssClientFactory.class);

	public static OSSClient getOssClient(String endPoint, String keyId, String keySecret){
		return new OSSClient(endPoint,keyId, keySecret);
	}
	
}
