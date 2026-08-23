package acsanfrancisco.invoice_system.repository;

import acsanfrancisco.invoice_system.entity.Customer;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.UUID;

public interface CustomerRepository extends JpaRepository<Customer, UUID> {
}
