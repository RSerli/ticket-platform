package it.prodotto.ticket_platform.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import it.prodotto.ticket_platform.model.ticket;

public interface ticketRepository extends JpaRepository<ticket, Integer> {

}
