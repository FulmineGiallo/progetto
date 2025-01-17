
CREATE TABLE Partecipazione(
   presenza varchar(10) check presenza in('assente', 'presente'),
   FOREIGN KEY (idImpiegato) REFERENCES Impiegato(idImpiegato),
   FOREIGN KEY (idRiunione) REFERENCES Riunione(idRiunione)
);


CREATE TABLE Riunione(
   idRiunione int PRIMARY KEY, 
   orarioInizio TIMESTAMP,
   orarioFine   TIMESTAMP,
   data date,
   titolo varchar(30),
   descrizione varchar(100)
);

CREATE TABLE RiunioneFisica
(
    Sede varchar(20),
    Piano varchar(3),
    nomeStanza varchar(30),
	idRiunioneF int,
    FOREIGN KEY (idRiunioneF) REFERENCES Riunione(idRiunione)
);

CREATE TABLE RiunioneFisica
(
    Sede varchar(20),
    Piano varchar(3),
    nomeStanza(30) 
    FOREIGN KEY (idRiunioneF) REFERENCES Riunione(idRiunione) NULL,
);


/* VINCOLI */
ALTER TABLE RiunioneTelematica
ADD CONSTRAINT unicità_RiunioneT UNIQUE (idriunioneT)

ALTER TABLE public.riunione
    ADD COLUMN idriunione integer NOT NULL DEFAULT nextval('idriunioneseq'::regclass);