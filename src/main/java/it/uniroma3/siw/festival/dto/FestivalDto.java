package it.uniroma3.siw.festival.dto;

import java.time.LocalDate;

public class FestivalDto {

    private Long id;
    private String nome;
    private Integer anno;
    private String citta;
    private LocalDate dataInizio;
    private LocalDate dataFine;
    private String descrizione;

    public FestivalDto() {
    }

    public FestivalDto(Long id, String nome, Integer anno,
                       String citta, LocalDate dataInizio,
                       LocalDate dataFine, String descrizione) {
        this.id = id;
        this.nome = nome;
        this.anno = anno;
        this.citta = citta;
        this.dataInizio = dataInizio;
        this.dataFine = dataFine;
        this.descrizione = descrizione;
    }

    public Long getId() {
        return id;
    }

    public String getNome() {
        return nome;
    }

    public Integer getAnno() {
        return anno;
    }

    public String getCitta() {
        return citta;
    }

    public LocalDate getDataInizio() {
        return dataInizio;
    }

    public LocalDate getDataFine() {
        return dataFine;
    }

    public String getDescrizione() {
        return descrizione;
    }
}
