package it.uniroma3.siw.service;

import it.uniroma3.siw.model.Consulente;
import it.uniroma3.siw.model.Prenotazione;
import it.uniroma3.siw.model.Utente;
import it.uniroma3.siw.repository.PrenotazioneRepository;
import jakarta.persistence.EntityManager;
import jakarta.persistence.Query;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.*;

@Service
public class PrenotazioneService {
    private final static int INTERVALLO = 7;
    private final static String ORA_INIZIO = "9:00";
    private final static String ORA_FINE = "19:00";

    @Autowired ConsulenteService consulenteService;
    @Autowired PrenotazioneRepository prenotazioneRepository;
    @Autowired EntityManager entityManager;

    class PrenotazioneComparatore implements Comparator<Prenotazione>{
        @Override
        public int compare(Prenotazione p1, Prenotazione p2) {
            String d1 = p1.getDataPrenotazione().toString();
            String d2 = p2.getDataPrenotazione().toString();
            if(d1.equals(d2)) {
                String t1 = p1.getOra().toString();
                String t2 = p2.getOra().toString();
                if(t1.equals(t2)) {
                    String c1 = p1.getConsulente().getCognome();
                    String c2 = p1.getConsulente().getCognome();
                    if(c1.equals(c2)) {
                        String n1 = p1.getConsulente().getNome();
                        String n2 = p1.getConsulente().getNome();
                        return n1.compareTo(n2); // Per nome
                    }
                    return c1.compareTo(c2); // Per cognome
                }
                return t1.compareTo(t2); // Per ora
            }
            return d1.compareTo(d2); // Per data
        }
    }

    // 2024-01-02
    // 0123456789
    private String convertiDataItaliana(String data) {
        String anno = data.substring(0, 4);
        String mese = data.substring(5, 7);
        String giorno = data.substring(8, 10);
        return giorno + "/" + mese + "/" + anno;
    }

    // 02-01-2024
    // 0123456789
    private String convertiDataAmericana(String data) {
        String giorno = data.substring(0, 2);
        String mese = data.substring(3, 5);
        String anno = data.substring(6, 10);
        return anno + "-" + mese + "-" + giorno;
    }
    // 14:35:56.279723
    // 012345...
    private String convertiOra(String ora) {
        String ore = String.valueOf(Integer.valueOf(ora.substring(0, 2)) + 1);
        return ore + ":00";
    }

    // 9:00
    // 1234
    private boolean confrontaOre(String ora1, String ora2, String operatore) {
        int ind;
        int inx;
        if (ora1.length() == 4) {
            ind = 1;
        } else {
            ind = 2;
        }
        if (ora2.length() == 4) {
            inx = 1;
        } else {
            inx = 2;
        }
        int num1 = Integer.parseInt(ora1.substring(0, ind));
        int num2 = Integer.parseInt(ora2.substring(0, inx));
        boolean esito = false;
        if (operatore.equals(">")) {
            esito = num1 > num2;
        }
        if (operatore.equals(">=")) {
            esito = num1 >= num2;
        }
        if (operatore.equals("<")) {
            esito = num1 < num2;
        }
        if (operatore.equals("<=")) {
            esito = num1 <= num2;
        }
        return esito;
    }

    private String aggiungiGiorni(String data, int intervallo) {
        StringBuilder nuova = new StringBuilder();
        nuova.append("select to_char(to_date('");
        nuova.append(data);
        nuova.append("', 'dd/mm/yyyy') + interval '");
        nuova.append(intervallo);
        nuova.append(" day', 'dd/mm/yyyy')");
        Query query = this.entityManager.createNativeQuery(nuova.toString());
        List<Object> ris = (List<Object>) query.getResultList();
        return (String) ris.get(0);
    }

    private String aggiungiOra(String ora, int intervallo) {
        int ind;
        if (ora.length() == 4) {
            ind = 1;
        } else {
            ind = 2;
        }
        int num = Integer.parseInt(ora.substring(0, ind)) + intervallo;
        return String.valueOf(num) + ":00";
    }

