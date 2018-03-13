/*
 * Copyright (c) 2017 songhyun All rights reserved.
 *
 * This software is the confidential and proprietary information of songhyun.
 * You shall not disclose such Confidential Information and shall use it
 * only in accordance with the terms of the license agreement you entered into
 * with songhyun.
 */
package com.wdfall.slot.business.utils;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.io.FilenameUtils;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import lombok.Data;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class ControllerUtil {

	public static class UploadDomain {
		public static final String DOMAIN_EXCEL =  "/uploads/excel";
	}
	
	/**
	 * 업로드 된 파일 정보
	 * 
	 * <pre>
	 * </pre>
	 * 
	 * @author sample
	 * @since 2017. 3. 14.  
	 */
	@Data
	public static class UpFileInfo {
		/** server쪽 file */
		private File upfile;
		/** dp에 들어갈 Path */
		private String dbPath;
		/** filename */
		private String filename;
		
	}
	
	/**
	 * 파일 업로드 처리
	 * 
	 * @param request
	 * @param fileParamName
	 * @param domain
	 * @return dbPath
	 * @throws IOException
	 */
	public static List<UpFileInfo> handleMultiFileUpload(MultipartHttpServletRequest request, String fileParamName,
			String domain) throws IOException {
		List<MultipartFile> reqFileList = request.getFiles(fileParamName);
		List<UpFileInfo> resultList = new ArrayList<UpFileInfo>();
		for (MultipartFile reqFile : reqFileList) {
			UpFileInfo upInfo = new UpFileInfo();
			if (reqFile != null) {
				String fileUploadDir = getFileUploadDir(request, domain);
				String saveFileName = UUID.randomUUID().toString();
				File file = saveFile(reqFile, fileUploadDir, saveFileName);
				String fileUploadDbPath = getFileUploadDbPath(request, file);
				upInfo.setUpfile(file);
				upInfo.setDbPath(fileUploadDbPath);
				upInfo.setFilename(reqFile.getOriginalFilename());
			}
			resultList.add(upInfo);
		}
		return resultList;
	}

	/**
	 * 파일 업로드 처리
	 * 
	 * @param request
	 * @param fileParamName
	 * @param domain
	 * @return dbPath
	 * @throws IOException
	 */
	public static UpFileInfo handleFileUpload(MultipartHttpServletRequest request, String fileParamName, String domain)
			throws IOException {
		List<UpFileInfo> fileUploads = handleMultiFileUpload(request, fileParamName, domain);
		if(!CollectionUtils.isEmpty(fileUploads)) {
			return fileUploads.get(0);
		} else {
			//없으면 empty한 object 리턴
			return new UpFileInfo();
		}
	}

	/**
	 * 원 파일명 가져오기
	 * 
	 * @param request
	 * @param fileParamName
	 * @param domain
	 * @return
	 * @throws IOException
	 */
	public static String handleFileUploadGetOriFileName(MultipartHttpServletRequest request, String fileParamName)
			throws IOException {
		MultipartFile reqFile = request.getFile(fileParamName);
		String oriFileName = "";
		if (reqFile != null) {
			oriFileName = reqFile.getOriginalFilename();
		}
		return oriFileName;
	}

	/**
	 * 파일 업로드
	 * 
	 * @param multipartFile
	 * @param fileUploadDir
	 * @param saveFileName
	 * @return
	 * @throws IllegalStateException
	 * @throws IOException
	 */
	public static File saveFile(MultipartFile multipartFile, String fileUploadDir, String saveFileName)
			throws IllegalStateException, IOException {
		String originalFilename = multipartFile.getOriginalFilename();
		String ext = FilenameUtils.getExtension(originalFilename);
		saveFileName = saveFileName + "." + ext;

		// System.out.println(saveFileName);
		// System.out.println(fileUploadDir);

		File directory = new File(fileUploadDir);
		if (!directory.exists()) {
			directory.mkdirs();
		}

		File file = new File(directory, saveFileName);
		multipartFile.transferTo(file);

		return file;
	}
	

	/**
	 * 파일 업로드 기본 목록
	 * 
	 * @param request
	 * @return
	 */
	public static String getFileUploadDir(HttpServletRequest request, String domain) {
		// 
		String fileUploadDir = request.getServletContext().getRealPath(domain);
		return fileUploadDir;
	}


	/**
	 * 파일 업로드 database에 저장할 path
	 * 
	 * @param request
	 * @param file
	 * @return
	 * @throws IOException
	 */
	public static String getFileUploadDbPath(HttpServletRequest request, File file) throws IOException {
		String realRootPath = request.getServletContext().getRealPath("/");
		String dbPath = file.getCanonicalPath().replace(realRootPath, "/");
		dbPath = dbPath.replaceAll("\\\\", "/");
		dbPath = dbPath.replaceAll("//", "/");
		return dbPath;
	}

	/**
	 * database path로 실제 path 조회
	 * 
	 * @param request
	 * @param dbPath
	 * @return
	 * @throws IOException
	 */
	public static String getRealPathFromDbPath(HttpServletRequest request, String dbPath) throws IOException {
		String result = "";
		String realRootPath = request.getServletContext().getRealPath("/");
		result = realRootPath + dbPath;
		result = result.replaceAll("//", "/");
		return result;
	}
	
	//=============== 다운로드 관련 ======================
	/**
     * @param aFile 다운로드 할 파일
     * @param response 
     */
    public static void download(File aFile, HttpServletResponse response){
        
        InputStream fis = null;
        try {
        	fis = new BufferedInputStream(new FileInputStream(aFile));
        	org.apache.commons.io.IOUtils.copy(fis, response.getOutputStream());
            response.flushBuffer();
        } catch(Exception e){
            e.printStackTrace();
            log.info("Error writing file to output stream. Filename was '{}'", aFile.getName(), e);
            throw new RuntimeException("IOError writing file to output stream");
        }finally{
        	try{ fis.close();  }catch(Exception e){}
        	
        }
        
    }

    /**
     * content type 
     * @param request
     * @param response
     * @param aFile
     * @param fileName
     */
    public static void setContentsType( HttpServletRequest request,  HttpServletResponse response, File aFile, String fileName){

        String userAgent = request.getHeader("User-Agent");
        fileName = encodeFileName( userAgent, fileName);

		String contentsType = request.getContentType();
		if( fileName.toLowerCase().indexOf(".pdf") > -1 ){
			contentsType = "application/pdf";
		} else if( fileName.toLowerCase().indexOf(".zip") > -1 ){
			contentsType = "application/zip";
		}
        response.setContentType(contentsType);
        response.setContentLength((int)aFile.length());
        response.setHeader("Content-Disposition", "attachment; filename=\"" + fileName + "\";");
        response.setHeader("Content-Transfer-Encoding", "binary");
    }
    
    /**
     * 파일 이름 처리
     * @param userAgent
     * @param fileName
     * @return
     */
    public static String encodeFileName( String userAgent, String fileName ){
    	try {
	        boolean ie = userAgent.indexOf("MSIE") > -1;
	        if( userAgent.contains("MSIE") || userAgent.contains("Trident") || userAgent.contains("Chrome")){             
	        	fileName = URLEncoder.encode(fileName,"UTF-8");				
	        	fileName = fileName.replaceAll("\\+", "%20").replaceAll("%28", "(").replaceAll("%29", ")");
	        } else {               
	        	fileName = new String(fileName.getBytes("UTF-8"), "ISO-8859-1");
	        }
    	} catch (UnsupportedEncodingException e) {
			log.error(e.getMessage());
		}
    	
        return fileName ;
    }
    //=============== ]]다운로드 관련 ======================

	
}
