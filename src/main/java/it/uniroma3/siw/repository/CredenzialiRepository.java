package it.uniroma3.siw.repository;

import it.uniroma3.siw.model.Credenziali;
import org.springframework.data.repository.CrudRepository;

import java.util.List;
import java.util.Optional;

public interface CredenzialiRepository extends CrudRepository<Credenziali, Long> {


    public Optional<Credenziali> findById(Long id);
    public Optional<Credenziali> findByUsername(String username);

    public List<Credenziali> findAll();

    public Credenziali save(Credenziali credenziali);

    public void delete(Credenziali credenziali);
}
