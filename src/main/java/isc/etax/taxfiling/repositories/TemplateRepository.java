package isc.etax.taxfiling.repositories;

import isc.etax.taxfiling.entities.Template;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface TemplateRepository extends JpaRepository<Template, Long> {

    Optional<Template> findByCompanyProfile_ProfileIdAndStatus(Long companyProfile, String status);
    Optional<Template> findTemplatesByTemplateCode(String templateCode);




}
