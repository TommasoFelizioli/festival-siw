package it.uniroma3.siw.festival.service;

import org.springframework.stereotype.Service;
import it.uniroma3.siw.festival.model.Festival;
import it.uniroma3.siw.festival.repository.FestivalRepository;
import org.springframework.transaction.annotation.Transactional;


@Service
public class FestivalService {

    private FestivalRepository festivalRepository;

    public FestivalService(FestivalRepository festivalRepository) {
        this.festivalRepository = festivalRepository;
    }

    // Operazione di sola lettura:
    // recupera tutti i Festival dal database.
    @Transactional(readOnly = true)
    public Iterable<Festival> findAll() {
        return festivalRepository.findAll();
    }

    // Operazione di sola lettura:
    // cerca un Festival tramite il suo id.
    @Transactional(readOnly = true)
    public Festival findById(Long id) {
        return festivalRepository.findById(id).orElse(null);
    }

    // Operazione di scrittura:
    // crea un nuovo Festival oppure
    // modifica un Festival esistente.
    @Transactional
    public Festival save(Festival festival) {
        return festivalRepository.save(festival);
    }
 // Strategia 1: LAZY.
    //
    // Recuperiamo prima il Festival.
    // Poi accediamo esplicitamente ai Film
    // e ai loro Registi mentre la transazione
    // è ancora aperta.
    //
    // In questo modo Hibernate sarà costretto
    // a caricare le relazioni LAZY.
    @Transactional(readOnly = true)
    public Festival findByIdLazy(Long id) {

        Festival festival =
                festivalRepository.findById(id).orElse(null);

        if (festival != null) {

            for (var film : festival.getFilms()) {

                // Accedendo al Regista,
                // costringiamo Hibernate a caricarlo.
                if (film.getRegista() != null) {
                	film.getRegista().getNome();
                }
            }
        }

        return festival;
    }
//Strategia 2: caricamento ottimizzato.
//La query JOIN FETCH recupera insieme
//Festival, Film e Registi.
@Transactional(readOnly = true)
public Festival findByIdWithFilmsAndRegisti(Long id) {
  return festivalRepository.findByIdWithFilmsAndRegisti(id);
}
//Carica un Festival insieme alle sue Proiezioni,
//ai Film e alle Sale, evitando problemi di lazy loading.
@Transactional(readOnly = true)
public Festival findByIdWithProiezioni(Long id) {
 return festivalRepository.findByIdWithProiezioni(id);
}
}
