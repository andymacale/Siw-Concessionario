package it.uniroma3.siw.repository;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

import it.uniroma3.siw.model.Alimentazione;

public interface AlimentazioneRepository extends CrudRepository<Alimentazione, Long> {
	public boolean existsByCarburante(String carburante);
	public Alimentazione findByCarburante(String carburante);
	public List<Alimentazione> findAll();
}
