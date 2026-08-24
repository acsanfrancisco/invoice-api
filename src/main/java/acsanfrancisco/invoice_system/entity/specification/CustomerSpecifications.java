package acsanfrancisco.invoice_system.entity.specification;

import acsanfrancisco.invoice_system.entity.Customer;
import acsanfrancisco.invoice_system.entity.enums.DocumentType;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Root;
import org.springframework.data.jpa.domain.Specification;

public class CustomerSpecifications {

    public static Specification<Customer> fullNameLike(String fullName){
        return (Root<Customer> root, CriteriaQuery<?> query, CriteriaBuilder cb) -> fullName == null ? null :
                cb.like(cb.upper(root.get("fullName")), "%" + fullName.toUpperCase() + "%");
    }

    public static Specification<Customer> documentLike(String document){
        return (Root<Customer> root, CriteriaQuery<?> query, CriteriaBuilder cb) -> document == null ? null :
                cb.like(cb.upper(root.get("document")), "%" + document + "%");
    }

    public static Specification<Customer> whatsappNumberEquals(String whatsappNumber){
        return (Root<Customer> root, CriteriaQuery<?> query, CriteriaBuilder cb) -> whatsappNumber == null ? null :
        cb.equal(root.get("whatsappNumber"), whatsappNumber);
    }

    public static Specification<Customer> documentTypeEquals(DocumentType documentType ){
        return (Root<Customer> root, CriteriaQuery<?> query, CriteriaBuilder cb) -> documentType == null ? null :
            cb.equal(root.get("documentType"), documentType);
    }

    public static Specification<Customer> isActive(Boolean active){
        return (Root<Customer> root, CriteriaQuery<?> query, CriteriaBuilder cb) -> active == null ? null :
                cb.equal(root.get("isActive"), active);
    }
}
