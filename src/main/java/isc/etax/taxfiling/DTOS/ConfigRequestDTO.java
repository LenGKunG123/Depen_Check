package isc.etax.taxfiling.DTOS;

import isc.etax.taxfiling.entities.Profile;
import isc.etax.taxfiling.entities.Template;
import isc.etax.taxfiling.entities.Config.CalendarType;
import isc.etax.taxfiling.entities.Config.ResetType;
import jakarta.persistence.Column;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
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
public class ConfigRequestDTO {
	
	private String bookFormat;
	private String documentFormat;
	private Integer sequencePadding;
    private CalendarType calendarType;
    private ResetType resetType;
	private String signaturePathName;
    private String templateCode;
    

}
