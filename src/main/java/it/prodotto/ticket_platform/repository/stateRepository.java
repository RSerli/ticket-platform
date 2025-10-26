package it.prodotto.ticket_platform.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import it.prodotto.ticket_platform.model.state;

public interface stateRepository extends JpaRepository<state, Integer> {

    // QUERY Custom di ricerca ticket per status
    public state findByName(String name);
}
