package it.uniroma3.siw.festival.service;

import java.time.LocalDateTime;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import it.uniroma3.siw.festival.model.Proiezione;
import it.uniroma3.siw.festival.repository.ProiezioneRepository;


// Il Service contiene la logica relativa alle Proiezioni.
@Service
public class ProiezioneService {

    private ProiezioneRepository proiezioneRepository;
    private FestivalService festivalService;
    private FilmService filmService;
    private SalaService salaService;

    public ProiezioneService(ProiezioneRepository proiezioneRepository,
                             FestivalService festivalService,
                             FilmService filmService,
                             SalaService salaService) {

        this.proiezioneRepository = proiezioneRepository;
        this.festivalService = festivalService;
        this.filmService = filmService;
        this.salaService = salaService;
    }


    @Transactional(readOnly = true)
    public Iterable<Proiezione> findAll() {
        return proiezioneRepository.findAll();
    }

    @Transactional(readOnly = true)
    public Proiezione findById(Long id) {
        return proiezioneRepository.findById(id).orElse(null);
    }


    // @Transactional indica che il controllo
    // e il successivo salvataggio fanno parte
    // della stessa operazione logica.
    @Transactional(isolation = Isolation.SERIALIZABLE)
    public Proiezione save(Proiezione proiezione) {
    	
    	Long festivalId = proiezione.getFestival().getId();
    	Long filmId = proiezione.getFilm().getId();
    	Long salaId = proiezione.getSala().getId();

    	proiezione.setFestival(
    	        festivalService.findById(festivalId)
    	);

    	proiezione.setFilm(
    	        filmService.findById(filmId)
    	);

    	proiezione.setSala(
    	        salaService.findById(salaId)
    	);


    	// Una Proiezione può essere programmata
    	// solo se il Film partecipa al Festival.
    	if (!proiezione.getFestival()
    	        .getFilms()
    	        .contains(proiezione.getFilm())) {

    	    throw new IllegalArgumentException(
    	            "Il film selezionato non partecipa a questo festival"
    	    );
    	}


    	// La data della Proiezione deve essere compresa
    	// tra la data di inizio e la data di fine del Festival.
    	if (proiezione.getData().isBefore(
    	        proiezione.getFestival().getDataInizio())
    	        ||
    	        proiezione.getData().isAfter(
    	        proiezione.getFestival().getDataFine())) {

    	    throw new IllegalArgumentException(
    	            "La proiezione deve svolgersi durante il periodo del festival"
    	    );
    	}


    	// Recuperiamo tutte le Proiezioni
    	// già presenti nella stessa Sala.
    	Iterable<Proiezione> proiezioniStessaSala =
    	        proiezioneRepository.findBySala(
    	                proiezione.getSala()
    	        );


        // Costruiamo DATA + ORA di inizio.
        //
        // Esempio:
        // 10 settembre 2026 + 23:30
        // diventa:
        // 2026-09-10T23:30
        LocalDateTime inizioNuova =
                LocalDateTime.of(
                        proiezione.getData(),
                        proiezione.getOra()
                );


        // Calcoliamo quando termina il Film
        // aggiungendo la sua durata in minuti.
        LocalDateTime fineNuova =
                inizioNuova.plusMinutes(
                        proiezione.getFilm().getDuration()
                );


     // Confrontiamo la nuova Proiezione
     // con tutte quelle della stessa Sala.
     for (Proiezione esistente : proiezioniStessaSala) {

         // Se stiamo MODIFICANDO una Proiezione,
         // dobbiamo evitare di confrontarla con se stessa.
         //
         // Se gli id sono uguali, significa che
         // "esistente" è proprio la Proiezione
         // che stiamo modificando.
         if (proiezione.getId() != null
                 && proiezione.getId().equals(esistente.getId())) {

             continue;
         }


         LocalDateTime inizioEsistente =
                 LocalDateTime.of(
                         esistente.getData(),
                         esistente.getOra()
                 );


         LocalDateTime fineEsistente =
                 inizioEsistente.plusMinutes(
                         esistente.getFilm().getDuration()
                 );


         if (inizioNuova.isBefore(fineEsistente)
                 && fineNuova.isAfter(inizioEsistente)) {

             throw new IllegalArgumentException(
                     "La sala è già occupata in questo orario"
             );
         }
     }


        // Se nessuna Proiezione si sovrappone,
        // possiamo salvare.
        return proiezioneRepository.save(proiezione);
    }
    
    @Transactional
    public void deleteById(Long id) {
        proiezioneRepository.deleteById(id);
    }
}