    // 9:00
    // 1234
    private LocalTime riportaInOra(String ora) {
        StringBuilder out = new StringBuilder();
        if(ora.length() == 4) {
            out.append("0");
        }
        out.append(ora);
        out.append(":00");
        return LocalTime.parse(out.toString());
    }

    private Map<LocalDate, Set<Prenotazione>> mettiInMappa (Set<Prenotazione> tutte) {
        Map<LocalDate, Set<Prenotazione>> out = new HashMap<>();

        for (Prenotazione prenotazione : tutte) {
            LocalDate data = prenotazione.getDataPrenotazione();

            if (out.containsKey(data)) {
                out.get(data).add(prenotazione);
            } else {
                Set<Prenotazione> nuova = new TreeSet<>(new PrenotazioneComparatore());
                nuova.add(prenotazione);
                out.put(data, nuova);
            }
        }
        return out;
    }

    private List<Prenotazione> mettiInLista(Set<Prenotazione> prenotazioni) {
        if (prenotazioni == null) {
            return null;
        }
        List<Prenotazione> out = new ArrayList<>();

        for(Prenotazione prenotazione : prenotazioni) {
            out.add(prenotazione);
        }
        return out;
    }


    public void salvaLista(List<Prenotazione> prenotazioni) {
        StringBuilder creaTabella = new StringBuilder();
        creaTabella.append("drop table if exists temp_prenotazioni cascade; ");
        creaTabella.append("create temp table temp_prenotazioni (");
        creaTabella.append("prog int8, ");
        creaTabella.append("data_prenotazione date, ");
        creaTabella.append("ora time, ");
        creaTabella.append("consulente_id int8)");

        Query query = this.entityManager.createNativeQuery(creaTabella.toString());
        int ind = query.executeUpdate();

        for (Prenotazione prenotazione : prenotazioni) {
            StringBuilder insert = new StringBuilder();
            insert.append("insert into temp_prenotazioni(prog, data_prenotazione, ora, consulente_id) values(");
            insert.append(prenotazione.getProg());
            insert.append(", '");
            insert.append(prenotazione.getDataPrenotazione());
            insert.append("', '");
            insert.append(prenotazione.getOra());
            insert.append("', ");
            insert.append(prenotazione.getConsulente().getId());
            insert.append(");");
            Query query1 = this.entityManager.createNativeQuery(insert.toString());
            int inx = query1.executeUpdate();
        }
    }



    public Prenotazione ottieniPrenotazione(Long prog) {
        String select = "select prog, to_char(data_prenotazione, 'dd/mm/yyyy'), to_char(ora, 'HH24:00'), consulente_id from temp_prenotazioni "+
                        "where prog = " + prog +" limit 1";

        Query query = this.entityManager.createNativeQuery(select);
        List<Object[]> ris = query.getResultList();
        Object[] temp = ris.get(0);


        Prenotazione prenotazione = new Prenotazione();
        prenotazione.setProg((Long) temp[0]);
        prenotazione.setDataPrenotazione(LocalDate.parse(convertiDataAmericana((String) temp[1])));
        prenotazione.setOra(riportaInOra((String) temp[2]));
        prenotazione.setConsulente(this.consulenteService.findById((Long) temp[3]));
        return prenotazione;
    }

