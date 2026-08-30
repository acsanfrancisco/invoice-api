package acsanfrancisco.invoice_system.exception;

public class   InvalidInvoiceException extends RuntimeException {
    public InvalidInvoiceException(String message) {
        super(message);
    }
}
