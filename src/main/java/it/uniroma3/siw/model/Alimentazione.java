package it.uniroma3.siw.model;

import java.util.*;

import jakarta.persistence.*;

@Entity
public class Alimentazione {
	/* Attributi */
	@Id
	@GeneratedValue
	@SequenceGenerator(initialValue = 1, allocationSize = 1, name = "id")
	public Long id;
	
	@Column(unique=true, nullable=false)
	public String carburante;


	/* Alimentazione uguale (stesso carburante) */
	@Override
	public int hashCode() {
		return Objects.hash(carburante);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Alimentazione other = (Alimentazione) obj;
		return Objects.equals(carburante, other.carburante);
	}
	
	/* Getter e setter */
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getCarburante() {
		return carburante;
	}

	public void setCarburante(String carburante) {
		this.carburante = carburante;
	}

	
}
