package jsf.studydemo.validation;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;
import java.lang.annotation.Documented;

import static java.lang.annotation.ElementType.*;
import static java.lang.annotation.RetentionPolicy.*;

@Constraint(validatedBy = BirthdayValidator.class)
@Documented
@Target({ANNOTATION_TYPE, METHOD, FIELD})
@Retention(RUNTIME)
public @interface Birthday {
    String message() default "Wrong birthday";
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};
}
