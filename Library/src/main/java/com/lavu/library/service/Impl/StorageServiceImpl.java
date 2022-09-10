package com.lavu.library.service.Impl;

import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.Arrays;

import org.apache.commons.io.FilenameUtils;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.lavu.library.config.StorageProperties;
import com.lavu.library.exception.ErrorMessage;
import com.lavu.library.service.StorageService;

@Service
public class StorageServiceImpl implements StorageService {

	private final Path rootLocation;

	public StorageServiceImpl(StorageProperties properties) {
		this.rootLocation = Paths.get(properties.getLocation());
	}

	@Override
	public void init() {
		try {
			Files.createDirectories(rootLocation);
		} catch (Exception e) {
			throw new ErrorMessage("Could not initialize store", e);
		}
	}

	@Override
	public String getStoredFilename(MultipartFile file, String id) {
		String ext = FilenameUtils.getExtension(file.getOriginalFilename());
		return "p" + id + "." + ext;
	}

	@Override
	public void store(MultipartFile file, String storedFilename) {
		try {
			if (file.isEmpty()) {
				throw new ErrorMessage("Failed to store emptyy file!");
			}
			// check file is image?
			if (!isImageFile(file)) {
				throw new ErrorMessage("You can only upload image file.");
			}
			// file must be <5mb
			float fileSizeInMegabytes = file.getSize() / 1_000_000.0f;
			if (fileSizeInMegabytes > 5.0f) {
				throw new ErrorMessage("File must be <= 5 Mb");
			}

			Path destinationFile = this.rootLocation.resolve(Paths.get(storedFilename)).normalize().toAbsolutePath();
			if (!destinationFile.getParent().equals(this.rootLocation.toAbsolutePath())) {
				throw new ErrorMessage("Cannot store file outside current directory!");
			}
			try (InputStream inputStream = file.getInputStream()) {
				Files.copy(inputStream, destinationFile, StandardCopyOption.REPLACE_EXISTING);
			}
		} catch (Exception e) {
			throw new ErrorMessage("Failed to store file", e);
		}
	}

	@Override
	public Resource loadAsResource(String filename) {
		try {
			Path file = load(filename);
			Resource resource = new UrlResource(file.toUri());
			if (resource.exists() || resource.isReadable()) {
				return resource;
			}
			throw new ErrorMessage("Could not read file " + filename);
		} catch (Exception e) {
			throw new ErrorMessage("Could not read file " + filename);
		}
	}

	@Override
	public Path load(String filename) {
		return rootLocation.resolve(filename);
	}

	@Override
	public void delete(String storedFilename) throws IOException {
		Path destinationFile = rootLocation.resolve(Paths.get(storedFilename)).normalize().toAbsolutePath();
		Files.delete(destinationFile);
	}

	private boolean isImageFile(MultipartFile file) {
		String fileExtension = FilenameUtils.getExtension(file.getOriginalFilename());
		return Arrays.asList(new String[] { "png", "jpg", "jpeg", "bmp", "svg" })
				.contains(fileExtension.trim().toLowerCase());
	}
}
