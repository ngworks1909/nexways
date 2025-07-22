package com.nithin.auth.validators;

import com.nithin.auth.interfaces.ValidationResponse;

public class OtpValidator {

    //verify if each value is number
    public static ValidationResponse validateOtp(String otp){
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
