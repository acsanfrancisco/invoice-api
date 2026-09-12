package acsanfrancisco.invoice_system.service;

import acsanfrancisco.invoice_system.dto.CreateInvoiceDto;
import acsanfrancisco.invoice_system.dto.InvoiceResponseDto;
import acsanfrancisco.invoice_system.dto.UpdateInvoiceDto;
import acsanfrancisco.invoice_system.entity.Customer;
import acsanfrancisco.invoice_system.entity.Invoice;
import acsanfrancisco.invoice_system.entity.enums.InvoiceStatus;
import acsanfrancisco.invoice_system.exception.InvalidCustomerException;
import acsanfrancisco.invoice_system.exception.InvalidInvoiceException;
import acsanfrancisco.invoice_system.mapper.InvoiceMapper;
import acsanfrancisco.invoice_system.repository.CustomerRepository;
import acsanfrancisco.invoice_system.repository.InvoiceRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

import static acsanfrancisco.invoice_system.specification.InvoiceSpecification.*;

@Service
@RequiredArgsConstructor
public class InvoiceService {

    private static final long DAYS_TO_DUE_DATE = 5;
    private final InvoiceRepository invoiceRepository;
    private final CustomerRepository customerRepository;

    @Transactional
    public InvoiceResponseDto createInvoice (CreateInvoiceDto dto){
        Customer customer = customerRepository.findById(dto.getCustomerId())
                .orElseThrow(()->new InvalidInvoiceException("Customer not found. ID: " + dto.getCustomerId()));

        Invoice invoice = InvoiceMapper.toEntity(dto, customer);

        LocalDateTime now =  LocalDateTime.now();
        invoice.setIssuedAt(now);
        LocalDate dueDate = invoice.getIssuedAt().plusDays(DAYS_TO_DUE_DATE).toLocalDate();
        BigDecimal netValue = invoice.getGrossValue().subtract(dto.getDiscount());
        invoice.setDueDate(dueDate);
        invoice.setNetValue(netValue);
        invoice.setStatus(InvoiceStatus.OPEN);
        invoice.setYetToPay(invoice.getNetValue());
        if(customer.getIsActive() == false){
            customer.setIsActive(true);
        }

        return InvoiceMapper
                .toDto(invoiceRepository.save(invoice));
    }

    @Transactional
    public InvoiceResponseDto updateInvoice(UpdateInvoiceDto dto){
        Invoice invoice = invoiceRepository.findById(dto.getId())
                .orElseThrow(()->new InvalidInvoiceException("Invoice not found for ID: " + dto.getId()));

        if(invoice.getStatus() == InvoiceStatus.CANCELLED ||
            invoice.getStatus() == InvoiceStatus.PARTIALLY_PAID ||
            invoice.getStatus() == InvoiceStatus.PAID){
            throw new InvalidInvoiceException("Impossible to update an invoice with status: " + invoice.getStatus() + ". ID: " + dto.getId());
        }

        if(dto.getCustomerId() != null){
            Customer customer = customerRepository.findById(dto.getCustomerId())
                    .orElseThrow(()->new InvalidCustomerException("Costumer not found for ID: " + dto.getCustomerId()));
            invoice.setCustomer(customer);
        }

        BigDecimal discount = dto.getDiscount() != null ? dto.getDiscount() : invoice.getDiscount();
        BigDecimal grossValue = dto.getGrossValue() != null ? dto.getGrossValue() : invoice.getGrossValue();
        String note = dto.getNote() != null ? dto.getNote() : invoice.getNote();
        invoice.setDiscount(discount);
        invoice.setGrossValue(grossValue);
        invoice.setNote(note);
        invoice.setNetValue(grossValue.subtract(discount));
        return InvoiceMapper
                .toDto(invoiceRepository.save(invoice));
    }

