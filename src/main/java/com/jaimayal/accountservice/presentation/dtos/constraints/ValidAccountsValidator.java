package com.jaimayal.accountservice.presentation.dtos.constraints;

import com.jaimayal.accountservice.persistence.entities.Account;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.util.List;

public class ValidAccountsValidator implements ConstraintValidator<ValidAccounts, List<String>> {
    @Override
    public void initialize(ValidAccounts constraintAnnotation) {
        ConstraintValidator.super.initialize(constraintAnnotation);
    }

    @Override
    public boolean isValid(List<String> accounts, ConstraintValidatorContext constraintValidatorContext) {
        for (String account : accounts) {
            try {
                Account.valueOf(account.toUpperCase());
            } catch (IllegalArgumentException ex) {
                return false;
            }
        }
        
        return true;
    }
}
