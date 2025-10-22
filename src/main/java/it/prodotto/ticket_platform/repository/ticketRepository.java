package it.prodotto.ticket_platform.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import it.prodotto.ticket_platform.model.ticket;


public interface ticketRepository extends JpaRepository<ticket, Integer> {

    // query  Custom sul nome COMPLETO dell ticket
    public Optional<ticket> findByNome(String nome);

}
