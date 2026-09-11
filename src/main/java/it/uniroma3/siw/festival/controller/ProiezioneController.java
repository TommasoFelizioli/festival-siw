package it.uniroma3.siw.festival.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import jakarta.validation.Valid;
import org.springframework.validation.BindingResult;
import it.uniroma3.siw.festival.model.Proiezione;
import it.uniroma3.siw.festival.model.StatoProiezione;
import it.uniroma3.siw.festival.service.FestivalService;
import it.uniroma3.siw.festival.service.FilmService;
import it.uniroma3.siw.festival.service.ProiezioneService;
import it.uniroma3.siw.festival.service.SalaService;


// Questo Controller gestisce le richieste HTTP
// relative alle Proiezioni.
@Controller
public class ProiezioneController {

    private ProiezioneService proiezioneService;
    private FestivalService festivalService;
    private FilmService filmService;
    private SalaService salaService;


    // Il Controller ha bisogno di più Service perché una Proiezione
    // è collegata a Festival, Film e Sala.
    public ProiezioneController(ProiezioneService proiezioneService,
                                FestivalService festivalService,
                                FilmService filmService,
                                SalaService salaService) {

        this.proiezioneService = proiezioneService;
        this.festivalService = festivalService;
        this.filmService = filmService;
        this.salaService = salaService;
    }


    // Mostra tutte le Proiezioni.
    @GetMapping("/proiezioni")
    public String getProiezioni(Model model) {

        model.addAttribute("proiezioni", proiezioneService.findAll());

        return "proiezioni";
    }


    // Mostra il dettaglio di una singola Proiezione.
    @GetMapping("/proiezione/{id}")
    public String getProiezione(@PathVariable("id") Long id, Model model) {

        model.addAttribute("proiezione",
                           proiezioneService.findById(id));

        return "proiezione";
    }


    // Mostra il form per creare una nuova Proiezione.
    @GetMapping("/proiezione/new")
    public String newProiezioneForm(Model model) {

        // Oggetto vuoto che verrà riempito dal form.
        model.addAttribute("proiezione", new Proiezione());

        // Diamo al form tutti i Festival disponibili.
        model.addAttribute("festivals", festivalService.findAll());

        // Diamo al form tutti i Film disponibili.
        model.addAttribute("films", filmService.findAll());

        // Diamo al form tutte le Sale disponibili.
        model.addAttribute("sale", salaService.findAll());

        // values() restituisce tutti i possibili valori dell'enum:
        // SCHEDULED, COMPLETED, CANCELLED.
        model.addAttribute("stati", StatoProiezione.values());

        return "proiezione-form";
    }


 // Riceve i dati del form, controlla la validazione
 // e poi prova a salvare la Proiezione.
 @PostMapping("/proiezione")
 public String saveProiezione(
         @Valid @ModelAttribute("proiezione") Proiezione proiezione,
         BindingResult bindingResult,
         Model model) {

     // Se mancano dati obbligatori,
     // non proviamo nemmeno a salvare.
     if (bindingResult.hasErrors()) {

         // Ricarichiamo tutti i dati necessari
         // alle select del form.
         model.addAttribute("festivals", festivalService.findAll());
         model.addAttribute("films", filmService.findAll());
         model.addAttribute("sale", salaService.findAll());
         model.addAttribute("stati", StatoProiezione.values());

         return "proiezione-form";
     }

     try {

         // Il Service controlla anche
         // eventuali sovrapposizioni nella Sala.
         proiezioneService.save(proiezione);

         return "redirect:/proiezioni";

     } catch (IllegalArgumentException e) {

         // Mostriamo l'errore prodotto dal Service,
         // ad esempio una sovrapposizione.
         model.addAttribute("errore", e.getMessage());

         // Ricarichiamo i dati necessari al form.
         model.addAttribute("festivals", festivalService.findAll());
         model.addAttribute("films", filmService.findAll());
         model.addAttribute("sale", salaService.findAll());
         model.addAttribute("stati", StatoProiezione.values());

         return "proiezione-form";
     }
 }
 // Mostra il form per modificare
 // una Proiezione già esistente.
 @GetMapping("/proiezione/edit/{id}")
 public String editProiezione(@PathVariable("id") Long id,
                              Model model) {

     // Recuperiamo dal database
     // la Proiezione che vogliamo modificare.
     Proiezione proiezione = proiezioneService.findById(id);

     // Passiamo la Proiezione al form.
     model.addAttribute("proiezione", proiezione);

     // Ricarichiamo tutti i dati necessari
     // per le select del form.
     model.addAttribute("festivals", festivalService.findAll());
     model.addAttribute("films", filmService.findAll());
     model.addAttribute("sale", salaService.findAll());

     // Passiamo anche tutti i possibili
     // valori dell'enum StatoProiezione.
     model.addAttribute("stati", StatoProiezione.values());

     return "proiezione-form";
 }
//L'eliminazione modifica il database,
//quindi utilizziamo POST invece di GET.
@PostMapping("/proiezione/delete/{id}")
public String deleteProiezione(@PathVariable("id") Long id) {

  proiezioneService.deleteById(id);

  return "redirect:/proiezioni";
}
}