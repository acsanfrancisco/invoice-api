package acsanfrancisco.invoice_system.controller;

import acsanfrancisco.invoice_system.dto.CreateCustomerDto;
import acsanfrancisco.invoice_system.dto.CustomerResponseDto;
import acsanfrancisco.invoice_system.dto.UpdateCustomerDto;
import acsanfrancisco.invoice_system.entity.enums.DocumentType;
import acsanfrancisco.invoice_system.service.CustomerService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/v1/customers")
@RequiredArgsConstructor
public class CustomerController {

    private final CustomerService customerService;

    @PostMapping
    public ResponseEntity<CustomerResponseDto> createCustomer(@RequestBody @Valid CreateCustomerDto createCustomerDto) {
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(customerService.createCustomer(createCustomerDto));
    }

    @PatchMapping("/{id}")
    public ResponseEntity<CustomerResponseDto> updateCustomer(@RequestBody @Valid UpdateCustomerDto dto, @PathVariable UUID id) {
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(customerService.updateCustomer(dto, id));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteCustomer(@PathVariable UUID id) {
        customerService.deleteCustomer(id);
        return ResponseEntity
                .noContent()
                .build();
    }

    @PatchMapping("/deactivate/{id}")
    public ResponseEntity<Void> deactivateCustomer(@PathVariable UUID id) {
        customerService.deactivateCustomer(id);
        return ResponseEntity
                .noContent()
                .build();
    }

    @PatchMapping("/activate/{id}")
    public ResponseEntity<Void> activeCustomer(@PathVariable UUID id) {
        customerService.activateCustomer(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/{id}")
    public ResponseEntity<CustomerResponseDto> findCustomerById(@PathVariable UUID id) {
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(customerService.findCustomerById(id));
    }

    @GetMapping(params = "document")
    public ResponseEntity<CustomerResponseDto> findCustomerByDocument(@RequestParam String document) {
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(customerService.findCustomerByDocument(document));
    }

    @GetMapping(params = "whatsappNumber")
    public ResponseEntity<List<CustomerResponseDto>> findCustomerByWhatsappNumber(@RequestParam String whatsappNumber) {
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(customerService.findCustomerByWhatsappNumber(whatsappNumber));
    }

    @GetMapping("/search")
    public ResponseEntity<Page<CustomerResponseDto>> searchCustomer(
            @RequestParam(required = false) String fullName,
            @RequestParam(required = false) String document,
            @RequestParam(required = false) String whatsappNumber,
            @RequestParam(required = false) DocumentType documentType,
            @RequestParam(required = false) Boolean active,
            @PageableDefault(size = 10, page = 0) Pageable pageable){
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(customerService.search(fullName, document, whatsappNumber, documentType, active, pageable));
    }
}
