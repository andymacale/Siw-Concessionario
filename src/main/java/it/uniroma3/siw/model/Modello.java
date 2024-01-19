package it.uniroma3.siw.model;

import java.util.Objects;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

@Entity
@Table(uniqueConstraints=@UniqueConstraint(columnNames={"fabbrica","nome"}))
public class Modello {
	/* Attributi */
	@Id
	@GeneratedValue
	@SequenceGenerator(initialValue = 1, allocationSize = 1, name = "id")
	public Long id;
	
	@Column(nullable=false)
	@NotBlank
	public String fabbrica;
	
	@Column(nullable=false)
	@NotBlank
	public String nome;
	
	@Column(nullable=false)
	@NotNull
	public Integer posti;
	
	@Column(nullable=false)
	@NotNull
	public Integer cilindrata;
	
	@Column(nullable=false)
	@NotNull
	public Integer cavalli;
	
	
	/* Modelli uguali (stessa fabbrica e stesso nome) */
	@Override
	public int hashCode() {
		return Objects.hash(fabbrica, nome);
	}
	
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Modello other = (Modello) obj;
		return Objects.equals(fabbrica, other.fabbrica) && Objects.equals(nome, other.nome);
	}
	
	/* Getter e setter */
	public Long getId() {
		return id;
	}
	
	public void setId(Long id) {
		this.id = id;
	}
	
	public String getFabbrica() {
		return fabbrica;
	}
	
	public void setFabbrica(String fabbrica) {
		this.fabbrica = fabbrica;
	}
	
	public String getNome() {
		return nome;
	}
	
	public void setNome(String nome) {
		this.nome = nome;
	}
	
	public Integer getPosti() {
		return posti;
	}

	public void setPosti(Integer posti) {
		this.posti = posti;
	}

	public Integer getCilindrata() {
		return cilindrata;
	}

	public void setCilindrata(Integer cilindrata) {
		this.cilindrata = cilindrata;
	}

	public Integer getCavalli() {
		return cavalli;
	}

	public void setCavalli(Integer cavalli) {
		this.cavalli = cavalli;
	}

}
