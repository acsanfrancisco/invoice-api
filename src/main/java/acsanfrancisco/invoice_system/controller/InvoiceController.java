package acsanfrancisco.invoice_system.controller;

import acsanfrancisco.invoice_system.dto.CreateInvoiceDto;
import acsanfrancisco.invoice_system.dto.InvoiceResponseDto;
import acsanfrancisco.invoice_system.dto.UpdateInvoiceDto;
import acsanfrancisco.invoice_system.entity.enums.InvoiceStatus;
import acsanfrancisco.invoice_system.service.InvoiceService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.math.BigDecimal;
import java.time.LocalDate;
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

    @GetMapping("/search")
    public ResponseEntity<Page<InvoiceResponseDto>> search(
            @RequestParam(value = "id", required = false) UUID id,
            @RequestParam(value = "customerId", required = false ) UUID customerId,
            @RequestParam(value = "status", required = false ) InvoiceStatus status,
            @RequestParam(value = "dueDate", required = false) LocalDate dueDate,
            @RequestParam(value = "grossValueFrom", required = false) BigDecimal grossValueFrom,
            @RequestParam(value = "grossValueUntil", required = false) BigDecimal grossValueUntil,
            @RequestParam(value = "netValueFrom", required = false) BigDecimal netValueFrom,
            @RequestParam(value = "netValueUntil", required = false) BigDecimal netValueUntil,
            @RequestParam(value = "yetToPayFrom", required = false) BigDecimal yetToPayFrom,
            @RequestParam(value = "yetToPayUntil", required = false) BigDecimal yetToPayUntil,
            @RequestParam(value = "issuedAtFrom", required = false) LocalDate issuedAtFrom,
            @RequestParam(value = "issuedAtUntil", required = false) LocalDate issuedAtUntil,
            @RequestParam(value = "dueDateFrom", required = false) LocalDate dueDateFrom,
            @RequestParam(value = "dueDateUntil", required = false) LocalDate dueDateUntil,
            @PageableDefault(size = 10, page = 0) Pageable pageable){
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(invoiceService.search(id, customerId, status, dueDate
                        , grossValueFrom, grossValueUntil,
                        netValueFrom, netValueUntil,
                        yetToPayFrom, yetToPayUntil,
                        issuedAtFrom, issuedAtUntil,
                        dueDateFrom, dueDateUntil,
                        pageable));
    }
}
