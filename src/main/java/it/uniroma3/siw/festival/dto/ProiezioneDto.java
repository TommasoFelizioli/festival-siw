package it.uniroma3.siw.festival.dto;

import java.time.LocalDate;
import java.time.LocalTime;

public class ProiezioneDto {

    private Long id;
    private LocalDate data;
    private LocalTime ora;
    private String stato;
    private Long filmId;
    private String filmTitle;
    private Long salaId;
    private String salaNome;

    public ProiezioneDto() {
    }

    public ProiezioneDto(Long id, LocalDate data, LocalTime ora,
                         String stato, Long filmId, String filmTitle,
                         Long salaId, String salaNome) {
        this.id = id;
        this.data = data;
        this.ora = ora;
        this.stato = stato;
        this.filmId = filmId;
        this.filmTitle = filmTitle;
        this.salaId = salaId;
        this.salaNome = salaNome;
    }

    public Long getId() {
        return id;
    }

    public LocalDate getData() {
        return data;
    }

    public LocalTime getOra() {
        return ora;
    }

    public String getStato() {
        return stato;
    }

    public Long getFilmId() {
        return filmId;
    }

    public String getFilmTitle() {
        return filmTitle;
    }

    public Long getSalaId() {
        return salaId;
    }

    public String getSalaNome() {
        return salaNome;
    }
}