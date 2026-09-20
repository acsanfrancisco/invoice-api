package acsanfrancisco.invoice_system.repository;

import acsanfrancisco.invoice_system.entity.Invoice;
import acsanfrancisco.invoice_system.entity.enums.InvoiceStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

public interface InvoiceRepository extends JpaRepository<Invoice, UUID>, JpaSpecificationExecutor<Invoice> {

    @Query("SELECT i from Invoice i WHERE i.customer.id = :customerId")
    List<Invoice> findInvoiceByCustomerId(@Param("customerId") UUID customerId);

    @Query("SELECT i from Invoice i WHERE i.customer.document = :document")
    List<Invoice> findInvoiceByCustomerDocument(@Param("document") String document);

    @Query("SELECT i from Invoice i WHERE i.customer.whatsappNumber = :whatsappNumber")
    List<Invoice> findInvoiceByCustomerWhatsappNumber(@Param("whatsappNumber") String whatsappNumber);

    boolean existsByCustomerId(UUID customerId);

    @Modifying
    @Query("""
    UPDATE Invoice i
    SET i.status = :status,
        i.discount = 0.00,
        i.netValue = i.grossValue,
        i.yetToPay = i.grossValue
    WHERE i.status = :currentStatus
        AND i.dueDate < :currentDate
    """)
    void setInvoiceStatusToOverdue(@Param("currentDate") LocalDate currentDate,@Param("status") InvoiceStatus status,@Param("currentStatus") InvoiceStatus currentStatus);

    @Query("""
    SELECT i
    FROM Invoice i
    WHERE i.status = :status
    AND i.lastMessageSentAt is NULL
    """)
    List<Invoice> findInvoicesForIssuedNotification(@Param("status") InvoiceStatus status);

    @Query("""
    SELECT i
    FROM Invoice i
    WHERE i.status = :status
    AND i.dueDate = :today
""")
    List<Invoice> findInvoicesForDueTodayNotification(@Param("status") InvoiceStatus status,  @Param("today") LocalDate today);

    @Query("""
    SELECT i
    FROM Invoice i
    WHERE i.status = :status
    AND (
        i.lastMessageSentAt is NULL
        OR
        i.lastMessageSentAt < :comparingDate
    )""")
    List<Invoice> findInvoicesForOverdueNotification(@Param("status") InvoiceStatus status, @Param("comparingDate") LocalDate comparingDate);

    @Query("""
    SELECT i
    FROM Invoice i
    WHERE i.status = :status
    AND (
        i.lastMessageSentAt is NULL
        OR
        i.lastMessageSentAt < :comparingDate
    )""")
    List<Invoice> findInvoicesForPartialPaymentNotification(@Param("status") InvoiceStatus status, @Param("comparingDate") LocalDate comparingDate);
}