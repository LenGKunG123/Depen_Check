package isc.etax.taxfiling.services;

import isc.etax.taxfiling.DTOS.DocumentNumberDTO;
import isc.etax.taxfiling.entities.Config;
import isc.etax.taxfiling.entities.CurrentDocument;
import isc.etax.taxfiling.repositories.CurrentDocumentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;

@Service
public class CurrentDocumentService {

    @Autowired
    private CurrentDocumentRepository currentDocumentRepository;

    public List<CurrentDocument> findAll() {
        return currentDocumentRepository.findAll();
    }

    public CurrentDocument getCurrentByConfigId(Long configId) {
        return currentDocumentRepository.findCurrentDocumentByConfigConfigId(configId);
    }

    public String generateString(String format, int sequence, int padding, boolean isBuddhist) {
        LocalDate currentDate = LocalDate.now();
        int yearValue = currentDate.getYear();
        String year = isBuddhist ? String.valueOf(yearValue + 543) : String.valueOf(yearValue);
        String month = currentDate.format(DateTimeFormatter.ofPattern("MM"));
        String day = currentDate.format(DateTimeFormatter.ofPattern("dd"));

        String paddedSeq = String.format("%0" + padding + "d", sequence);

        return format.replace("{YYYY}", year)
        		.replace("{yyyy}", year)
                .replace("{MM}", month)
                .replace("{dd}", day)
                .replace("{SEQ}", paddedSeq);
    }

    public DocumentNumberDTO newDocumentNumber(Config config, boolean isBuddhist) {
        CurrentDocument currentDocument = currentDocumentRepository.findCurrentDocumentByConfigConfigId(config.getConfigId());

        if (currentDocument == null || currentDocument.getConfig() == null || currentDocument.getConfig().getDocumentFormat() == null) {
            throw new IllegalArgumentException("Invalid configuration or document format.");
        }
        int currentDocNumber = currentDocument.getDocumentNumber() + 1;
        currentDocument.setDocumentNumber(currentDocNumber);
        
        
        String formattedAttachNumber = generateString(
                currentDocument.getConfig().getBookFormat(),
                1,
                1,
                isBuddhist
        );

        String formattedSerialNumber = generateString(
                currentDocument.getConfig().getDocumentFormat(),
                currentDocNumber,
                currentDocument.getConfig().getSequencePadding(),
                isBuddhist
        );

        


        currentDocumentRepository.save(currentDocument);
        DocumentNumberDTO documentNumberDTO = new DocumentNumberDTO(formattedAttachNumber, formattedSerialNumber, "1");
        
        return documentNumberDTO;
    }
}
