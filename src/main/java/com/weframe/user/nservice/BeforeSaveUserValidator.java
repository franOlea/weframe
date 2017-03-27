package com.weframe.user.nservice;

import com.weframe.user.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;

//@Component("beforeSaveUserValidator")
public class BeforeSaveUserValidator implements Validator {

    @Autowired
    private UserRepository userRepository;

    @Override
    public boolean supports(Class<?> aClass) {
        return User.class.isAssignableFrom(aClass);
    }

    @Override
    public void validate(Object object, Errors errors) {
        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "email", "email.blank");
        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "firstName", "firstName.blank");
        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "lastName", "lastName.blank");

        User user = (User) object;

        if(userRepository.findByEmail(user.getEmail()) == null) {
            errors.rejectValue("email", "email.used");
        }
    }
}
