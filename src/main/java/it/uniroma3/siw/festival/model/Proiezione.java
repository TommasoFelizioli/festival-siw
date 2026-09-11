package it.uniroma3.siw.festival.model;

import java.time.LocalDate;
import java.time.LocalTime;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.ManyToOne;
import jakarta.validation.constraints.NotNull;

// Questa classe rappresenta una singola proiezione di un Film.
//
// Per esempio:
// Interstellar
// 5 settembre 2026
// ore 21:00
//
// Più avanti collegheremo questa Proiezione
// a un Festival, a un Film e a una Sala.
@Entity
public class Proiezione {

    // Identificatore unico della Proiezione.
    // Viene generato automaticamente dal database.
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

 // La data della Proiezione deve essere presente.
    @NotNull
    private LocalDate data;

    // L'ora di inizio deve essere presente.
    @NotNull
    private LocalTime ora;

    // Lo stato deve essere selezionato.
    @NotNull
    @Enumerated(EnumType.STRING)
    private StatoProiezione stato;
    
 // Ogni Proiezione deve appartenere a un Festival.
    @NotNull
    @ManyToOne
    private Festival festival;

    // Ogni Proiezione deve riguardare un Film.
    @NotNull
    @ManyToOne
    private Film film;

    // Ogni Proiezione deve svolgersi in una Sala.
    @NotNull
    @ManyToOne
    private Sala sala;
    
    public Proiezione() {
    }


    // Costruttore utile per creare una Proiezione
    // passando direttamente data e ora.
    public Proiezione(LocalDate data, LocalTime ora, StatoProiezione stato) {
        this.data = data;
        this.ora = ora;
        this.stato = stato;
    }


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }


    public LocalDate getData() {
        return data;
    }

    public void setData(LocalDate data) {
        this.data = data;
    }


    public LocalTime getOra() {
        return ora;
    }

    public void setOra(LocalTime ora) {
        this.ora = ora;
    }
    
    public StatoProiezione getStato() {
        return stato;
    }

    public void setStato(StatoProiezione stato) {
        this.stato = stato;
    }
    
    public Festival getFestival() {
        return festival;
    }

    public void setFestival(Festival festival) {
        this.festival = festival;
    }

    public Film getFilm() {
        return film;
    }

    public void setFilm(Film film) {
        this.film = film;
    }

    public Sala getSala() {
        return sala;
    }

    public void setSala(Sala sala) {
        this.sala = sala;
    }
}