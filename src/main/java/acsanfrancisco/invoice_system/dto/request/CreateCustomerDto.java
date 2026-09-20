package acsanfrancisco.invoice_system.dto.request;

import acsanfrancisco.invoice_system.validation.ValidDocument;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.validator.constraints.Length;

@Getter
@Setter
public class CreateCustomerDto {

    @NotBlank(message = "Customer name must be informed")
    @Length(min = 5, max = 255, message = "Length for this field, min = 5 / max = 255")
    private String fullName;

    @NotBlank(message = "Customer document must be informed")
    @Length(min = 11, max = 14, message = "Length for this field, min = 11 / max = 14")
    @ValidDocument(message = "Inform a valid document")
    private String document;

    @NotBlank(message = "Whatsapp number must be informed")
    @Length(min = 13, max = 13, message = "Must inform DDI and DDD")
    @Pattern(regexp = "^\\d{13}$", message = "Expected format: 5511999999999")
    private String whatsappNumber;
}
