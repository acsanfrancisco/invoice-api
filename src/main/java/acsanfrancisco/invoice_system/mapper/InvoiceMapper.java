package acsanfrancisco.invoice_system.mapper;

import acsanfrancisco.invoice_system.dto.CreateInvoiceDto;
import acsanfrancisco.invoice_system.dto.InvoiceResponseDto;
import acsanfrancisco.invoice_system.entity.Customer;
import acsanfrancisco.invoice_system.entity.Invoice;

public class InvoiceMapper {

    public static Invoice toEntity(CreateInvoiceDto dto, Customer customer) {
        Invoice invoice = new Invoice();
        invoice.setGrossValue(dto.getGrossValue());
        invoice.setDiscount(dto.getDiscount());
        invoice.setNote((dto.getNote()));
        invoice.setCustomer(customer);
        return invoice;
    }

    public static InvoiceResponseDto toDto(Invoice invoice){
        InvoiceResponseDto dto= new InvoiceResponseDto();
        dto.setId(invoice.getId());
        dto.setIssuedAt(invoice.getIssuedAt());
        dto.setDueDate(invoice.getDueDate());
        dto.setGrossValue(invoice.getGrossValue());
        dto.setDiscount(invoice.getDiscount());
        dto.setNetValue(invoice.getNetValue());
        dto.setStatus(invoice.getStatus());
        dto.setNote(invoice.getNote());
        dto.setCustomer(CustomerMapper.toDto(invoice.getCustomer()));
        return dto;
    }
}
