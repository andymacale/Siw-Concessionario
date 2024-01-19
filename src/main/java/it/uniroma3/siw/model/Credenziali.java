package it.uniroma3.siw.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;

import java.util.Objects;

@Entity
public class Credenziali {

    public static final String DEFAULT_ROLE = "DEFAULT";
    public static final String ADMIN_ROLE = "ADMIN";

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    public Long id;

    @Column(unique = true, nullable = false)
    @NotBlank
    public String username;

    @Column(nullable = false)
    @NotBlank
    public String password;

    @Column(nullable = false)
    public String ruolo;

    @OneToOne(cascade = CascadeType.ALL)
    private Utente utente;

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (getClass() != obj.getClass())
            return false;
        Credenziali other = (Credenziali) obj;
        return Objects.equals(username, other.username);
    }

    @Override
    public int hashCode() {
        return Objects.hash(username);
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getRuolo() {
        return ruolo;
    }

    public void setRuolo(String ruolo) {
        this.ruolo = ruolo;
    }

    public Utente getUtente() {
        return utente;
    }

    public void setUtente(Utente utente) {
        this.utente = utente;
    }
}
