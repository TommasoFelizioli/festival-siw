package it.uniroma3.siw.festival.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import jakarta.validation.Valid;
import org.springframework.validation.BindingResult;
import it.uniroma3.siw.festival.model.Regista;
import it.uniroma3.siw.festival.service.RegistaService;


// Questo Controller gestisce
// le richieste relative ai Registi.
//
// Il Controller riceve la richiesta dal browser,
// chiede i dati al Service
// e poi li passa alla pagina HTML.
@Controller
public class RegistaController {


    // Service che utilizziamo
    // per recuperare e gestire i Registi.
    private RegistaService registaService;


    // Spring ci fornisce automaticamente
    // il RegistaService tramite il costruttore.
    public RegistaController(RegistaService registaService) {
        this.registaService = registaService;
    }


    // Questo metodo viene eseguito quando
    // il browser richiede:
    //
    // localhost:8080/registi
    @GetMapping("/registi")
    public String getRegisti(Model model) {

        // Recuperiamo tutti i Registi
        // tramite il Service
        // e li inseriamo nel Model
        // con il nome "registi".
        model.addAttribute("registi", registaService.findAll());

        // Spring mostrerà la pagina:
        //
        // registi.html
        return "registi";
    }
    
 // Questo metodo viene eseguito quando andiamo su:
    //
    // localhost:8080/registi/new
    //
    // Serve per mostrare il form
    // con cui inserire un nuovo Regista.
    @GetMapping("/registi/new")
    public String newRegistaForm(Model model) {

        // Creiamo un Regista vuoto.
        //
        // Il form HTML userà questo oggetto
        // per inserire nome, cognome,
        // data di nascita e nazionalità.
        model.addAttribute("regista", new Regista());

        // Mostriamo la pagina:
        //
        // regista-form.html
        return "regista-form";
    }
 // Questo metodo viene eseguito quando
 // il browser richiede un Regista specifico.
 //
 // Esempio:
 // localhost:8080/regista/1
 //
 // Il numero finale rappresenta l'id
 // del Regista che vogliamo visualizzare.
 @GetMapping("/regista/{id}")
 public String getRegista(@PathVariable("id") Long id, Model model) {

     // Cerchiamo il Regista tramite il suo id.
     Regista regista = registaService.findById(id);

     // Inseriamo il Regista nel Model
     // con il nome "regista".
     //
     // In questo modo la pagina HTML
     // potrà usare ${regista}.
     model.addAttribute("regista", regista);

     // Mostriamo la pagina:
     //
     // regista.html
     return "regista";
 }
	 // Questo metodo riceve i dati del form
	 // quando premiamo "Salva Regista".
	 //
	 // La richiesta sarà:
	 //
	 // POST /registi
//Riceve i dati del form e controlla
//le regole di validazione definite nel Model Regista.
@PostMapping("/registi")
public String saveRegista(@Valid @ModelAttribute("regista") Regista regista,
                        BindingResult bindingResult) {

  // Se ci sono errori, non salviamo il Regista.
  // Torniamo al form mantenendo i dati inseriti.
  if (bindingResult.hasErrors()) {
      return "regista-form";
  }

  // Se i dati sono validi, chiediamo al Service
  // di salvare il Regista nel database.
  registaService.save(regista);

  return "redirect:/registi";
}
	 
	// Mostra il form per modificare
	// un Regista già esistente.
	@GetMapping("/regista/edit/{id}")
	public String editRegista(@PathVariable("id") Long id,
	                          Model model) {

	    // Recuperiamo il Regista dal database.
	    Regista regista = registaService.findById(id);

	    // Lo passiamo al form.
	    model.addAttribute("regista", regista);

	    return "regista-form";
	}
	
	
    
}