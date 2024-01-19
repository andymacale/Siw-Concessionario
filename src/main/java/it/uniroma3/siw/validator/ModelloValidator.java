package it.uniroma3.siw.validator;

import it.uniroma3.siw.model.Modello;
import it.uniroma3.siw.service.ModelloService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

@Component
public class ModelloValidator implements Validator{

	@Override
	public boolean supports(Class<?> clazz) {
		return Modello.class.equals(clazz);
	}

	@Override
	public void validate(Object target, Errors errors) {
		Modello modello = (Modello) target;

		String fabbrica = modello.getFabbrica().trim();
		String nome = modello.getNome().trim();

		if(fabbrica.isEmpty()) {
			errors.reject("NotBlank.modello.fabbrica");
		}
		
		if(nome.isEmpty()) {
			errors.reject("NotBlank.modello.nome");
		}
		
		if(modello.getPosti() == null) {
			errors.reject("NotNull.modello.posti");
		}
		
		if(modello.getCilindrata() == null) {
			errors.reject("NotNull.modello.cilindrata");
		}
		
		if(modello.getCavalli() == null) {
			errors.reject("NotNull.modello.cavalli");
		}
	}
}
