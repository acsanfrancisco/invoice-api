package acsanfrancisco.invoice_system.entity.specification;

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

    public static Specification<Payment> paymentDateEquals(LocalDate paymentDate){
        return(Root<Payment> root, CriteriaQuery<?> query, CriteriaBuilder cb) -> paymentDate == null ? null :
                cb.equal(root.get("paymentDate"), paymentDate);
    }

    public static Specification<Payment> amountEquals(BigDecimal amount){
        return (Root<Payment> root, CriteriaQuery<?> query, CriteriaBuilder cb) -> amount == null ? null :
                cb.equal(root.get("amount"), amount);
    }

    public static Specification<Payment> paymentMethodEquals(PaymentMethod paymentMethod){
        return (Root<Payment> root, CriteriaQuery<?> query, CriteriaBuilder cb) -> paymentMethod == null ? null :
                cb.equal(root.get("paymentMethod"), paymentMethod);
    }

    public static Specification<Payment> invoiceIdEquals(UUID invoiceId){
        return (Root<Payment> root, CriteriaQuery<?> query, CriteriaBuilder cb) -> invoiceId == null ? null :
                cb.equal(root.get("invoice").get("id"), invoiceId);

    }
}
