package isc.etax.taxfiling.entities;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Embeddable
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Address {
	@Column(name = "build_name", length = 40, nullable = true)
    private String buildName;
	
	@Column(name = "room_no", length = 20, nullable = true)
    private String roomNo;
	
	@Column(name = "floor_no", length = 20, nullable = true)
    private String floorNo;
	
	@Column(name = "village_name", length = 100, nullable = true)
    private String villageNane;
	
	@Column(name = "add_no", length = 20, nullable = true)
    private String addNo;
	
	@Column(name = "moo_no", length = 20, nullable = true)
    private String mooNo;
	
	@Column(name = "soi", length = 100, nullable = true)
    private String soi;
	
	@Column(name = "street_name", length = 100, nullable = true)
    private String streetName;
	
	@Column(name = "tambon", length = 50, nullable = true)
    private String tambon;
	
	@Column(name = "amphur", length = 50, nullable = true)
    private String amphur;
	
	@Column(name = "province", length = 50, nullable = true)
    private String province;
	
	@Column(name = "postal_code", length = 5, nullable = true)
    private String postalCode;
    

}