    // 01-13-2024 10:00
    // 0123456789012345
    public List<Prenotazione> trovaPrenotazioni(Consulente consulente) {
        List<Prenotazione> prenotazioni = new ArrayList<>(); // risultato finale

        /* PRENDI TUTTE LE PRENOTAZIONI DI QUEL CONSULENTE */
        List<Prenotazione> risParz = this.findByConsulente(consulente);
        List<String> ris = new ArrayList<>();

        for (Prenotazione prenotazione : risParz) {
            String data = this.convertiDataItaliana(prenotazione.getDataPrenotazione().toString());
            String ora = prenotazione.getOra().toString();
            if (ora.length() == 4) {
                ora = "0" + ora;
            }
            ris.add(data + " " + ora);
        }

        String dataOggi = this.convertiDataItaliana(LocalDate.now().toString());
        String oraOggi = this.convertiOra(LocalTime.now().toString());

        /* IMPOSTA LA DATA DI INIZIO CORRETTAMENTE E IN FUNZIONE DI QUEST'ULTIMA SI TROVA LA DATA DI FINE */
        if(this.confrontaOre(oraOggi, ORA_FINE, ">")) {
            oraOggi = ORA_INIZIO;
        } else if (this.confrontaOre(oraOggi, ORA_INIZIO, "<")) {
            dataOggi = this.aggiungiGiorni(dataOggi, 1);
            oraOggi = ORA_INIZIO;
        }
        String dataFine = this.aggiungiGiorni(dataOggi, INTERVALLO);

        /* SELEZIONE DI TUTTE LE PRENOTAZIONI DISPONIBILI */
        int ind = 0;
        long prog = 0;
        while(!dataOggi.equals(dataFine)) {
            if (oraOggi.length() == 4) {
                oraOggi = "0" + oraOggi; // gestisci l'ora ad una cifra
            }
            String slot = dataOggi + " " + oraOggi;
            if(ind < ris.size() && slot.equals(ris.get(ind))) {
                ind++; // prenotazione occupata
            } else {
                // prenotazione disponibile
                prog++;
                Prenotazione nuova = new Prenotazione();
                nuova.setProg(prog);
                nuova.setConsulente(consulente);
                nuova.setDataPrenotazione(LocalDate.parse(this.convertiDataAmericana(dataOggi)));
                nuova.setOra(this.riportaInOra(oraOggi));
                prenotazioni.add(nuova);
            }
            // passa all'ora successiva
            oraOggi = this.aggiungiOra(oraOggi, 1);
            if (this.confrontaOre(oraOggi, "19:00", ">")) {
                // passa alla data successiva e imposta l'ora alle 9
                dataOggi = this.aggiungiGiorni(dataOggi, 1);
                oraOggi = ORA_INIZIO;
            }
        }
        this.salvaLista(prenotazioni);
        return prenotazioni;
    }

    public Map<LocalDate, Set<Prenotazione>> findAll() {
        List<Prenotazione> prenotazioni = this.prenotazioneRepository.findAll();
        Set<Prenotazione> ordinate = new TreeSet<>(new PrenotazioneComparatore());
        ordinate.addAll(prenotazioni);
        return this.mettiInMappa(ordinate);
    }

    public List<Prenotazione> findByData(LocalDate data) {
        Map<LocalDate, Set<Prenotazione>> out = this.findAll();
        return this.mettiInLista(out.get(data));
    }

    public List<Prenotazione> findByConsulente(Consulente consulente) {
        Map<LocalDate, Set<Prenotazione>> tutte = this.findAll();
        List<Prenotazione> out = new ArrayList<>();

        for (LocalDate data : tutte.keySet()) {
            Set<Prenotazione> prenotazioni = tutte.get(data);
            for(Prenotazione prenotazione : prenotazioni) {
                if(prenotazione.getConsulente().getCodiceFiscale().equals(consulente.getCodiceFiscale())) {
                    out.add(prenotazione);
                }
            }
        }
        return out;
    }

    public List<Prenotazione> findByPersona(Utente persona) {
        Map<LocalDate, Set<Prenotazione>> tutte = this.findAll();
        List<Prenotazione> out = new ArrayList<>();

        for (LocalDate data : tutte.keySet()) {
            Set<Prenotazione> prenotazioni = tutte.get(data);
            for(Prenotazione prenotazione : prenotazioni) {
                if(prenotazione.getPersona().getEmail().equals(persona.getEmail())) {
                    out.add(prenotazione);
                }
            }
        }
        return out;
    }

    public Optional<Prenotazione> findById(Long id) {
        return this.prenotazioneRepository.findById(id);
    }

    @Transactional
    public Prenotazione save(Prenotazione prenotazione) {
        return this.prenotazioneRepository.save(prenotazione);
    }

    @Transactional
    public void delete(Prenotazione prenotazione) {
        this.prenotazioneRepository.delete(prenotazione);
    }

    @Transactional
    public void refresh(Prenotazione prenotazione) {
        this.entityManager.refresh(prenotazione);
    }
}