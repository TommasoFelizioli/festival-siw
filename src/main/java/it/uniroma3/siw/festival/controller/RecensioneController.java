package it.uniroma3.siw.festival.controller;

import java.security.Principal;
import java.time.LocalDate;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import jakarta.validation.Valid;
import org.springframework.validation.BindingResult;
import it.uniroma3.siw.festival.model.Recensione;
import it.uniroma3.siw.festival.model.Utente;
import it.uniroma3.siw.festival.service.FilmService;
import it.uniroma3.siw.festival.service.RecensioneService;
import it.uniroma3.siw.festival.service.UtenteService;


// Controller che gestisce le richieste
// relative alle Recensioni.
@Controller
public class RecensioneController {

    private RecensioneService recensioneService;
    private FilmService filmService;
    private UtenteService utenteService;


    public RecensioneController(RecensioneService recensioneService,
                                FilmService filmService,
                                UtenteService utenteService) {

        this.recensioneService = recensioneService;
        this.filmService = filmService;
        this.utenteService = utenteService;
    }


    // Mostra tutte le Recensioni.
    @GetMapping("/recensioni")
    public String getRecensioni(Model model) {

        model.addAttribute(
                "recensioni",
                recensioneService.findAll()
        );

        return "recensioni";
    }


    // Mostra il dettaglio di una singola Recensione.
    @GetMapping("/recensione/{id}")
    public String getRecensione(@PathVariable("id") Long id,
                                Principal principal,
                                Model model) {

        Recensione recensione = recensioneService.findById(id);

        model.addAttribute("recensione", recensione);

     // Di default la Recensione non può essere modificata.
        boolean puoModificare = false;

        // Se c'è un utente autenticato,
        // chiediamo al Service se è il proprietario
        // della Recensione.
        if (principal != null) {
            puoModificare =
                    recensioneService.appartieneA(id, principal.getName());
        }

        // Passiamo il risultato alla pagina HTML.
        model.addAttribute("puoModificare", puoModificare);

        return "recensione";
    }


    // Mostra il form per creare una nuova Recensione.
    @GetMapping("/recensione/new")
    public String newRecensioneForm(Model model) {

        Recensione recensione = new Recensione();

        // Impostiamo automaticamente la data di oggi.
        recensione.setData(LocalDate.now());

        model.addAttribute("recensione", recensione);

     // Passiamo i Film al form.
     // L'Utente invece non viene scelto manualmente:
     // viene ricavato automaticamente dall'utente autenticato.
        model.addAttribute("films", filmService.findAll());
     

        return "recensione-form";
    }


 // Riceve i dati del form e controlla la validazione.
 // Il proprietario e il controllo dei duplicati restano gestiti
 // come prima.
 @PostMapping("/recensione")
 public String salvaRecensione(@Valid @ModelAttribute("recensione") Recensione recensione,
                               BindingResult bindingResult,
                               Principal principal,
                               Model model) {

     // Prendiamo l'utente autenticato.
     String username = principal.getName();
     Utente utente = utenteService.findByUsername(username);

     // Se stiamo modificando una Recensione,
     // controlliamo che appartenga all'utente autenticato.
     if (recensione.getId() != null) {

         if (!recensioneService.appartieneA(
                 recensione.getId(),
                 username)) {

             return "redirect:/recensioni";
         }
     }

     // L'utente non viene scelto dal form.
     recensione.setUtente(utente);

     // Se testo o voto non sono validi, non salviamo.
     if (bindingResult.hasErrors()) {
         model.addAttribute("films", filmService.findAll());
         return "recensione-form";
     }

     try {
         recensioneService.save(recensione);
         return "redirect:/recensioni";

     } catch (IllegalArgumentException e) {

         // Manteniamo il controllo già esistente:
         // massimo una recensione per utente e film.
         model.addAttribute("errore", e.getMessage());
         model.addAttribute("films", filmService.findAll());

         return "recensione-form";
     }
 }
    
    
 // Mostra il form per modificare
 // una Recensione già esistente.
    @GetMapping("/recensione/edit/{id}")
    public String editRecensione(@PathVariable("id") Long id,
                                 Principal principal,
                                 Model model) {

        // Recuperiamo la recensione che si vuole modificare.
        Recensione recensione = recensioneService.findById(id);

     // Chiediamo al Service se la Recensione
     // appartiene all'utente autenticato.
     if (!recensioneService.appartieneA(id, principal.getName())) {

         return "redirect:/recensioni";
     }

        model.addAttribute("recensione", recensione);
        model.addAttribute("films", filmService.findAll());

        return "recensione-form";
    }
 
 // Elimina una Recensione solamente tramite una richiesta POST.
 // È più sicuro perché GET dovrebbe essere usato solo per leggere dati.
 @PostMapping("/recensione/delete/{id}")
 public String deleteRecensione(@PathVariable("id") Long id,
                                Principal principal) {
     
	  // Chiediamo al Service se la Recensione
	  // appartiene all'utente autenticato.
	  if (!recensioneService.appartieneA(id, principal.getName())) {
	
	      return "redirect:/recensioni";
	  }

     // Se invece è la sua Recensione,
     // può eliminarla.
     recensioneService.deleteById(id);

     return "redirect:/recensioni";
 }
}