package acsanfrancisco.invoice_system.dto;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.PositiveOrZero;
import lombok.Getter;
import lombok.Setter;
import java.math.BigDecimal;
import java.util.UUID;

@Getter
@Setter
public class CreateInvoiceDto {

    @NotNull(message = "Must inform gross value")
    @Positive(message = "Gross value must be greater than zero")
    private BigDecimal grossValue;

    @PositiveOrZero(message = "Discount must not be negative")
    private BigDecimal discount;

    private String note;

    @NotNull(message = "Must inform customer ID")
    private UUID customer_id;

}
