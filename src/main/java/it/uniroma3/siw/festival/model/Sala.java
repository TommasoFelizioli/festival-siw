package it.uniroma3.siw.festival.model;

import java.util.List;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;

@Entity
public class Sala {
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	
	private Long id;
	// Il nome della Sala non può essere vuoto.
	@NotBlank
	private String nome;

	// L'indirizzo non può essere vuoto.
	@NotBlank
	private String indirizzo;

	// La capienza deve essere presente e maggiore di zero.
	@NotNull
	@Positive
	private Integer capienza;
	

	@OneToMany(mappedBy = "sala")
	private List<Proiezione> proiezioni;

	public Sala(String nome, String indirizzo, Integer capienza) {
		super();
		this.nome = nome; 
		this.indirizzo = indirizzo;
		this.capienza = capienza;
	
	}
	public Sala() {
		
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
	public String getIndirizzo() {
		return indirizzo;
	}
	public void setIndirizzo(String indirizzo) {
		this.indirizzo = indirizzo;
	}
	public Integer getCapienza() {
		return capienza;
	}
	public void setCapienza(Integer capienza) {
		this.capienza = capienza;
	}
	public List<Proiezione> getProiezioni() {
		return proiezioni;
	}
	public void setProiezioni(List<Proiezione> proiezioni) {
		this.proiezioni = proiezioni;
	}
	
}
