package com.core;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.fileupload.*;
import org.apache.commons.fileupload.disk.*;
import org.apache.commons.fileupload.servlet.*;

import java.util.List;
import java.io.File;
import java.io.UnsupportedEncodingException;
import java.util.Iterator;

//DiskFileUpload - Deprecated
// ServletFileUpload(FileItemFactory -- 파일이 저장될 수단)
// ServletFileUpload(org.apache.commons.fileupload.servlet)
// DiskFileItemFactory(org.apache.commons.fileupload.disk)
// FileItem(org.apache.commons.fileupload) -> 업로드된 파일 정보, 파일이 아닌 일반 양식 데이터
public class FileManager {
	/** 
	 * 파일 업로드 처리 
	 * 
	 * @param request
	 * @return
	 * @throws FileUploadException 
	 * @throws UnsupportedEncodingException 
	 */
	public static String upload(HttpServletRequest request) throws Exception {
		DiskFileItemFactory factory = new DiskFileItemFactory();
		ServletFileUpload upload = new ServletFileUpload(factory);
		upload.setHeaderEncoding("UTF-8");
		
		String uploadPath = request.getServletContext().getRealPath(File.separator + "upload");
		String uploadURI = request.getServletContext().getContextPath() + "/upload";
		
		List<FileItem> items = upload.parseRequest(request);
		Iterator<FileItem> params = items.iterator();
		
		StringBuilder sb = new StringBuilder();
		boolean isFirstFile = true; // 첫번째 파일 여부 
		while(params.hasNext()) {
			FileItem item = params.next();
			if (item.isFormField()) { // 일반 양식 데이터 
				//String key = item.getFieldName();
				//String value = item.getString("UTF-8");
				//System.out.println(key + " = " + value);
			} else { // 파일 데이터 
				/**
				 * item.write(File file); 
				 * -> 임시 메모리 공간에 올라와 있는 파일을
				 * -> file 인스턴스에 지정된 서버 실제 경로로 파일을 이동
				 *  C:\images\1.png  
				 */
				String contentType = item.getContentType(); // image/png image/gif
				if (contentType.indexOf("image") == -1) {
					// 이미지가 아닌 경우 건너 뛰기 
					continue;
				}
				
				String originalFileName = item.getName();
				String ext = originalFileName.substring(originalFileName.lastIndexOf("."));
				String fileName = System.currentTimeMillis() + ext;
				File file = new File(uploadPath + File.separator + fileName);	
				item.write(file);
				
				if (!isFirstFile)
					sb.append("||");
				
				sb.append(uploadURI + "/" + fileName);
				
				isFirstFile = false;
			}
		}
		
		return sb.toString(); // 업로드된 URL 경로들(||구분)
	}
}
