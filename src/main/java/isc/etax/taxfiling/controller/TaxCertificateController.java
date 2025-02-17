package isc.etax.taxfiling.controller;

import isc.etax.taxfiling.services.CurrentDocumentService;
import isc.etax.taxfiling.services.ProfileService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/tax-certificate")
public class TaxCertificateController {

	@Autowired
	private CurrentDocumentService currentDocumentService;

	@Autowired
	private ProfileService profileService;
	
	@GetMapping("/b")
	public ResponseEntity<String> getTaxCertificate(){
		System.out.println("-------------------------");
		return ResponseEntity.ok("helloooooooooo");
		
	}
	
//	@PostMapping("")
//	public String generatePdf(@RequestBody TaxCertificateDTO taxCertificateDTO) throws Exception , JRException {
//		JRBeanCollectionDataSource beenCollectionDataSource = new JRBeanCollectionDataSource((Collection<?>) taxCertificateDTO);
//		JasperReport compileReport = JasperCompileManager.compileReport(new FileInputStream("src/main/resources/templates/Withholding_Tax.jrxml"));
//		
//		HashMap<String, Object> map = new HashMap <>();
//		JasperPrint report = JasperFillManager.fillReport(compileReport, null, beenCollectionDataSource);
//		JasperExportManager.exportReportToPdf(report);
//				
//		return "generated";
//	}
	
//	@PostMapping("/tax")
////	public ResponseEntity<String> generatePdf(@RequestBody TaxCertificateDTO taxCertificateDTO) {
//	public ResponseEntity<byte[]> generatePdf(@RequestBody TaxCertificateDTO taxCertificateDTO) {
//		System.out.println("-------------------------");
//	    try {
//
////	        JRBeanCollectionDataSource beanCollectionDataSource = new JRBeanCollectionDataSource(taxCertificateDTO.getClass());
//
//	        JasperReport compileReport = JasperCompileManager.compileReport(new FileInputStream("src/main/resources/templates/Withholding_Tax.jrxml"));
//			DocumentNumberDTO documentNumberDTO = currentDocumentService.newDocumentNumber(1l);
//			System.out.println(documentNumberDTO.getDocumentNumber());
//
//			Profile holderProfile = profileService.getProfileByTaxId13(taxCertificateDTO.getHolderTaxId13());
//
//	        HashMap<String, Object> parameters= new HashMap<>();
//	        parameters.put("HolderName", holderProfile.getCompName());
//	        parameters.put("HolderAddress", holderProfile.getAddress());
//	        parameters.put("RecipientName", taxCertificateDTO.getRecipientName());
//	        parameters.put("RecipientAddress", taxCertificateDTO.getRecipientAddress());
//			parameters.put("payerTaxId13", taxCertificateDTO.getPayerTaxId13());
//			parameters.put("taxForms", taxCertificateDTO.getTaxForms());
//			parameters.put("bookNumber", Optional.ofNullable(documentNumberDTO.getDocumentNumber()).orElse(""));
//			parameters.put("documentNumber", documentNumberDTO.getDocumentNumber());
//
//			try {
//				parameters.put("signature", new FileInputStream("src/main/resources/images/mocSignature.png"));
//			} catch (FileNotFoundException e) {
//				System.err.println("File not found: src/main/resources/images/mocSignature.png");
//				parameters.put("signature", null);
//			}
//			try {
//				parameters.put("compSeal", new FileInputStream("src/main/resources/images/TN16_v2_blue.png"));
//			} catch (FileNotFoundException e) {
//				System.err.println("File not found: src/main/resources/images/TN16_v2_blue.png");
//				parameters.put("compSeal", null);
//			}
//
//
//
//	        JasperPrint report = JasperFillManager.fillReport(compileReport, parameters, new JREmptyDataSource());
//
//
//	        byte[] pdfData = JasperExportManager.exportReportToPdf(report);
//
//	        String base64Pdf = Base64.getEncoder().withoutPadding().encodeToString(pdfData);
//
////	        return ResponseEntity.ok(base64Pdf);
//	        return ResponseEntity.ok()
//	                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=WHT.pdf")
//	                .contentType(MediaType.APPLICATION_PDF)
//	                .body(pdfData);
//	    } catch (Exception e) {
//	        e.printStackTrace();
//	        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
//	    }
//	}
	
	
	
	
	
	
	
	
	
	
	
	
	

}
