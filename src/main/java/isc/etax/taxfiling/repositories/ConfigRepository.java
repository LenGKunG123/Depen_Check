package isc.etax.taxfiling.repositories;

import isc.etax.taxfiling.entities.Config;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ConfigRepository extends JpaRepository<Config, Long>  {

//    Optional<Config> findConfigByCompanyProfileAndStatusEqualsIgnoreCase(Long companyProfile, String status);
    Optional<Config> findByCompanyProfile_ProfileIdAndStatus(Long companyId, String status);

    boolean existsByCompanyProfile_ProfileIdAndStatus(Long companyId, String status);

}
