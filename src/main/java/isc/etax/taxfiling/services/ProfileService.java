package isc.etax.taxfiling.services;

import isc.etax.taxfiling.entities.Config;
import isc.etax.taxfiling.entities.Profile;
import isc.etax.taxfiling.repositories.ConfigRepository;
import isc.etax.taxfiling.repositories.ProfileRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class ProfileService {
	
	@Autowired
    private ProfileRepository profileRepository;
	
	@Autowired
    private ConfigRepository configRepository;
	

	public List<Profile> getAllProfiles(){
		return profileRepository.findAll();
	}
	
	public List<Config> getAllConfigs(){
		return configRepository.findAll();
	}


	public Optional<Profile> getProfileByTaxId13(String tax13){
		return profileRepository.findProfileByTaxId13(tax13);
	}
	
	
	public Optional<Profile> getProfileByTaxId13AndBranchNo(String tax13, String branchNo){
		if (branchNo == null) {
			branchNo = "00000";
		}
		return profileRepository.findProfileByTaxId13AndBranchNo(tax13, branchNo);
	}

	public boolean newProfile(Profile profile){
		
		boolean isExist = profileRepository.existsByTaxId13AndBranchNo(profile.getTaxId13(), profile.getBranchNo());
		if (isExist) {
			throw new ResponseStatusException(HttpStatus.CONFLICT, "Company profile is already exist.");
		}
		
		try {
//			Profile newProfile = new Profile();
//			newProfile.setTaxId13(profile.getTaxId13());
//			newProfile.setTaxId10(profile.getTaxId10());
//			newProfile.setCompTitle(profile.getCompTitle());
//			newProfile.setCompName(profile.getCompName());
//			newProfile.setBranchNo(profile.getBranchNo());
//			newProfile.setBranchName(profile.getBranchName());
//			newProfile.setAddress(profile.getAddress());
//			newProfile.setConfig(null);
//			if (profile.getConfig() != null) {
//				
//				
//			}
			profileRepository.save(profile);
			return true;
		}
		catch (Exception e){
//			System.out.println(e);
			return false;
		}

	}

}
