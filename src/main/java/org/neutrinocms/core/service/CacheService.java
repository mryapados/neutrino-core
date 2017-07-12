package org.neutrinocms.core.service;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.NoSuchFileException;
import java.nio.file.Path;
import java.nio.file.Paths;

import javax.annotation.PostConstruct;

import org.apache.log4j.Logger;
import org.neutrinocms.core.conf.NeutrinoCoreProperties;
import org.neutrinocms.core.constant.CacheConst;
import org.neutrinocms.core.util.CommonUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;


@Service
public class CacheService{

	private Logger logger = Logger.getLogger(CacheService.class);

	@Autowired
	private NeutrinoCoreProperties neutrinoCoreProperties;
	
	@Autowired
	protected CommonUtil common;
	
	
    private Path rootLocation;

    @PostConstruct
    private void initialize(){
    	this.rootLocation = Paths.get(common.getWebInfFolder() + neutrinoCoreProperties.getCacheDir());
    }
    
	@Cacheable(value = CacheConst.JSP, unless = "#result == null")
	public String getContentFromCache(int hashCode) throws IOException {
		if (!neutrinoCoreProperties.getJspCache()) return null;
		
		logger.debug("Enter in getContentFromCache");
		try {
			return new String(Files.readAllBytes(Paths.get(rootLocation + "/" + hashCode)));
		} catch (NoSuchFileException e) {
			return null;
		}
	}
	
	public void mkCachedFile(int hashCode, String Content) throws IOException{
		if (!neutrinoCoreProperties.getJspCache()) return;
		
		logger.debug("Enter in mkCachedFile");
		
		File file = new File(rootLocation  + "/");
		file.mkdirs();

		file = new File(file, Integer.toString(hashCode));
		
		file.createNewFile();
		FileWriter fw = new FileWriter(file);

		fw.write(Content);
		fw.flush();

		fw.close();
	}

}
