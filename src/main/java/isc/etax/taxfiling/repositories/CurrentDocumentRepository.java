package isc.etax.taxfiling.repositories;

import isc.etax.taxfiling.entities.CurrentDocument;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CurrentDocumentRepository extends JpaRepository<CurrentDocument, Long> {

    CurrentDocument findCurrentDocumentByConfigConfigId(Long configId);
}
