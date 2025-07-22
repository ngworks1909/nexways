package com.nithin.auth.user;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class AuthService {
    
    @Autowired
    private AuthRepository authRepository;

    public void upsertAuth(String email){
         authRepository.findByEmail(email)
            .map(existingAuth -> {
                return authRepository.save(existingAuth);
            })
            .orElseGet(() -> {
                Auth auth = new Auth(email);
                return authRepository.save(auth);
            });
    }

    public Auth getAuthByEmail(String email){
        return authRepository.findByEmail(email).orElse(null);
    }

    public String getEmailByAuthId(String authId){
        return authRepository.findById(authId).map(Auth::getEmail).orElse(null);
    }

    public void deleteAuth(String authId) {
        authRepository.deleteById(authId);
    }
}
