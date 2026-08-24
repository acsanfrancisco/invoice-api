package acsanfrancisco.invoice_system.exception;

public class CustomerIsNotActiveException extends RuntimeException {
    public CustomerIsNotActiveException(String message) {
        super(message);
    }
}
