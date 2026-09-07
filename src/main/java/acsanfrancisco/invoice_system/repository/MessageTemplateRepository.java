package acsanfrancisco.invoice_system.repository;

import acsanfrancisco.invoice_system.entity.MessageTemplate;
import acsanfrancisco.invoice_system.entity.enums.MessageType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import java.util.Optional;
import java.util.UUID;

public interface MessageTemplateRepository extends JpaRepository<MessageTemplate,UUID> {

    @Query("SELECT m.message FROM MessageTemplate m WHERE m.messageType = :messageType")
    Optional<String> findByMessageType(@Param("messageType") MessageType messageType);
}
