package it.uniroma3.siw.controller;

import java.io.IOException;
import java.util.List;

import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import it.uniroma3.siw.model.*;
import it.uniroma3.siw.service.*;
import it.uniroma3.siw.validator.*;


@Controller
public class MacchinaController {
	@Autowired ModelloService modelloService;
	@Autowired ModelloValidator modelloValidator;
	@Autowired AlimentazioneService alimentazioneService;
	@Autowired VeicoloService veicoloService;
	@Autowired VeicoloValidator veicoloValidator;
	@Autowired FileDataService fileDataService;
	
	public final static String ERRORE = "messaggioErrore";
	public final static String ROOT_IMMAGINI = "/immagini/caricate/";
	public final static String UPLOAD_DIRECTORY = System.getProperty("user.dir") + "/uploads";
	public final static int DIM_MAX_IMMAGINE = 2097152;
	


	private String primaMaiuscola(String stringa) {
		StringBuilder ris = new StringBuilder();
		if(stringa == null || stringa.isEmpty() || stringa.length() == 1) {
			return stringa;
		}
		ris.append(stringa.substring(0, 1).toUpperCase());
		ris.append(stringa.substring(1, stringa.length()).toLowerCase());
		return ris.toString();
	}

	private String pulisci(String stringa) {
		StringBuilder out = new StringBuilder();
		for (char carattere : stringa.toCharArray()) {
			if ((carattere >= '0' && carattere <= '9') || (carattere >= 'A' && carattere <= 'Z') || (carattere >= 'a' && carattere <= 'z')) {
				out.append(carattere);
			}
		}
		return out.toString();
	}

	
	@GetMapping("/admin/macchinaAggiunta")
	public String macchinaAggiunta() {
		return "admin/macchinaAggiunta.html";
	}

	@GetMapping("/admin/macchinaVenduta")
	public String macchinaVenduta() {
		return "admin/macchinaVenduta.html";
	}

	@GetMapping("/admin/targaAggiornata")
	public String targaAggiornata() {
		return "admin/targaAggiornata.html";
	}

	@GetMapping("/admin/formNewMacchina")
	public String formVeicolo(Model model) {
		model.addAttribute("modello", new Modello());
		model.addAttribute("veicolo", new Veicolo());
		return "admin/formNewMacchina.html";
	}
	
	@PostMapping("/admin/macchine")
	public String newVeicolo(@Valid @ModelAttribute("modello") Modello modello, BindingResult modelloBindingResult, @Valid @ModelAttribute("veicolo") Veicolo veicolo, BindingResult veicoloBindingResult, @RequestParam("carburante") String carburante, @ModelAttribute("immagine") MultipartFile file, Model model) throws IllegalStateException, IOException {

		String fabbrica = this.primaMaiuscola(modello.getFabbrica());
		String nome = this.primaMaiuscola(modello.getNome());

		modello.setFabbrica(fabbrica);
		modello.setNome(nome);
		veicolo.setTarga(veicolo.getTarga().toUpperCase());
		veicolo.setColore(primaMaiuscola(veicolo.getColore()));

		this.modelloValidator.validate(modello, modelloBindingResult);
		if(modelloBindingResult.hasErrors()) {
			model.addAttribute("modello", modello);
			this.veicoloValidator.validate(veicolo, veicoloBindingResult);
			model.addAttribute("veicolo", veicolo);
			return "admin/formNewMacchina.html";
		}
		if (this.modelloService.existsByFabbricaAndNome(fabbrica, nome)) {
			model.getAttribute("modello");
		} else {
			this.modelloService.save(modello);
			model.addAttribute("modello");
		}
		//this.fileDataService.uploadImmagine(file);
		veicolo.setModello(this.modelloService.findByFabbricaAndNome(fabbrica, nome));
		veicolo.setAlimentazione(this.alimentazioneService.findByCarburante(carburante));
		//veicolo.setUrlImmagine("/fileSystem/" + file.getOriginalFilename());
		if (veicolo.getPrezzo() == null) {
			veicolo.setPrezzo(0.0);
		}
		veicolo.setVenduta(false);
		this.veicoloValidator.validate(veicolo, veicoloBindingResult);
		if(veicoloBindingResult.hasErrors()) {
			model.addAttribute("veicolo", veicolo);
			return "admin/formNewMacchina.html";
		}
		this.veicoloService.save(veicolo);
		model.addAttribute("veicolo");
		return "admin/macchinaAggiunta.html";
	}
	
	
	@GetMapping("/formCercaMacchine")
	public String getMacchine(Model model) {
		return "formCercaMacchine.html";
	}
	
