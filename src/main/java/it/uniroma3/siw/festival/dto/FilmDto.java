package it.uniroma3.siw.festival.dto;

import java.time.LocalDate;

public class FilmDto {

    private Long id;

    private String title;

    private Integer year;

    private Integer duration;

    private String genre;

    private String countryProduction;

    // Dati del Regista associato al Film.
    private Long registaId;

    private String registaNome;

    private String registaCognome;

    private LocalDate registaDataNascita;

    private String registaNazionalita;


    // Costruttore vuoto.
    public FilmDto() {

    }


    // Costruttore completo con i dati del Film
    // e del Regista.
    public FilmDto(Long id,
                   String title,
                   Integer year,
                   Integer duration,
                   String genre,
                   String countryProduction,
                   Long registaId,
                   String registaNome,
                   String registaCognome,
                   LocalDate registaDataNascita,
                   String registaNazionalita) {

        this.id = id;
        this.title = title;
        this.year = year;
        this.duration = duration;
        this.genre = genre;
        this.countryProduction = countryProduction;
        this.registaId = registaId;
        this.registaNome = registaNome;
        this.registaCognome = registaCognome;
        this.registaDataNascita = registaDataNascita;
        this.registaNazionalita = registaNazionalita;
    }


    // Getter: permettono a Jackson di leggere i campi
    // e trasformarli in JSON.

    public Long getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    public Integer getYear() {
        return year;
    }

    public Integer getDuration() {
        return duration;
    }

    public String getGenre() {
        return genre;
    }

    public String getCountryProduction() {
        return countryProduction;
    }

    public Long getRegistaId() {
        return registaId;
    }

    public String getRegistaNome() {
        return registaNome;
    }

    public String getRegistaCognome() {
        return registaCognome;
    }

    public LocalDate getRegistaDataNascita() {
        return registaDataNascita;
    }

    public String getRegistaNazionalita() {
        return registaNazionalita;
    }
}