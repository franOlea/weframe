package com.weframe.user.nservice;

import com.weframe.user.model.User;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;

import java.security.GeneralSecurityException;

@Component("beforeCreateUserValidator")
public class BeforeCreateUserValidator implements Validator {

    private static final Logger logger = Logger.getLogger(BeforeCreateUserValidator.class);

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private UserPasswordCryptographer userPasswordCryptographer;

    @Override
    public boolean supports(Class<?> aClass) {
        return User.class.isAssignableFrom(aClass);
    }

    @Override
    public void validate(Object object, Errors errors) {
        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "email", "email.blank");
        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "password", "password.blank");
        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "firstName", "firstName.blank");
        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "lastName", "lastName.blank");

        User user = (User) object;

        if(userRepository.findByEmail(user.getEmail()) != null) {
            errors.rejectValue("email", "email.used");
        }

        try {
            user.setPassword(encryptPassword(user.getPassword()));
        } catch(GeneralSecurityException e) {
            errors.reject("internal-server-error");
            logger.error("An exception occurred while trying to encrypt the user password. ", e);
        }
    }

    private String encryptPassword(String password) throws GeneralSecurityException {
        return userPasswordCryptographer.generateStoringPasswordHash(password);
    }
}
