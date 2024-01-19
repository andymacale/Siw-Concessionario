package it.uniroma3.siw.service;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import it.uniroma3.siw.model.FileData;
import it.uniroma3.siw.repository.FileDataRepository;
import it.uniroma3.siw.repository.ImmagineDataRepository;

@Service
public class FileDataService {
	@Autowired public FileDataRepository fileDataRepository;
	@Autowired public ImmagineDataRepository immagineDataRepository;

	public final static String ROOT_PATH = 
			"/Users/andrea/Documents/workspace-spring-tool-suite-4-4.20.0.RELEASE/"
					+ "Concessionario/src/main/resources/static/immagini/caricate";

	private FileData buildFileData(MultipartFile file, String filePath) {
		return FileData.builder().nome(file.getOriginalFilename())
				.tipo(file.getContentType())
				.path(filePath).build();
	}
	
	public String uploadImmagine(MultipartFile file) throws IllegalStateException, IOException {
		String filePath = ROOT_PATH + file.getOriginalFilename();

		FileData fileData = this.fileDataRepository.save(this.buildFileData(file, filePath));

		file.transferTo(new File(filePath));

		if (fileData != null) {
			return "file uploaded successfully : " + filePath;
		}
		return null;
	}
	
	public byte[] downloadImmagine(String nomeFile) throws IOException {
		java.util.Optional<FileData> file = this.fileDataRepository.findByNome(nomeFile);
		String filePath = file.get().getPath();
		byte[] immagine = Files.readAllBytes(new File(filePath).toPath());
		return immagine;
	}

	//public FileData findBy();

}
