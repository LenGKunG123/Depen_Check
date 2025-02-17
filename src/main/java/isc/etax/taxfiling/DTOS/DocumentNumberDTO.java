package isc.etax.taxfiling.DTOS;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class DocumentNumberDTO {
    private String attachNumber; // เลขที่
    private String serialNumber; // เล่มที่
    private String sequenceNumber; // ลำดับที่ ใบแบบ
}
