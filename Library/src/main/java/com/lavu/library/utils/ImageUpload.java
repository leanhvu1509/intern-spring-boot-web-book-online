package com.lavu.library.utils;

import java.io.File;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;

import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

@Component
public class ImageUpload {

	private final String UPLOAD_FOLDER = "F:\\OJT Source\\wp_intern\\myintern\\Admin\\src\\main\\resources\\static\\assets\\img\\image-product";

	public boolean uploadImage(MultipartFile file) {
		boolean isUpload = false;
		try {
			Files.copy(file.getInputStream(), Paths.get(UPLOAD_FOLDER + File.separatorChar, file.getOriginalFilename()),
					StandardCopyOption.REPLACE_EXISTING);
			isUpload = true;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return isUpload;
	}

	public boolean checkExisted(MultipartFile file) {
		boolean isExisted = false;
		try {
			File isFile = new File(UPLOAD_FOLDER + "\\" + file.getOriginalFilename());
			isExisted = isFile.exists();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return isExisted;
	}
}
