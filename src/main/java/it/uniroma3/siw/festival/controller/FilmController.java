// Qui mettiamo le classi che ricevono
// le richieste che arrivano dal browser.
package it.uniroma3.siw.festival.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import it.uniroma3.siw.festival.model.Film;
import it.uniroma3.siw.festival.service.FilmService;
import it.uniroma3.siw.festival.service.RegistaService;
import jakarta.validation.Valid;
import org.springframework.validation.BindingResult;

//Diciamo a Spring che questa classe deve occuparsi
//delle richieste web relative ai Film.
@Controller
public class FilmController {
	
	// Il Controller non va direttamente nel database.
    // Quando gli servono dei Film, chiede al FilmService.
	private FilmService filmService;
	// Service utilizzato per recuperare
	// i Registi dal database.
	private RegistaService registaService;
	// Spring ci fornisce automaticamente
	// sia il FilmService sia il RegistaService.
	public FilmController(FilmService filmService,
	                      RegistaService registaService) {

	    this.filmService = filmService;
	    this.registaService = registaService;
	}
	
	// Questo metodo viene eseguito quando nel browser andiamo su:
    //
    // localhost:8080/films
    //
    // GET significa che il browser sta CHIEDENDO una risorsa/pagina.
	@GetMapping("/films")
	public String getFilms(Model model) {
		
		// 1. filmService.findAll()
        //    recupera tutti i Film dal database.
        //
        // 2. model.addAttribute(...)
        //    mette questi Film in una specie di "contenitore"
        //    che possiamo passare alla pagina HTML.
        //
        // 3. Diamo a questi dati il nome "films".
        //
        // Quindi films.html potrà usare qualcosa chiamato "films"
        // che contiene tutti i Film recuperati dal database.
	    model.addAttribute("films", filmService.findAll());
	    
	    // Dopo aver recuperato i dati diciamo a Spring:
        // "Mostra la pagina films.html".
        //
        // Spring la cerca nella cartella templates.
	    return "films";
	}
	
	// Questo metodo viene invece eseguito per indirizzi come:
    //
    // localhost:8080/film/1
    // localhost:8080/film/2
    //
    // {id} è una parte variabile dell'indirizzo.
	@GetMapping("/film/{id}")
	public String getFilm(@PathVariable("id") Long id, Model model) {

		// @PathVariable prende il numero presente nell'indirizzo.
        //
        // Se andiamo su:
        // /film/1
        //
        // allora:
        // id = 1
        //
        // Usiamo quell'id per chiedere al Service
        // di trovare proprio quel Film.
        //
        // Poi mettiamo il Film trovato nel Model
        // con il nome "film".
	    model.addAttribute("film", filmService.findById(id));

	    // Infine diciamo a Spring di mostrare film.html,
        // che sarà la pagina dedicata al singolo Film.
	    return "film";
	}
	
	// Questo metodo viene eseguito quando andiamo su:
	//
	// localhost:8080/films/new
	//
	// Il suo scopo è mostrare la pagina con il form
	// per inserire i dati di un nuovo Film.
	// Questo metodo mostra il form
	// per inserire un nuovo Film.
	@GetMapping("/films/new")
	public String newFilmForm(Model model) {

	    // Creiamo un Film vuoto
	    // che verrà compilato attraverso il form.
	    model.addAttribute("film", new Film());


	    // Recuperiamo tutti i Registi
	    // presenti nel database.
	    //
	    // Li passiamo alla pagina HTML
	    // con il nome "registi".
	    //
	    // In questo modo il form potrà
	    // mostrare una lista da cui scegliere
	    // il Regista del Film.
	    model.addAttribute("registi", registaService.findAll());


	    return "film-form";
	}
	
	// Questo metodo riceve i dati del form
	// quando premiamo "Salva Film".
	// Riceve i dati del form e controlla le regole
	// di validazione definite nel Model Film.
	@PostMapping("/films")
	public String saveFilm(@Valid @ModelAttribute("film") Film film,
	                       BindingResult bindingResult,
	                       Model model) {

	    // Se ci sono errori, non salviamo il Film.
	    // Torniamo al form mostrando i dati inseriti.
	    if (bindingResult.hasErrors()) {

	        // Ricarichiamo i Registi perché il form
	        // deve poter mostrare nuovamente la lista.
	        model.addAttribute("registi", registaService.findAll());

	        return "film-form";
	    }

	    // Se i dati sono validi, salviamo il Film.
	    filmService.save(film);

	    return "redirect:/films";
	}
	
	// Mostra il form per modificare
	// un Film già esistente.
	@GetMapping("/film/edit/{id}")
	public String editFilm(@PathVariable("id") Long id,
	                       Model model) {

	    // Recuperiamo dal database
	    // il Film che vogliamo modificare.
	    Film film = filmService.findById(id);

	    // Passiamo il Film al form.
	    model.addAttribute("film", film);

	    // Servono anche tutti i Registi,
	    // così possiamo eventualmente cambiare Regista.
	    model.addAttribute("registi", registaService.findAll());

	    return "film-form";
	}
	
}