    @Transactional
    public void setInvoiceCancelled(UUID id){
        Invoice invoice = invoiceRepository.findById(id)
                .orElseThrow(()->new InvalidInvoiceException("Invoice not found for ID: " + id));
        if(invoice.getStatus() == InvoiceStatus.CANCELLED){
            throw new InvalidInvoiceException("Invoice is already set cancelled. ID: " + invoice.getId());
        }
        invoice.setStatus(InvoiceStatus.CANCELLED);
    }

    @Transactional(readOnly = true)
    public InvoiceResponseDto findInvoiceById(UUID id) {
        Invoice invoice = invoiceRepository.findById(id)
                .orElseThrow(()->new InvalidInvoiceException("Invoice not found for ID: " + id));
        return InvoiceMapper.toDto(invoice);
    }

    @Transactional(readOnly = true)
    public List<InvoiceResponseDto> findInvoicesByCustomerId(UUID id) {
        if(!customerRepository.existsById(id)){
            throw new InvalidCustomerException("Customer not found. ID: " + id);
        }
        List<Invoice> invoices = invoiceRepository.findInvoiceByCustomerId(id);
        return invoices.stream().map(InvoiceMapper::toDto).toList();
    }

    @Transactional(readOnly = true)
    public List<InvoiceResponseDto> findInvoiceByCustomerDocument(String document) {
        if(!customerRepository.existsByDocument(document)){
            throw new InvalidCustomerException("Customer not found. Document: " + document);
        }

        List<Invoice> invoices = invoiceRepository.findInvoiceByCustomerDocument(document);
        return  invoices.stream().map(InvoiceMapper::toDto).toList();
    }

    @Transactional(readOnly = true)
    public List<InvoiceResponseDto> findInvoiceByCustomerWhatsappNumber(String whatsappNumber) {
        if(!customerRepository.existsByWhatsappNumber(whatsappNumber)){
            throw new InvalidCustomerException("Customer not found. Whatsapp Number: " + whatsappNumber);
        }

        List<Invoice> invoices = invoiceRepository.findInvoiceByCustomerWhatsappNumber(whatsappNumber);
        return invoices.stream().map(InvoiceMapper::toDto).toList();
    }

    @Transactional(readOnly = true)
    public Page<InvoiceResponseDto> search(UUID id, UUID customerId, InvoiceStatus status, LocalDate dueDate,
                                           BigDecimal grossValueFrom, BigDecimal grossValueUntil,
                                           BigDecimal netValueFrom, BigDecimal netValueUntil,
                                           BigDecimal yetToPayFrom, BigDecimal yetToPayUntil,
                                           LocalDate issuedAtFrom, LocalDate issuedAtUntil,
                                           LocalDate dueDateFrom, LocalDate dueDateUntil,
                                           Pageable pageable){
        Specification<Invoice> specification = Specification.allOf(
                invoiceIdEquals(id), customerIdEquals(customerId), statusEquals(status), dueDateEquals(dueDate),
                grossValueFrom(grossValueFrom), grossValueUntil(grossValueUntil),
                netValueFrom(netValueFrom), netValueUntil(netValueUntil),
                yetToPayFrom(yetToPayFrom), yetToPayUntil(yetToPayUntil),
                issuedAtFrom(issuedAtFrom), issuedAtUntil(issuedAtUntil),
                dueDateFrom(dueDateFrom), dueDateUntil(dueDateUntil));
        return invoiceRepository.findAll(specification, pageable).map(InvoiceMapper::toDto);
    }

    @Transactional
    public void setInvoiceStatusToOverdue(){
        invoiceRepository.setInvoiceStatusToOverdue(LocalDate.now(), InvoiceStatus.OVERDUE, InvoiceStatus.OPEN);
    }

    @Transactional(readOnly = true)
    public List<Invoice> findInvoicesByStatus(InvoiceStatus status){
        return invoiceRepository.findInvoicesByStatus(status);
    }

    @Transactional
    public void updateLastMessageSentAt(Invoice invoice){
        invoice.setLastMessageSentAt(LocalDate.now());
        invoiceRepository.save(invoice);
    }

}
