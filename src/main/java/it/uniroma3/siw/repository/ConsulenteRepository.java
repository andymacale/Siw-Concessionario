package it.uniroma3.siw.repository;

import it.uniroma3.siw.model.Consulente;
import org.springframework.data.repository.CrudRepository;

import java.util.List;
import java.util.Optional;

public interface ConsulenteRepository extends CrudRepository<Consulente, Long> {

    public boolean existsByCodiceFiscale(String codiceFiscale);
    public Consulente findByCodiceFiscale(String codiceFiscale);

    public Optional<Consulente> findById(Long id);

    public List<Consulente> findAll();

    public void delete(Consulente consulente);

}
