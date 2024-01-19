package it.uniroma3.siw.controller;

import it.uniroma3.siw.model.Credenziali;
import it.uniroma3.siw.service.CredenzialiService;
import it.uniroma3.siw.service.UtenteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;

@Controller
public class MainController {

    @Autowired CredenzialiService credenzialiService;

    @GetMapping("/homepage")
    public String homepage(Model model) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (!(authentication instanceof AnonymousAuthenticationToken)) {
            UserDetails userDetails = (UserDetails) authentication.getPrincipal();
            Credenziali credenziali = this.credenzialiService.getCredenziali(userDetails.getUsername());
            model.addAttribute("credenziali", credenziali);
        }
        return "homepage.html";
    }

    @GetMapping("/admin/opzioni")
    public String opzioni() {
        return "admin/opzioni.html";
    }

    @GetMapping("/admin/aggiungi")
    public String aggiungi() {
        return "admin/aggiungi.html";
    }

    @GetMapping("/admin/aggiorna")
    public String aggiorna() {
        return "admin/aggiorna.html";
    }

    @GetMapping("/prenota")
    public String prenota() {
        return "prenota.html";
    }

}
