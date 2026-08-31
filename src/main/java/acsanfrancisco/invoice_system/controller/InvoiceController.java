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

    @GetMapping(value = "/gross-value/greater-than-or-equal", params = "grossValue")
    public ResponseEntity<List<InvoiceResponseDto>> findInvoicesByGrossValueGreaterThanOrEqualTo(@RequestParam("grossValue") BigDecimal grossValue){
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(invoiceService.findInvoicesByGrossValueGreaterThanOrEqualTo(grossValue));
    }

    @GetMapping(value = "/gross-value/less-than-or-equal", params = "grossValue")
    public ResponseEntity<List<InvoiceResponseDto>> findInvoicesByGrossValueLessThanOrEqualTo(@RequestParam("grossValue") BigDecimal grossValue){
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(invoiceService.findInvoicesByGrossValueLessThanOrEqualTo(grossValue));
    }

    @GetMapping(value = "/net-value/greater-than-or-equal", params = "netValue")
    public ResponseEntity<List<InvoiceResponseDto>> findInvoicesByNetValueGreaterThanOrEqualTo(@RequestParam("netValue") BigDecimal netValue){
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(invoiceService.findInvoicesByNetValueGreaterThanOrEqualTo(netValue));
    }

    @GetMapping(value = "/net-value/less-than-or-equal", params = "netValue")
    public ResponseEntity<List<InvoiceResponseDto>> findInvoicesByNetValueLessThanOrEqualTo(@RequestParam("netValue") BigDecimal netValue){
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(invoiceService.findInvoicesByNetValueLessThanOrEqualTo(netValue));
    }

    @GetMapping(value = "/yet-to-pay/greater-than-or-equal", params = "yetToPay")
    public ResponseEntity<List<InvoiceResponseDto>> findInvoicesByYetToPayGreaterThanOrEqualTo(@RequestParam("yetToPay") BigDecimal yetToPay){
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(invoiceService.findInvoicesByYetToPayGreaterThanOrEqualTo(yetToPay));
    }

    @GetMapping(value = "/yet-to-pay/less-than-or-equal", params = "yetToPay")
    public ResponseEntity<List<InvoiceResponseDto>> findInvoicesByYetToPayLessThanOrEqualTo(@RequestParam("yetToPay") BigDecimal yetToPay){
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(invoiceService.findInvoicesByYetToPayLessThanOrEqualTo(yetToPay));
    }

    @GetMapping("/search")
    public ResponseEntity<Page<InvoiceResponseDto>> search(
            @RequestParam(value = "id", required = false) UUID id,
            @RequestParam(value = "customerId", required = false ) UUID customerId,
            @RequestParam(value = "grossValue", required = false ) BigDecimal grossValue,
            @RequestParam(value = "netValue", required = false) BigDecimal netValue,
            @RequestParam(value = "yetToPay", required = false) BigDecimal yetToPay,
            @RequestParam(value = "status", required = false) InvoiceStatus status,
            @RequestParam(value = "dueDate", required = false) LocalDate dueDate,
            @RequestParam(value = "firstDate", required = false) LocalDate firstDate,
            @RequestParam(value = "lastDate", required = false) LocalDate lastDate,
            @PageableDefault(size = 10, page = 0) Pageable pageable){
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(invoiceService.search(id, customerId, grossValue, netValue, yetToPay, status, dueDate, firstDate, lastDate, pageable));
    }

}
