package com.chekn.oss;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.apache.commons.io.FilenameUtils;
import org.apache.commons.lang.StringUtils;

import com.aliyun.oss.OSSClient;
import com.aliyun.oss.model.OSSObjectSummary;
import com.aliyun.oss.model.ObjectListing;

/**
 * 
 * @author CodeFlagAI
 * @date 2017年1月17日-下午4:35:24
 */
public class OssUtils {

	public static List<String> getAliOssFileList(OSSClient ossClient, String bucket, String prefix, String... ignoreFileNameExts) {
		ObjectListing objectListing = ossClient.listObjects(bucket, prefix);
		List<OSSObjectSummary> sums = objectListing.getObjectSummaries();
		List<String> fileNames= new ArrayList<String> ();
		
		for (OSSObjectSummary s : sums) {
			String sFileName=StringUtils.removeStart(s.getKey(), prefix);
			
			if( sFileName.contains("/") ) {
				//System.out.println("/ >>"+ sFileName);
				continue;
			}
			
			//扩展名忽略
			String sFileNameExt=FilenameUtils.getExtension(sFileName).toLowerCase();
			if(StringUtils.isBlank(sFileNameExt) || Arrays.asList(ignoreFileNameExts).contains(sFileNameExt)) 
				continue;
				
		    if( StringUtils.isNotBlank(sFileName) ) {
		    	fileNames.add(sFileName);
		    }
		}
		
		return fileNames.size()==0?null:fileNames;
	}

}
