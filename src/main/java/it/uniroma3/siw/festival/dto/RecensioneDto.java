package it.uniroma3.siw.festival.dto;

import java.time.LocalDate;

public class RecensioneDto {

    private Long id;
    private String testo;
    private Integer voto;
    private LocalDate data;
    private Long filmId;
    private String username;

    public RecensioneDto() {
    }

    public RecensioneDto(Long id, String testo, Integer voto,
                         LocalDate data, Long filmId, String username) {
        this.id = id;
        this.testo = testo;
        this.voto = voto;
        this.data = data;
        this.filmId = filmId;
        this.username = username;
    }

    public Long getId() {
        return id;
    }

    public String getTesto() {
        return testo;
    }

    public Integer getVoto() {
        return voto;
    }

    public LocalDate getData() {
        return data;
    }

    public Long getFilmId() {
        return filmId;
    }

    public String getUsername() {
        return username;
    }
}