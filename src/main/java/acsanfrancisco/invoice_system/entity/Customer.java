package acsanfrancisco.invoice_system.entity;

import acsanfrancisco.invoice_system.entity.enums.DocumentType;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import java.util.List;
import java.util.UUID;

@Entity
@Table(name = "tb_customer")
@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class Customer {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @Column(name = "full_name", nullable = false)
    private String fullName;

    @Column(name = "document", nullable = false, length = 14, unique = true)
    private String document;

    @Column(name = "whatsapp_number", nullable = false, length = 13)
    private String whatsappNumber;

    @Column(name = "document_type", nullable = false)
    @Enumerated(EnumType.STRING)
    private DocumentType documentType;

    @OneToMany(mappedBy = "customer")
    @JsonIgnore
    private List<Invoice> invoices;
}
