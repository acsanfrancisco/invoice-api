package acsanfrancisco.invoice_system.dto;

import lombok.Getter;
import lombok.Setter;
import java.util.UUID;

@Setter
@Getter
public class CustomerResponseDto {

    private UUID id;

    private String fullName;

    private String whatsappNumber;

    private String document;
}
