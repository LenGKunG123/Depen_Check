package isc.etax.taxfiling.entities;

import jakarta.persistence.*;
import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Data
@Entity
@Table(name = "company_profile")
public class Profile {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "profile_id")
	private Long profileId;

	@Column(name = "tax_id13", length = 13, nullable = false)
	private String taxId13;

	@Column(name = "tax_id10", length = 10)
	private String taxId10;

	@Column(name = "comp_title", length = 50, nullable = false)
	private String compTitle;

	@Column(name = "comp_name", length = 100, nullable = false)
	private String compName;

	@Column(name = "branch_no", length = 5)
	private String branchNo;
	
	@Column(name = "branch_name", length = 100)
	private String branchName;

	@Embedded
	private Address address;
	
//	@ManyToOne
//    @JoinColumn(name = "config_id")
//    private Config config;


}
