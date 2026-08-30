package acsanfrancisco.invoice_system.entity.specification;

import acsanfrancisco.invoice_system.entity.Invoice;
import acsanfrancisco.invoice_system.entity.enums.InvoiceStatus;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Root;
import org.springframework.data.jpa.domain.Specification;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.UUID;

public class InvoiceSpecification {

    public static Specification<Invoice> invoiceIdEquals(UUID id){
        return (Root<Invoice> root, CriteriaQuery<?> query, CriteriaBuilder cb) -> id == null ? null :
                cb.equal(root.get("id"), id);
    }

    public static Specification<Invoice> customerIdEquals(UUID customerId){
        return (Root<Invoice> root, CriteriaQuery<?> query, CriteriaBuilder cb) -> customerId == null ? null :
                cb.equal(root.get("customer").get("id"), customerId);
    }

    public static Specification<Invoice> statusEquals(InvoiceStatus status){
        return (Root<Invoice> root, CriteriaQuery<?> query, CriteriaBuilder cb) -> status == null ? null :
                cb.equal(root.get("status"), status);
    }

    public static Specification<Invoice> issuedAtBetween(LocalDate firstDate, LocalDate lastDate) {
       return (Root<Invoice> root, CriteriaQuery<?> query, CriteriaBuilder cb) -> {
           if(firstDate == null || lastDate == null){
               return null;
           }
           LocalDateTime firstDateLdt = firstDate.atStartOfDay();
           LocalDateTime lastDateLdt = lastDate.atTime(LocalTime.MAX);
           return cb.between(root.get("issuedAt"), firstDateLdt, lastDateLdt);
       };
    }

    public static Specification<Invoice> dueDateEquals(LocalDate dueDate) {
        return (Root<Invoice> root, CriteriaQuery<?> query, CriteriaBuilder cb) -> dueDate == null ? null:
                cb.equal(root.get("dueDate"), dueDate);
    }

    public static Specification<Invoice> dueDateBetween(LocalDate firstDate, LocalDate lastDate) {
        return (Root<Invoice> root, CriteriaQuery<?> query, CriteriaBuilder cb) -> firstDate == null || lastDate == null ? null :
                cb.between(root.get("dueDate"), firstDate, lastDate);
    }
}
