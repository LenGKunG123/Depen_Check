package isc.etax.taxfiling.services;

import isc.etax.taxfiling.DTOS.ConfigRequestDTO;
import isc.etax.taxfiling.entities.Config;
import isc.etax.taxfiling.entities.Config.CalendarType;
import isc.etax.taxfiling.entities.Config.ResetType;
import isc.etax.taxfiling.entities.CurrentDocument;
import isc.etax.taxfiling.entities.Profile;
import isc.etax.taxfiling.entities.Template;
import isc.etax.taxfiling.repositories.ConfigRepository;
import isc.etax.taxfiling.repositories.CurrentDocumentRepository;
import isc.etax.taxfiling.utils.FileUploadUtil;
import isc.etax.taxfiling.utils.NanoUtil;

import org.apache.commons.io.FilenameUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.server.ResponseStatusException;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class ConfigService {

    @Autowired
    private ConfigRepository configRepository;
    
    @Autowired
    private FileUploadUtil fileUploadUtil;
    
    @Autowired
    private NanoUtil nanoUtil;
    
    @Autowired
    private CurrentDocumentRepository currentDocRepository;
    
    @Autowired
    private TemplateService templateService;
    
    String uploadDir = new File("uploads/").getAbsolutePath();
    String subFolder = "/signatures/";
    String fullPath = uploadDir + subFolder;

    public List<Config> getAll() {
        return configRepository.findAll();
    }

    public Config getById(Long id) {
        return configRepository.findById(id).get();
    }
    
    public Config getCurrentConfig(Long compId) {
    	 Optional<Config> config = configRepository.findByCompanyProfile_ProfileIdAndStatus(compId, "ACTIVE");
        return config.orElse(null);
    }
    
    public void newConfig (MultipartFile file, Profile company, ConfigRequestDTO configDTO) {
//    	if (configRepository.existsByCompanyProfile_ProfileIdAndStatus(company.getProfileId(), "ACTIVE")) {
//    		throw new ResponseStatusException(HttpStatus.CONFLICT, "Config is already exist");
//    	}
    	
    	Config config = new Config();
    	config.setBookFormat(configDTO.getBookFormat());
    	config.setDocumentFormat(configDTO.getDocumentFormat());
    	config.setSequencePadding(configDTO.getSequencePadding());
    	config.setCalendarType(configDTO.getCalendarType());
    	config.setResetType(configDTO.getResetType());
    	config.setStatus("ACTIVE");
    	config.setCompanyProfile(company);
    	
    	Template template = templateService.getTemplateByTemplateCode(configDTO.getTemplateCode());
    	if (template == null) {
    		throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Template not found.") ;
    	}
    	config.setTemplate(template);
    	
    	String fileName = nanoUtil.nanoIdGenerate(4) + "_" + System.currentTimeMillis() + "." + FilenameUtils.getExtension(file.getOriginalFilename());
    	try {
			File directory = new File(fullPath);
			if (!directory.exists()) {
				directory.mkdirs();
			}
			Path filePath = Paths
					.get(fullPath + fileName);
			Files.write(filePath, file.getBytes());
			config.setSignaturePathName(fileName);
//			config.setTemplate(1);

			try {
				configRepository.save(config);
			} catch (Exception e) {
				Files.deleteIfExists(filePath);
				throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR,
						"Database save failed. " + e.getMessage());
			}
			
			CurrentDocument currDoc = new CurrentDocument();
			currDoc.setConfig(config);
			currentDocRepository.save(currDoc);
		} catch (IOException e) {
			throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR,
					"File upload failed: " + e.getMessage());
		}
    	
    }
    
    
    public void editConfig (MultipartFile file, Profile company, ConfigRequestDTO configDTO) {
    	
    }
    

}
