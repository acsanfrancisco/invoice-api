package acsanfrancisco.invoice_system.exception;

public class DocumentAlreadyRegisteredException extends RuntimeException {
    public DocumentAlreadyRegisteredException(String message) {
        super(message);
    }
}
