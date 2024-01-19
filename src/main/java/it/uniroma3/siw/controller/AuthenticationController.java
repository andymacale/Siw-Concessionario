package it.uniroma3.siw.controller;

import it.uniroma3.siw.model.Credenziali;
import it.uniroma3.siw.model.Utente;
import it.uniroma3.siw.service.CredenzialiService;
import it.uniroma3.siw.service.UtenteService;
import it.uniroma3.siw.validator.CredenzialiValidator;
import it.uniroma3.siw.validator.UtenteValidator;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import static it.uniroma3.siw.model.Credenziali.ADMIN_ROLE;

@Controller
public class AuthenticationController {
	
	@Autowired private CredenzialiService credenzialiService;
	@Autowired private CredenzialiValidator credenzialiValidator;
    @Autowired private UtenteService utenteService;
	@Autowired private UtenteValidator utenteValidator;
	private final static String CREATORE = "and.macale@stud.uniroma3.it";

	@GetMapping(value = "/register")
	public String showRegisterForm (Model model) {
		model.addAttribute("utente", new Utente());
		model.addAttribute("credenziali", new Credenziali());
		return "formRegistrazione.html";
	}
	
	@GetMapping(value = "/login")
	public String showLoginForm (Model model) {
		return "formLogin.html";
	}

	@GetMapping(value = "/") 
	public String index(Model model) {
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		if (authentication instanceof AnonymousAuthenticationToken) {
	        return "index.html";
		}
		else {		
			UserDetails  utenteDetails = (UserDetails)SecurityContextHolder.getContext().getAuthentication().getPrincipal();
			Credenziali credenziali = this.credenzialiService.getCredenziali(utenteDetails.getUsername());
			if (credenziali.getRuolo().equals(ADMIN_ROLE)) {
				return "admin/opzioni.html";
			}
		}
        return "index.html";
	}
		
    @GetMapping(value = "/success")
    public String defaultAfterLogin(Model model) {

		UserDetails utenteDetails = (UserDetails)SecurityContextHolder.getContext().getAuthentication().getPrincipal();
    	Credenziali credenziali = this.credenzialiService.getCredenziali(utenteDetails.getUsername());
    	if (credenziali.getRuolo().equals(ADMIN_ROLE)) {
            return "admin/opzioni.html";
        }
        return "index.html";
    }

	@PostMapping(value = { "/register" })
    public String registerUtente(@Valid @ModelAttribute("utente") Utente utente,
                 BindingResult utenteBindingResult, @Valid
                 @ModelAttribute("credenziali") Credenziali credenziali,
                 BindingResult credenzialiBindingResult,
                 Model model) {

		// se utente e credential hanno entrambi contenuti validi, memorizza utente e the Credenziali nel DB

		this.utenteValidator.validate(utente, utenteBindingResult);
		this.credenzialiValidator.validate(credenziali, credenzialiBindingResult);
		if(!utenteBindingResult.hasErrors() || !credenzialiBindingResult.hasErrors()) {
			String username = credenziali.getUsername();
			utente.setEmail(username);
			if(username.equals(CREATORE)) {
				credenziali.setRuolo(ADMIN_ROLE);
			} else {
				credenziali.setRuolo(Credenziali.DEFAULT_ROLE);
			}
			this.utenteService.save(utente);
			credenziali.setUtente(utente);
            this.credenzialiService.save(credenziali);
            model.addAttribute("utente", utente);
            return "registrazioneSuccesso.html";
        }
		model.addAttribute("utente", utente);
		model.addAttribute("credenziali", credenziali);
        return "formRegistrazione.html";
    }
}