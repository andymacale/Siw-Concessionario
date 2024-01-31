package it.uniroma3.siw.service;

import java.util.*;

import it.uniroma3.siw.model.Prenotazione;
import jakarta.persistence.EntityManager;
import jakarta.persistence.Query;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import it.uniroma3.siw.model.Alimentazione;
import it.uniroma3.siw.model.Modello;
import it.uniroma3.siw.model.Veicolo;
import it.uniroma3.siw.repository.VeicoloRepository;
import jakarta.transaction.Transactional;

@Service
public class VeicoloService {
	@Autowired VeicoloRepository veicoloRepository;
	@Autowired EntityManager entityManager;
	

	public boolean existsById(Long id) {
		return this.veicoloRepository.existsById(id);
	}
	public boolean existsByTelaio(String telaio) {
		return this.veicoloRepository.existsByTelaio(telaio);
	}
	
	public boolean existsByTarga(String targa) {
		return this.veicoloRepository.existsByTarga(targa);
	}
	
	public Veicolo findByTelaio(String telaio) {
		return this.veicoloRepository.findByTelaio(telaio);
	}

	public Veicolo findByTarga(String targa) {
		return this.veicoloRepository.findByTarga(targa);
	}
	
	@Transactional
	public Veicolo save(Veicolo veicolo) {
		return this.veicoloRepository.save(veicolo);
	}

	@Transactional
	public void delete(Veicolo veicolo) {
		this.veicoloRepository.delete(veicolo);
	}

	public Veicolo findById(Long id) {
		Optional<Veicolo> veicolo = this.veicoloRepository.findById(id);
		return veicolo.orElse(null);
	}
	
	public List<Veicolo> findVeicoli(String fabbrica, String nome, String carburante, String colore, String cambio, String prezzoMinimo, String prezzoMassimo, String venduta, String ordinamento, String tipo) {
		StringBuilder select = new StringBuilder();

		select.append("select v.id, v.telaio, m.fabbrica, m.nome, a.carburante, v.colore, v.cambio, v.anno, v.prezzo, v.venduta, v.url_immagine " +
							"from veicolo v " +
							"join modello m on m.id = v.modello_id " +
							"join alimentazione a on a.id = v.alimentazione_id  " +
							"where 1=1 ");
		if (!fabbrica.isEmpty()) {
			select.append("and m.fabbrica='"+fabbrica+"' ");
		}

		if (!nome.isEmpty()) {
			select.append("and m.nome='"+nome+"' ");
		}

		if (!carburante.isEmpty()) {
			select.append("and a.carburante='"+carburante+"' ");
		}

		if (!colore.isEmpty()) {
			select.append("and v.colore='"+colore+"' ");
		}

		//select.append("and v.cambio='"+"Manuale"+"' ");
		if (!cambio.isEmpty()) {
			select.append("and v.cambio='"+cambio+"' ");
		}

		if (!prezzoMinimo.isEmpty()) {
			select.append("and v.prezzo >= "+prezzoMinimo+" ");
		}

		if (!prezzoMassimo.isEmpty()) {
			select.append("and v.prezzo <= " + prezzoMassimo + " ");
		}

		if (!venduta.isEmpty()) {
			select.append("and v.venduta = " + venduta + " ");
		}

		select.append("order by " + ordinamento + " " + tipo );

		Query query = this.entityManager.createNativeQuery(select.toString());
		List<Object[]> ris = query.getResultList();

		List<Veicolo> out = new ArrayList<>();

		for(Object[] campi : ris) {

			Modello nuovoModello = new Modello();
			Alimentazione nuovaAlimentazione = new Alimentazione();
			Veicolo nuovoVeicolo = new Veicolo();

			nuovoVeicolo.setId((Long) campi[0]);
			nuovoVeicolo.setTelaio((String) campi[1]);
			nuovoModello.setFabbrica((String) campi[2]);
			nuovoModello.setNome((String) campi[3]);
			nuovaAlimentazione.setCarburante((String) campi[4]);
			nuovoVeicolo.setColore((String) campi[5]);
			nuovoVeicolo.setCambio((String) campi[6]);
			nuovoVeicolo.setAnno((Integer) campi[7]);
			nuovoVeicolo.setPrezzo((Double) campi[8]);
			nuovoVeicolo.setVenduta((Boolean) campi[9]);
			nuovoVeicolo.setUrlImmagine((String) campi[10]);

			nuovoVeicolo.setModello(nuovoModello);
			nuovoVeicolo.setAlimentazione(nuovaAlimentazione);

			out.add(nuovoVeicolo);
		}
		return out;
	}

	public List<Veicolo> findVeicoliDisponibili() {
		List<Veicolo> veicoli = this.veicoloRepository.findAll();
		List<Veicolo> out = new ArrayList<>();
		for (Veicolo veicolo : veicoli) {
			if(!veicolo.getVenduta()) {
				out.add(veicolo);
			}
		}
		return out;
	}

	public List<Veicolo> findVeicoliDisponibiliETargati() {
		List<Veicolo> veicoli = this.findVeicoliDisponibili();
		List<Veicolo> out = new ArrayList<>();
		for (Veicolo veicolo : veicoli) {
			if(!veicolo.getTarga().isEmpty()) {
				out.add(veicolo);
			}
		}
		return out;
	}


	
}
