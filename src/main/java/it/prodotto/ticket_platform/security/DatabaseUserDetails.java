package it.prodotto.ticket_platform.security;

import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import it.prodotto.ticket_platform.model.role;
import it.prodotto.ticket_platform.model.user;

public class DatabaseUserDetails implements UserDetails{

    private Integer id;
    
    private String username;

    private String password;

    private Set<GrantedAuthority> authorities;

    public DatabaseUserDetails(user inputUser){
        this.id = inputUser.getId();
        this.username = inputUser.getEmail();
        this.password = inputUser.getPassword();
        // prendiamo i ruoli assegnati all'user inserito
        this.authorities = new HashSet<>();
        for (role singleRole : inputUser.getRoles()){
            SimpleGrantedAuthority sGA = new SimpleGrantedAuthority(singleRole.getName());
            this.authorities.add(sGA);
        }
    }

    

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return authorities;
    }

    @Override
    public String getPassword() {
        return password;
    }

    @Override
    public String getUsername() {
        return username;
    }

    public Integer getId() {
        return id;
    }
    
}
