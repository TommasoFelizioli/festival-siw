package it.uniroma3.siw.festival.model;

import java.time.LocalDate;
import java.util.List;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.AssertTrue;

@Entity
public class Festival {
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	
	private Long id;

	// Il nome del Festival non può essere vuoto.
	@NotBlank
	private String nome;

	// L'anno deve essere presente e positivo.
	@NotNull
	@Positive
	private Integer anno;

	// La città non può essere vuota.
	@NotBlank
	private String citta;

	// Le date devono essere presenti.
	@NotNull
	private LocalDate dataInizio;

	@NotNull
	private LocalDate dataFine;

	// La descrizione non può essere vuota.
	@NotBlank
	private String descrizione;
	
	// Un Festival può avere molte Proiezioni.
	// "mappedBy = festival" indica che la relazione
	// è gestita dall'attributo "festival" di Proiezione.
	@OneToMany(mappedBy = "festival")
	private List<Proiezione> proiezioni;
	
	// Un Festival può avere molti Film.
	// Allo stesso tempo, uno stesso Film può partecipare a più Festival:
	// per questo la relazione è ManyToMany.
	@ManyToMany
	private List<Film> films;
	
	public List<Film> getFilms() {
		return films;
	}
	public void setFilms(List<Film> films) {
		this.films = films;
	}
	public Festival(String nome, Integer anno, String citta, LocalDate dataInizio, LocalDate dataFine, String descrizione) {
		super();
		this.nome = nome; 
		this.anno = anno;
		this.citta = citta;
		this.dataInizio = dataInizio;
		this.dataFine = dataFine;
		this.descrizione = descrizione;
	}
	public Festival() {
		
	}
	
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

	public Integer getAnno() {
		return anno;
	}

	public void setAnno(Integer anno) {
		this.anno = anno;
	}

	public LocalDate getDataInizio() {
		return dataInizio;
	}

	public void setDataInizio(LocalDate dataInizio) {
		this.dataInizio = dataInizio;
	}

	public LocalDate getDataFine() {
		return dataFine;
	}

	public void setDataFine(LocalDate dataFine) {
		this.dataFine = dataFine;
	}

	public String getDescrizione() {
		return descrizione;
	}

	public void setDescrizione(String descrizione) {
		this.descrizione = descrizione;
	}
	public String getCitta() {
		return citta;
	}
	public void setCitta(String citta) {
		this.citta = citta;
	}
	
	public List<Proiezione> getProiezioni() {
	    return proiezioni;
	}

	public void setProiezioni(List<Proiezione> proiezioni) {
	    this.proiezioni = proiezioni;
	}
	// Controlla che la data di fine non preceda
	// la data di inizio del Festival.
	@AssertTrue(message = "La data di fine non può precedere la data di inizio")
	public boolean isDateValide() {

	    // Se manca una data, ci pensa @NotNull.
	    if (dataInizio == null || dataFine == null) {
	        return true;
	    }

	    // La fine deve essere uguale o successiva all'inizio.
	    return !dataFine.isBefore(dataInizio);
	}

}
