package isc.etax.taxfiling.entities;

import jakarta.persistence.*;
import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Data
@Entity
@Table(name = "template")
public class Template {

	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "template_id")
    private Long templateId;

    @Column(name = "template_code", length = 15, nullable = false, unique = true)
    private String templateCode;

    @Column(name = "template_name", length = 10, nullable = false)
    private String templateName;

    @Column(name = "file_name", length = 20, nullable = false)
    private String fileName;
    
    @ManyToOne
    @JoinColumn(name = "comp_id", nullable = false)
    private Profile companyProfile;
    
    @Column(name = "status", length = 10, nullable = false, columnDefinition = "VARCHAR(10) DEFAULT 'ACTIVE'")
    private String status;

	public Template(String templateCode, String templateName, String fileName, Profile companyProfile, String status) {
		super();
		this.templateCode = templateCode;
		this.templateName = templateName;
		this.fileName = fileName;
		this.companyProfile = companyProfile;
		this.status = status;
	}
    
    


}
