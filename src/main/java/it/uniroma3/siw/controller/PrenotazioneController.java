package it.uniroma3.siw.controller;

import it.uniroma3.siw.model.Consulente;
import it.uniroma3.siw.model.Prenotazione;
import it.uniroma3.siw.model.Utente;
import it.uniroma3.siw.model.Veicolo;
import it.uniroma3.siw.service.ConsulenteService;
import it.uniroma3.siw.service.PrenotazioneService;
import it.uniroma3.siw.service.UtenteService;
import it.uniroma3.siw.service.VeicoloService;
import jakarta.persistence.EntityManager;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.*;
import java.util.function.LongPredicate;

@Controller
public class PrenotazioneController {

    @Autowired VeicoloService veicoloService;
    @Autowired ConsulenteService consulenteService;
    @Autowired UtenteService utenteService;
    @Autowired PrenotazioneService prenotazioneService;

    @GetMapping("/vediPrenotazioni")
    public String vediPrenotazioni() {
        return "vediPrenotazioni.html";
    }

    @GetMapping("/formScegliConsulente")
    public String formScegliConsulente(Model model) {
        model.addAttribute("consulenti", this.consulenteService.findAll());
        return "formScegliConsulente.html";
    }

    @Transactional
    @PostMapping("/consulente")
    public String scegliConsulente(@RequestParam String codiceFiscale,Model model) {
        if(codiceFiscale.isEmpty() || !this.consulenteService.existsByCodiceFiscale(codiceFiscale)) {
            model.addAttribute("messaggioErrore", "consulente è un campo obbligatorio!");
            return "prenotazioneFallita.html";
        }
        Consulente consulente = this.consulenteService.findByCodiceFiscale(codiceFiscale);
        model.addAttribute("consulente", consulente);
        model.addAttribute("veicoli", this.veicoloService.findVeicoliDisponibili());
        model.addAttribute("prenotazioni", this.prenotazioneService.trovaPrenotazioni(consulente));
        return "formNewPrenotazione.html";
    }

    @PostMapping("/prenotazione")
    public String newPrenotazione(@RequestParam String prog, @RequestParam("telaio") String telaio, UserDetails userDetails, Model model) {
        if (prog.isEmpty()) {
            model.addAttribute("messaggioErrore", "data e ora è un campo obbligatorio!");
            return "prenotazioneFallita.html";
        }
        Prenotazione prenotazione = this.prenotazioneService.ottieniPrenotazione(Long.valueOf(prog));
        Utente utente = this.utenteService.findByEmail(userDetails.getUsername());
        prenotazione.setPersona(utente);
        if(telaio.isEmpty()) {
            prenotazione.setVeicolo(null);
        } else {
            Veicolo veicolo = this.veicoloService.findByTelaio(telaio);
            if (veicolo.getVenduta()) {
                model.addAttribute("messaggioErrore", "il veicolo selezionato è già stato venduto!");
                return "prenotazioneFallita.html";
            }
            prenotazione.setVeicolo(veicolo);
        }
        prenotazione.setProg(Long.valueOf(0));
        this.prenotazioneService.save(prenotazione);
        model.addAttribute("prenotazione", prenotazione);
        this.prenotazioneService.refresh(prenotazione);
        return "prenotazioneSuccesso.html";
    }

    @GetMapping("/prenotazioneSuccesso")
    public String getPrenotazioneSuccesso(@RequestParam("prenotazione") Prenotazione prenotazione, Model model){
        model.addAttribute("consulente", prenotazione.getConsulente());
        return "prenotazioneSuccesso.html";
    }

    @GetMapping("/admin/formCercaPrenotazioni")
    public String formCercaPrenotazioni(Model model) {
        return "admin/formCercaPrenotazioni.html";
    }

    @PostMapping("/admin/cercaPrenotazioni")
    public String cercaPrenotazioni(@ModelAttribute("data") String data, Model model) {
        List<Prenotazione> prenotazioni = new ArrayList<>();
        if (data.isEmpty()) {
            Map<LocalDate, Set<Prenotazione>> ris = this.prenotazioneService.findAll();
            if (!ris.isEmpty()) {
                for (LocalDate chiave : ris.keySet()) {
                    prenotazioni.addAll(ris.get(chiave));
                }
            }
        } else {
            prenotazioni = this.prenotazioneService.findByData(LocalDate.parse(data));
        }

        model.addAttribute("prenotazioni", prenotazioni);
        model.addAttribute("num", this.prenotazioneService.contaRisultati(prenotazioni));
        return "admin/risPrenotazioni.html";
    }

    @GetMapping("/admin/risPrenotazioni/{id}")
    public String getPrenotazione(@PathVariable("id") Long id, Model model){
        Prenotazione prenotazione = this.prenotazioneService.findById(id).get();
        model.addAttribute("prenotazione", prenotazione);
        return "admin/prenotazione.html";
    }

    @GetMapping("/risPrenotazioniEffettuate")
    public String cercaPrenotazioniEffettuate(UserDetails userDetails, Model model) {
        Utente utente = this.utenteService.findByEmail(userDetails.getUsername());
        List<Prenotazione> prenotazioni = this.prenotazioneService.findByPersona(utente);
        model.addAttribute("prenotazioni", prenotazioni);
        model.addAttribute("num", this.prenotazioneService.contaRisultati(prenotazioni));
        return "risPrenotazioniEffettuate.html";
    }

    @GetMapping("/risPrenotazioniEffettuate/{id}")
    public String getPrenotazioneEffettuata(@PathVariable("id") Long id, Model model){
        Prenotazione prenotazione = this.prenotazioneService.findById(id).get();
        model.addAttribute("prenotazione", prenotazione);
        return "prenotazioneEffettuata.html";
    }
}
