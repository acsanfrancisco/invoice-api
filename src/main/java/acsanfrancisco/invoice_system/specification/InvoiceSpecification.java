package acsanfrancisco.invoice_system.specification;

import acsanfrancisco.invoice_system.entity.Invoice;
import acsanfrancisco.invoice_system.entity.enums.InvoiceStatus;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Root;
import org.springframework.data.jpa.domain.Specification;
import java.math.BigDecimal;
import java.time.*;
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

    public static Specification<Invoice> dueDateEquals(LocalDate dueDate) {
        return (Root<Invoice> root, CriteriaQuery<?> query, CriteriaBuilder cb) -> dueDate == null ? null:
                cb.equal(root.get("dueDate"), dueDate);
    }

    public static Specification<Invoice> grossValueFrom(BigDecimal grossValueFrom) {
        return (Root<Invoice> root, CriteriaQuery<?> query, CriteriaBuilder cb) -> grossValueFrom == null ? null :
                cb.greaterThanOrEqualTo(root.get("grossValue"), grossValueFrom);
    }

    public static Specification<Invoice> grossValueUntil(BigDecimal grossValueUntil){
        return (Root<Invoice> root, CriteriaQuery<?> query, CriteriaBuilder cb) -> grossValueUntil == null ? null :
                cb.lessThanOrEqualTo(root.get("grossValue"), grossValueUntil);
    }

    public static Specification<Invoice> netValueFrom(BigDecimal netValueFrom) {
        return (Root<Invoice> root, CriteriaQuery<?> query, CriteriaBuilder cb) -> netValueFrom == null ? null :
                cb.greaterThanOrEqualTo(root.get("netValue"), netValueFrom);
    }

    public static Specification<Invoice> netValueUntil(BigDecimal netValueUntil){
        return (Root<Invoice> root, CriteriaQuery<?> query, CriteriaBuilder cb) -> netValueUntil == null ? null :
                cb.lessThanOrEqualTo(root.get("netValue"), netValueUntil);
    }

    public static Specification<Invoice> yetToPayFrom(BigDecimal yetToPayFrom) {
        return (Root<Invoice> root, CriteriaQuery<?> query, CriteriaBuilder cb) -> yetToPayFrom == null ? null :
                cb.greaterThanOrEqualTo(root.get("yetToPay"), yetToPayFrom);
    }

    public static Specification<Invoice> yetToPayUntil(BigDecimal yetToPayUntil) {
        return(Root<Invoice> root, CriteriaQuery<?> query, CriteriaBuilder cb) -> yetToPayUntil == null ? null :
                cb.lessThanOrEqualTo(root.get("yetToPay"), yetToPayUntil);
    }

    public static Specification<Invoice> issuedAtFrom(LocalDate issuedAtFrom) {
        return(Root<Invoice> root, CriteriaQuery<?> query, CriteriaBuilder cb) -> issuedAtFrom == null ? null :
                cb.greaterThanOrEqualTo(root.get("issuedAt"), issuedAtFrom.atStartOfDay().atZone(ZoneId.systemDefault()));
    }

    public static Specification<Invoice> issuedAtUntil(LocalDate issuedAtUntil) {
        return(Root<Invoice> root, CriteriaQuery<?> query, CriteriaBuilder cb) -> issuedAtUntil == null ? null :
                cb.lessThanOrEqualTo(root.get("issuedAt"), issuedAtUntil.atStartOfDay().atZone(ZoneId.systemDefault()));
    }

    public static Specification<Invoice> dueDateFrom(LocalDate dueDateFrom) {
        return(Root<Invoice> root, CriteriaQuery<?> query, CriteriaBuilder cb) -> dueDateFrom == null ? null :
                cb.greaterThanOrEqualTo(root.get("dueDate"), dueDateFrom);
    }

    public static Specification<Invoice> dueDateUntil(LocalDate dueDateUntil) {
        return(Root<Invoice> root, CriteriaQuery<?> query, CriteriaBuilder cb) -> dueDateUntil == null ? null :
                cb.lessThanOrEqualTo(root.get("dueDate"), dueDateUntil);
    }
}
