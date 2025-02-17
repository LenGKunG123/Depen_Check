package isc.etax.taxfiling.DTOS;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.FileInputStream;
import java.lang.reflect.Field;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.Objects;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonInclude;

import isc.etax.taxfiling.entities.Address;
import isc.etax.taxfiling.entities.WhtLog;
import jakarta.annotation.PostConstruct;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@JsonInclude()
public class WithholdingTaxDTO {

    private DocumentNumberDTO documentNumberDTO;
    private String senderName;
    private String senderAddress;
    private String nid; //sender tax ID 13 digits
    private String branchNo;
    
    
    private String titleName;
    private String firstName;
    private String middleName;
    private String surName;
    private Address address;
    
    private String pin; // recipient tax ID 13 digits
    private String tin; // recipient tax ID 10 digits
    private String taxType;
    
    private RowFieldDTO incType1 = new RowFieldDTO();
    private RowFieldDTO incType2 = new RowFieldDTO();
    private RowFieldDTO incType3 = new RowFieldDTO();
    private RowFieldDTO incType4a = new RowFieldDTO();
    private RowFieldDTO incType4b11 = new RowFieldDTO();
    private RowFieldDTO incType4b12 = new RowFieldDTO();
    private RowFieldDTO incType4b13 = new RowFieldDTO();
    private RowFieldDTO incType4b14 = new RowFieldDTO();
    private RowFieldDTO incType4b21 = new RowFieldDTO();
    private RowFieldDTO incType4b22 = new RowFieldDTO();
    private RowFieldDTO incType4b23 = new RowFieldDTO();
    private RowFieldDTO incType4b24 = new RowFieldDTO();
    private RowFieldDTO incType4b25 = new RowFieldDTO();
    private RowFieldDTO incType5 = new RowFieldDTO();
    private RowFieldDTO incType6 = new RowFieldDTO();

    private Double paidAmt;
    private Double taxAmt;
    private String taxAmtAlphabet;
    private Double pensionFund;
    private Double socialSecurityFund;
    private Double lifeSupportFund;
    private String payCon;
    private String payConDetail;
    
