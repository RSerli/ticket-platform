package it.prodotto.ticket_platform.model;

import java.util.Set;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

@Entity
@Table(name="user")
public class user {

    @Id
    private Integer id;

    @NotBlank
    @NotNull (message="Campo obbligatorio")
    @Column(unique=true)
    private String email;

    @NotBlank (message="Deve contenenre almeno un carattere")
    @NotNull (message="Campo obbligatorio")
    private String password;

    @ManyToMany(fetch = FetchType.EAGER)
    private Set<role> roles;
    
    private boolean isAvailable;

    @OneToMany (mappedBy="userAssigned")
    private Set<ticket> assignedTickets;

    private String name;

    private Integer phoneNumber;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Set<role> getRoles() {
        return roles;
    }

    public void setRoles(Set<role> roles) {
        this.roles = roles;
    }

    public boolean isAvailable() {
        return isAvailable;
    }

    public void setAvailable(boolean isAvailable) {
        this.isAvailable = isAvailable;
    }

    public Set<ticket> getAssignedTickets() {
        return assignedTickets;
    }

    public void setAssignedTickets(Set<ticket> assignedTickets) {
        this.assignedTickets = assignedTickets;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(Integer phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

}
