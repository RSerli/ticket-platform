package it.prodotto.ticket_platform.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import it.prodotto.ticket_platform.model.Note;

public interface  noteRepository extends JpaRepository<Note, Integer> {

}
