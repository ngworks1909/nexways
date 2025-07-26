package com.nithin.auth.authentication;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class AuthService {
    
    @Autowired
    private AuthRepository authRepository;

    public String upsertAuth(String email){
         return authRepository.findByEmail(email)
            .map(existingAuth -> {
                return authRepository.save(existingAuth);
            })
            .orElseGet(() -> {
                Auth auth = new Auth(email);
                return authRepository.save(auth);
            }).getOtp();
    }

    public Auth getAuthByEmail(String email){
        return authRepository.findByEmail(email).orElse(null);
    }

    public Auth getAuth(String authId){
        return authRepository.findById(authId).orElse(null);
    }


    public void deleteAuth(String authId) {
        authRepository.deleteById(authId);
    }
}
