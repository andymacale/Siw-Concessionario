insert into alimentazione(id, carburante) values(1, 'Benzina');
insert into alimentazione(id, carburante) values(2, 'Gasolio');
insert into alimentazione(id, carburante) values(3, 'GPL');
insert into alimentazione(id, carburante) values(4, 'Ibrida');
insert into alimentazione(id, carburante) values(5, 'Elettrica');

insert into modello(id, fabbrica, nome, posti, cilindrata, cavalli) values(nextval('modello_seq'), 'Fiat', '500', 4, 900, 70);
insert into veicolo(id, telaio, anno, prezzo, colore, cambio, venduta, modello_id, alimentazione_id) values(nextval('veicolo_seq'), 'bsbs', 2019, 10000, 'Nero', 'Manuale', false, (select id from modello where fabbrica = 'Fiat' and nome = '500'), 1);
insert into veicolo(id, telaio, anno, prezzo, colore, cambio, venduta, modello_id, alimentazione_id) values(nextval('veicolo_seq'), 'dhhd', 2019, 10000, 'Grigio', 'Manuale', true, (select id from modello where fabbrica = 'Fiat' and nome = '500'), 1);


insert into consulente(id, nome, cognome, data_di_nascita, sesso, codice_fiscale) values(nextval('consulente_seq'), 'Mario', 'Rossi', '1990-12-23', 'M', 'sxnks');
insert into consulente(id, nome, cognome, data_di_nascita, sesso, codice_fiscale) values(nextval('consulente_seq'), 'Luigi', 'Verdi', '1993-01-02', 'M', 'hfhdf');

--insert into utente(id, nome, cognome, data_nascita, email) values(nextval('utente_seq'), 'Andrea', 'Macale', '09/09/2001', 'and.macale@stud.uniroma3.it')

--insert into credenziali(id, username, password, ruolo, utente_id) values(nextval('credenziali_seq'), 'and.macale@stud.uniroma3.it', '5jLv50xg', 'ADMIN', (select id from utente where email = 'and.macale@stud.uniroma3.it'))

--insert into prenotazione(id, prog, data_prenotazione, ora, consulente_id, persona_id) values(nextval('prenotazione_seq'), 0, (now() + interval '1 day'), '9:00', (select id from consulente where codice_fiscale = 'sxnks'), (select id from utente where email='and.macale@stud.uniroma3.it'));
--insert into prenotazione(id, prog, data_prenotazione, ora, consulente_id, persona_id) values(nextval('prenotazione_seq'), 0, (now() + interval '1 day'), '13:00', (select id from consulente where codice_fiscale = 'sxnks'), (select id from utente where email='and.macale@stud.uniroma3.it'));
--insert into prenotazione(id, prog, data_prenotazione, ora, consulente_id, persona_id) values(nextval('prenotazione_seq'), 0, (now() + interval '1 day'), '15:00', (select id from consulente where codice_fiscale = 'hfhdf'), (select id from utente where email='and.macale@stud.uniroma3.it'));
