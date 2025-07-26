package com.nithin.auth.validators;

import org.springframework.stereotype.Service;

import com.nithin.auth.interfaces.ValidationResponse;


@Service
public class OtpValidator {

    //verify if each value is number
    public ValidationResponse validateOtp(String otp){
        if (otp == null || otp.trim().length() != 6) {
            return new ValidationResponse(false, "Invalid OTP");
        }
    
        for (char ch : otp.trim().toCharArray()) {
            if (!Character.isDigit(ch)) {
                return new ValidationResponse(false, "OTP must contain only digits");
            }
        }
    
        return new ValidationResponse(true, "Valid OTP");
    }
}
