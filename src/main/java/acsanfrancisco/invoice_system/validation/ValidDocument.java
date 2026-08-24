package acsanfrancisco.invoice_system.validation;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = DocumentValidator.class)
public @interface ValidDocument {

    String message() default "Invalid Document";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}
