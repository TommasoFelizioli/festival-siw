// Qui mettiamo le classi che rappresentano le "cose"
// della nostra applicazione, in questo caso i Film.
package it.uniroma3.siw.festival.model;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.FetchType;
import java.util.List;
import jakarta.persistence.ManyToMany;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

//Con @Entity diciamo a Spring/JPA:
//"Gli oggetti Film devono poter essere salvati nel database".
@Entity
public class Film {
	
	// Questo è l'identificatore unico di ogni film.
    // @Id dice che sarà la chiave primaria nel database.
	@Id
    
	// L'id non lo scegliamo noi:
    // viene generato automaticamente quando salviamo il film.
	@GeneratedValue(strategy = GenerationType.AUTO)
	
	// Questi sono i dati che caratterizzano ogni Film.
    // Nel database corrisponderanno alle colonne della tabella film.
	private Long id;
	// Il titolo non può essere null, vuoto o composto solo da spazi.
	@NotBlank
	private String title;

	// L'anno deve essere presente.
	@NotNull
	private Integer year;

	// La durata deve essere presente e maggiore di zero.
	@NotNull
	@Positive
	private Integer duration;

	// Il genere non può essere vuoto.
	@NotBlank
	private String genre;

	// Il paese di produzione non può essere vuoto.
	@NotBlank
	private String countryProduction;
	
	
	@OneToMany(mappedBy = "film")
	private List<Proiezione> proiezioni;
	public List<Proiezione> getProiezioni() {
		return proiezioni;
	}
	
	// Un Film può ricevere molte Recensioni.
	//
	// La relazione viene gestita dall'attributo "film"
	// presente nella classe Recensione.
	@OneToMany(mappedBy = "film")
	private List<Recensione> recensioni;

	public void setProiezioni(List<Proiezione> proiezioni) {
		this.proiezioni = proiezioni;
	}

	// Questo costruttore ci permette di creare facilmente un Film
    // passando direttamente tutti i suoi dati.
    //
    // Esempio:
    // new Film("Interstellar", 2014, 169, "Science Fiction", "USA")
    //
    // Non passiamo l'id perché viene generato automaticamente.
	public Film(String title, Integer year, Integer duration, String genre, String countryProduction) {
		super();
		this.title = title;  // Salviamo dentro il nuovo oggetto i valori che abbiamo ricevuto.
		this.year = year;
		this.duration = duration;
		this.genre = genre;
		this.countryProduction = countryProduction;
	}
	
	 // Costruttore vuoto.
    // Serve perché JPA deve poter creare un oggetto Film
    // anche senza passargli subito tutti i valori.
	public Film() {
		
	}

	// I GETTER servono per LEGGERE i valori dell'oggetto.
    // Questo, per esempio, restituisce l'id del Film.
	public Long getId() {
		return id;
	}
    
    // I SETTER servono per MODIFICARE i valori dell'oggetto.
    // Questo assegna un nuovo valore all'id.
	public void setId(Long id) {
		this.id = id;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public Integer getYear() {
		return year;
	}

	public void setYear(Integer year) {
		this.year = year;
	}

	public Integer getDuration() {
		return duration;
	}

	public void setDuration(Integer duration) {
		this.duration = duration;
	}

	public String getGenre() {
		return genre;
	}

	public void setGenre(String genre) {
		this.genre = genre;
	}

	public String getCountryProduction() {
		return countryProduction;
	}

	public void setCountryProduction(String countryProduction) {
		this.countryProduction = countryProduction;
	}
	// Ogni Film ha un Regista.
	//
	// LAZY significa che il Regista non deve
	// essere caricato automaticamente insieme al Film.
	// Verrà caricato quando ne avremo bisogno.
	//
	// Questo ci permette di confrontare
	// il caricamento LAZY con una query JOIN FETCH.
	@ManyToOne(fetch = FetchType.LAZY)
	private Regista regista;
	
	// Restituisce il Regista associato al Film.
	public Regista getRegista() {
	    return regista;
	}


	// Permette di associare un Regista al Film.
	public void setRegista(Regista regista) {
	    this.regista = regista;
	}
	
	// Un Film può partecipare a più Festival.
	// "mappedBy = films" indica che la relazione
	// viene gestita dall'attributo "films" della classe Festival.
	@ManyToMany(mappedBy = "films")
	private List<Festival> festivals;
	
	public List<Festival> getFestivals() {
	    return festivals;
	}

	public void setFestivals(List<Festival> festivals) {
	    this.festivals = festivals;
	}
	public List<Recensione> getRecensioni() {
	    return recensioni;
	}

	public void setRecensioni(List<Recensione> recensioni) {
	    this.recensioni = recensioni;
	}

}
