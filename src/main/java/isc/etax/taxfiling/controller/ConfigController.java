package isc.etax.taxfiling.controller;

import isc.etax.taxfiling.DTOS.ConfigRequestDTO;
import isc.etax.taxfiling.entities.Config;
import isc.etax.taxfiling.entities.Profile;
import isc.etax.taxfiling.services.ConfigService;
import isc.etax.taxfiling.services.ProfileService;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.server.ResponseStatusException;

@Controller
@RequestMapping("/api/configs")
public class ConfigController {
	
	@Autowired
	private ProfileService profileService;
	
	@Autowired
	private ConfigService configService;
	
	
    @PostMapping(path = "" ,consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<Object> newConfig(
    		@RequestParam String nid,
			@RequestParam(required = false) String branchNo,
            @RequestPart("config") ConfigRequestDTO config,
            @RequestPart(value = "signature", required = false) MultipartFile file
    ){
    	Optional<Profile> profile = profileService.getProfileByTaxId13AndBranchNo(nid, branchNo);
		if (!profile.isPresent()) {
			throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Company profile not found");
		}
		
		configService.newConfig(file, profile.get(), config);

        return ResponseEntity.created(null).build();
    }
    
    @PutMapping(path = "" ,consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<Object> editConfig(
    		@RequestParam String nid,
			@RequestParam(required = false) String branchNo,
            @RequestPart("config") ConfigRequestDTO config,
            @RequestPart(value = "signature", required = false) MultipartFile file
    ){
    	Optional<Profile> profile = profileService.getProfileByTaxId13AndBranchNo(nid, branchNo);
		if (!profile.isPresent()) {
			throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Company profile not found");
		}
		
		configService.newConfig(file, profile.get(), config);

        return ResponseEntity.created(null).build();
    }


}
