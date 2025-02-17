package isc.etax.taxfiling.utils;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import org.apache.commons.io.FilenameUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.server.ResponseStatusException;

@Component
public class FileUploadUtil {
	
	@Autowired
    private NanoUtil nanoUtil;
	
	String uploadDir = new File("uploads/").getAbsolutePath();
    String subFolder = "/templates/";
    String fullPath = uploadDir + subFolder;
	
	
	public String newFileUpload(MultipartFile file, String subFolder) {
		String fileName = nanoUtil.nanoIdGenerate(4) + "_" + System.currentTimeMillis() + "." + FilenameUtils.getExtension(file.getOriginalFilename());
		if (subFolder == "template") {
			subFolder = "/templates/";
		} else {
			subFolder = "/signatures/";
		}
		try {
//            File directory = new File(fullPath);
//            if (!directory.exists()) {
//                directory.mkdirs();
//            }
            Path filePath = Paths.get(fullPath + fileName);
            Files.write(filePath, file.getBytes());
        } catch (IOException e) {
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "File upload failed: " + e.getMessage());
        }
		return fileName;
	}
	

}
