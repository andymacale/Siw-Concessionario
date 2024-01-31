package it.uniroma3.siw.service;

import it.uniroma3.siw.model.Credenziali;
import it.uniroma3.siw.model.Utente;
import it.uniroma3.siw.repository.CredenzialiRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class CredenzialiService {

    @Autowired CredenzialiRepository credenzialiRepository;
    @Autowired protected PasswordEncoder passwordEncoder;
    public Credenziali getCredenziali(Long id) {
        Optional<Credenziali> credenziali = this.credenzialiRepository.findById(id);
        return credenziali.orElse(null);
    }

    public Credenziali getCredenziali(String username) {
        Optional<Credenziali> credenziali = this.credenzialiRepository.findByUsername(username);
        return credenziali.orElse(null);
    }

    @Transactional
    public Credenziali save(Credenziali credenziali) {
        credenziali.setPassword(this.passwordEncoder.encode(credenziali.getPassword()));
        return this.credenzialiRepository.save(credenziali);
    }

    public List<Credenziali> findAll() {
        return this.credenzialiRepository.findAll();
    }

    public void delate(Credenziali credenziali) {
        this.credenzialiRepository.delete(credenziali);
    }
}
