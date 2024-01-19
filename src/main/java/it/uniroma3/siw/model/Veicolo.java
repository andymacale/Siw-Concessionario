package it.uniroma3.siw.model;

//import java.sql.Date;
import java.util.Objects;

import it.uniroma3.siw.service.VeicoloService;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import org.springframework.beans.factory.annotation.Autowired;

import jakarta.validation.constraints.NotBlank;


@Entity
public class Veicolo {

	/* Attributi */
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@SequenceGenerator(initialValue = 1, allocationSize = 1, name = "id")
	public Long id;
	@Column(unique=true, nullable=false, length=17)
	@NotBlank
	public String telaio;
	
	@Column(nullable=false)
	@NotNull
	public Integer anno;

	public String targa;
	
	@ManyToOne
	public Modello modello;
	
	@ManyToOne
	public Alimentazione alimentazione;

	@Column(nullable=false)
	@NotBlank
	public String colore;
	
	@Column(nullable=false)
	@NotBlank
	public String cambio;

	public Double prezzo;

	@Column(nullable = false)
	public Boolean venduta;
	
	/*@OneToOne
	public FileData immagine;*/

	@Column(length = 2000)
	public String urlImmagine;

	/* Veicoli uguali (stesso telaio) */
	@Override
	public int hashCode() {
		return Objects.hash(telaio);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Veicolo other = (Veicolo) obj;
		return Objects.equals(telaio, other.telaio);
	}

	/* Getter e setter */
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getTelaio() {
		return telaio;
	}

	public void setTelaio(String telaio) {
		this.telaio = telaio;
	}

	public String getTarga() {
		return targa;
	}

	public void setTarga(String targa) {
		this.targa = targa;
	}

	public Modello getModello() {
		return modello;
	}

	public void setModello(Modello modello) {
		this.modello = modello;
	}

	public String getColore() {
		return colore;
	}

	public void setColore(String colore) {
		this.colore = colore;
	}

	public String getCambio() {
		return cambio;
	}

	public void setCambio(String cambio) {
		this.cambio = cambio;
	}

	public Integer getAnno() {
		return anno;
	}

	public void setAnno(Integer anno) {
		this.anno = anno;
	}
	
	public Alimentazione getAlimentazione() {
		return alimentazione;
	}

	public void setAlimentazione(Alimentazione alimentazione) {
		this.alimentazione = alimentazione;
	}

	public String getUrlImmagine() {
		return urlImmagine;
	}

	public void setUrlImmagine(String urlImmagine) {
		this.urlImmagine = urlImmagine;
	}

	/*public FileData getImmagine() {
		return immagine;
	}

	public void setImmagine(FileData immagine) {
		this.immagine = immagine;
	}*/

	public Double getPrezzo() {
		return prezzo;
	}

	public void setPrezzo(Double prezzo) {
		this.prezzo = prezzo;
	}

	public Boolean getVenduta() {
		return venduta;
	}

	public void setVenduta(Boolean venduta) {
		this.venduta = venduta;
	}


}
