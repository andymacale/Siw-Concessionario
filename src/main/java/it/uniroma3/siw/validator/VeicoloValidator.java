package it.uniroma3.siw.validator;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

import it.uniroma3.siw.model.*;
import it.uniroma3.siw.service.*;

@Component
public class VeicoloValidator implements Validator{


	@Autowired VeicoloService veicoloService;


	@Override
	public boolean supports(Class<?> clazz) {
		return Veicolo.class.equals(clazz);
	}

	@Override
	public void validate(Object target, Errors errors) {
		Veicolo veicolo = (Veicolo) target;
		String telaio = veicolo.getTelaio().trim();
		String colore = veicolo.getColore().trim();
		String cambio = veicolo.getCambio().trim();
		String targa = veicolo.getTarga().trim();

		if(telaio.isEmpty()) {
			errors.reject("NotBlank.veicolo.telaio");
		}

		if(colore.isEmpty()) {
			errors.reject("NotBlank.veicolo.colore");
		}

		if(cambio.isEmpty()) {
			errors.reject("NotBlank.veicolo.cambio");
		}

		if(veicolo.getAnno() == null) {
			errors.reject("NotNull.veicolo.anno");
		}

		if (!targa.isEmpty() && this.veicoloService.existsByTarga(targa)) {
			errors.reject("veicolo.targa.duplicate");
		}
		
		if (!telaio.isEmpty() && this.veicoloService.existsByTelaio(telaio)) {
			errors.reject("veicolo.telaio.duplicate");
		}
	}
}
