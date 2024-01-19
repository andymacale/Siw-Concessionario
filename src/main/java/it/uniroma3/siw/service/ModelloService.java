package it.uniroma3.siw.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.*;

import it.uniroma3.siw.model.Modello;
import it.uniroma3.siw.repository.ModelloRepository;
import jakarta.transaction.Transactional;

@Service
public class ModelloService {
	@Autowired ModelloRepository modelloRepository;
	
	public boolean existsByFabbricaAndNome(String fabbrica, String nome) {
		return this.modelloRepository.existsByFabbricaAndNome(fabbrica, nome);
	}
	
	public Modello findByFabbricaAndNome(String fabbrica, String nome) {
		return this.modelloRepository.findByFabbricaAndNome(fabbrica, nome);
	}
	
	public List<Modello> findAll() {
		return this.modelloRepository.findAll();
	}
	
	@Transactional
	public Modello save(Modello modello) {
		return this.modelloRepository.save(modello);
	} 
}
