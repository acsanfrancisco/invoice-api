package acsanfrancisco.invoice_system.exception;

public class InvoiceIsCancelledException extends RuntimeException {
    public InvoiceIsCancelledException(String message) {
        super(message);
    }
}
