package it.uniroma3.siw.controller;

import it.uniroma3.siw.model.Consulente;
import it.uniroma3.siw.model.Prenotazione;
import it.uniroma3.siw.service.ConsulenteService;
import it.uniroma3.siw.service.PrenotazioneService;
import it.uniroma3.siw.validator.ConsulenteValidator;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@Controller
public class ConsulenteController {

    @Autowired ConsulenteService consulenteService;
    @Autowired ConsulenteValidator consulenteValidator;
    @Autowired PrenotazioneService prenotazioneService;

    @GetMapping("/admin/consulenteAggiunto")
    public String consulenteAggiunto() {
        return "admin/consulenteAggiunto.html";
    }

    @GetMapping("/admin/formNewConsulente")
    public String formNewConsulente(Model model){
        model.addAttribute("consulente", new Consulente());
        return "admin/formNewConsulente";
    }

    @PostMapping("/admin/consulenti")
    public String newConsulente(@Valid @ModelAttribute("consulente") Consulente consulente, BindingResult bindingResult, Model model) {
        consulente.setCodiceFiscale(consulente.getCodiceFiscale().toUpperCase());
        this.consulenteValidator.validate(consulente, bindingResult);
        if(bindingResult.hasErrors()) {
            model.addAttribute("consulente", consulente);
            return "admin/formNewConsulente";
        }
        this.consulenteService.save(consulente);
        model.addAttribute("consulente", consulente);
        return "admin/consulenteAggiunto.html";
    }

    @GetMapping("/admin/formEliminaConsulente")
    public String formEliminaConsulente(Model model) {
        model.addAttribute("consulenti", this.consulenteService.findAll());
        return "admin/formEliminaConsulente.html";
    }

    @PostMapping("/admin/eliminaConsulente")
    public String eliminaConsulente(@RequestParam("codiceFiscale") String codiceFiscale, Model model) {
        if (codiceFiscale.isEmpty()) {
            return "admin/consulenteNonEliminato.html";
        }
        Consulente consulente = this.consulenteService.findByCodiceFiscale(codiceFiscale);
        List<Prenotazione> daEliminare = this.prenotazioneService.findByConsulente(consulente);
        for(Prenotazione prenotazione : daEliminare) {
            this.prenotazioneService.delete(prenotazione);
            model.addAttribute("prenotazione", prenotazione);
        }
        this.consulenteService.delete(consulente);
        model.addAttribute("consulente", consulente);
        return "admin/consulenteEliminato.html";
    }
}
