package com.nithin.auth.api;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.nithin.auth.authentication.Auth;
import com.nithin.auth.authentication.AuthService;
import com.nithin.auth.interfaces.CreateRequest;
import com.nithin.auth.interfaces.LoginRequest;
import com.nithin.auth.interfaces.SignupRequest;
import com.nithin.auth.interfaces.ValidationResponse;
import com.nithin.auth.interfaces.VerifyRequest;
import com.nithin.auth.jwt.PasswordHasher;
import com.nithin.auth.jwt.TokenGenerator;
import com.nithin.auth.mailer.MailService;
import com.nithin.auth.user.User;
import com.nithin.auth.user.UserService;
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

    @Autowired
    private JavaMailSender mailSender;

    @Autowired
    private MailService mailService;

    @Autowired
    private OtpValidator otpValidator;

    @Value("${sender.email}")
    private String senderEmail;





    private String subject = "Verify your email for Nexways";

    private void sendMail(String to, String body){
        SimpleMailMessage mailMessage = new SimpleMailMessage();
        mailMessage.setFrom(senderEmail);
        mailMessage.setTo(to);
        mailMessage.setSubject(subject);
        mailMessage.setText(body);
        mailSender.send(mailMessage);
    }

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

        String otp = authService.upsertAuth(email);
        String body = mailService.getBody(otp);
        sendMail(email, body);
        
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
        ValidationResponse response = otpValidator.validateOtp(otp);
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

        Auth auth = authService.getAuth(req.authId);
        if(auth == null){
            return ResponseEntity.status(400).body(Map.of(
                    "success", false,
                    "message", "Something went wrong"
                ));
        }

        if(auth.getExpiry().isBefore(java.time.LocalDateTime.now())){
            return ResponseEntity.status(400).body(Map.of(
                    "success", false,
                    "message", "Something went wrong"
                ));
        }

        authService.deleteAuth(req.authId);

        
        String hashedPassword = PasswordHasher.hashPassword(req.password);
        userService.saveUser(new User(req.username, auth.getEmail(), hashedPassword));

        return ResponseEntity.status(200).body(Map.of(
                    "success", true,
                    "message", "User created"
        ));
    }
}
