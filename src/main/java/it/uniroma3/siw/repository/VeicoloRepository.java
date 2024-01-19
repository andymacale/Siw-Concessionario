package it.uniroma3.siw.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.repository.CrudRepository;

import it.uniroma3.siw.model.Alimentazione;
import it.uniroma3.siw.model.Modello;
import it.uniroma3.siw.model.Veicolo;

public interface VeicoloRepository extends CrudRepository<Veicolo, Long>{

	public boolean existsById(Long id);
	public boolean existsByTelaio(String telaio);
	public boolean existsByTarga(String targa);
	public Veicolo findByTelaio(String telaio);
	public Veicolo findByTarga(String targa);

	public Optional<Veicolo> findById(Long id);
	public void delete(Veicolo veicolo);

	public List<Veicolo> findAll();



}
