package it.uniroma3.siw.repository;

import it.uniroma3.siw.model.Consulente;
import it.uniroma3.siw.model.Prenotazione;
import org.springframework.data.repository.CrudRepository;

import java.util.List;
import java.util.Optional;

public interface PrenotazioneRepository extends CrudRepository<Prenotazione, Long> {

    public Optional<Prenotazione> findById(Long id);

    public List<Prenotazione> findAll();

    public Prenotazione save(Prenotazione prenotazione);

    public void delete(Prenotazione prenotazione);
}
