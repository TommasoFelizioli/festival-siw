package it.uniroma3.siw.festival.controller.rest;

import java.util.ArrayList;
import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import it.uniroma3.siw.festival.dto.FilmDto;
import it.uniroma3.siw.festival.model.Film;
import it.uniroma3.siw.festival.dto.FestivalDto;
import it.uniroma3.siw.festival.model.Festival;
import it.uniroma3.siw.festival.service.FestivalService;
import it.uniroma3.siw.festival.dto.ProiezioneDto;
import it.uniroma3.siw.festival.model.Proiezione;

@RestController
@RequestMapping("/api/festivals")
@CrossOrigin(origins = "http://localhost:5173")
public class FestivalRestController {

    private FestivalService festivalService;

    public FestivalRestController(FestivalService festivalService) {
        this.festivalService = festivalService;
    }

    // Restituisce tutti i festival in formato JSON
    @GetMapping
    public Iterable<FestivalDto> getAllFestivals() {

        List<FestivalDto> festivalDto = new ArrayList<>();

        for (Festival festival : festivalService.findAll()) {
            festivalDto.add(new FestivalDto(
                    festival.getId(),
                    festival.getNome(),
                    festival.getAnno(),
                    festival.getCitta(),
                    festival.getDataInizio(),
                    festival.getDataFine(),
                    festival.getDescrizione()
            ));
        }

        return festivalDto;
    }

    // Restituisce un singolo festival tramite ID.
    // Se non esiste, restituisce HTTP 404 Not Found.
    @GetMapping("/{id}")
    public ResponseEntity<FestivalDto> getFestivalById(@PathVariable Long id) {

        Festival festival = festivalService.findById(id);

        if (festival == null) {
            return ResponseEntity.notFound().build();
        }

        FestivalDto dto = new FestivalDto(
                festival.getId(),
                festival.getNome(),
                festival.getAnno(),
                festival.getCitta(),
                festival.getDataInizio(),
                festival.getDataFine(),
                festival.getDescrizione()
        );

        return ResponseEntity.ok(dto);
    }
    
 // Restituisce i film associati a un determinato Festival.
 // Se il Festival non esiste, restituisce HTTP 404 Not Found.
 @GetMapping("/{id}/movies")
 public ResponseEntity<List<FilmDto>> getFilmsByFestival(@PathVariable Long id) {

     Festival festival = festivalService.findByIdWithFilmsAndRegisti(id);

     if (festival == null) {
         return ResponseEntity.notFound().build();
     }

     List<FilmDto> films = new ArrayList<>();

     for (Film film : festival.getFilms()) {
         films.add(new FilmDto(
        	        film.getId(),
        	        film.getTitle(),
        	        film.getYear(),
        	        film.getDuration(),
        	        film.getGenre(),
        	        film.getCountryProduction(),

        	        film.getRegista() != null
        	                ? film.getRegista().getId()
        	                : null,

        	        film.getRegista() != null
        	                ? film.getRegista().getNome()
        	                : null,

        	        film.getRegista() != null
        	                ? film.getRegista().getCognome()
        	                : null,

        	        film.getRegista() != null
        	                ? film.getRegista().getDataNascita()
        	                : null,

        	        film.getRegista() != null
        	                ? film.getRegista().getNazionalita()
        	                : null
        	));
     }

     return ResponseEntity.ok(films);
 }
//Restituisce il programma delle proiezioni di un Festival.
//Se il Festival non esiste, restituisce HTTP 404 Not Found.
@GetMapping("/{id}/screenings")
public ResponseEntity<List<ProiezioneDto>> getScreeningsByFestival(@PathVariable Long id) {

	Festival festival = festivalService.findByIdWithProiezioni(id);

  if (festival == null) {
      return ResponseEntity.notFound().build();
  }

  List<ProiezioneDto> screenings = new ArrayList<>();

  for (Proiezione proiezione : festival.getProiezioni()) {
      screenings.add(new ProiezioneDto(
              proiezione.getId(),
              proiezione.getData(),
              proiezione.getOra(),
              proiezione.getStato().name(),
              proiezione.getFilm().getId(),
              proiezione.getFilm().getTitle(),
              proiezione.getSala().getId(),
              proiezione.getSala().getNome()
      ));
  }

  return ResponseEntity.ok(screenings);
}
}