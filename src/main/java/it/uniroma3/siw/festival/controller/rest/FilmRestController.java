package it.uniroma3.siw.festival.controller.rest;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import it.uniroma3.siw.festival.dto.FilmDto;
import it.uniroma3.siw.festival.model.Film;
import it.uniroma3.siw.festival.service.FilmService;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;

@RestController
@RequestMapping("/api/movies")
@CrossOrigin(origins = "http://localhost:5173")
public class FilmRestController {

    private FilmService filmService;

    public FilmRestController(FilmService filmService) {
        this.filmService = filmService;
    }

    // Restituisce tutti i film in formato JSON
    @GetMapping
    public Iterable<FilmDto> getAllFilms() {

        java.util.List<FilmDto> filmDto = new java.util.ArrayList<>();

        for (Film film : filmService.findAll()) {
            filmDto.add(new FilmDto(
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

        return filmDto;
    }
    
	 // Restituisce un singolo film tramite il suo ID.
	 // Se il film non esiste, restituisce HTTP 404 Not Found.
	 @GetMapping("/{id}")
	 public ResponseEntity<FilmDto> getFilmById(@PathVariable Long id) {
	
	     Film film = filmService.findById(id);
	
	     if (film == null) {
	         return ResponseEntity.notFound().build();
	     }
	
	     FilmDto dto = new FilmDto(
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
	    	);
	
	     return ResponseEntity.ok(dto);
	 }
}