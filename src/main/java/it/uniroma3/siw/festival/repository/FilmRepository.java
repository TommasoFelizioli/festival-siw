// Qui mettiamo le classi/interfacce che ci permettono
// di comunicare con il database.
package it.uniroma3.siw.festival.repository;

import org.springframework.data.repository.CrudRepository;
import it.uniroma3.siw.festival.model.Film;

//Questo Repository è il nostro "accesso al database" per i Film.
//
//Grazie a CrudRepository Spring ci dà già operazioni come:
//- salvare un Film
//- cercare un Film
//- leggere tutti i Film
//- eliminare un Film
//
//Film = il tipo di oggetto che vogliamo gestire.
//Long = il tipo dell'id di Film.
public interface FilmRepository extends CrudRepository<Film, Long> {

}