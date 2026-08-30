package acsanfrancisco.invoice_system.controller;

import acsanfrancisco.invoice_system.dto.CreatePaymentDto;
import acsanfrancisco.invoice_system.dto.PaymentResponseDto;
import acsanfrancisco.invoice_system.service.PaymentService;
import lombok.RequiredArgsConstructor;
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
    public ResponseEntity<PaymentResponseDto> createPayment(@RequestBody CreatePaymentDto createPaymentDto) {
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

    @GetMapping(params = "amount")
    public ResponseEntity<List<PaymentResponseDto>> findPaymentsGreaterThan(@RequestParam("amount") BigDecimal amount) {
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(paymentService.findPaymentsGreaterThan(amount));

    }

    @GetMapping(value = "/customers/{customerId}", params = "amount")
    public ResponseEntity<List<PaymentResponseDto>> findPaymentsByCustomerIdGreaterThan(@PathVariable UUID customerId, @RequestParam("amount") BigDecimal amount) {
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(paymentService.findPaymentsByCustomerIdEqualOrGreaterThan(customerId, amount));
    }
}
