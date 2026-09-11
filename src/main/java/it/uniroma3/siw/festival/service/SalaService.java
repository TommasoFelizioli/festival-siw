package it.uniroma3.siw.festival.service;

import org.springframework.stereotype.Service;
import it.uniroma3.siw.festival.model.Sala;
import it.uniroma3.siw.festival.repository.SalaRepository;
import org.springframework.transaction.annotation.Transactional;


@Service
public class SalaService {

	private SalaRepository salaRepository;

	public SalaService(SalaRepository salaRepository) {
		
		this.salaRepository = salaRepository;
	}
	
	@Transactional(readOnly = true)
	public Iterable<Sala> findAll() {
	    return salaRepository.findAll();
	}

	@Transactional(readOnly = true)
	public Sala findById(Long id) {
	    return salaRepository.findById(id).orElse(null);
	}

	@Transactional
	public Sala save(Sala sala) {
	    return salaRepository.save(sala);
	}
}
