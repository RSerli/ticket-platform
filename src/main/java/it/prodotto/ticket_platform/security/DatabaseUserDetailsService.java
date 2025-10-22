package it.prodotto.ticket_platform.security;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import it.prodotto.ticket_platform.model.user;
import it.prodotto.ticket_platform.repository.userRepository;

@Service
public class DatabaseUserDetailsService implements UserDetailsService{

    @Autowired
    private userRepository userRepo;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Optional<user> userOpt = userRepo.findByEmail(username); 

        // check se esiste l'utente inserito
        if (userOpt.isPresent()) {
            return new DatabaseUserDetails(userOpt.get());
        } else {
            throw new UsernameNotFoundException("Utente non trovato!");
        }
    }

}
