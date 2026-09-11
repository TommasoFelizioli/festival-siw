package it.uniroma3.siw.festival.service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import it.uniroma3.siw.festival.model.Recensione;
import it.uniroma3.siw.festival.repository.RecensioneRepository;
import it.uniroma3.siw.festival.model.Film;
import java.util.ArrayList;
import java.util.List;
import it.uniroma3.siw.festival.dto.RecensioneDto;

// Service che gestisce la logica relativa alle Recensioni.
@Service
public class RecensioneService {

    private RecensioneRepository recensioneRepository;


    public RecensioneService(RecensioneRepository recensioneRepository) {
        this.recensioneRepository = recensioneRepository;
    }


    @Transactional(readOnly = true)
    public Iterable<Recensione> findAll() {
        return recensioneRepository.findAll();
    }


    @Transactional(readOnly = true)
    public Recensione findById(Long id) {
        return recensioneRepository.findById(id).orElse(null);
    }


 // Salva una nuova Recensione oppure modifica
 // una Recensione già esistente.
 @Transactional
 public Recensione save(Recensione recensione) {

     boolean recensioneGiaEsistente;


     // Se id == null significa che stiamo CREANDO
     // una nuova Recensione.
     if (recensione.getId() == null) {

         recensioneGiaEsistente =
                 recensioneRepository.existsByUtenteAndFilm(
                         recensione.getUtente(),
                         recensione.getFilm()
                 );

     } else {

         // Se invece l'id esiste, significa che stiamo
         // MODIFICANDO una Recensione già presente.
         //
         // In questo caso dobbiamo escludere dal controllo
         // la Recensione stessa.
         recensioneGiaEsistente =
                 recensioneRepository.existsByUtenteAndFilmAndIdNot(
                         recensione.getUtente(),
                         recensione.getFilm(),
                         recensione.getId()
                 );
     }


     if (recensioneGiaEsistente) {

         throw new IllegalArgumentException(
                 "Hai già recensito questo film"
         );
     }

     return recensioneRepository.save(recensione);
 	}
 
//Controlla se una Recensione appartiene
//all'utente con questo username.
//
//Questa è logica applicativa, quindi la mettiamo
//nel Service e non nel Controller.
 @Transactional(readOnly = true)
 public boolean appartieneA(Long recensioneId, String username) {

     Recensione recensione = this.findById(recensioneId);

     return recensione.getUtente().getUsername().equals(username);
 }
//Elimina una Recensione tramite il suo id.
 @Transactional
 public void deleteById(Long id) {
     recensioneRepository.deleteById(id);
 }
 
//Restituisce tutte le recensioni di un Film.
//La lettura avviene all'interno di una transazione.
@Transactional(readOnly = true)
public Iterable<Recensione> findByFilm(Film film) {
  return recensioneRepository.findByFilm(film);
}

//Restituisce le recensioni di un Film come DTO.
//Costruiamo i DTO dentro la transazione, così possiamo
//leggere anche l'Utente senza problemi di lazy loading.
@Transactional(readOnly = true)
public List<RecensioneDto> findDtoByFilm(Film film) {

 List<RecensioneDto> recensioni = new ArrayList<>();

 for (Recensione recensione : recensioneRepository.findByFilm(film)) {
     recensioni.add(new RecensioneDto(
             recensione.getId(),
             recensione.getTesto(),
             recensione.getVoto(),
             recensione.getData(),
             recensione.getFilm().getId(),
             recensione.getUtente().getUsername()
     ));
 }

 return recensioni;
}
}