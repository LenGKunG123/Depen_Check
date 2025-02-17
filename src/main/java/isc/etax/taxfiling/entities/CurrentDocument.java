package isc.etax.taxfiling.entities;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Data
@Entity
@Table(name = "current_document")
public class CurrentDocument {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "rec_id")
    private Long recId;

    @ManyToOne
    @JoinColumn(name = "config_id")
    private Config config;

    @Column(name = "book_number", length = 50, nullable = false)
    private Integer bookNumber = 0;

    @Column(name = "document_number", length = 50, nullable = false)
    private Integer documentNumber = 0;

    @Column(name = "sequence_number", length = 10)
    private Integer sequenceNumber = 0;



}
