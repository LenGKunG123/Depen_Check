package isc.etax.taxfiling.services;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import isc.etax.taxfiling.entities.WhtLog;
import isc.etax.taxfiling.repositories.WhtLogRepository;

@Service
public class LogService {
	
	@Autowired
	private WhtLogRepository whtLogRepository;
	
	public boolean newWhtLog(WhtLog newWhtLog) {
		try {
			whtLogRepository.save(newWhtLog);
			return true;
		} catch (Exception e) {
			System.out.println(e);
			return false;
		}		
		
	}
	
	public Optional<WhtLog> findBySerialNumberAndNid (String serialNumber, String nid) {
		return whtLogRepository.findWhtLogBySerialNumberAndNid(serialNumber, nid);
	}
	
	

}
