package acsanfrancisco.invoice_system.service;

import acsanfrancisco.invoice_system.dto.CreatePaymentDto;
import acsanfrancisco.invoice_system.dto.PaymentResponseDto;
import acsanfrancisco.invoice_system.entity.Invoice;
import acsanfrancisco.invoice_system.entity.Payment;
import acsanfrancisco.invoice_system.entity.enums.InvoiceStatus;
import acsanfrancisco.invoice_system.entity.enums.PaymentMethod;
import acsanfrancisco.invoice_system.exception.InvalidCustomerException;
import acsanfrancisco.invoice_system.exception.InvalidInvoiceException;
import acsanfrancisco.invoice_system.exception.InvalidPaymentException;
import acsanfrancisco.invoice_system.mapper.PaymentMapper;
import acsanfrancisco.invoice_system.repository.CustomerRepository;
import acsanfrancisco.invoice_system.repository.InvoiceRepository;
import acsanfrancisco.invoice_system.repository.PaymentRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

import static acsanfrancisco.invoice_system.entity.specification.PaymentSpecification.*;

@Service
@RequiredArgsConstructor
public class PaymentService {

    private final PaymentRepository paymentRepository;
    private final InvoiceRepository invoiceRepository;
    private final CustomerRepository customerRepository;

    @Transactional
    public PaymentResponseDto createPayment(CreatePaymentDto dto) {
        Invoice invoice = invoiceRepository.findById(dto.getInvoice())
                .orElseThrow(()-> new InvalidInvoiceException("Invoice not found. ID: " + dto.getInvoice()));

        if(invoice.getStatus() == (InvoiceStatus.PAID) ||
                invoice.getStatus() == (InvoiceStatus.CANCELLED)) {
            throw new InvalidPaymentException("Impossible to create a payment for invoice with status " + invoice.getStatus() + ". ID: " + dto.getInvoice());
        }

        if(dto.getAmount().compareTo(invoice.getYetToPay()) > 0){
            throw new InvalidPaymentException("Amount must not be greater than yet to pay value");
        }

        BigDecimal yetToPay = invoice.getYetToPay().subtract(dto.getAmount());
        invoice.setYetToPay(yetToPay);
        invoice.setStatus(InvoiceStatus.PARTIALLY_PAID);

        if(invoice.getYetToPay().compareTo(BigDecimal.ZERO) == 0){
            invoice.setYetToPay(BigDecimal.ZERO);
            invoice.setStatus(InvoiceStatus.PAID);
        }
        return PaymentMapper.toDto(paymentRepository.save(PaymentMapper.toEntity(dto, invoice)));
    }

    @Transactional(readOnly = true)
    public List<PaymentResponseDto> findPaymentsByInvoiceId(UUID id) {
        return paymentRepository
                .findPaymentsByInvoiceId(id)
                .stream().map(PaymentMapper::toDto).toList();
    }

    @Transactional(readOnly = true)
    public List<PaymentResponseDto> findPaymentsByCustomerId(UUID id) {
        if(!customerRepository.existsById(id)) {
            throw new InvalidCustomerException("Customer not found. ID: " + id);
        }
        return paymentRepository
                .findPaymentsByCustomerId(id)
                .stream().map(PaymentMapper::toDto).toList();
    }

    @Transactional(readOnly = true)
    public List<PaymentResponseDto> findPaymentsByPaymentDate(LocalDate paymentDate) {
        return paymentRepository
                .findPaymentsByPaymentDate(paymentDate)
                .stream().map(PaymentMapper::toDto).toList();
    }

    @Transactional(readOnly = true)
    public List<PaymentResponseDto> findPaymentsByCustomerDocument(String document) {
        if(!customerRepository.existsByDocument(document)) {
            throw new InvalidCustomerException("Document not found. Document: " + document);
        }
        return paymentRepository
                .findPaymentsByCustomerDocument(document)
                .stream().map(PaymentMapper::toDto).toList();
    }

    public List<PaymentResponseDto> findPaymentsGreaterThan(BigDecimal amount) {
        return paymentRepository
                .findPaymentsGreaterThan(amount)
                .stream().map(PaymentMapper::toDto).toList();
    }

    @Transactional(readOnly = true)
    public List<PaymentResponseDto> findPaymentsByCustomerIdEqualOrGreaterThan(UUID id, BigDecimal amount) {
        if(!customerRepository.existsById(id)) {
            throw new InvalidCustomerException("Customer not found. ID: " + id);
        }

        return paymentRepository
                .findPaymentsByCustomerIdEqualOrGreaterThan(id ,amount)
                .stream().map(PaymentMapper::toDto).toList();
    }

    @Transactional(readOnly = true)
    public Page<PaymentResponseDto> search(LocalDate paymentDate, BigDecimal amount,
                                           PaymentMethod paymentMethod, UUID invoiceId,
                                           Pageable pageable) {
        Specification<Payment> specification = Specification.allOf(
                paymentDateEquals(paymentDate),
                amountEquals(amount),
                paymentMethodEquals(paymentMethod),
                invoiceIdEquals(invoiceId));
        return paymentRepository.findAll(specification, pageable).map(PaymentMapper::toDto);
    }
}