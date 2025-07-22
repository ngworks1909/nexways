package com.nithin.auth.validators;


import java.util.regex.Pattern;

import com.nithin.auth.interfaces.CreateRequest;
import com.nithin.auth.interfaces.LoginRequest;
import com.nithin.auth.interfaces.SignupRequest;
import com.nithin.auth.interfaces.ValidationResponse;



public class UserValidator {
    private static final String EMAIL_REGEX =
            "^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+\\.[A-Za-z]{2,6}$";

    private static final Pattern EMAIL_PATTERN = Pattern.compile(EMAIL_REGEX);

    public static boolean isValidEmail(String email) {
        if (email == null) return false;
        return EMAIL_PATTERN.matcher(email).matches();
    }

    public static boolean isValidUsername(String username) {
        if (username == null) return false;
        String regex = "^(?![_.])(?!.*[_.]{2})[a-zA-Z0-9._]{3,20}(?<![_.])$";
        return Pattern.matches(regex, username);
    }

    public static boolean isValidSignupPassword(String password) {
        if (password == null) return false;
        String regex = "^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d)(?=.*[@#$%^&+=!])(?=\\S+$).{8,}$";
        return Pattern.matches(regex, password);
    }

    public static boolean isValidLoginPassword(String password){
        return password.trim().length() >= 6;
    }

    public static ValidationResponse validateLogin(LoginRequest req){

        final String email = req.email;
        final String password = req.password;

        if(email == null || !isValidEmail(email)){
            return new ValidationResponse(false, "Invalid email");
        }

        if(password == null || !isValidLoginPassword(password)){
            return new ValidationResponse(false, "Invalid password");
        }
        return new ValidationResponse(true, "Valid credentials");
    }


    public static ValidationResponse validateSignup(SignupRequest req){
        final String email = req.email;
        if(email == null || !isValidEmail(email)){
            return new ValidationResponse(false, "Invalid email");
        }
        return new ValidationResponse(true, "Valid credentials");
    }

    public static ValidationResponse createRecord(CreateRequest req){
        final String username = req.username;
        final String password = req.password;
        final String authId = req.authId;
        if(authId == null){
            return new ValidationResponse(false, "Something went wrong");
        }
        if(username == null || !isValidUsername(username)){
            return new ValidationResponse(false, "Invalid username");
        }
        if(password == null || !isValidSignupPassword(password)){
            return new ValidationResponse(false, "Invalid password");
        }
        return new ValidationResponse(true, "Valid credentials");
    }
}
