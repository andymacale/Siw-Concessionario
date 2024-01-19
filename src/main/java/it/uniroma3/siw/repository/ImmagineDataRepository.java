package it.uniroma3.siw.repository;

import java.util.Optional;

import org.springframework.data.repository.CrudRepository;

import it.uniroma3.siw.model.ImmagineData;

public interface ImmagineDataRepository extends CrudRepository<ImmagineData, Long>{
	public Optional<ImmagineData> findByNome(String nome);
}
