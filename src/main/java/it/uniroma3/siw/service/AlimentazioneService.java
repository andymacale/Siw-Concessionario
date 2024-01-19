package it.uniroma3.siw.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.*;

import it.uniroma3.siw.model.Alimentazione;
import it.uniroma3.siw.repository.AlimentazioneRepository;
import jakarta.transaction.Transactional;

@Service
public class AlimentazioneService {
	@Autowired AlimentazioneRepository alimentazioneRepository;
	
	public boolean existsByCarburante(String carburante) {
		return this.alimentazioneRepository.existsByCarburante(carburante);
	}
	
	public Alimentazione findByCarburante(String carburante) {
		return this.alimentazioneRepository.findByCarburante(carburante);
	}
	
	public List<Alimentazione> findAll() {
		return this.alimentazioneRepository.findAll();
	}
	
	@Transactional
	public Alimentazione save(Alimentazione alimentazione) {
		return this.alimentazioneRepository.save(alimentazione);
	} 
}
