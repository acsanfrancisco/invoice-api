package acsanfrancisco.invoice_system.repository;

import acsanfrancisco.invoice_system.entity.Payment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

public interface PaymentRepository extends JpaRepository<Payment, UUID> {

    @Query("SELECT p FROM Payment p WHERE p.invoice.id = :id")
    List<Payment> findPaymentsByInvoiceId(@Param("id") UUID id);

    @Query("SELECT p FROM Payment p WHERE p.invoice.customer.document = :document")
    List<Payment> findPaymentsByCustomerDocument(@Param("document") String document);

    @Query("SELECT p FROM Payment p WHERE p.invoice.customer.id = :customerId")
    List<Payment> findPaymentsByCustomerId(@Param("customerId") UUID customerId);

    @Query("SELECT p from Payment p WHERE p.paymentDate = :paymentDate")
    List<Payment> findPaymentsByPaymentDate(@Param("paymentDate") LocalDate paymentDate);

    @Query("SELECT p from Payment p WHERE p.amount >= :amount")
    List<Payment> findPaymentsGreaterThan(@Param("amount") BigDecimal amount);

    @Query("SELECT p from Payment p WHERE p.invoice.customer.id = :customerId AND p.amount >= :amount")
    List<Payment> findPaymentsByCustomerIdGreaterThan(@Param("customerId") UUID customerId, @Param("amount")BigDecimal amount);

}
