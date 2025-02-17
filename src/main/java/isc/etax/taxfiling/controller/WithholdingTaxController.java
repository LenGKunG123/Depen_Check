package isc.etax.taxfiling.controller;

import isc.etax.taxfiling.DTOS.DocumentNumberDTO;
import isc.etax.taxfiling.DTOS.WithholdingTaxDTO;
import isc.etax.taxfiling.entities.Config;
import isc.etax.taxfiling.entities.Profile;
import isc.etax.taxfiling.services.ConfigService;
import isc.etax.taxfiling.services.CurrentDocumentService;
import isc.etax.taxfiling.services.ProfileService;
import isc.etax.taxfiling.services.WithholdingTaxService;
import net.sf.jasperreports.engine.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.Base64;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@RestController
@RequestMapping("/api/wht")
public class WithholdingTaxController {
    
    @Autowired
    private WithholdingTaxService withholdingTaxService;
    
    @GetMapping("/{serialNumber}")
    public ResponseEntity<byte[]> getWht (
    		@PathVariable String serialNumber,
    		@RequestParam(required = false) String nid
    		) {
    	System.out.println(serialNumber);
    	System.out.println(nid);
    	
    	
    	
    	
//        return ResponseEntity.ok(base64Pdf);
        return ResponseEntity.ok()
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=" + "documentNumberDTO_getSerialNumber()" + ".pdf")
                .contentType(MediaType.APPLICATION_PDF)
                .body(withholdingTaxService.getWhtBySerialNumberAndNid(serialNumber, nid));
    }


    @PostMapping("")
//	public ResponseEntity<String> newWithholdingTax(
    public ResponseEntity<Object> newWithholdingTax(
//	public ResponseEntity<Object> newWithholdingTax(
    		@RequestHeader(HttpHeaders.AUTHORIZATION) String token,
            @RequestBody WithholdingTaxDTO taxCertificateDTO,
            @RequestParam(value = "template", required = false) Long configId
    ) {
    	if (token == null) {
    		return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
    	}

//        try {
        	
//        	String base64Pdf = withholdingTaxService.newWithholdingTax(taxCertificateDTO, configId);
        	byte[] pdfData = withholdingTaxService.newWithholdingTax(taxCertificateDTO);
//        	Object pdfData = withholdingTaxService.newWithholdingTax(taxCertificateDTO, configId);


//	        return ResponseEntity.ok(base64Pdf);
            return ResponseEntity.ok()
                    .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=" + "documentNumberDTO_getSerialNumber()" + ".pdf")
                    .contentType(MediaType.APPLICATION_PDF)
                    .body(pdfData);
//        	return ResponseEntity.ok()
//	          .body(pdfData);
//        } catch (Exception e) {
//            e.printStackTrace();
//            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
//        }
    }
}
