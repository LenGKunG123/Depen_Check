package isc.etax.taxfiling.DTOS;

import java.time.LocalDate;

import com.fasterxml.jackson.annotation.JsonFormat;

import isc.etax.taxfiling.entities.Profile;
import isc.etax.taxfiling.entities.Template;
import jakarta.persistence.Column;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class TemplateDTO {

    private Long templateId;
    private String templateCode;
    private String templateName;
    private String fileName;
    private String status;
    private Long companyId;
    
    public TemplateDTO(Template temp) {
    	this.templateId = temp.getTemplateId();
    	this.templateCode = temp.getTemplateCode();
    	this.templateName = temp.getTemplateName();
    	this.fileName = temp.getFileName();
    	this.status = temp.getStatus();
    	this.companyId = temp.getCompanyProfile().getProfileId();
    	
    }

}
