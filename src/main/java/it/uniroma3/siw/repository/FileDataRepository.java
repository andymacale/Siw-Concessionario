package it.uniroma3.siw.repository;

import java.util.Optional;

import org.springframework.data.repository.CrudRepository;

import it.uniroma3.siw.model.FileData;

public interface FileDataRepository extends CrudRepository<FileData, Long>{
	public Optional<FileData> findByNome(String nome);
}
