package com.isat46.isaback.service;

import com.isat46.isaback.model.User;
import com.isat46.isaback.model.VerificationToken;
import com.isat46.isaback.repository.VerificationTokenRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class VerificationTokenService {

    @Autowired
    private VerificationTokenRepository verificationTokenRepository;

    public void GenerateVerificationToken(User user){
        VerificationToken token = new VerificationToken(user);
        verificationTokenRepository.save(token);
    }

    public String getEmailAndVerify(String token){
        VerificationToken verificationToken = verificationTokenRepository.findOneByTokenOrderByExpirationDateDesc(token);
        if(verificationToken != null && !verificationToken.getRedeemed()){
            verificationToken.setRedeemed(true);
            verificationTokenRepository.save(verificationToken);
            return verificationToken.getEmail();
        }
        return "";
    }
}
