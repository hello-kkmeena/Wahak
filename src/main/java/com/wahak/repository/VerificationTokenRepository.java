package com.wahak.repository;

import com.wahak.entity.VerificationToken;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @author krishna.meena
 */

@Component
public class VerificationTokenRepository {

    List<VerificationToken> tokens=new ArrayList<>();

    public synchronized void save(VerificationToken verificationToken) {

        tokens = tokens.stream()
                .map(u -> u.getToken() == verificationToken.getToken() ? verificationToken : u)
                .collect(Collectors.toList());

        // If user was not found, add it
        if (tokens.stream().noneMatch(t -> t.getToken() == verificationToken.getToken())) {
            tokens.add(verificationToken);
        }

//        return verificationToken;
    }

    public VerificationToken findByToken(String token) {
        return tokens.stream().filter(t -> t.getToken() == token).findAny().orElseThrow();
    }

    public void delete(VerificationToken verificationToken) {

        tokens.stream()
                .filter(token -> token.equals(verificationToken))
                .findFirst()
                .ifPresent(token -> token.setIsUsed(true)); // Mark as used
    }

}
