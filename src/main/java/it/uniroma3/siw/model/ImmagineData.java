package it.uniroma3.siw.model;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "ImmagineData")
@Data
@AllArgsConstructor
@NoArgsConstructor
@Getter
public class ImmagineData {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	public Long id;

    public String nome;
    
    public String tipo;
    
    @Lob
    @Column(name = "dati",length = 1000)
    public byte[] dati;
    
    public ImmagineData(String nome, String tipo, byte[] dati) {
		this.nome = nome;
		this.tipo = tipo;
		this.dati = dati;
	}
    
    public static ImmagineDataBuilder builder() {
		return new ImmagineDataBuilder();
	}
    
    public static class ImmagineDataBuilder {
		private String nome;
		
		private String tipo;
		
		private byte[] dati;
		
		public ImmagineDataBuilder nome(String nome) {
			this.nome = nome;
			return this;
		}
		
		public ImmagineDataBuilder tipo(String tipo) {
			this.tipo = tipo;
			return this;
		}
		
		public ImmagineDataBuilder dati(byte[] dati) {
			this.dati = dati;
			return this;
		}
		
		public ImmagineData build() {
			return new ImmagineData(this.nome, this.tipo, this.dati);
		}
	}
}
