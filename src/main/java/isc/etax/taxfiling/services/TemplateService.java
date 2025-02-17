package isc.etax.taxfiling.services;

import isc.etax.taxfiling.DTOS.TemplateDTO;
import isc.etax.taxfiling.entities.Profile;
import isc.etax.taxfiling.entities.Template;
import isc.etax.taxfiling.repositories.TemplateRepository;
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
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@Transactional
public class TemplateService {

	@Autowired
	private TemplateRepository templateRepository;

	@Autowired
	private NanoUtil nanoUtil;

	@Autowired
	private FileUploadUtil fileUploadUtil;

	String uploadDir = new File("uploads/").getAbsolutePath();
	String subFolder = "/templates/";
	String fullPath = uploadDir + subFolder;

	public List<Template> getAllTemplates() {
		return templateRepository.findAll();
	}
	
	public List<TemplateDTO> getAllTemplateDTOs() {
//		List<Template> templates = templateRepository.findAll();
//		List<TemplateDTO> dtos = new ArrayList<>();
//		for (Template temp : templates) {
//			dtos.add(new TemplateDTO(temp));
//		}
//		return dtos;
		return templateRepository.findAll()
        .stream()
        .map(TemplateDTO::new)
        .collect(Collectors.toList());
	}

	public Template getCurrentTemplate(Long compId) {
		Optional<Template> template = templateRepository.findByCompanyProfile_ProfileIdAndStatus(compId, "ACTIVE");
		return template.orElse(null);
	}
	
	public Template getTemplateByTemplateCode(String templateCode) {
		Optional<Template> template = templateRepository.findTemplatesByTemplateCode(templateCode);
		return template.orElse(null);	
	}

//    public Template createNewTemplate(MultipartFile file, Profile prolfie, String templateCode, String templateName, String status) {
//    	String fileName = nanoUtil.nanoIdGenerate(4) + "_" + System.currentTimeMillis();
//    	
//    	Template newTemplate = new Template(templateCode, templateName, fileName, prolfie, status);
//    	
//    	return templateRepository.save(newTemplate);
//    }

	public void newTemplate(MultipartFile file, Profile company, Template template) {
		Template currentTemplate = getCurrentTemplate(company.getProfileId());
		if (currentTemplate != null) {
			throw new ResponseStatusException(HttpStatus.CONFLICT, "Template already exist");
		}
		String fileName = nanoUtil.nanoIdGenerate(4) + "_" + System.currentTimeMillis();
		Template newTemp = new Template(template.getTemplateCode(), template.getTemplateName(), fileName, company, "ACTIVE");

		try {
			File directory = new File(fullPath);
			if (!directory.exists()) {
				directory.mkdirs();
			}
			Path filePath = Paths
					.get(fullPath + fileName + "." + FilenameUtils.getExtension(file.getOriginalFilename()));
			Files.write(filePath, file.getBytes());

			try {
				templateRepository.save(newTemp);
			} catch (Exception e) {
				Files.deleteIfExists(filePath);
				throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR,
						"Database save failed. " + e.getMessage());
			}

		} catch (IOException e) {
			throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR,
					"File upload failed: " + e.getMessage());
		}
	}

	public void updateTemplate(MultipartFile file, Profile company, Template template) {
    	if (company == null || company.getProfileId() == null) {
    	    throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Invalid company profile.");
    	}
    	
    	Template currentTemplate = getCurrentTemplate(company.getProfileId());
    	if (currentTemplate == null ) {
    		throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Template not found.");
    	}
//    	if (company.getProfileId() != currentTemplate.getCompanyProfile().getProfileId()) {
//    			}

		Template newTemp = new Template();
	
		if(file!=null) {
			newTemp.setFileName(fileUploadUtil.newFileUpload(file, "template"));
		} else {
			newTemp.setFileName(currentTemplate.getFileName());
		}
	
		if(template!=null) {
			newTemp.setTemplateCode(
					template.getTemplateCode() != null ? template.getTemplateCode() : currentTemplate.getTemplateCode());
			newTemp.setTemplateName(
					template.getTemplateName() != null ? template.getTemplateName() : currentTemplate.getTemplateName());
		}
	
		newTemp.setCompanyProfile(company);newTemp.setStatus("ACTIVE");currentTemplate.setStatus("EDITED");
	
		try {
			templateRepository.save(currentTemplate);
			templateRepository.save(newTemp);
		} catch (Exception e) {
			throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Database save failed. " + e.getMessage());
		}

	}

}
