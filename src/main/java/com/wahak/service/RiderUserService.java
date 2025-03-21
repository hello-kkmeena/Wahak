package com.wahak.service;

import com.wahak.entity.Chalak;
import com.wahak.repository.ChalakRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.Optional;

/**
 * @author krishna.meena
 */

@Service
public class RiderUserService implements UserDetailsService {

    @Autowired
    private ChalakRepository chalakRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

        Optional<Chalak> chalakOpt = chalakRepository.findByActiveId(Integer.valueOf(username));
        if(chalakOpt.isPresent()) {
            Chalak chalak = chalakOpt.get();
            return new User(String.valueOf(chalak.getId()), "", Collections.emptyList());
        }
        return null;

    }
}
