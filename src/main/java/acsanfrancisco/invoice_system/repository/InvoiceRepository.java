package acsanfrancisco.invoice_system.repository;

import acsanfrancisco.invoice_system.entity.Invoice;
import acsanfrancisco.invoice_system.entity.enums.InvoiceStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import java.math.BigDecimal;
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

    @Modifying
    @Query("UPDATE Invoice i SET i.status = :status WHERE i.status = :currentStatus AND i.dueDate < :currentDate")
    void setInvoiceStatusToOverdue(@Param("currentDate") LocalDate currentDate,@Param("status") InvoiceStatus status,@Param("currentStatus") InvoiceStatus currentStatus);

    @Query("SELECT i from Invoice i WHERE i.status = :status")
    List<Invoice> findInvoicesByStatus(@Param("status") InvoiceStatus status);
}
