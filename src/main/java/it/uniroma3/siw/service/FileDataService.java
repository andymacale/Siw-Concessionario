package it.uniroma3.siw.service;

import java.io.File;
import java.io.IOException;
import java.nio.file.FileSystems;
import java.nio.file.Files;
import java.util.Optional;

import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import it.uniroma3.siw.model.FileData;
import it.uniroma3.siw.repository.FileDataRepository;

@Service
public class FileDataService {
	@Autowired public FileDataRepository fileDataRepository;

	public final static String ROOT_PATH =
			FileSystems.getDefault().getPath("").toAbsolutePath().toString() + "/src/main/resources/static/immagini/caricate/";

	private FileData buildFileData(MultipartFile file, String filePath) {
		return FileData.builder().nome(file.getOriginalFilename())
				.tipo(file.getContentType())
				.path(filePath).build();
	}
	
	public String uploadImmagine(MultipartFile file, String telaio) throws IllegalStateException, IOException {
		/*if (file.getOriginalFilename().isEmpty()) {
			return null;
		}*/
		String filePath = ROOT_PATH + telaio + ".jpg";
		FileData fileData = this.fileDataRepository.save(this.buildFileData(file, filePath));

		file.transferTo(new File(filePath));

		if (fileData != null) {
			return filePath;
		}
		return null;
	}


	public FileData findByNome(String nome) {
		return this.fileDataRepository.findByNome(nome).orElse(null);
	}

	@Transactional
	public FileData save(FileData file) {
		return this.fileDataRepository.save(file);
	}
}
