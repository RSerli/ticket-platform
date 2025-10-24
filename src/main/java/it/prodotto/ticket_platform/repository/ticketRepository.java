package it.prodotto.ticket_platform.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import it.prodotto.ticket_platform.model.state;
import it.prodotto.ticket_platform.model.ticket;
import it.prodotto.ticket_platform.model.user;




public interface ticketRepository extends JpaRepository<ticket, Integer> {

    // query  Custom sul nome COMPLETO dell ticket
    public Optional<ticket> findByNome(String nome);

    // QUERY Custom per raggruppameto ticket a secondo dello stato di lavorazione e utente assegnato
    public List<ticket> findByUserAssignedAndActualStatus(user userAssigned, state actualStatus);

    // QUERY Custom di ricerca ticket per nome
    public List<ticket> findByNomeContainingIgnoringCase(String nome);
}
