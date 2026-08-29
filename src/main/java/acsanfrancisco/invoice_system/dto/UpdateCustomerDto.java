package acsanfrancisco.invoice_system.dto;

import jakarta.validation.constraints.AssertTrue;
import jakarta.validation.constraints.Pattern;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.validator.constraints.Length;

@Setter
@Getter
public class UpdateCustomerDto {

    @Length(min = 5, max = 255, message = "Length for this field, min = 5 / max = 255")
    private String fullName;

    @Length(min = 13, max = 13, message = "Must inform DDI and DDD")
    @Pattern(regexp = "^\\d{13}$")
    private String whatsappNumber;

    @AssertTrue(message = "At least one field must be provided")
    public boolean isValid() {
        return fullName != null ||  whatsappNumber != null;
    }
}
