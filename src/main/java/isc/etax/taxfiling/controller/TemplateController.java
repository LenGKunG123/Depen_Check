package isc.etax.taxfiling.controller;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.server.ResponseStatusException;

import isc.etax.taxfiling.entities.Config;
import isc.etax.taxfiling.entities.Profile;
import isc.etax.taxfiling.entities.Template;
import isc.etax.taxfiling.services.ProfileService;
import isc.etax.taxfiling.services.TemplateService;
import isc.etax.taxfiling.utils.NanoUtil;

@RestController
@RequestMapping("/api/templates")
public class TemplateController {
	
	@Autowired
	private TemplateService templateservice;
	
	@Autowired
	private ProfileService profileService;
	
	@Autowired
	private NanoUtil nanoUtil;
	
	
	@PostMapping(path = "" ,consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
	public ResponseEntity<Object> uploadTemplate(
			@RequestParam String nid,
			@RequestParam(required = false) String branchNo,
			@RequestPart(value = "data", required = false) Template template,
			@RequestPart(value = "template", required = false) MultipartFile file
			) {
	
		Optional<Profile> profile = profileService.getProfileByTaxId13AndBranchNo(nid, branchNo);
		if (!profile.isPresent()) {
			throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Company profile not found");
		}
		
//		System.out.println(template.getTemplateCode());
		
		templateservice.newTemplate(file, profile.get(), template);
		
//		String nano = nanoUtil.nanoIdGenerate(4);
//		String nano = nanoUtil.nanoIdGenerate(4) + "_" + System.currentTimeMillis();
		
		return ResponseEntity.created(null).build(); 
	}
	
	@PutMapping(path = "" ,consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
	public ResponseEntity<Object> editTemplate(
			@RequestParam String nid,
			@RequestParam(required = false) String branchNo,
			@RequestPart(value = "data", required = false) Template template,
			@RequestPart(value = "template", required = false) MultipartFile file
			) {
		Optional<Profile> profile = profileService.getProfileByTaxId13AndBranchNo(nid, branchNo);
		if (!profile.isPresent()) {
			throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Company profile not found");
		}
		
		templateservice.updateTemplate(file, profile.get(), template);
		
		
		return ResponseEntity.ok().build();
	}
	
	@GetMapping("")
	public ResponseEntity<Object> getAllTemplate(){
		return ResponseEntity.ok(templateservice.getAllTemplateDTOs());
		
	}

}
