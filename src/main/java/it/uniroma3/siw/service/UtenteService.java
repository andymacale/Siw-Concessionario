package it.uniroma3.siw.service;

import it.uniroma3.siw.model.Prenotazione;
import it.uniroma3.siw.model.Utente;
import it.uniroma3.siw.repository.UtenteRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Optional;

@Service
public class UtenteService {

    @Autowired
    UtenteRepository utenteRepository;

    public Utente getUtente(Long id) {
        Optional<Utente> utente = this.utenteRepository.findById(id);
        return utente.orElse(null);
    }

    public boolean existsByEmail(String email) {
        return this.utenteRepository.existsByEmail(email);
    }

    public Utente findByEmail(String email) {
        return this.utenteRepository.findByEmail(email);
    }

    @Transactional
    public Utente save(Utente utente) {
        return this.utenteRepository.save(utente);
    }

    @Transactional
    public List<Utente> findAll() {
        return this.utenteRepository.findAll();
    }

}
