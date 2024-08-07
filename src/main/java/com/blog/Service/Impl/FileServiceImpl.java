package com.blog.Service.Impl;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.UUID;

import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.blog.Service.FileService;

@Service
public class FileServiceImpl implements FileService {

	@Override                             // MultipartFile representing the file to be uploaded.
	public String uplodImage(String path, MultipartFile file) throws IOException {
		
		// 1. Take file name
		// This line retrieves the original file name from the MultipartFile object and stores it in the name variable.
		String name = file.getOriginalFilename();
		
		// 2. Generate random name for file to make file name unique
		String randomId = UUID.randomUUID().toString();
		
		// 3. Add randomID to file name
		// name.substring(name.lastIndexOf(".")) extracts the file extension from the original file name.
		String fileName1 = randomId.concat(name.substring(name.lastIndexOf(".")));
		
		// 4. Take full path and file name to pick file
		String filePath = path + File.separator + fileName1;
		
		// 5. Create folder if not exist
		File f = new File(path);
		if(!f.exists()) {
			f.mkdir();
		}
		
		// 6. Copy file
		Files.copy(file.getInputStream(), Paths.get(filePath));
		
		return fileName1;	
		
	}

	@Override
	public InputStream getResource(String path, String fileName) throws FileNotFoundException {
		
		String fullPath = path + File.separator + fileName;
		
		InputStream is = new FileInputStream(fullPath);
		
		// DB logic to return the inputstream
		
		
		return is;
	}

}
