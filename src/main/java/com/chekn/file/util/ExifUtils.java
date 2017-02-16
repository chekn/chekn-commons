package com.chekn.file.util;
import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.drew.imaging.ImageMetadataReader;
import com.drew.imaging.ImageProcessingException;
import com.drew.metadata.Directory;
import com.drew.metadata.Metadata;
import com.drew.metadata.Tag;

public class ExifUtils {

	private Logger logger = LoggerFactory.getLogger(this.getClass());
	
	public Map<String,String> read(File file) throws ImageProcessingException, IOException {
		Map<String,String> revCareInfo = new HashMap<String,String>();
		Metadata metadata = ImageMetadataReader.readMetadata(file);
		for (Directory directory : metadata.getDirectories()) {
            for (Tag tag : directory.getTags()) {
                String tagName = tag.getTagName();
                String desc = tag.getDescription();
                revCareInfo.put(tagName, desc);
            }
            for (String error : directory.getErrors()){
            	logger.error("ERROR: {}", error);
            }
        }
		return revCareInfo;
	}
}
