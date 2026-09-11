package it.uniroma3.siw.festival.model;

import java.time.LocalDate;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.Max;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;


// Questa classe rappresenta una Recensione scritta da un Utente
// su un determinato Film.
@Entity
public class Recensione {

    // Identificatore unico della Recensione.
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

 // Il testo della recensione non può essere vuoto.
    @NotBlank
    private String testo;

    // Il voto deve essere presente e compreso tra 1 e 10.
    @NotNull
    @Min(1)
    @Max(10)
    private Integer voto;

    // Data in cui viene scritta la Recensione.
    private LocalDate data;


    // Molte Recensioni possono riguardare lo stesso Film.
    //
    // Esempio:
    // 100 utenti possono recensire Interstellar.
    //
    // Quindi:
    // molte Recensioni -> un Film.
    @ManyToOne
    private Film film;
    
 // Molte Recensioni possono essere scritte dallo stesso Utente.
    //
    // Esempio:
    // Tommaso può recensire Interstellar,
    // Inception e Oppenheimer.
    //
    // Quindi:
    // molte Recensioni -> un Utente.
    @ManyToOne
    private Utente utente;


    // Più avanti collegheremo qui anche l'Utente
    // che ha scritto la Recensione.
    //
    // Non lo facciamo ancora perché prima
    // dobbiamo creare la classe Utente.


    // Costruttore vuoto necessario a JPA.
    public Recensione() {

    }


    // Costruttore utile per creare una Recensione.
    // Non passiamo id perché viene generato automaticamente.
    public Recensione(String testo, Integer voto, LocalDate data) {
        this.testo = testo;
        this.voto = voto;
        this.data = data;
    }


    public Long getId() {
        return id;
    }


    public void setId(Long id) {
        this.id = id;
    }


    public String getTesto() {
        return testo;
    }


    public void setTesto(String testo) {
        this.testo = testo;
    }


    public Integer getVoto() {
        return voto;
    }


    public void setVoto(Integer voto) {
        this.voto = voto;
    }


    public LocalDate getData() {
        return data;
    }


    public void setData(LocalDate data) {
        this.data = data;
    }


    public Film getFilm() {
        return film;
    }


    public void setFilm(Film film) {
        this.film = film;
    }
    
 // Restituisce l'Utente che ha scritto la Recensione.
    public Utente getUtente() {
        return utente;
    }


    // Associa un Utente alla Recensione.
    public void setUtente(Utente utente) {
        this.utente = utente;
    }
}