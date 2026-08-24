package acsanfrancisco.invoice_system.dto;

import acsanfrancisco.invoice_system.validation.ValidDocument;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Pattern;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.validator.constraints.Length;

@Getter
@Setter
public class CreateCustomerDto {

    @NotEmpty(message = "Customer name must be informed")
    @Length(min = 5, max = 255, message = "Length for this field, min = 5 / max = 255")
    private String fullName;

    @NotEmpty(message = "Customer document must be informed")
    @Length(min = 11, max = 14, message = "Length for this field, min = 11 / max = 14")
    @ValidDocument(message = "Inform a valid document")
    private String document;

    @NotEmpty
    @Length(min = 13, max = 13, message = "Must inform DDI and DDD")
    @Pattern(regexp = "^\\d{13}$")
    private String whatsappNumber;
}
