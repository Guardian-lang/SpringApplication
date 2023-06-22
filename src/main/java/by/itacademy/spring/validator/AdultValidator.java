package by.itacademy.spring.validator;

import by.itacademy.spring.dto.UserCreateEditDto;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.time.LocalDate;

@Component
@RequiredArgsConstructor
public class AdultValidator implements ConstraintValidator<Adult, UserCreateEditDto> {

    @Override
    public boolean isValid(UserCreateEditDto value, ConstraintValidatorContext context) {
        int validValueYear = LocalDate.now().getYear() - value.getBirthDate().getYear();
        int validValueMonth = LocalDate.now().getMonthValue() - value.getBirthDate().getMonthValue();
        int validValueDay = LocalDate.now().getDayOfMonth() - value.getBirthDate().getDayOfMonth();
        if (validValueYear < 18) {
            return false;
        }
        else if (validValueYear == 18 && validValueMonth < 0) {
            return false;
        }
        else if (validValueYear == 18 && validValueMonth == 0 && validValueDay < 0) {
            return false;
        }
        else return true;
    }
}
