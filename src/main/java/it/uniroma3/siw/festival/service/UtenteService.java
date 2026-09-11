package it.uniroma3.siw.festival.service;

import org.springframework.stereotype.Service;

import it.uniroma3.siw.festival.model.Utente;
import it.uniroma3.siw.festival.repository.UtenteRepository;


// Service che gestisce la logica relativa agli Utenti.
@Service
public class UtenteService {

    private UtenteRepository utenteRepository;


    public UtenteService(UtenteRepository utenteRepository) {
        this.utenteRepository = utenteRepository;
    }


    // Restituisce tutti gli Utenti.
    public Iterable<Utente> findAll() {
        return utenteRepository.findAll();
    }


    // Cerca un Utente tramite id.
    public Utente findById(Long id) {
        return utenteRepository.findById(id).orElse(null);
    }


    // Salva o aggiorna un Utente.
    public Utente save(Utente utente) {
        return utenteRepository.save(utente);
    }
    public Utente findByUsername(String username) {
        return utenteRepository.findByUsername(username);
    }
}