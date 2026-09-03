package acsanfrancisco.invoice_system.dto;

import acsanfrancisco.invoice_system.entity.enums.PaymentMethod;
import lombok.Getter;
import lombok.Setter;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.UUID;

@Getter
@Setter
public class PaymentResponseDto {

    private UUID id;

    private LocalDate paymentDate;

    private BigDecimal amount;

    private PaymentMethod paymentMethod;

    private UUID invoiceId;
}

