package it.uniroma3.siw.festival.repository;

import org.springframework.data.repository.CrudRepository;
import it.uniroma3.siw.festival.model.Festival;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface FestivalRepository extends CrudRepository <Festival, Long> {
	
	// Questa query recupera un Festival
	// insieme ai suoi Film e ai Registi dei Film.
	//
	// JOIN FETCH dice a JPA/Hibernate:
	// "carica queste relazioni subito nella stessa query",
	// invece di aspettare che vengano richieste successivamente.
	//
	// DISTINCT evita che lo stesso Festival venga
	// restituito più volte a causa dei molti Film associati.
	@Query("""
	       SELECT DISTINCT f
	       FROM Festival f
	       LEFT JOIN FETCH f.films film
	       LEFT JOIN FETCH film.regista
	       WHERE f.id = :id
	       """)
	Festival findByIdWithFilmsAndRegisti(@Param("id") Long id);
							
	@Query("""
		       SELECT DISTINCT f
		       FROM Festival f
		       LEFT JOIN FETCH f.proiezioni p
		       LEFT JOIN FETCH p.film
		       LEFT JOIN FETCH p.sala
		       WHERE f.id = :id
		       """)
		Festival findByIdWithProiezioni(@Param("id") Long id);
}



