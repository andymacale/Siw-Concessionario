package it.uniroma3.siw.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import org.springframework.format.annotation.DateTimeFormat;
import java.time.LocalDate;
import java.util.Objects;
import jakarta.validation.constraints.NotBlank;


@Entity
public class Consulente {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    public Long id;

    @NotBlank
    @Column(nullable = false)
    public String nome;

    @NotBlank
    @Column(nullable = false)
    public String cognome;

    @DateTimeFormat(pattern="dd-MM-yyyy")
    @Column(columnDefinition = "DATE", nullable = false)
    @NotNull
    public LocalDate dataDiNascita;

    @Column(nullable = false)
    @NotNull
    public Character sesso;

    @NotBlank
    @Column(unique = true, nullable = false, length = 16)
    public String codiceFiscale;

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (getClass() != obj.getClass())
            return false;
        Consulente other = (Consulente) obj;
        return Objects.equals(codiceFiscale, other.codiceFiscale);
    }

    @Override
    public int hashCode() {
        return Objects.hash(codiceFiscale);
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

    public String getCognome() {
        return cognome;
    }

    public void setCognome(String cognome) {
        this.cognome = cognome;
    }

    public LocalDate getDataDiNascita() {
        return dataDiNascita;
    }

    public void setDataDiNascita(LocalDate dataDiNascita) {
        this.dataDiNascita = dataDiNascita;
    }

    public String getCodiceFiscale() {
        return codiceFiscale;
    }

    public void setCodiceFiscale(String codiceFiscale) {
        this.codiceFiscale = codiceFiscale;
    }

    public Character getSesso() {
        return sesso;
    }

    public void setSesso(Character sesso) {
        this.sesso = sesso;
    }
}
