package isc.etax.taxfiling.controller;

import isc.etax.taxfiling.entities.Profile;
import isc.etax.taxfiling.services.ProfileService;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequestMapping("/api/profiles")
public class CompProfileController {

    @Autowired
    private ProfileService profileService;

    @PostMapping("")
    public ResponseEntity<Object> createProfile(@RequestBody Profile profile) {
    	
    	System.out.println(profile.getCompName());
    	System.out.println(profile.getTaxId13());
//    	System.out.println(profile.getConfig());
    	
        boolean isSave = profileService.newProfile(profile);
        if (!isSave) {
        	return ResponseEntity.internalServerError().build();
        }
        return ResponseEntity.ok().build();
    }
    
    @GetMapping("")
    public ResponseEntity<Object> getAllProfile() {
    	
        List<Profile> profiles = profileService.getAllProfiles();

        return ResponseEntity.ok(profiles);
    }
    
    @GetMapping("/{taxId13}")
    public ResponseEntity<Object> getProfile(@PathVariable String taxId13) {
    	
        Optional<Profile> profile = profileService.getProfileByTaxId13(taxId13);
        if (!profile.isPresent()) {
        	return ResponseEntity.notFound().build();
        	
        }
        return ResponseEntity.ok(profile);
    }
}
