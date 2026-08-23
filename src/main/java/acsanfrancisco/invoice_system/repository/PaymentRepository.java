package acsanfrancisco.invoice_system.repository;

import acsanfrancisco.invoice_system.entity.Payment;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.UUID;

public interface PaymentRepository extends JpaRepository<Payment, UUID> {
}
