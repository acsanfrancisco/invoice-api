package acsanfrancisco.invoice_system.dto;

import acsanfrancisco.invoice_system.entity.enums.InvoiceStatus;
import lombok.Getter;
import lombok.Setter;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.UUID;

@Getter
@Setter
public class InvoiceResponseDto {

    private UUID id;

    private LocalDateTime issuedAt;

    private LocalDate dueDate;

    private BigDecimal grossValue;

    private BigDecimal discount;

    private BigDecimal netValue;

    private InvoiceStatus status;

    private String note;

    private CustomerResponseDto customer;
}
