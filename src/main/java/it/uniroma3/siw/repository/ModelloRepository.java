package it.uniroma3.siw.repository;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

import it.uniroma3.siw.model.Modello;

public interface ModelloRepository extends CrudRepository<Modello, Long> {
	public boolean existsByFabbricaAndNome(String fabbrica, String nome);
	public Modello findByFabbricaAndNome(String fabbrica, String nome);
	public List<Modello> findAll();
	
}
