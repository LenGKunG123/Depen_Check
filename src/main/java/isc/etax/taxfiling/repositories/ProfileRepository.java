package isc.etax.taxfiling.repositories;

import isc.etax.taxfiling.entities.Profile;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ProfileRepository extends JpaRepository<Profile, Long>  {

    Optional<Profile> findProfileByTaxId13 (String taxId13);
    Optional<Profile> findProfileByTaxId13AndBranchNo (String taxId13, String branchOn);
    boolean existsByTaxId13AndBranchNo (String taxId13, String branchOn);
    
    


}
