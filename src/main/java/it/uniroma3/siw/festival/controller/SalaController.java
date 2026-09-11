package it.uniroma3.siw.festival.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import jakarta.validation.Valid;
import org.springframework.validation.BindingResult;
import it.uniroma3.siw.festival.model.Sala;
import it.uniroma3.siw.festival.service.SalaService;

@Controller
public class SalaController {
	
	
	private SalaService salaService;
	
	public SalaController( SalaService salaService) {
		this.salaService = salaService;
	}
	
	@GetMapping("/sale")
	public String getSale(Model model) {

	    model.addAttribute("sale", salaService.findAll());

	    return "sale";
	}
	
	@GetMapping("/sala/{id}")
	public String getSala(@PathVariable("id") Long id, Model model) {

	    model.addAttribute("sala", salaService.findById(id));

	    return "sala";
	}
	
	@GetMapping("/sala/new")
	public String newSalaForm(Model model) {

	    model.addAttribute("sala", new Sala());
	    return "sala-form";
	}
	
	// Riceve i dati del form e controlla
	// le regole di validazione definite nel Model Sala.
	@PostMapping("/sala")
	public String saveSala(@Valid @ModelAttribute("sala") Sala sala,
	                       BindingResult bindingResult) {

	    // Se ci sono errori, non salviamo la Sala.
	    // Torniamo al form mantenendo i dati inseriti.
	    if (bindingResult.hasErrors()) {
	        return "sala-form";
	    }

	    // Se i dati sono validi, salviamo la Sala.
	    salaService.save(sala);

	    return "redirect:/sale";
	}
	
	// Mostra il form per modificare
	// una Sala già esistente.
	@GetMapping("/sala/edit/{id}")
	public String editSala(@PathVariable("id") Long id,
	                       Model model) {

	    // Recuperiamo la Sala dal database.
	    Sala sala = salaService.findById(id);

	    // La passiamo al form.
	    model.addAttribute("sala", sala);

	    return "sala-form";
	}
	

}
