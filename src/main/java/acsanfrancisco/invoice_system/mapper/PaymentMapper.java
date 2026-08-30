package acsanfrancisco.invoice_system.mapper;

import acsanfrancisco.invoice_system.dto.CreatePaymentDto;
import acsanfrancisco.invoice_system.dto.PaymentResponseDto;
import acsanfrancisco.invoice_system.entity.Invoice;
import acsanfrancisco.invoice_system.entity.Payment;

public class PaymentMapper {

    public static Payment toEntity(CreatePaymentDto dto, Invoice invoice) {
        Payment payment = new Payment();
        payment.setPaymentDate(dto.getPaymentDate());
        payment.setAmount(dto.getAmount());
        payment.setPaymentMethod(dto.getPaymentMethod());
        payment.setInvoice(invoice);
        return payment;
    }

    public static PaymentResponseDto toDto(Payment payment){
        PaymentResponseDto dto = new PaymentResponseDto();
        dto.setId(payment.getId());
        dto.setPaymentDate(payment.getPaymentDate());
        dto.setAmount(payment.getAmount());
        dto.setPaymentMethod(payment.getPaymentMethod());
        return dto;
    }
}
