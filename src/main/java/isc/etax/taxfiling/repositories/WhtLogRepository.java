package isc.etax.taxfiling.repositories;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import isc.etax.taxfiling.entities.WhtLog;

public interface WhtLogRepository extends JpaRepository<WhtLog, Long>{
	
	Optional<WhtLog> findWhtLogBySerialNumberAndNid(String serialNumber, String nid);

	
	

}
