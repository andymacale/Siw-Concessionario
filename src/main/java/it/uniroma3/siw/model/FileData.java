package it.uniroma3.siw.model;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "FILE_DATA")
@Data
@AllArgsConstructor
@NoArgsConstructor
@Getter
public class FileData {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	public Long id;

	public String nome;
	
	public String tipo;
	
	public String path;
	
	public FileData(String nome, String tipo, String path) {
		this.nome = nome;
		this.tipo = tipo;
		this.path = path;
	}

	public Long getId() {
		return id;
	}



	public void setId(Long id) {
		this.id = id;
	}



	public String getNome() {
		return nome;
	}



	public void setNome(String nome) {
		this.nome = nome;
	}



	public String getTipo() {
		return tipo;
	}



	public void setTipo(String tipo) {
		this.tipo = tipo;
	}



	public String getPath() {
		return path;
	}



	public void setPath(String path) {
		this.path = path;
	}



	public static FileDataBuilder builder() {
		return new FileDataBuilder();
	}
	
	public static class FileDataBuilder {
		private String nome;
		
		private String tipo;
		
		private String path;
		
		public FileDataBuilder nome(String nome) {
			this.nome = nome;
			return this;
		}
		
		public FileDataBuilder tipo(String tipo) {
			this.tipo = tipo;
			return this;
		}
		
		public FileDataBuilder path(String path) {
			this.path = path;
			return this;
		}
		
		public FileData build() {
			return new FileData(nome, tipo, path);
		}
	}
}