    private String date;
    private String month;
    private String year;
    
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd-MM-yyyy")
    private LocalDate signDate = LocalDate.now();;
//    private FileInputStream signature;
//    private FileInputStream compSeal;
    
    
    public Double calculateSum(String fieldName) {
        double sum = 0.0;
        try {
            // Get all declared fields in the class
            Field[] fields = this.getClass().getDeclaredFields();
            for (Field field : fields) {
                if (field.getType().equals(RowFieldDTO.class)) { // Check if the field is a RowFieldDTO
                    field.setAccessible(true);
                    RowFieldDTO rowField = (RowFieldDTO) field.get(this);
                    if (rowField != null) {
                        Field targetField = RowFieldDTO.class.getDeclaredField(fieldName);
                        targetField.setAccessible(true);
                        Double value = (Double) targetField.get(rowField);
                        if (value != null) {
                            sum += value;
                        }
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return sum;
    }
    
    public void updatePaidAmount() {
        this.paidAmt = calculateSum("payAmount");
    }
    public void updateTaxAmount() {
    	this.taxAmt = calculateSum("withheldAmount");
    }
    
    
    
    public WithholdingTaxDTO(WhtLog whtLog) {
        this.nid = whtLog.getNid();
        this.firstName = whtLog.getRecipientFname();
        
        if (whtLog.getRecipientSname() != null && !whtLog.getRecipientSname().isEmpty()) {
            String[] nameParts = whtLog.getRecipientSname().split(" ", 2);
            this.middleName = nameParts.length > 1 ? nameParts[0] : "";
            this.surName = nameParts.length > 1 ? nameParts[1] : nameParts[0];
        }
        
        this.pin = whtLog.getPin();
        this.tin = whtLog.getTin();
        this.taxType = whtLog.getTaxType();
        this.taxAmtAlphabet = whtLog.getTaxAmtAlphabet();
        this.pensionFund = whtLog.getPensionFund();
        this.socialSecurityFund = whtLog.getSocialSecurityFund();
        this.lifeSupportFund = whtLog.getLifeSupportFund();
        this.payCon = whtLog.getPayCon();
        this.payConDetail = whtLog.getPayConDetail();
        
    	this.paidAmt = whtLog.getPaidAmt();
    	this.taxAmt = whtLog.getTaxAmt();
    	
    	if (whtLog.getSignDate() != null) {
    		this.signDate = whtLog.getSignDate();
    	} else {
    		this.signDate = whtLog.getGeneratedDate().atZone(ZoneId.systemDefault()).toLocalDate();;
    		
    	}
        
        
        
        if (whtLog.getAttachNumber() != null || whtLog.getSerialNumber() != null || whtLog.getSequenceNumber() != null) {
            this.documentNumberDTO = new DocumentNumberDTO();
            this.documentNumberDTO.setAttachNumber(whtLog.getAttachNumber());
            this.documentNumberDTO.setSerialNumber(whtLog.getSerialNumber());
            this.documentNumberDTO.setSequenceNumber(whtLog.getSequenceNumber());
        }
        
        this.address = new Address();
        this.address.setBuildName(whtLog.getBuildName());
        this.address.setRoomNo(whtLog.getRoomNo());
        this.address.setFloorNo(whtLog.getFloorNo());
        this.address.setVillageNane(whtLog.getVillageName());
        this.address.setAddNo(whtLog.getAddNo());
        this.address.setMooNo(whtLog.getMooNo());
        this.address.setSoi(whtLog.getSoi());
        this.address.setStreetName(whtLog.getStreetName());
        this.address.setTambon(whtLog.getTambon());
        this.address.setAmphur(whtLog.getAmphur());
        this.address.setProvince(whtLog.getProvince());
        this.address.setPostalCode(whtLog.getPostalCode());
        
        this.incType1 = new RowFieldDTO(whtLog.getIncType1Date(), whtLog.getIncType1Paid(), whtLog.getIncType1Tax(), null);
        this.incType2 = new RowFieldDTO(whtLog.getIncType2Date(), whtLog.getIncType2Paid(), whtLog.getIncType2Tax(), null);
        this.incType3 = new RowFieldDTO(whtLog.getIncType3Date(), whtLog.getIncType3Paid(), whtLog.getIncType3Tax(), null);
        this.incType4a = new RowFieldDTO(whtLog.getIncType4aDate(), whtLog.getIncType4aPaid(), whtLog.getIncType4aTax(), null);
        this.incType4b11 = new RowFieldDTO(whtLog.getIncType4b11Date(), whtLog.getIncType4b11Paid(), whtLog.getIncType4b11Tax(), null);
        this.incType4b12 = new RowFieldDTO(whtLog.getIncType4b12Date(), whtLog.getIncType4b12Paid(), whtLog.getIncType4b12Tax(), null);
        this.incType4b13 = new RowFieldDTO(whtLog.getIncType4b13Date(), whtLog.getIncType4b13Paid(), whtLog.getIncType4b13Tax(), null);
        this.incType4b14 = new RowFieldDTO(whtLog.getIncType4b14Date(), whtLog.getIncType4b14Paid(), whtLog.getIncType4b14Tax(), whtLog.getIncType4b14Detail());
        this.incType4b21 = new RowFieldDTO(whtLog.getIncType4b21Date(), whtLog.getIncType4b21Paid(), whtLog.getIncType4b21Tax(), null);
        this.incType4b22 = new RowFieldDTO(whtLog.getIncType4b22Date(), whtLog.getIncType4b22Paid(), whtLog.getIncType4b22Tax(), null);
        this.incType4b23 = new RowFieldDTO(whtLog.getIncType4b23Date(), whtLog.getIncType4b23Paid(), whtLog.getIncType4b23Tax(), null);
        this.incType4b24 = new RowFieldDTO(whtLog.getIncType4b24Date(), whtLog.getIncType4b24Paid(), whtLog.getIncType4b24Tax(), null);
        this.incType4b25 = new RowFieldDTO(whtLog.getIncType4b25Date(), whtLog.getIncType4b25Paid(), whtLog.getIncType4b25Tax(), whtLog.getIncType4b25Detail());
        this.incType5 = new RowFieldDTO(whtLog.getIncType5Date(), whtLog.getIncType5Paid(), whtLog.getIncType5Tax(), null);
        this.incType6 = new RowFieldDTO(whtLog.getIncType6Date(), whtLog.getIncType6Paid(), whtLog.getIncType6Tax(), whtLog.getIncType6Detail());
    }



}
