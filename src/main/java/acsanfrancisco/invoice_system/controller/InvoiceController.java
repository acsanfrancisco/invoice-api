package acsanfrancisco.invoice_system.controller;

import acsanfrancisco.invoice_system.dto.CreateInvoiceDto;
import acsanfrancisco.invoice_system.dto.InvoiceResponseDto;
import acsanfrancisco.invoice_system.dto.UpdateInvoiceDto;
import acsanfrancisco.invoice_system.service.InvoiceService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/v1/invoices")
@RequiredArgsConstructor
public class InvoiceController {

    private final InvoiceService invoiceService;

    @PostMapping
    public ResponseEntity<InvoiceResponseDto> createInvoice(@RequestBody @Valid CreateInvoiceDto dto){
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(invoiceService.createInvoice(dto));
    }

    @PutMapping
    public ResponseEntity<InvoiceResponseDto> updateInvoice(@RequestBody @Valid UpdateInvoiceDto dto){
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(invoiceService.updateInvoice(dto));
    }

    @PatchMapping("/{id}/cancel")
    public ResponseEntity<Void> setInvoiceCancelled(@PathVariable UUID id){
        invoiceService.setInvoiceCancelled(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/{id}")
    public ResponseEntity<InvoiceResponseDto> findInvoiceById(@PathVariable UUID id){
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(invoiceService.findInvoiceById(id));
    }

    @GetMapping("/customers/{customerId}")
    public ResponseEntity<List<InvoiceResponseDto>> findInvoicesByCustomerId(@PathVariable UUID customerId){
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(invoiceService.findInvoicesByCustomerId(customerId));

    }

    @GetMapping(value = "/customers", params = "document")
    public ResponseEntity<List<InvoiceResponseDto>> findInvoicesByCustomerDocument(@RequestParam("document") String document){
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(invoiceService.findInvoiceByCustomerDocument(document));
    }

    @GetMapping(value = "/customers", params = "whatsappNumber")
    public ResponseEntity<List<InvoiceResponseDto>> findInvoicesByCustomerWhatsappNumber(@RequestParam("whatsappNumber") String whatsappNumber){
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(invoiceService.findInvoiceByCustomerWhatsappNumber(whatsappNumber));
    }
}
