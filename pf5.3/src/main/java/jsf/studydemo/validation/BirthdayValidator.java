package jsf.studydemo.validation;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.util.Calendar;
import java.util.Date;

public class BirthdayValidator implements ConstraintValidator<Birthday, Date> {

    public void initialize(Birthday birthday) {
    }

    public boolean isValid(Date date, ConstraintValidatorContext ctx) {
        boolean dateCorrect = true;
        if (date != null) {
            // Method name was changed from disabledDefaultError() in bean validation 1.0
            ctx.disableDefaultConstraintViolation();
            // Check if birthdate is after now
            if (date.after(new Date())) {
                // Adding custom constraint validations was changed in bean validation 1.0
                ctx.buildConstraintViolationWithTemplate("Birthday is in the future.").addConstraintViolation();
                dateCorrect = false;
            }

            // Check if birthdate is before 1.1.1900
            Calendar cal = Calendar.getInstance();
            cal.set(Calendar.YEAR, 1900);
            cal.set(Calendar.MONTH, 0);
            cal.set(Calendar.DAY_OF_MONTH, 1);

            if (date.before(cal.getTime())) {
                // Adding custom constraint validations was changed in bean validation 1.0
                ctx.buildConstraintViolationWithTemplate("Birthday is before Jan 1, 1900.").addConstraintViolation();
                dateCorrect = false;
            }
        }
        return dateCorrect;
    }

}