package acsanfrancisco.invoice_system.dto;

import acsanfrancisco.invoice_system.entity.enums.PaymentMethod;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.Getter;
import lombok.Setter;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.UUID;

@Getter
@Setter
public class CreatePaymentDto {

    @NotNull(message = "Must inform payment date")
    private LocalDate paymentDate;

    @Positive(message = "Amount must be greater than 0")
    @NotNull(message = "Must  inform amount")
    private BigDecimal amount;

    @NotNull(message = "Must inform payment method")
    private PaymentMethod paymentMethod;

    @NotNull(message = "Must inform Invoice ID")
    private UUID invoice;
}
