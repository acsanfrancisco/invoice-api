package acsanfrancisco.invoice_system.exception.handler;

import acsanfrancisco.invoice_system.exception.*;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(Exception.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public ResponseEntity<ErrorMessage> genericError(Exception exception, HttpServletRequest request) {
        return ResponseEntity
                .status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body(new ErrorMessage(request, HttpStatus.INTERNAL_SERVER_ERROR, "Unexpected error occurred."));
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    @ResponseStatus(HttpStatus.UNPROCESSABLE_CONTENT)
    public ResponseEntity<ErrorMessage> methodArgumentNotValidException(MethodArgumentNotValidException exception, HttpServletRequest request) {
        BindingResult bindingResult = exception.getBindingResult();
        return ResponseEntity
                .status(HttpStatus.UNPROCESSABLE_CONTENT)
                .body(new ErrorMessage(request, HttpStatus.UNPROCESSABLE_CONTENT, "Validation Failed.", bindingResult));
    }

    @ExceptionHandler(HttpMessageNotReadableException.class)
    @ResponseStatus(HttpStatus.UNPROCESSABLE_CONTENT)
    public ResponseEntity<ErrorMessage> httpMessageNotReadableException(HttpMessageNotReadableException exception, HttpServletRequest request) {
        return ResponseEntity
                .status(HttpStatus.UNPROCESSABLE_CONTENT)
                .body(new ErrorMessage(request, HttpStatus.UNPROCESSABLE_CONTENT, "Invalid Request Body"));
    }

    @ExceptionHandler(InvalidDocumentTypeException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ResponseEntity<ErrorMessage> invalidDocumentTypeException(InvalidDocumentTypeException exception, HttpServletRequest request) {
        return ResponseEntity
                .status(HttpStatus.BAD_REQUEST)
                .body(new ErrorMessage(request, HttpStatus.BAD_REQUEST, exception.getMessage()));
    }

    @ExceptionHandler(InvalidMessageTemplateException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ResponseEntity<ErrorMessage> invalidMessageTemplateException(InvalidMessageTemplateException exception, HttpServletRequest request) {
        return ResponseEntity
                .status(HttpStatus.BAD_REQUEST)
                .body(new ErrorMessage(request, HttpStatus.BAD_REQUEST, exception.getMessage()));
    }

    @ExceptionHandler(InvalidPaymentException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ResponseEntity<ErrorMessage> invalidPaymentException(InvalidPaymentException exception, HttpServletRequest request) {
        return ResponseEntity
                .status(HttpStatus.BAD_REQUEST)
                .body(new ErrorMessage(request, HttpStatus.BAD_REQUEST, exception.getMessage()));
    }

    @ExceptionHandler(InvalidInvoiceException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ResponseEntity<ErrorMessage> invalidInvoiceException(InvalidInvoiceException exception, HttpServletRequest request) {
        return ResponseEntity
                .status(HttpStatus.BAD_REQUEST)
                .body(new ErrorMessage(request, HttpStatus.BAD_REQUEST, exception.getMessage()));

    }

    @ExceptionHandler(InvalidCustomerException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ResponseEntity<ErrorMessage> invalidCustomerException(InvalidCustomerException exception, HttpServletRequest request) {
        return ResponseEntity
                .status(HttpStatus.BAD_REQUEST)
                .body(new ErrorMessage(request, HttpStatus.BAD_REQUEST, exception.getMessage()));
    }

    @ExceptionHandler(ResourceNotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public ResponseEntity<ErrorMessage> resourceNotFoundException(ResourceNotFoundException exception, HttpServletRequest request) {
        return ResponseEntity
                .status(HttpStatus.NOT_FOUND)
                .body(new ErrorMessage(request, HttpStatus.NOT_FOUND, exception.getMessage()));
    }
}
