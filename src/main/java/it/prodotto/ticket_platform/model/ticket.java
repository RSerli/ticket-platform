package it.prodotto.ticket_platform.model;

import java.time.LocalDate;
import java.util.List;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

@Entity
@Table(name = "tickets")
public class ticket {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @NotBlank
    @NotNull (message="Campo obbligatorio")
    private String nome;

    @NotNull
    @NotBlank (message="Fornire un minimo di descrizione!")
    private String descrizione;

    private LocalDate createdAt = LocalDate.now();

    @ManyToOne
    @JoinColumn(name="user_id")
    @NotNull (message="Seleziona un tecnico")
    private user userAssigned;

    @NotNull (message="Seleziona uno stato")
    @ManyToOne
    @JoinColumn(name="state_id")
    private state actualStatus;

    @OneToMany (mappedBy= "targetTicket")
    private List<Note> noteList;

    public state getActualStatus() {
        return actualStatus;
    }


    public void setActualStatus(state actualStatus) {
        this.actualStatus = actualStatus;
    }


    public Integer getId() {
        return id;
    }


    public void setId(Integer id) {
        this.id = id;
    }


    public String getNome() {
        return nome;
    }


    public void setNome(String nome) {
        this.nome = nome;
    }


    public String getDescrizione() {
        return descrizione;
    }


    public void setDescrizione(String descrizione) {
        this.descrizione = descrizione;
    }


    public LocalDate getCreatedAt() {
        return createdAt;
    }


    public void setCreatedAt(LocalDate createdAt) {
        this.createdAt = createdAt;
    }


    public user getUserAssigned() {
        return userAssigned;
    }


    public void setUserAssigned(user userAssigned) {
        this.userAssigned = userAssigned;
    }


    public List<Note> getNoteList() {
        return noteList;
    }


    public void setNoteList(List<Note> noteList) {
        this.noteList = noteList;
    }
}
