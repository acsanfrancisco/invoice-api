package acsanfrancisco.invoice_system.service;

import acsanfrancisco.invoice_system.entity.Invoice;
import acsanfrancisco.invoice_system.entity.enums.MessageType;
import acsanfrancisco.invoice_system.exception.InvalidMessageTemplateException;
import acsanfrancisco.invoice_system.repository.MessageTemplateRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.math.BigDecimal;
import java.text.NumberFormat;
import java.time.format.DateTimeFormatter;
import java.util.Locale;

@Service
@RequiredArgsConstructor
public class MessageTemplateService {

    private final MessageTemplateRepository messageTemplateRepository;
    private static final DateTimeFormatter DATE_FORMATTER = DateTimeFormatter.ofPattern("dd/MM/yyyy");

    @Transactional(readOnly = true)
    public String buildMessage(MessageType messageType, Invoice invoice) {
        String message = messageTemplateRepository.findByMessageType(messageType)
                .orElseThrow(() -> new InvalidMessageTemplateException("Message type not found. MessageType: " + messageType));

        return message
                .replace("{{fullName}}", invoice.getCustomer().getFullName())
                .replace("{{document}}", invoice.getCustomer().getDocument())
                .replace("{{grossValue}}", formatCurrency(invoice.getGrossValue()))
                .replace("{{dueDate}}", invoice.getDueDate().format(DATE_FORMATTER))
                .replace("{{netValue}}", formatCurrency(invoice.getNetValue()))
                .replace("{{discount}}", formatCurrency(invoice.getDiscount()));
    }

    private String formatCurrency(BigDecimal amount) {
        return NumberFormat
                .getCurrencyInstance(Locale.of("pt", "BR"))
                .format(amount);
    }
}
