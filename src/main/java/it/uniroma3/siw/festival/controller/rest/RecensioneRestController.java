package it.uniroma3.siw.festival.controller.rest;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import it.uniroma3.siw.festival.dto.RecensioneDto;
import it.uniroma3.siw.festival.model.Film;
import it.uniroma3.siw.festival.service.FilmService;
import it.uniroma3.siw.festival.service.RecensioneService;

@RestController
@RequestMapping("/api/movies")
@CrossOrigin(origins = "http://localhost:5173")
public class RecensioneRestController {

    private FilmService filmService;
    private RecensioneService recensioneService;

    public RecensioneRestController(FilmService filmService,
                                    RecensioneService recensioneService) {
        this.filmService = filmService;
        this.recensioneService = recensioneService;
    }

    // Restituisce le recensioni di un determinato Film.
    // Se il Film non esiste, restituisce HTTP 404 Not Found.
    @GetMapping("/{id}/reviews")
    public ResponseEntity<List<RecensioneDto>> getReviewsByFilm(@PathVariable Long id) {

        Film film = filmService.findById(id);

        if (film == null) {
            return ResponseEntity.notFound().build();
        }

        return ResponseEntity.ok(recensioneService.findDtoByFilm(film));
    }
}