package com.wind.utils;

import java.io.File;
import java.io.IOException;
import java.util.UUID;

import org.apache.commons.io.FileUtils;
import org.apache.commons.io.FilenameUtils;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class FileUtil {

	private static final String FILE_ENCODING = "UTF-8";

	public static File getFileOnClasspath(String name) {
		ClassLoader loader = Thread.currentThread().getContextClassLoader();
		File file = new File(loader.getResource("").getPath(), name);
		return file;
	}
	
	public static void writeToFile(File file, String data) {
		try {
			FileUtils.writeStringToFile(file, data, FILE_ENCODING);
		} catch (IOException e) {
			log.error("",e);
			throw new RuntimeException(e);
		}
	}
	
	public static String readFromFile(File file) {
		String data = "";
		try {
			data = FileUtils.readFileToString(file, FILE_ENCODING);
		} catch (IOException e) {
			log.error("",e);
			throw new RuntimeException(e);
		}
		return data;
	}
	
	
	public static File copyToTmpFile(File in) throws IOException {
		String systemTmpDirectory = System.getProperty("java.io.tmpdir");
		File tmpDirectory = new File(systemTmpDirectory, "vslot_excel_tmp");
		log.debug(">> systemTmpDirectory={}",systemTmpDirectory);
		
		if(in == null || !in.exists()) {
			throw new IllegalArgumentException();
		}
		if(!tmpDirectory.exists()) {
			FileUtils.forceMkdir(tmpDirectory);
		}
		
		String extension = "." + FilenameUtils.getExtension(in.getCanonicalPath());
		String tmpFileName = UUID.randomUUID().toString() + extension;
		File out = new File(tmpDirectory, tmpFileName);
		FileUtils.copyFile(in, out);
		return out;
	}
	
	public static void deleteFile(File file) {
		FileUtils.deleteQuietly(file);
	}
	
	public static void main(String[] args) throws IOException {
		File copyTmpFile = copyToTmpFile(new File("D:/tmp/stage01.xlsx"));
		log.debug("copyTmpFile={}", copyTmpFile);
		deleteFile(copyTmpFile);
	}
	
}
