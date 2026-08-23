package acsanfrancisco.invoice_system.exception;

public class InvalidDocumentType extends RuntimeException {
    public InvalidDocumentType(String message) {
        super(message);
    }
}
