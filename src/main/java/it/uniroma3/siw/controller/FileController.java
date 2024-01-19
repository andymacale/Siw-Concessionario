package it.uniroma3.siw.controller;

import java.io.IOException;
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
	
	@GetMapping("/admin/formNewImmagine")
	public String formNewImmagine(Model model) {
		model.addAttribute("immagine", new FileData());
		return "admin/formNewImmagine.html";
	}
	
	
	/* Gestione upload/download dei file */
	@PostMapping("/image")
	public ResponseEntity<?> uploadImmagine(@RequestParam("image") MultipartFile file) throws IllegalStateException, IOException {
		String immagine = this.fileDataService.uploadImmagine(file);
		return ResponseEntity.status(HttpStatus.OK).body(immagine);
	}

	@GetMapping("/image/{nomeFile}")
	public ResponseEntity<?> downloadImmagine(@PathVariable("nomeFile") String nomeFile) throws IllegalStateException, IOException {
		byte[] immagine = this.fileDataService.downloadImmagine(nomeFile);
		return ResponseEntity.status(HttpStatus.OK).contentType(MediaType.valueOf("image/jpg")).body(immagine);

	}
}
