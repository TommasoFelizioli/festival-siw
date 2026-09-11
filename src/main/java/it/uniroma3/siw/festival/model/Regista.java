package it.uniroma3.siw.festival.model;

import java.time.LocalDate;
import java.util.List;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Past;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;


// Questa classe rappresenta un Regista.
//
// @Entity dice a JPA/Hibernate che questa classe
// deve essere salvata nel database.
//
// Quindi Hibernate creerà una tabella
// corrispondente ai Registi.
@Entity
public class Regista {


    // id è la chiave primaria del Regista.
    //
    // @Id indica che questo campo identifica
    // in modo univoco ogni Regista.
    //
    // @GeneratedValue fa generare automaticamente
    // il valore dell'id dal database.
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;


 // Nome del Regista: non può essere vuoto.
    @NotBlank
    private String nome;

    // Cognome del Regista: non può essere vuoto.
    @NotBlank
    private String cognome;

    // La data di nascita deve essere presente
    // e deve essere una data passata.
    @NotNull
    @Past
    private LocalDate dataNascita;

    // Nazionalità del Regista: non può essere vuota.
    @NotBlank
    private String nazionalita;


    // Costruttore vuoto.
    //
    // JPA ha bisogno di un costruttore
    // senza parametri per poter creare
    // gli oggetti recuperati dal database.
    public Regista() {
    }


    // Costruttore che ci permette di creare
    // facilmente un nuovo Regista
    // specificando tutti i suoi dati.
    //
    // Non inseriamo l'id perché verrà
    // generato automaticamente dal database.
    public Regista(String nome, String cognome,
                   LocalDate dataNascita, String nazionalita) {

        this.nome = nome;
        this.cognome = cognome;
        this.dataNascita = dataNascita;
        this.nazionalita = nazionalita;
    }


    // Getter e Setter permettono
    // di leggere e modificare gli attributi
    // dell'oggetto Regista.

    public Long getId() {
        return id;
    }


    public void setId(Long id) {
        this.id = id;
    }


    public String getNome() {
        return nome;
    }


    public void setNome(String nome) {
        this.nome = nome;
    }


    public String getCognome() {
        return cognome;
    }


    public void setCognome(String cognome) {
        this.cognome = cognome;
    }


    public LocalDate getDataNascita() {
        return dataNascita;
    }


    public void setDataNascita(LocalDate dataNascita) {
        this.dataNascita = dataNascita;
    }


    public String getNazionalita() {
        return nazionalita;
    }


    public void setNazionalita(String nazionalita) {
        this.nazionalita = nazionalita;
    }
    
 // Un Regista può aver diretto più Film.
    //
    // @OneToMany significa:
    // un singolo Regista può essere associato
    // a molti Film.
    //
    // mappedBy = "regista" significa che
    // la relazione viene gestita dal campo
    // "regista" presente dentro Film.
    //
    // Quindi la chiave esterna resta
    // nella tabella Film.
    @OneToMany(mappedBy = "regista")
    private List<Film> films;
    
 // Restituisce tutti i Film
 // diretti da questo Regista.
 public List<Film> getFilms() {
     return films;
 }


 // Permette di impostare
 // la lista dei Film del Regista.
 public void setFilms(List<Film> films) {
     this.films = films;
 }
}