package it.uniroma3.siw.festival.model;

import java.util.List;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;


// Questa classe rappresenta un Utente dell'applicazione.
//
// Un Utente potrà effettuare il login
// e avere un ruolo, per esempio USER oppure ADMIN.
@Entity
public class Utente {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;


    // Nome utilizzato dall'Utente per effettuare il login.
    private String username;


    // Password dell'Utente.
    //
    // Più avanti NON la salveremo in chiaro:
    // useremo Spring Security per codificarla.
    private String password;


    // Ruolo dell'Utente.
    //
    // Per esempio:
    // USER
    // ADMIN
    private String ruolo;


    // Un Utente può scrivere molte Recensioni.
    //
    // La relazione viene gestita dall'attributo
    // "utente" che aggiungeremo nella classe Recensione.
    @OneToMany(mappedBy = "utente")
    private List<Recensione> recensioni;


    // Costruttore vuoto necessario a JPA.
    public Utente() {

    }


    public Utente(String username, String password, String ruolo) {
        this.username = username;
        this.password = password;
        this.ruolo = ruolo;
    }


    public Long getId() {
        return id;
    }


    public void setId(Long id) {
        this.id = id;
    }


    public String getUsername() {
        return username;
    }


    public void setUsername(String username) {
        this.username = username;
    }


    public String getPassword() {
        return password;
    }


    public void setPassword(String password) {
        this.password = password;
    }


    public String getRuolo() {
        return ruolo;
    }


    public void setRuolo(String ruolo) {
        this.ruolo = ruolo;
    }


    public List<Recensione> getRecensioni() {
        return recensioni;
    }


    public void setRecensioni(List<Recensione> recensioni) {
        this.recensioni = recensioni;
    }
}