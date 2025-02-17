package isc.etax.taxfiling.entities;

import java.time.Instant;
import java.time.LocalDate;

import org.hibernate.annotations.CreationTimestamp;

import com.fasterxml.jackson.annotation.JsonFormat;

import isc.etax.taxfiling.DTOS.WithholdingTaxDTO;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "wht_log")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class WhtLog {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "rec_id")
    private Long recId;

    @CreationTimestamp
    @Column(name = "generated_date", nullable = false, columnDefinition = "TIMESTAMP DEFAULT CURRENT_TIMESTAMP")
    private Instant generatedDate;

    @Column(name = "generated_by", length = 15)
    private String generatedBy;

    @Column(name = "config_id", nullable = true)
    private Integer configId;

    @Column(name = "attach_number", length = 30)
    private String attachNumber;

    @Column(name = "serial_number", length = 30)
    private String serialNumber;

    @Column(name = "sequence_number")
    private String sequenceNumber;

    @Column(name = "nid", length = 13)
    private String nid;

    @Column(name = "recipient_fname", length = 100)
    private String recipientFname;

    @Column(name = "recipient_sname", length = 80)
    private String recipientSname;

    @Column(name = "build_name", length = 40)
    private String buildName;

    @Column(name = "room_no", length = 20)
    private String roomNo;

    @Column(name = "floor_no", length = 20)
    private String floorNo;

    @Column(name = "village_name", length = 100)
    private String villageName;

    @Column(name = "add_no", length = 20)
    private String addNo;

    @Column(name = "moo_no", length = 20)
    private String mooNo;

    @Column(name = "soi", length = 100)
    private String soi;

    @Column(name = "street_name", length = 100)
    private String streetName;

    @Column(name = "tambon", length = 50)
    private String tambon;

    @Column(name = "amphur", length = 50)
    private String amphur;

    @Column(name = "province", length = 50)
    private String province;

    @Column(name = "postal_code", length = 5)
    private String postalCode;

    @Column(name = "pin", length = 13)
    private String pin;

    @Column(name = "tin", length = 10)
    private String tin;

    @Column(name = "tax_type", length = 10)
    private String taxType;

    @Column(name = "tax_month", length = 2)
    private String taxMonth;

    @Column(name = "tax_year", length = 4)
    private String taxYear;

    @Column(name = "inc_type1_date")
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd-MM-yyyy")
    private LocalDate  incType1Date;
    @Column(name = "inc_type1_paid")
    private Double incType1Paid;
    @Column(name = "inc_type1_tax")
    private Double incType1Tax;

    @Column(name = "inc_type2_date")
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd-MM-yyyy")
    private LocalDate incType2Date;
    @Column(name = "inc_type2_paid")
    private Double incType2Paid;
    @Column(name = "inc_type2_tax")
    private Double incType2Tax;

    @Column(name = "inc_type3_date")
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd-MM-yyyy")
    private LocalDate incType3Date;
    @Column(name = "inc_type3_paid")
    private Double incType3Paid;
    @Column(name = "inc_type3_tax")
    private Double incType3Tax;

    @Column(name = "inc_type4a_date")
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd-MM-yyyy")
    private LocalDate incType4aDate;
    @Column(name = "inc_type4a_paid")
    private Double incType4aPaid;
    @Column(name = "inc_type4a_tax")
    private Double incType4aTax;

    @Column(name = "inc_type4b11_date")
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd-MM-yyyy")
    private LocalDate incType4b11Date;
    @Column(name = "inc_type4b11_paid")
    private Double incType4b11Paid;
    @Column(name = "inc_type4b11_tax")
    private Double incType4b11Tax;

    @Column(name = "inc_type4b12_date")
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd-MM-yyyy")
    private LocalDate incType4b12Date;
    @Column(name = "inc_type4b12_paid")
    private Double incType4b12Paid;
    @Column(name = "inc_type4b12_tax")
    private Double incType4b12Tax;

    @Column(name = "inc_type4b13_date")
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd-MM-yyyy")
    private LocalDate incType4b13Date;
    @Column(name = "inc_type4b13_paid")
    private Double incType4b13Paid;
    @Column(name = "inc_type4b13_tax")
    private Double incType4b13Tax;

    @Column(name = "inc_type4b14_date")
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd-MM-yyyy")
    private LocalDate incType4b14Date;
    @Column(name = "inc_type4b14_paid")
    private Double incType4b14Paid;
    @Column(name = "inc_type4b14_tax")
    private Double incType4b14Tax;
    @Column(name = "inc_type4b14_detail", length = 20)
    private String incType4b14Detail;

    @Column(name = "inc_type4b21_date")
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd-MM-yyyy")
    private LocalDate incType4b21Date;
    @Column(name = "inc_type4b21_paid")
    private Double incType4b21Paid;
    @Column(name = "inc_type4b21_tax")
    private Double incType4b21Tax;

    @Column(name = "inc_type4b22_date")
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd-MM-yyyy")
    private LocalDate incType4b22Date;
    @Column(name = "inc_type4b22_paid")
    private Double incType4b22Paid;
    @Column(name = "inc_type4b22_tax")
    private Double incType4b22Tax;

    @Column(name = "inc_type4b23_date")
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd-MM-yyyy")
    private LocalDate incType4b23Date;
    @Column(name = "inc_type4b23_paid")
    private Double incType4b23Paid;
    @Column(name = "inc_type4b23_tax")
    private Double incType4b23Tax;

    @Column(name = "inc_type4b24_date")
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd-MM-yyyy")
    private LocalDate incType4b24Date;
    @Column(name = "inc_type4b24_paid")
    private Double incType4b24Paid;
    @Column(name = "inc_type4b24_tax")
    private Double incType4b24Tax;

    @Column(name = "inc_type4b25_date")
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd-MM-yyyy")
    private LocalDate incType4b25Date;
    @Column(name = "inc_type4b25_paid")
    private Double incType4b25Paid;
    @Column(name = "inc_type4b25_tax")
    private Double incType4b25Tax;
    @Column(name = "inc_type4b25_detail", length = 20)
    private String incType4b25Detail;

    @Column(name = "inc_type5_date")
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd-MM-yyyy")
    private LocalDate incType5Date;
    @Column(name = "inc_type5_paid")
    private Double incType5Paid;
    @Column(name = "inc_type5_tax")
    private Double incType5Tax;

    @Column(name = "inc_type6_date")
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd-MM-yyyy")
    private LocalDate incType6Date;
    @Column(name = "inc_type6_paid")
    private Double incType6Paid;
    @Column(name = "inc_type6_tax")
    private Double incType6Tax;
    @Column(name = "inc_type6_detail", length = 30)
    private String incType6Detail;

    @Column(name = "paid_amt")
    private Double paidAmt;

    @Column(name = "tax_amt")
    private Double taxAmt;

    @Column(name = "tax_amt_alphabet", length = 255)
    private String taxAmtAlphabet;

    @Column(name = "pension_fund")
    private Double pensionFund;

    @Column(name = "social_security_fund")
    private Double socialSecurityFund;

    @Column(name = "life_support_fund")
    private Double lifeSupportFund;

    @Column(name = "pay_con", length = 1)
    private String payCon;

    @Column(name = "pay_con_detail", length = 20)
    private String payConDetail;
    
    @Column(name = "sign_date")
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd-MM-yyyy")
    private LocalDate signDate;
    
    
    public WhtLog(WithholdingTaxDTO dto) {
        this.nid = dto.getNid();
        this.recipientFname = dto.getFirstName();
        this.recipientSname = dto.getMiddleName() + (dto.getMiddleName()  == null ? "" : " " )+ dto.getSurName();
        this.pin = dto.getPin();
        this.tin = dto.getTin();
        this.taxType = dto.getTaxType();
        this.paidAmt =  dto.getPaidAmt();
        this.taxAmt =  dto.getTaxAmt();
        this.taxAmtAlphabet = dto.getTaxAmtAlphabet();
        this.pensionFund =  dto.getPensionFund();
        this.socialSecurityFund =  dto.getSocialSecurityFund();
        this.lifeSupportFund =  dto.getLifeSupportFund();
        this.payCon = dto.getPayCon();
        this.payConDetail = dto.getPayConDetail();

        if (dto.getDocumentNumberDTO() != null) {
        	this.attachNumber = dto.getDocumentNumberDTO().getAttachNumber();
            this.serialNumber = dto.getDocumentNumberDTO().getSerialNumber();
            this.sequenceNumber = dto.getDocumentNumberDTO().getSequenceNumber();
        }
        
        
        if (dto.getAddress() != null) {
            this.buildName = dto.getAddress().getBuildName();
            this.roomNo = dto.getAddress().getRoomNo();
            this.floorNo = dto.getAddress().getFloorNo();
            this.villageName = dto.getAddress().getVillageNane();
            this.addNo = dto.getAddress().getAddNo();
            this.mooNo = dto.getAddress().getMooNo();
            this.soi = dto.getAddress().getSoi();
            this.streetName = dto.getAddress().getStreetName();
            this.tambon = dto.getAddress().getTambon();
            this.amphur = dto.getAddress().getAmphur();
            this.province = dto.getAddress().getProvince();
            this.postalCode = dto.getAddress().getPostalCode();
        }
        if (dto.getIncType1() != null){
            this.incType1Date = dto.getIncType1().getDate();
            this.incType1Paid = dto.getIncType1().getPayAmount();
            this.incType1Tax = dto.getIncType1().getWithheldAmount();
        }
        if (dto.getIncType2() != null){
            this.incType2Date = dto.getIncType2().getDate();
            this.incType2Paid = dto.getIncType2().getPayAmount();
            this.incType2Tax = dto.getIncType2().getWithheldAmount();
        }
        if (dto.getIncType3() != null) {
            this.incType3Date = dto.getIncType3().getDate();
            this.incType3Paid = dto.getIncType3().getPayAmount();
            this.incType3Tax = dto.getIncType3().getWithheldAmount();
        }
        if (dto.getIncType4a() != null) {
            this.incType4aDate = dto.getIncType4a().getDate();
            this.incType4aPaid = dto.getIncType4a().getPayAmount();
            this.incType4aTax = dto.getIncType4a().getWithheldAmount();
        }
        if (dto.getIncType4b11() != null) {
            this.incType4b11Date = dto.getIncType4b11().getDate();
            this.incType4b11Paid = dto.getIncType4b11().getPayAmount();
            this.incType4b11Tax = dto.getIncType4b11().getWithheldAmount();
        }
        if (dto.getIncType4b12() != null) {
            this.incType4b12Date = dto.getIncType4b12().getDate();
            this.incType4b12Paid = dto.getIncType4b12().getPayAmount();
            this.incType4b12Tax = dto.getIncType4b12().getWithheldAmount();
        }
        if (dto.getIncType4b13() != null) {
            this.incType4b13Date = dto.getIncType4b13().getDate();
            this.incType4b13Paid = dto.getIncType4b13().getPayAmount();
            this.incType4b13Tax = dto.getIncType4b13().getWithheldAmount();
        }
        if (dto.getIncType4b14() != null) {
            this.incType4b14Date = dto.getIncType4b14().getDate();
            this.incType4b14Paid = dto.getIncType4b14().getPayAmount();
            this.incType4b14Tax = dto.getIncType4b14().getWithheldAmount();
            this.incType4b14Detail = dto.getIncType4b14().getDetail();
        }
        if (dto.getIncType4b21() != null) {
            this.incType4b21Date = dto.getIncType4b21().getDate();
            this.incType4b21Paid = dto.getIncType4b21().getPayAmount();
            this.incType4b21Tax = dto.getIncType4b21().getWithheldAmount();
        }
        if (dto.getIncType4b22() != null) {
            this.incType4b22Date = dto.getIncType4b22().getDate();
            this.incType4b22Paid = dto.getIncType4b22().getPayAmount();
            this.incType4b22Tax = dto.getIncType4b22().getWithheldAmount();
        }
        if (dto.getIncType4b23() != null) {
            this.incType4b23Date = dto.getIncType4b23().getDate();
            this.incType4b23Paid = dto.getIncType4b23().getPayAmount();
            this.incType4b23Tax = dto.getIncType4b23().getWithheldAmount();
        }
        if (dto.getIncType4b24() != null) {
            this.incType4b24Date = dto.getIncType4b24().getDate();
            this.incType4b24Paid = dto.getIncType4b24().getPayAmount();
            this.incType4b24Tax = dto.getIncType4b24().getWithheldAmount();
        }
        if (dto.getIncType4b25() != null) {
            this.incType4b25Date = dto.getIncType4b25().getDate();
            this.incType4b25Paid = dto.getIncType4b25().getPayAmount();
            this.incType4b25Tax = dto.getIncType4b25().getWithheldAmount();
            this.incType4b25Detail = dto.getIncType4b25().getDetail();
        }
        if (dto.getIncType5() != null) {
            this.incType5Date = dto.getIncType5().getDate();
            this.incType5Paid = dto.getIncType5().getPayAmount();
            this.incType5Tax = dto.getIncType5().getWithheldAmount();
        }
        if (dto.getIncType6() != null) {
            this.incType6Date = dto.getIncType6().getDate();
            this.incType6Paid = dto.getIncType6().getPayAmount();
            this.incType6Tax = dto.getIncType6().getWithheldAmount();
            this.incType6Detail = dto.getIncType6().getDetail();
        }
        
        if (dto.getSignDate() != null) {
        	this.signDate = dto.getSignDate();
        }

    }
    
    
    
    
    
}
