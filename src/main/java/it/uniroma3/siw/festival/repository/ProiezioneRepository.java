package it.uniroma3.siw.festival.repository;

import org.springframework.data.repository.CrudRepository;

import it.uniroma3.siw.festival.model.Proiezione;
import it.uniroma3.siw.festival.model.Sala;


public interface ProiezioneRepository
        extends CrudRepository<Proiezione, Long> {

    // Restituisce tutte le Proiezioni
    // che si svolgono in una determinata Sala.
    //
    // In questo modo possiamo controllare
    // eventuali sovrapposizioni anche tra giorni diversi.
    Iterable<Proiezione> findBySala(Sala sala);

}