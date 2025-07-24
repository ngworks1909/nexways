package com.nithin.auth.otp;

import org.springframework.stereotype.Service;

import java.security.SecureRandom;

@Service
public class OtpGenerator {

    private static final String DIGITS = "0123456789";
    private static final SecureRandom secureRandom = new SecureRandom();

    public static String generateOtp(int length) {
        if (length <= 0) {
            throw new IllegalArgumentException("OTP length must be > 0");
        }

        StringBuilder otp = new StringBuilder(length);
        for (int i = 0; i < length; i++) {
            otp.append(DIGITS.charAt(secureRandom.nextInt(DIGITS.length())));
        }

        return otp.toString();
    }
}
