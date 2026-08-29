package acsanfrancisco.invoice_system.repository;

import acsanfrancisco.invoice_system.entity.Customer;
import acsanfrancisco.invoice_system.entity.enums.DocumentType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface CustomerRepository extends JpaRepository<Customer, UUID>, JpaSpecificationExecutor<Customer> {

    Optional<Customer> findByDocument(String document);

    Optional<Customer> findByWhatsappNumber(String whatsappNumber);

    boolean existsByDocument(String document);

    boolean existsByWhatsappNumber(String whatsappNumber);
}
