package it.uniroma3.siw.model;

import jakarta.persistence.*;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.Comparator;
import java.util.Objects;

@Entity
public class Prenotazione {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    public Long id;

    @Column(nullable = false)
    public Long prog;

    @Column(nullable = false)
    @DateTimeFormat(pattern="dd-MM-yyyy")
    public LocalDate dataPrenotazione;

    @Column(nullable = false)
    public LocalTime ora;

    @ManyToOne
    public Utente persona;

    @ManyToOne
    public Consulente consulente;

    @OneToOne
    public Veicolo veicolo;

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (getClass() != obj.getClass())
            return false;
        Prenotazione other = (Prenotazione) obj;
        return Objects.equals(dataPrenotazione, other.dataPrenotazione) && Objects.equals(ora, other.ora) && Objects.equals(persona, other.persona) && Objects.equals(consulente, other.consulente);
    }

    @Override
    public int hashCode() {
        return Objects.hash(dataPrenotazione, ora, persona, consulente);
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public LocalDate getDataPrenotazione() {
        return dataPrenotazione;
    }

    public void setDataPrenotazione(LocalDate dataPrenotazione) {
        this.dataPrenotazione = dataPrenotazione;
    }

    public LocalTime getOra() {
        return ora;
    }

    public void setOra(LocalTime ora) {
        this.ora = ora;
    }

    public Utente getPersona() {
        return persona;
    }

    public void setPersona(Utente persona) {
        this.persona = persona;
    }

    public Consulente getConsulente() {
        return consulente;
    }

    public void setConsulente(Consulente consulente) {
        this.consulente = consulente;
    }

    public Veicolo getVeicolo() {
        return veicolo;
    }

    public void setVeicolo(Veicolo veicolo) {
        this.veicolo = veicolo;
    }

    public Long getProg() {
        return prog;
    }

    public void setProg(Long prog) {
        this.prog = prog;
    }
}
