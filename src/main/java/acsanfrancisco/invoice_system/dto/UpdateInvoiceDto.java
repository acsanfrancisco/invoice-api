package acsanfrancisco.invoice_system.dto;

import jakarta.validation.constraints.AssertTrue;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.PositiveOrZero;
import lombok.Getter;
import lombok.Setter;
import java.math.BigDecimal;
import java.util.UUID;

@Getter
@Setter
public class UpdateInvoiceDto {

    @NotNull(message = "Must inform the Invoice ID")
    private UUID id;

    @Positive(message = "Gross value must be greater than zero")
    private BigDecimal grossValue;

    @PositiveOrZero(message = "Discount must not be negative")
    private BigDecimal discount;

    private UUID customerId;

    private String note;

    @AssertTrue(message = "At least one field must be provided")
    public boolean isValid(){
        return grossValue  != null || discount != null || customerId != null || note != null;
    }
}
