package it.uniroma3.siw.controller;

import it.uniroma3.siw.model.Credenziali;
import it.uniroma3.siw.model.Prenotazione;
import it.uniroma3.siw.model.Utente;
import it.uniroma3.siw.service.CredenzialiService;
import it.uniroma3.siw.service.UtenteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@Controller
public class UtenteController {
    @Autowired UtenteService utenteService;
    @Autowired CredenzialiService credenzialiService;


    @GetMapping("/admin/risUtenti")
    public String getUtenti(Model model) {
        List<Credenziali> credenziali = this.credenzialiService.findAll();
        model.addAttribute("credenziali", credenziali);
        return "admin/risUtenti.html";
    }

    @GetMapping("/admin/risUtenti/{id}")
    public String mostraUtente(@PathVariable("id") Long id, Model model){
        Utente utente = this.utenteService.getUtente(id);
        model.addAttribute("utente", utente);
        return "admin/utente.html";
    }
}
