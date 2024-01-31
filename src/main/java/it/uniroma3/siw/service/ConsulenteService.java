package it.uniroma3.siw.service;

import it.uniroma3.siw.model.Consulente;
import it.uniroma3.siw.model.Utente;
import it.uniroma3.siw.repository.ConsulenteRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

@Service
public class ConsulenteService {
    @Autowired ConsulenteRepository consulenteRepository;

    public boolean existsByCodiceFiscale(String codiceFiscale) {
        return this.consulenteRepository.existsByCodiceFiscale(codiceFiscale);
    }

    public Consulente findByCodiceFiscale(String codiceFiscale) {
        return this.consulenteRepository.findByCodiceFiscale(codiceFiscale);
    }

    public List<Consulente> findAll() {
        List<Consulente> consulenti = this.consulenteRepository.findAll();
        return consulenti;
    }

    public Consulente findById(Long id) {
        return this.consulenteRepository.findById(id).get();
    }

    @Transactional
    public Consulente save(Consulente consulente) {
        return this.consulenteRepository.save(consulente);
    }

    @Transactional
    public void delete(Consulente consulente) {
        this.consulenteRepository.delete(consulente);
    }
}
