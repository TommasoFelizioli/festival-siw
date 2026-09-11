package it.uniroma3.siw.festival.repository;

import org.springframework.data.repository.CrudRepository;

import it.uniroma3.siw.festival.model.Regista;


// Questo Repository serve per accedere
// ai Registi salvati nel database.
//
// Estendendo CrudRepository otteniamo automaticamente
// i principali metodi per lavorare con il database,
// senza doverli scrivere noi.
//
// Per esempio:
// save()     -> salva un Regista
// findAll()  -> trova tutti i Registi
// findById() -> cerca un Regista tramite il suo id
// delete()   -> elimina un Regista
//
// Regista = tipo di oggetto che gestiamo
// Long = tipo dell'id di Regista
public interface RegistaRepository extends CrudRepository<Regista, Long> {

}