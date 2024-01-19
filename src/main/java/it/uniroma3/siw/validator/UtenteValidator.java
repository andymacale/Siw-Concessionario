package it.uniroma3.siw.validator;

import it.uniroma3.siw.model.Utente;
import it.uniroma3.siw.repository.UtenteRepository;
import it.uniroma3.siw.service.UtenteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

@Component
public class UtenteValidator implements Validator {

    @Override
    public boolean supports(Class<?> clazz) {
        return Utente.class.equals(clazz);
    }

    @Override
    public void validate(Object target, Errors errors) {
        Utente utente = (Utente) target;

        String nome = utente.getNome().trim();
        String cognome = utente.getCognome().trim();

        if(nome.isEmpty()) {
            errors.reject("NotBlank.utente.nome");
        }

        if(cognome.isEmpty()) {
            errors.reject("NotBlank.utente.cognome");
        }

        if(utente.getDataNascita() == null) {
            errors.reject("NotNull.utente.dataNascita");
        }
    }
}
