package it.uniroma3.siw.festival.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import it.uniroma3.siw.festival.model.Festival;
import it.uniroma3.siw.festival.service.FestivalService;
import it.uniroma3.siw.festival.service.FilmService;
import jakarta.validation.Valid;
import org.springframework.validation.BindingResult;

@Controller
public class FestivalController {
	
	
	private FilmService filmService;
	private FestivalService festivalService;
	
	public FestivalController( FestivalService festivalService,  FilmService filmService) {
		this.festivalService = festivalService;
		this.filmService = filmService;
	}
	
	@GetMapping("/festivals")
	public String getFestival(Model model) {

	    model.addAttribute("festivals", festivalService.findAll());

	    return "festivals";
	}
	
	@GetMapping("/festival/{id}")
	public String getFestival(@PathVariable("id") Long id, Model model) {

	    model.addAttribute("festival", festivalService.findById(id));

	    return "festival";
	}
	
	@GetMapping("/festival/new")
	public String newFestivalForm(Model model) {

	    model.addAttribute("festival", new Festival());
	    model.addAttribute("films", filmService.findAll());
	    return "festival-form";
	}
	
	// Riceve i dati del form e controlla
	// le regole di validazione definite nel Model Festival.
	@PostMapping("/festival")
	public String saveFestival(@Valid @ModelAttribute("festival") Festival festival,
	                           BindingResult bindingResult,
	                           Model model) {

	    // Se ci sono errori, non salviamo il Festival.
	    // Torniamo al form mantenendo i dati inseriti.
	    if (bindingResult.hasErrors()) {

	        // Ricarichiamo i Film perché il form
	        // deve poter mostrare nuovamente la lista.
	        model.addAttribute("films", filmService.findAll());

	        return "festival-form";
	    }

	    // Se i dati sono validi, salviamo il Festival.
	    festivalService.save(festival);

	    return "redirect:/festivals";
	}
	// Mostra il form per modificare
	// un Festival già esistente.
	@GetMapping("/festival/edit/{id}")
	public String editFestival(@PathVariable("id") Long id,
	                           Model model) {

	    // Recuperiamo il Festival dal database.
	    Festival festival = festivalService.findById(id);

	    // Passiamo il Festival al form.
	    model.addAttribute("festival", festival);

	    // Servono anche tutti i Film,
	    // perché nel form possiamo modificare
	    // i Film associati al Festival.
	    model.addAttribute("films", filmService.findAll());

	    return "festival-form";
	}

}
