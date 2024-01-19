package it.uniroma3.siw.validator;

import it.uniroma3.siw.model.Consulente;
import it.uniroma3.siw.service.ConsulenteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;


@Component
public class ConsulenteValidator implements Validator {

    @Autowired ConsulenteService consulenteService;

    @Override
    public boolean supports(Class<?> clazz) {
        return Consulente.class.equals(clazz);
    }

    @Override
    public void validate(Object target, Errors errors) {
        Consulente consulente = (Consulente) target;
        if (consulente.getCodiceFiscale().isEmpty()) {
            errors.reject("NotBlank.consulente.codiceFiscale");
        }
        if (consulente.getNome().isEmpty()) {
            errors.reject("NotBlank.consulente.nome");
        }
        if (consulente.getCognome().isEmpty()) {
            errors.reject("NotBlank.consulente.cognome");
        }
        if (consulente.getDataDiNascita() == null) {
            errors.reject("NotNull.consulente.dataDiNascita");
        }
        if (consulente.getSesso() == null) {
            errors.reject("NotNull.consulente.sesso");
        }
        if(!consulente.getCodiceFiscale().isEmpty() && this.consulenteService.existsByCodiceFiscale(consulente.getCodiceFiscale())) {
            errors.reject("consulente.duplicate.codiceFiscale");
        }
    }
}
