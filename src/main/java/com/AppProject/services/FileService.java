package com.AppProject.services;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Date;
import java.util.Optional;

import org.apache.commons.io.FilenameUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.AppProject.Utils.Constants;
import com.AppProject.Utils.MimeTypes;
import com.AppProject.Utils.Utils;
import com.AppProject.entities.Medias;
import com.AppProject.entities.Project;
import com.AppProject.entities.User;
import com.AppProject.models.FileEnum;
import com.AppProject.models.FileModel;
import com.AppProject.models.ResponseObject;
import com.AppProject.repositories.MediaRepository;
import com.AppProject.repositories.ProjectRepository;
import com.AppProject.repositories.UserRepository;
import com.AppProject.security.FileStorageProperties;

@Service
public class FileService {

	private final Logger logger = LoggerFactory.getLogger(FileService.class);
	private final Path fileStorageLocation;

	@Autowired
	ProjectRepository projectRepository;
	@Autowired
	UserRepository userRepository;
	@Autowired
	MediaRepository mediaRepository;

	@Autowired
	public FileService(FileStorageProperties fileStorageProperties) {
		this.fileStorageLocation = Paths.get(fileStorageProperties.getUploadDir()).toAbsolutePath().normalize();
		try {
			Files.createDirectories(this.fileStorageLocation);
		} catch (Exception ex) {
			throw new RuntimeException("Could not create the directory where the uploaded files will be stored.", ex);
		}
	}

	public Object addNewUpload(FileModel media) {
		ResponseObject object = new ResponseObject();
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		String userName = auth.getName(); // get logged in username
		if (media == null || !valid(media.getUploadType())) {
			object.setStatus(Constants.CODE_ERROR);
			object.setMessage("Required fields are missing");
			return object;
		}

		logger.debug("-------------------");
		
		Date currentDate =new Date();

		Optional<Project> projinfo = projectRepository.findById(media.getProjectid());
		Project project = projinfo.get();

		User fuser = userRepository.findByUsername(userName);
		logger.debug("Project Id :" + media.getProjectid());
		logger.debug("Project Type :" + media.getUploadType());
		logger.debug("-------------------");
		if (media.getFiles() != null) {
			for (MultipartFile file : media.getFiles()) {
				
				String filename = file.getOriginalFilename();
				String extension=FilenameUtils.getExtension(filename);
				
				String renameFilename="AS"+"_"+Utils.getDate(currentDate)+"."+extension;

				logger.debug("Qrig Name : " + file.getOriginalFilename());
				logger.debug("Name 		: " + file.getName());
				logger.debug("Type 		: " + file.getContentType());
				logger.debug("Size		: " + file.getSize());
				logger.debug("Extension	: " + extension);
				
				String mimmetype = MimeTypes.getMimeType(extension);
				logger.debug("MimeType		: " + mimmetype);
				
				String fileFolder = MimeTypes.getFolderName(mimmetype.toLowerCase().trim());
				logger.debug("Mime Folder		: " + fileFolder);
				
				String filePath = "//" + project.getName() + "//" + fileFolder;
				logger.debug("Folder	: " + fileFolder);

				String folder = this.fileStorageLocation.toString() + filePath;

				File destination = null;
				if (StringUtils.isNotEmpty(folder)) {
					destination = new File(folder);
					if (!destination.exists()) {
						destination.mkdirs();
					}
				}

				logger.debug("Path Folder	: " + folder);

				try {
					if (destination != null) {
						byte[] bytes = file.getBytes();
						// Create the file on server
						File serverFile = new File(destination.getAbsolutePath() + File.separator + filename);
						File serverRenamed=new File(destination.getAbsolutePath()+File.separator+renameFilename);
						BufferedOutputStream stream = new BufferedOutputStream(new FileOutputStream(serverFile));
						stream.write(bytes);
						stream.close();

						serverFile.renameTo(serverRenamed);
						logger.debug("Server File Location= {}" ,serverRenamed.getAbsolutePath());
						Medias smedia=new Medias();
						smedia.setActive(1);
						smedia.setCreatedAt(currentDate);
						smedia.setFile_new_name(renameFilename);
						smedia.setFile_org_name(filename);
						smedia.setFile_size(file.getSize());
						smedia.setFilePath(serverFile.getParent());
						smedia.setFiletype(extension.toLowerCase());
						smedia.setProject_id(project.getId());
						smedia.setUser_id(fuser.getId());
						mediaRepository.save(smedia);
					}
				} catch (Exception e) {
					object.setStatus(Constants.CODE_ERROR);
					object.setMessage("You failed to upload " + filename + " => " + e.getMessage());
					return object;
				}

				logger.debug("-------------------");
			}
			object.setStatus(Constants.CODE_SUCCESS);
			object.setMessage("You have successfully uploaded files");
			return object;
		} else {
			object.setStatus(Constants.CODE_ERROR);
			object.setMessage("No files found to Upload. Please try again later");
			return object;
		}

	}

	
	private boolean valid(int uploadType) {
		return FileEnum.isCheck(uploadType);
	}

}
