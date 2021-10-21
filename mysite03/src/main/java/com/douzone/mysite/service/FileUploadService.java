package com.douzone.mysite.service;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.UUID;

import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

@Service
public class FileUploadService {
	private String SAVE_PATH = "/upload-images";
	private String URL_BASE = "/images";
	
	public String restore(MultipartFile multipartFile, String path) {
		String url = null;
		int extensionIdx = multipartFile.getOriginalFilename().lastIndexOf('.');
		
		if(multipartFile.isEmpty() || extensionIdx == -1) {
			return url;
		}
		
		String extension = multipartFile.getOriginalFilename().substring(extensionIdx);
		String fileName = UUID.randomUUID() + extension;
		url = URL_BASE + "/" + path + "/" + fileName;
		String filePath = SAVE_PATH + "/" + path + "/" + fileName; 
		File file = new File(filePath);
		file.getParentFile().mkdirs();
		try(OutputStream outputStream = new FileOutputStream(file)) {
			outputStream.write(multipartFile.getBytes());
		} catch (IOException e1) {
			throw new RuntimeException("error occurs in file upload");
		}
		
		return url;
	}

}
