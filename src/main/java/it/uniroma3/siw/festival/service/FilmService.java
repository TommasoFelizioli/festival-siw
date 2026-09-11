package it.uniroma3.siw.festival.service;

import org.springframework.stereotype.Service;
import it.uniroma3.siw.festival.model.Film;
import it.uniroma3.siw.festival.model.Regista;
import it.uniroma3.siw.festival.repository.FilmRepository;
import org.springframework.transaction.annotation.Transactional;

// Diciamo a Spring che questa classe è un Service,
// quindi una classe che gestisce le operazioni sui Film.
@Service
public class FilmService {

    private FilmRepository filmRepository;

    // Il FilmService usa anche il RegistaService
    // perché quando salviamo un Film dobbiamo
    // recuperare il vero Regista dal database.
    private RegistaService registaService;


    public FilmService(FilmRepository filmRepository,
                       RegistaService registaService) {

        this.filmRepository = filmRepository;
        this.registaService = registaService;
    }


    // Restituisce tutti i Film.
    @Transactional(readOnly = true)
    public Iterable<Film> findAll() {
        return filmRepository.findAll();
    }


    // Cerca un Film tramite id.
    @Transactional(readOnly = true)
    public Film findById(Long id) {
        return filmRepository.findById(id).orElse(null);
    }


    // Salva un Film nuovo oppure modifica
    // un Film già esistente.
    @Transactional
    public Film save(Film film) {

        // Se dal form è stato scelto un Regista,
        // recuperiamo dal database il vero oggetto Regista.
        if (film.getRegista() != null) {

            Long registaId = film.getRegista().getId();

            Regista regista = registaService.findById(registaId);

            // Associamo il Regista recuperato
            // al Film prima del salvataggio.
            film.setRegista(regista);
        }

        return filmRepository.save(film);
    }


}