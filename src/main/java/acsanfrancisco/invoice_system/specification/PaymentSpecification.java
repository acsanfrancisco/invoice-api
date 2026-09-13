package acsanfrancisco.invoice_system.specification;

import acsanfrancisco.invoice_system.entity.Payment;
import acsanfrancisco.invoice_system.entity.enums.PaymentMethod;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Root;
import org.springframework.data.jpa.domain.Specification;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.UUID;

public class PaymentSpecification {

    public static Specification<Payment> paymentIdEquals(UUID id) {
        return (Root<Payment> root, CriteriaQuery<?> query, CriteriaBuilder cb) -> id == null ? null :
                cb.equal(root.get("id"), id);
    }

    public static Specification<Payment> invoiceIdEquals(UUID invoiceId){
        return (Root<Payment> root, CriteriaQuery<?> query, CriteriaBuilder cb) -> invoiceId == null ? null :
                cb.equal(root.get("invoice").get("id"), invoiceId);

    }
    public static Specification<Payment> customerIdEquals(UUID customerId){
        return (Root<Payment> root, CriteriaQuery<?> query, CriteriaBuilder cb) -> customerId == null ? null :
                cb.equal(root.get("invoice").get("customer").get("id"), customerId);
    }

    public static Specification<Payment> paymentMethodEquals(PaymentMethod paymentMethod){
        return (Root<Payment> root, CriteriaQuery<?> query, CriteriaBuilder cb) -> paymentMethod == null ? null :
                cb.equal(root.get("paymentMethod"), paymentMethod);
    }

    public static Specification<Payment> paymentDateEquals(LocalDate paymentDate){
        return(Root<Payment> root, CriteriaQuery<?> query, CriteriaBuilder cb) -> paymentDate == null ? null :
                cb.equal(root.get("paymentDate"), paymentDate);
    }

    public static Specification<Payment> paymentDateFrom(LocalDate paymentDateFrom){
        return (Root<Payment> root, CriteriaQuery<?> query, CriteriaBuilder cb) -> paymentDateFrom == null ? null :
                cb.greaterThanOrEqualTo(root.get("paymentDate"), paymentDateFrom);
    }

    public static Specification<Payment> paymentDateUntil(LocalDate paymentDateUntil){
        return (Root<Payment> root, CriteriaQuery<?> query, CriteriaBuilder cb) -> paymentDateUntil == null ? null :
                cb.lessThanOrEqualTo(root.get("paymentDate"), paymentDateUntil);
    }

    public static Specification<Payment> amountFrom(BigDecimal amountFrom){
        return (Root<Payment> root, CriteriaQuery<?> query, CriteriaBuilder cb) ->  amountFrom == null ? null :
                cb.greaterThanOrEqualTo(root.get("amount"), amountFrom);
    }

    public static Specification<Payment> amountUntil(BigDecimal amountUntil){
        return (Root<Payment> root, CriteriaQuery<?> query, CriteriaBuilder cb) ->  amountUntil == null ? null :
                cb.lessThanOrEqualTo(root.get("amount"), amountUntil);
    }
}
