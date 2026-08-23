package acsanfrancisco.invoice_system.repository;

import acsanfrancisco.invoice_system.entity.Invoice;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.UUID;

public interface InvoiceRepository extends JpaRepository<Invoice, UUID> {
}
