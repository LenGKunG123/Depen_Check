package isc.etax.taxfiling.entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Entity
@Table(name = "configs")
public class Config {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "config_id")
	private Long configId;

//	@Column(name = "config_name", length = 50, nullable = false)
//	private String configName;

	@Column(name = "book_format", length = 50, nullable = false)
	private String bookFormat;

	@Column(name = "document_format", length = 50, nullable = false)
	private String documentFormat;

	@Column(name = "sequence_padding", nullable = false)
	private Integer sequencePadding;
	
	@Enumerated(EnumType.STRING)
	@Column(name = "calendar_type", columnDefinition = "calendar_type")
    private CalendarType calendarType;
	public enum CalendarType {
		GREGORIAN, BUDDHIST
    }

	@Enumerated(EnumType.STRING)
    @Column(name = "reset_type")
    private ResetType resetType;
    public enum ResetType {
        MONTHLY, YEARLY
    }

	@Column(name = "signature_path_name", length = 50)
	private String signaturePathName;
	
	@ManyToOne
    @JoinColumn(name = "comp_id")
    private Profile companyProfile;

    @ManyToOne
    @JoinColumn(name = "template_id")
    private Template template;
    
    @Column(name = "status", length = 10, nullable = false, columnDefinition = "VARCHAR(10) DEFAULT 'ACTIVE'")
    private String status;


	
}

