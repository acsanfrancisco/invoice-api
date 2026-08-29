package acsanfrancisco.invoice_system.service;

import acsanfrancisco.invoice_system.dto.CreateInvoiceDto;
import acsanfrancisco.invoice_system.dto.InvoiceResponseDto;
import acsanfrancisco.invoice_system.dto.UpdateInvoiceDto;
import acsanfrancisco.invoice_system.entity.Customer;
import acsanfrancisco.invoice_system.entity.Invoice;
import acsanfrancisco.invoice_system.entity.enums.InvoiceStatus;
import acsanfrancisco.invoice_system.exception.CustomerIsNotActiveException;
import acsanfrancisco.invoice_system.exception.CustomerNotFoundException;
import acsanfrancisco.invoice_system.exception.InvoiceIsCancelledException;
import acsanfrancisco.invoice_system.exception.InvoiceNotFoundException;
import acsanfrancisco.invoice_system.mapper.InvoiceMapper;
import acsanfrancisco.invoice_system.repository.CustomerRepository;
import acsanfrancisco.invoice_system.repository.InvoiceRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class InvoiceService {

    private static final long DAYS_TO_DUE_DATE = 5;
    private final InvoiceRepository invoiceRepository;
    private final CustomerRepository customerRepository;

    @Transactional
    public InvoiceResponseDto createInvoice (CreateInvoiceDto dto){
        Customer customer = customerRepository.findById(dto.getCustomer_id())
                .orElseThrow(()->new CustomerNotFoundException("Customer not found. ID: " + dto.getCustomer_id()));
        if(customer.getIsActive() == false){
            throw new CustomerIsNotActiveException("Customer is not active. ID: " + dto.getCustomer_id());
        }

        Invoice invoice = InvoiceMapper.toEntity(dto, customer);
        LocalDate dueDate = invoice.getIssuedAt().plusDays(DAYS_TO_DUE_DATE).toLocalDate();
        BigDecimal netValue = invoice.getGrossValue().subtract(dto.getDiscount());
        invoice.setDueDate(dueDate);
        invoice.setNetValue(netValue);
        invoice.setStatus(InvoiceStatus.OPEN);

        return InvoiceMapper
                .toDto(invoiceRepository.save(invoice));
    }

    @Transactional
    public InvoiceResponseDto updateInvoice(UpdateInvoiceDto dto){
        Invoice invoice = invoiceRepository.findById(dto.getId())
                .orElseThrow(()->new InvoiceNotFoundException("Invoice not found for ID: " + dto.getId()));
        if(invoice.getStatus() == InvoiceStatus.CANCELLED){
            throw new InvoiceIsCancelledException("Invoice is set cancelled. ID: " + invoice.getId());
        }

        if(dto.getCustomerId() != null){
            Customer customer = customerRepository.findById(dto.getCustomerId())
                    .orElseThrow(()->new CustomerNotFoundException("Costumer not found for ID: " + dto.getCustomerId()));
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
                .orElseThrow(()->new InvoiceNotFoundException("Invoice not found for ID: " + id));
        if(invoice.getStatus() == InvoiceStatus.CANCELLED){
            throw new InvoiceIsCancelledException("Invoice is already set cancelled. ID: " + invoice.getId());
        }
        invoice.setStatus(InvoiceStatus.CANCELLED);
    }

    @Transactional(readOnly = true)
    public InvoiceResponseDto findInvoiceById(UUID id) {
        Invoice invoice = invoiceRepository.findById(id)
                .orElseThrow(()->new InvoiceNotFoundException("Invoice not found for ID: " + id));
        return InvoiceMapper.toDto(invoice);
    }

    @Transactional(readOnly = true)
    public List<InvoiceResponseDto> findInvoicesByCustomerId(UUID id) {
        if(!customerRepository.existsById(id)){
            throw new CustomerNotFoundException("Customer not found. ID: " + id);
        }
        List<Invoice> invoices = invoiceRepository.findInvoiceByCustomerId(id);
        return invoices.stream().map(InvoiceMapper::toDto).toList();
    }

    @Transactional(readOnly = true)
    public List<InvoiceResponseDto> findInvoiceByCustomerDocument(String document) {
        if(!customerRepository.existsByDocument(document)){
            throw new CustomerNotFoundException("Customer not found. Document: " + document);
        }

        List<Invoice> invoices = invoiceRepository.findInvoiceByCustomerDocument(document);
        return  invoices.stream().map(InvoiceMapper::toDto).toList();
    }

    @Transactional(readOnly = true)
    public List<InvoiceResponseDto> findInvoiceByCustomerWhatsappNumber(String whatsappNumber) {
        if(!customerRepository.existsByWhatsappNumber(whatsappNumber)){
            throw new CustomerNotFoundException("Customer not found. Whatsapp Number: " + whatsappNumber);
        }

        List<Invoice> invoices = invoiceRepository.findInvoiceByCustomerWhatsappNumber(whatsappNumber);
        return invoices.stream().map(InvoiceMapper::toDto).toList();
    }
}
