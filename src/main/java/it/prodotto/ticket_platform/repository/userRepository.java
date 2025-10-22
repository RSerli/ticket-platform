package it.prodotto.ticket_platform.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import it.prodotto.ticket_platform.model.user;


public interface userRepository extends JpaRepository<user, Integer>{

    public Optional<user> findByEmail(String email);
}
