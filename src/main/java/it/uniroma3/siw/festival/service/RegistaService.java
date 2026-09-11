package it.uniroma3.siw.festival.service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import it.uniroma3.siw.festival.model.Regista;
import it.uniroma3.siw.festival.repository.RegistaRepository;


// Questa classe rappresenta il Service dei Registi.
//
// Il Service si trova tra Controller e Repository.
//
// Il Controller chiede al Service cosa fare.
// Il Service poi usa il Repository
// per accedere al database.
@Service
public class RegistaService {


    // Repository che utilizziamo
    // per lavorare con i Registi nel database.
    private RegistaRepository registaRepository;


    // Costruttore usato da Spring
    // per fornire automaticamente
    // il RegistaRepository al Service.
    public RegistaService(RegistaRepository registaRepository) {
        this.registaRepository = registaRepository;
    }


    // Restituisce tutti i Registi
    // presenti nel database.
    @Transactional(readOnly = true)
    public Iterable<Regista> findAll() {
        return registaRepository.findAll();
    }


    // Cerca un Regista tramite il suo id.
    //
    // Se il Regista esiste, viene restituito.
    // Se non esiste, restituiamo null.
    @Transactional(readOnly = true)
    public Regista findById(Long id) {
        return registaRepository.findById(id).orElse(null);
    }


    // Salva un Regista nel database.
    //
    // Se è nuovo viene inserito.
    // Più avanti useremo lo stesso metodo
    // anche per modificare un Regista esistente.
    @Transactional
    public Regista save(Regista regista) {
        return registaRepository.save(regista);
    }
}