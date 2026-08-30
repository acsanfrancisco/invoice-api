package acsanfrancisco.invoice_system.repository;

import acsanfrancisco.invoice_system.entity.Invoice;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import java.math.BigDecimal;
import java.util.List;
import java.util.UUID;

public interface InvoiceRepository extends JpaRepository<Invoice, UUID>, JpaSpecificationExecutor<Invoice> {

    @Query("SELECT i from Invoice i WHERE i.customer.id = :customerId")
    List<Invoice> findInvoiceByCustomerId(@Param("customerId") UUID customerId);

    @Query("SELECT i from Invoice i WHERE i.customer.document = :document")
    List<Invoice> findInvoiceByCustomerDocument(@Param("document") String document);

    @Query("SELECT i from Invoice i WHERE i.customer.whatsappNumber = :whatsappNumber")
    List<Invoice> findInvoiceByCustomerWhatsappNumber(@Param("whatsappNumber") String whatsappNumber);

    @Query("SELECT i from Invoice i WHERE i.grossValue >= :grossValue")
    List<Invoice> findInvoicesByGrossValueGreaterOrEqualTo(@Param("grossValue") BigDecimal grossValue);

    @Query("SELECT i from Invoice i WHERE i.grossValue <= :grossValue")
    List<Invoice> findInvoiceByGrossValueLessThanOrEqualTo(@Param("grossValue") BigDecimal grossValue);

    @Query("SELECT i from Invoice i WHERE i.netValue >= :netValue")
    List<Invoice> findInvoicesByNetValueGreaterThanOrEqualTo(@Param("netValue") BigDecimal netValue);

    @Query("SELECT i from Invoice i WHERE i.netValue <= :netValue")
    List<Invoice> findInvoicesByNetValueLessThanOrEqualTo(@Param("netValue") BigDecimal netValue);

    @Query("SELECT i from Invoice i WHERE i.yetToPay >= :yetToPay")
    List<Invoice> findInvoicesByYetToPayGreaterThanOrEqualTo(@Param("yetToPay") BigDecimal yetToPay);

    @Query("SELECT i from Invoice i WHERE i.yetToPay <= :yetToPay")
    List<Invoice> findInvoicesByYetToPayLessThanOrEqualTo(@Param("yetToPay") BigDecimal yetToPay);
}
