package it.uniroma3.siw.repository;

import it.uniroma3.siw.model.Utente;
import org.springframework.data.repository.CrudRepository;

import java.util.List;
import java.util.Optional;

public interface UtenteRepository extends CrudRepository<Utente, Long> {

    public Optional<Utente> findById(Long id);

    public boolean existsByEmail(String email);

    public Utente findByEmail(String email);

    public List<Utente> findAll();

    public Utente save(Utente utente);
}
