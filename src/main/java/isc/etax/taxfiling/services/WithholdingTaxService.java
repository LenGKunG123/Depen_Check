package isc.etax.taxfiling.services;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.lang.reflect.Field;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.Base64;
import java.util.HashMap;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import isc.etax.taxfiling.DTOS.DocumentNumberDTO;
import isc.etax.taxfiling.DTOS.RowFieldDTO;
import isc.etax.taxfiling.DTOS.WithholdingTaxDTO;
import isc.etax.taxfiling.entities.Address;
import isc.etax.taxfiling.entities.Config;
import isc.etax.taxfiling.entities.Profile;
import isc.etax.taxfiling.entities.WhtLog;
import isc.etax.taxfiling.utils.DateUtil;
import isc.etax.taxfiling.utils.StringUtil;
import net.sf.jasperreports.engine.JREmptyDataSource;
import net.sf.jasperreports.engine.JasperCompileManager;
import net.sf.jasperreports.engine.JasperExportManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperReport;

@Service
@Transactional
public class WithholdingTaxService {

	@Autowired
	private CurrentDocumentService currentDocumentService;

	@Autowired
	private ProfileService profileService;

	@Autowired
	private ConfigService configService;
	
	@Autowired
	private StringUtil stringUtils;
	
	@Autowired
	private DateUtil dateUtils;
	
