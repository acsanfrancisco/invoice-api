package acsanfrancisco.invoice_system.controller;

import acsanfrancisco.invoice_system.dto.request.CreatePaymentDto;
import acsanfrancisco.invoice_system.dto.response.PaymentResponseDto;
import acsanfrancisco.invoice_system.entity.enums.PaymentMethod;
import acsanfrancisco.invoice_system.service.PaymentService;
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
@RequestMapping("/api/v1/payments")
@RequiredArgsConstructor
public class PaymentController {

    private final PaymentService paymentService;

    @PostMapping
    public ResponseEntity<PaymentResponseDto> createPayment(@RequestBody @Valid CreatePaymentDto createPaymentDto) {
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(paymentService.createPayment(createPaymentDto));
    }

    @GetMapping("/invoices/{invoiceId}")
    public ResponseEntity<List<PaymentResponseDto>> findPaymentsByInvoiceId(@PathVariable UUID invoiceId) {
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(paymentService.findPaymentsByInvoiceId(invoiceId));
    }

    @GetMapping("/customers/{customerId}")
    public ResponseEntity<List<PaymentResponseDto>> findPaymentsByCustomerId(@PathVariable UUID customerId){
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(paymentService.findPaymentsByCustomerId(customerId));
    }

    @GetMapping(params = "paymentDate")
    public ResponseEntity<List<PaymentResponseDto>> findPaymentsByPaymentDate(@RequestParam("paymentDate") LocalDate paymentDate) {
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(paymentService.findPaymentsByPaymentDate(paymentDate));
    }

    @GetMapping(value = "/customers", params = "document")
    public ResponseEntity<List<PaymentResponseDto>> findPaymentsByCustomerDocument(@RequestParam("document") String document) {
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(paymentService.findPaymentsByCustomerDocument(document));
    }

    @GetMapping("/search")
    public ResponseEntity<Page<PaymentResponseDto>> search(
            @RequestParam(value = "id", required = false) UUID id,
            @RequestParam(value = "invoiceId", required = false) UUID invoiceId,
            @RequestParam(value = "customerId", required = false) UUID customerId,
            @RequestParam(value = "paymentMethod", required = false) PaymentMethod paymentMethod,
            @RequestParam(value = "paymentDate", required = false) LocalDate paymentDate,
            @RequestParam(value = "paymentDateFrom", required = false) LocalDate paymentDateFrom,
            @RequestParam(value = "paymentDateUntil", required = false) LocalDate paymentDateUntil,
            @RequestParam(value = "amountFrom", required = false) BigDecimal amountFrom,
            @RequestParam(value = "amountUntil", required = false) BigDecimal amountUntil,
            @PageableDefault(page = 0, size = 10) Pageable pageable) {
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(paymentService.search(id, invoiceId, customerId, paymentMethod, paymentDate, paymentDateFrom, paymentDateUntil, amountFrom, amountUntil, pageable));
    }
}
