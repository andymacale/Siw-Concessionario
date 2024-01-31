package it.uniroma3.siw.controller;

import java.io.IOException;

import it.uniroma3.siw.model.Veicolo;
import it.uniroma3.siw.service.VeicoloService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import it.uniroma3.siw.model.FileData;
import it.uniroma3.siw.service.FileDataService;

@Controller
public class FileController {
	@Autowired FileDataService fileDataService;
	@Autowired VeicoloService veicoloService;

	
	@GetMapping("/admin/formNewImmagine")
	public String formNewImmagine(Model model) {
		model.addAttribute("immagine", new FileData());
		model.addAttribute("veicoli", veicoloService.findVeicoliDisponibili());
		return "admin/formNewImmagine.html";
	}
	
	
	/* upload del file */
	@PostMapping("/admin/image")
	public String uploadImmagine(@RequestParam("image") MultipartFile file, @RequestParam("telaio")String telaio) throws IllegalStateException, IOException {
		if (telaio.trim().isEmpty()) {
			return "admin/immagineFallita";
		}
		String immagine = this.fileDataService.uploadImmagine(file, telaio);
		System.out.println(immagine);
		Veicolo veicolo = this.veicoloService.findByTelaio(telaio);
		veicolo.setUrlImmagine("/immagini/caricate/"+telaio + ".jpg");
		this.veicoloService.save(veicolo);
		return "admin/immagineSuccesso";
	}
}