	@Autowired
	private LogService logService;
	
//	private Double getFieldValue(RowFieldDTO dto, String fieldName) {
//        try {
//            Field field = RowFieldDTO.class.getDeclaredField(fieldName);
//            field.setAccessible(true);
//            return (Double) field.get(dto);
//        } catch (NoSuchFieldException | IllegalAccessException e) {
//            e.printStackTrace();
//            return 0.0; // Default to 0 if field is not found
//        }
//    }
//    public Double calculateTotalAmount(WithholdingTaxDTO taxDto) {
//        List<RowFieldDTO> incTypes = Arrays.asList(
//        	taxDto.incType1, taxDto.incType2, incType3, incType4a, 
//            incType4b11, incType4b12, incType4b13, incType4b14, 
//            incType4b21, incType4b22, incType4b23, incType4b24, incType4b25, 
//            incType5, incType6
//        );
//
//        return incTypes.stream()
//            .map(dto -> getFieldValue(dto, fieldName))
//            .filter(value -> value != null) // Avoid NullPointerException
//            .reduce(0.0, Double::sum);
//    }

//	public String newWithholdingTax(
	public byte[] newWithholdingTax(
//	public Object newWithholdingTax(
			WithholdingTaxDTO taxCertificateDTO) {
		
		System.out.println("---------------");
		System.out.println(taxCertificateDTO.getSignDate());
		
		if (taxCertificateDTO.getPaidAmt() == null) {
			taxCertificateDTO.updatePaidAmount();
		}
		if (taxCertificateDTO.getTaxAmt() == null) {
			taxCertificateDTO.updateTaxAmount();
		}
		
		boolean isNew = false;
		boolean isBuddhist = false;
		Profile holderProfile;
		Optional<Profile> optProfile = profileService.getProfileByTaxId13AndBranchNo(taxCertificateDTO.getNid(), taxCertificateDTO.getBranchNo());
		if (!optProfile.isPresent()) {
			throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Company profile not found.");
		} else {
			holderProfile = optProfile.get();
		}
		if (taxCertificateDTO.getSignDate() == null ) {
			taxCertificateDTO.setSignDate(LocalDate.now());
			
		}
		Config tamplateConfig = configService.getCurrentConfig(holderProfile.getProfileId());
		
		if (tamplateConfig == null) {
			throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Template config not found.");
		}
		
		try {
			JasperReport compileReport = JasperCompileManager
					.compileReport(new FileInputStream("src/main/resources/templates/Withholding_Tax.jrxml"));
			
			
			isBuddhist = tamplateConfig.getCalendarType().toString().equals("BUDDHIST");
			if (taxCertificateDTO.getDocumentNumberDTO() == null ) {
				DocumentNumberDTO documentNumberDTO = currentDocumentService.newDocumentNumber(tamplateConfig, isBuddhist);
				taxCertificateDTO.setDocumentNumberDTO(documentNumberDTO);
				isNew = true;
			}

			Address holderAddress =holderProfile.getAddress(); 

			HashMap<String, Object> parameters = new HashMap<>();
			parameters.put("holderName", holderProfile.getCompTitle() + " " + holderProfile.getCompName()
							+ stringUtils.getBranchName(holderProfile));
			parameters.put("holderAddress", stringUtils.addressConcat(holderAddress));
			parameters.put("nid", holderProfile.getTaxId13());
			parameters.put("recipientName",
					taxCertificateDTO.getTitleName() + taxCertificateDTO.getFirstName() + " "
							+ Optional.ofNullable(taxCertificateDTO.getMiddleName()).orElse("")
							+ taxCertificateDTO.getSurName());
			parameters.put("recipientAddress", stringUtils.addressConcat(taxCertificateDTO.getAddress()));
			parameters.put("pin", taxCertificateDTO.getPin());
			parameters.put("tin", taxCertificateDTO.getTin());
			parameters.put("taxType", taxCertificateDTO.getTaxType());
			parameters.put("attachNumber", Optional.ofNullable(taxCertificateDTO.getDocumentNumberDTO().getAttachNumber()).orElse(""));
			parameters.put("serialNumber", taxCertificateDTO.getDocumentNumberDTO().getSerialNumber());
			parameters.put("sequenceNumber", taxCertificateDTO.getDocumentNumberDTO().getSequenceNumber());
			
			parameters.put("incType1Date", stringUtils.safeString(dateUtils.convertStringToLocalDate(taxCertificateDTO.getIncType1().getDate(), isBuddhist)));
            parameters.put("incType1Paid", stringUtils.formatNumber(taxCertificateDTO.getIncType1().getPayAmount()));
            parameters.put("incType1Tax", stringUtils.formatNumber(taxCertificateDTO.getIncType1().getWithheldAmount()));
			
            parameters.put("incType2Date", stringUtils.safeString(dateUtils.convertStringToLocalDate(taxCertificateDTO.getIncType2().getDate(), isBuddhist)));
            parameters.put("incType2Paid", stringUtils.formatNumber(taxCertificateDTO.getIncType2().getPayAmount()));
            parameters.put("incType2Tax", stringUtils.formatNumber(taxCertificateDTO.getIncType2().getWithheldAmount()));
            
            parameters.put("incType3Date", stringUtils.safeString(dateUtils.convertStringToLocalDate(taxCertificateDTO.getIncType3().getDate(), isBuddhist)));
            parameters.put("incType3Paid", stringUtils.formatNumber(taxCertificateDTO.getIncType3().getPayAmount()));
            parameters.put("incType3Tax", stringUtils.formatNumber(taxCertificateDTO.getIncType3().getWithheldAmount()));
			
            parameters.put("incType4ADate", stringUtils.safeString(dateUtils.convertStringToLocalDate(taxCertificateDTO.getIncType4a().getDate(), isBuddhist)));
            parameters.put("incType4APaid", stringUtils.formatNumber(taxCertificateDTO.getIncType4a().getPayAmount()));
            parameters.put("incType4ATax", stringUtils.formatNumber(taxCertificateDTO.getIncType4a().getWithheldAmount()));
            
            parameters.put("incType4B11Date", stringUtils.safeString(dateUtils.convertStringToLocalDate(taxCertificateDTO.getIncType4b11().getDate(), isBuddhist)));
            parameters.put("incType4B11Paid", stringUtils.formatNumber(taxCertificateDTO.getIncType4b11().getPayAmount()));
            parameters.put("incType4B11Tax", stringUtils.formatNumber(taxCertificateDTO.getIncType4b11().getWithheldAmount()));
            
            parameters.put("incType4B12Date", stringUtils.safeString(dateUtils.convertStringToLocalDate(taxCertificateDTO.getIncType4b12().getDate(), isBuddhist)));
            parameters.put("incType4B12Paid", stringUtils.formatNumber(taxCertificateDTO.getIncType4b12().getPayAmount()));
            parameters.put("incType4B12Tax", stringUtils.formatNumber(taxCertificateDTO.getIncType4b12().getWithheldAmount()));

            parameters.put("incType4B13Date", stringUtils.safeString(dateUtils.convertStringToLocalDate(taxCertificateDTO.getIncType4b13().getDate(), isBuddhist)));
            parameters.put("incType4B13Paid", stringUtils.formatNumber(taxCertificateDTO.getIncType4b13().getPayAmount()));
            parameters.put("incType4B13Tax", stringUtils.formatNumber(taxCertificateDTO.getIncType4b13().getWithheldAmount()));
            
            parameters.put("incType4B14Date", stringUtils.safeString(dateUtils.convertStringToLocalDate(taxCertificateDTO.getIncType4b14().getDate(), isBuddhist)));
            parameters.put("incType4B14Paid", stringUtils.formatNumber(taxCertificateDTO.getIncType4b14().getPayAmount()));
            parameters.put("incType4B14Tax", stringUtils.formatNumber(taxCertificateDTO.getIncType4b14().getWithheldAmount()));
            parameters.put("incType4B14Detail", stringUtils.safeString(taxCertificateDTO.getIncType4b14().getDetail()));
            
            parameters.put("incType4B21Date", stringUtils.safeString(dateUtils.convertStringToLocalDate(taxCertificateDTO.getIncType4b21().getDate(), isBuddhist)));
            parameters.put("incType4B21Paid", stringUtils.formatNumber(taxCertificateDTO.getIncType4b21().getPayAmount()));
            parameters.put("incType4B21Tax", stringUtils.formatNumber(taxCertificateDTO.getIncType4b21().getWithheldAmount()));
            
            parameters.put("incType4B22Date", stringUtils.safeString(dateUtils.convertStringToLocalDate(taxCertificateDTO.getIncType4b22().getDate(), isBuddhist)));
            parameters.put("incType4B22Paid", stringUtils.formatNumber(taxCertificateDTO.getIncType4b22().getPayAmount()));
            parameters.put("incType4B22Tax", stringUtils.formatNumber(taxCertificateDTO.getIncType4b22().getWithheldAmount()));
            
            parameters.put("incType4B23Date", stringUtils.safeString(dateUtils.convertStringToLocalDate(taxCertificateDTO.getIncType4b23().getDate(), isBuddhist)));
            parameters.put("incType4B23Paid", stringUtils.formatNumber(taxCertificateDTO.getIncType4b23().getPayAmount()));
            parameters.put("incType4B23Tax", stringUtils.formatNumber(taxCertificateDTO.getIncType4b23().getWithheldAmount()));

            parameters.put("incType4B24Date", stringUtils.safeString(dateUtils.convertStringToLocalDate(taxCertificateDTO.getIncType4b24().getDate(), isBuddhist)));
            parameters.put("incType4B24Paid", stringUtils.formatNumber(taxCertificateDTO.getIncType4b24().getPayAmount()));
            parameters.put("incType4B24Tax", stringUtils.formatNumber(taxCertificateDTO.getIncType4b24().getWithheldAmount()));
            
            parameters.put("incType4B25Date", stringUtils.safeString(dateUtils.convertStringToLocalDate(taxCertificateDTO.getIncType4b25().getDate(), isBuddhist)));
            parameters.put("incType4B25Paid", stringUtils.formatNumber(taxCertificateDTO.getIncType4b25().getPayAmount()));
            parameters.put("incType4B25Tax", stringUtils.formatNumber(taxCertificateDTO.getIncType4b25().getWithheldAmount()));
            parameters.put("incType4B25Detail", stringUtils.safeString(taxCertificateDTO.getIncType4b25().getDetail()));
            
            parameters.put("incType5Date", stringUtils.safeString(dateUtils.convertStringToLocalDate(taxCertificateDTO.getIncType5().getDate(), isBuddhist)));
            parameters.put("incType5Paid", stringUtils.formatNumber(taxCertificateDTO.getIncType5().getPayAmount()));
            parameters.put("incType5Tax", stringUtils.formatNumber(taxCertificateDTO.getIncType5().getWithheldAmount()));

            parameters.put("incType6Date", stringUtils.safeString(dateUtils.convertStringToLocalDate(taxCertificateDTO.getIncType6().getDate(), isBuddhist)));
            parameters.put("incType6Paid", stringUtils.formatNumber(taxCertificateDTO.getIncType6().getPayAmount()));
            parameters.put("incType6Tax", stringUtils.formatNumber(taxCertificateDTO.getIncType6().getWithheldAmount()));
            parameters.put("incType6Detail", stringUtils.safeString(taxCertificateDTO.getIncType6().getDetail()));
            
            parameters.put("paidAmt", stringUtils.formatNumber(taxCertificateDTO.getPaidAmt()));
            parameters.put("taxAmt", stringUtils.formatNumber(taxCertificateDTO.getTaxAmt()));
            parameters.put("taxAmtAlphabet", stringUtils.convertToThaiWords(taxCertificateDTO.getTaxAmt()));
            
//            System.out.println("------------------------");
//            System.out.println(stringUtils.formatNumber(taxCertificateDTO.getPaidAmt()));
//            System.out.println(stringUtils.formatNumber(taxCertificateDTO.calculateSum("payAmount")));
//            System.out.println("------------------------");

            parameters.put("pensionFund", stringUtils.formatNumber(taxCertificateDTO.getPensionFund()));
            parameters.put("socialSecurityFund", stringUtils.formatNumber(taxCertificateDTO.getSocialSecurityFund()));
            parameters.put("lifeSupportFund", stringUtils.formatNumber(taxCertificateDTO.getLifeSupportFund()));
            
			

			parameters.put("payCon", taxCertificateDTO.getPayCon());
			parameters.put("payConDetail", stringUtils.safeString(taxCertificateDTO.getPayConDetail()));
			
			parameters.put("date", dateUtils.formatDatePart(taxCertificateDTO.getSignDate(), "dd", false));
			parameters.put("month", dateUtils.formatDatePart(taxCertificateDTO.getSignDate(), "MM", false));
			parameters.put("year", dateUtils.formatDatePart(taxCertificateDTO.getSignDate(), "YYYY", isBuddhist));
//            parameters.put("signature", new FileInputStream("src/main/resources/images/mocSignature.png"));
//            parameters.put("compSeal", new FileInputStream("src/main/resources/images/TN16_v2_blue.png"));
			try {
				parameters.put("signature",
						new FileInputStream("uploads/signatures/" + tamplateConfig.getSignaturePathName()));
			} catch (FileNotFoundException e) {
				System.err
						.println("File not found: uploads/signatures/" + tamplateConfig.getSignaturePathName());
				parameters.put("signature", null);
			}
			try {
				parameters.put("compSeal", new FileInputStream("src/main/resources/images/TN16_v2_blue.png"));
			} catch (FileNotFoundException e) {
				System.err.println("File not found: src/main/resources/images/TN16_v2_blue.png");
				parameters.put("compSeal", null);
			}
			
			if (isNew) {
				boolean isLogSave = logService.newWhtLog(new WhtLog(taxCertificateDTO));
				if (!isLogSave) {
					return null;
				}
			}
			
			

			JasperPrint report = JasperFillManager.fillReport(compileReport, parameters, new JREmptyDataSource());

			byte[] pdfData = JasperExportManager.exportReportToPdf(report);

			String base64Pdf = Base64.getEncoder().withoutPadding().encodeToString(pdfData);

//	        return base64Pdf;
			return pdfData;
//			return taxCertificateDTO;
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
		
		
	}
	
//		public String newWithholdingTax(
			public byte[] getWhtBySerialNumberAndNid(
					String serialNumber, String nid) {
				
				WhtLog whtLog;
				Optional<WhtLog> optionalWhtLog = logService.findBySerialNumberAndNid(serialNumber, nid);
				if (!optionalWhtLog.isPresent()) {
					throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Document not found");
		        }
				
				
				WithholdingTaxDTO withholdingTaxDTO = new WithholdingTaxDTO(optionalWhtLog.get());
				return newWithholdingTax(withholdingTaxDTO);
			}

}
