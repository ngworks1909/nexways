package com.nithin.auth.user;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.nithin.auth.interfaces.CreateRequest;
import com.nithin.auth.interfaces.LoginRequest;
import com.nithin.auth.interfaces.SignupRequest;
import com.nithin.auth.interfaces.ValidationResponse;
import com.nithin.auth.interfaces.VerifyRequest;
import com.nithin.auth.jwt.PasswordHasher;
import com.nithin.auth.jwt.TokenGenerator;
import com.nithin.auth.validators.OtpValidator;
import com.nithin.auth.validators.UserValidator;

@RestController
@RequestMapping("/api/auth/v1")
public class UserRouter {

    @Autowired
    private UserService userService;

    @Autowired
    private AuthService authService;

    @Autowired
    private TokenGenerator tokenGenerator;

    @PostMapping("/login")
    private ResponseEntity<?> login(@RequestBody LoginRequest req){
        ValidationResponse response = UserValidator.validateLogin(req);
        if(!response.success){
            return ResponseEntity.status(400).body(Map.of(
                    "success", false,
                    "message", response.message
                ));
        }

        User user = userService.getUserByEmail(req.email);
        if(user == null){
            return ResponseEntity.status(400).body(Map.of(
                    "success", false,
                    "message", "Invalid email or password"
                ));
        }

        if(!PasswordHasher.matchPassword(req.password, user.getPassword())){
            return ResponseEntity.status(400).body(Map.of(
                    "success", false,
                    "message", "Invalid email or password"
                ));
        }
        String authToken = tokenGenerator.generateToken(user.getUserId(), user.getUsername(), user.getEmail());
        return ResponseEntity.status(200).body(Map.of(
                    "success", true,
                    "message", "Login successful",
                    "authToken", authToken
            ));
    }

    @PostMapping("/signup")
    private ResponseEntity<?> signup(@RequestBody SignupRequest req){
        ValidationResponse response = UserValidator.validateSignup(req);
        if(!response.success){
            return ResponseEntity.status(400).body(Map.of(
                    "success", false,
                    "message", response.message
                ));
        }

        String email = req.email;
        User existingUser = userService.getUserByEmail(email);
        if(existingUser != null){
            return ResponseEntity.status(400).body(Map.of(
                    "success", false,
                    "message", "User already exists"
                ));
        }

        authService.upsertAuth(email);

        return ResponseEntity.status(200).body(Map.of(
                    "success", true,
                    "message", "OTP Sent"
            ));
    }


    @PostMapping("/verifyotp")
    private ResponseEntity<?> verifyOtp(@RequestBody VerifyRequest req){
        String otp = req.otp;
        String email = req.email;
        boolean isValidEmail = UserValidator.isValidEmail(email);
        if(!isValidEmail){
            return ResponseEntity.status(400).body(Map.of(
                    "success", false,
                    "message", "Something went wrong"
                ));
        }
        ValidationResponse response = OtpValidator.validateOtp(otp);
        if(!response.success){
            return ResponseEntity.status(400).body(Map.of(
                    "success", false,
                    "message", response.message
                ));
        }
        Auth auth = authService.getAuthByEmail(email);
        if(auth == null){
            return ResponseEntity.status(400).body(Map.of(
                    "success", false,
                    "message", "Something went wrong"
                ));
        }
        if(!auth.getExpiry().isAfter(java.time.LocalDateTime.now())){
            return ResponseEntity.status(400).body(Map.of(
                    "success", false,
                    "message", "OTP Expired"
                ));
        }
        if(!auth.getOtp().equals(otp)){
            return ResponseEntity.status(400).body(Map.of(
                    "success", false,
                    "message", "Invalid OTP"
                ));
        }
        return ResponseEntity.status(200).body(Map.of(
                    "success", true,
                    "message", "OTP Verified",
                    "authId", auth.getAuthId()
        ));
    }


    @PostMapping("/details")
    private ResponseEntity<?> createUser(@RequestBody CreateRequest req){
        ValidationResponse response = UserValidator.createRecord(req);
        if(!response.success){
            return ResponseEntity.status(400).body(Map.of(
                    "success", false,
                    "message", response.message
                ));
        }

        String email = authService.getEmailByAuthId(req.authId);
        if(email == null){
            return ResponseEntity.status(400).body(Map.of(
                    "success", false,
                    "message", "Something went wrong"
                ));
        }

        authService.deleteAuth(req.authId);

        
        String hashedPassword = PasswordHasher.hashPassword(req.password);
        User user = new User(req.username, email, hashedPassword);
        userService.saveUser(user);

        return ResponseEntity.status(200).body(Map.of(
                    "success", true,
                    "message", "User created"
        ));
    }
}