	@PostMapping("/cercaMacchine")
	public String cercaMacchine(Model model, @RequestParam String fabbrica, @RequestParam String nome, @RequestParam String colore, @RequestParam String cambio, @RequestParam String carburante, @RequestParam String prezzoMinimo, @RequestParam String prezzoMassimo, @RequestParam String venduta, @RequestParam String ordinamento, @RequestParam String tipo) {
		List<Veicolo> veicoli = this.veicoloService.findVeicoli(primaMaiuscola(fabbrica), primaMaiuscola(nome), carburante, primaMaiuscola(colore), pulisci(cambio), prezzoMinimo, prezzoMassimo, venduta, ordinamento, tipo);
		model.addAttribute("num", this.veicoloService.contaRisultati(veicoli));
		model.addAttribute("veicoli", veicoli);
		if(veicoli != null) {
			for (Veicolo veicolo : veicoli) {
				model.addAttribute("modello", veicolo.getModello());
				model.addAttribute("alimentazione", veicolo.getAlimentazione());
			}
		}
		return "risMacchine.html";
	}

	@GetMapping("/risMacchine/{id}")
	public String getMacchina(@PathVariable("id") Long id, Model model){
		Veicolo veicolo = this.veicoloService.findById(id);
		model.addAttribute("veicolo", veicolo);
		if (veicolo != null) {
			model.addAttribute("modello", veicolo.getModello());
			model.addAttribute("alimentazione", veicolo.getAlimentazione());
		}
		return "macchina.html";
	}

	@GetMapping("/admin/formAggiungiTarga")
	public String targaMacchina(Model model) {
		model.addAttribute("veicoli", this.veicoloService.findVeicoliDisponibili());
		return "admin/formAggiungiTarga.html";
	}

	@PostMapping("/admin/targa")
	public String modificaTarga(@RequestParam String telaio, @RequestParam String nuova, Model model){
		Veicolo veicolo = null;

		if(telaio.isEmpty()) {
			model.addAttribute("messaggioErrore", "il veicolo non è stato inserito!");
			return "admin/targaNonAggiornata.html";
		}
		veicolo = this.veicoloService.findByTelaio(telaio);
		if(nuova.isEmpty()) {
			model.addAttribute("messaggioErrore", "la nuova targa non è stata inserita!");
			return "admin/targaNonAggiornata.html";
		}
		veicolo.setTarga(nuova);
		this.veicoloService.save(veicolo);
		model.addAttribute("veicolo", veicolo);
		return "admin/targaAggiornata.html";
	}

	@GetMapping("/admin/formVendi")
	public String formVendi(Model model){
		model.addAttribute("veicoli", this.veicoloService.findVeicoliDisponibiliETargati());
		return "admin/formVendi.html";
	}

	@PostMapping("/admin/vendi")
	public String vendiMacchina(@RequestParam String telaio, @RequestParam String prezzo, Model model){

			Veicolo veicolo = null;

			if(telaio.isEmpty()) {
				model.addAttribute("messaggioErrore", "il veicolo non è stato inserito!");
				return "admin/macchinaNonVenduta.html";
			}
			veicolo = this.veicoloService.findByTelaio(telaio);
			veicolo.setVenduta(true);
			if(!prezzo.isEmpty()) {
				veicolo.setPrezzo(Double.parseDouble(prezzo));
			}
			this.veicoloService.save(veicolo);
			model.addAttribute("veicolo", veicolo);
			return "admin/macchinaVenduta.html";
		}
}
