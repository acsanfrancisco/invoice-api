package acsanfrancisco.invoice_system.repository;

import acsanfrancisco.invoice_system.entity.Invoice;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.UUID;

public interface InvoiceRepository extends JpaRepository<Invoice, UUID> {

    @Query("SELECT i from Invoice i WHERE i.customer.id = :customerId")
    List<Invoice> findInvoiceByCustomerId(@Param("customerId") UUID customerId);

    @Query("SELECT i from Invoice i WHERE i.customer.document = :document")
    List<Invoice> findInvoiceByCustomerDocument(@Param("document") String document);

    @Query("SELECT i from Invoice i where i.customer.whatsappNumber = :whatsappNumber")
    List<Invoice> findInvoiceByCustomerWhatsappNumber(@Param("whatsappNumber") String whatsappNumber);
}
