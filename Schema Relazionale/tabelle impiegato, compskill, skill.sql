CREATE TABLE Impiegato(
	Nome VARCHAR(255) NOT NULL,
	Cognome VARCHAR(255) NOT NULL,
	Salario INTEGER NOT NULL,
	CF VARCHAR(255) PRIMARY KEY,
	DataN DATE NOT NULL,
	ComuneN VARCHAR(255) NOT NULL,
	Indirizzo VARCHAR(255),
	Email VARCHAR(255) NOT NULL,
	Grado VARCHAR(255) NOT NULL,
	
	CONSTRAINT CKNome CHECK (Nome ~* '^[A-Za-z]+$'),
	CONSTRAINT CKCognome CHECK (Cognome ~* '^[A-Za-z]+$'),
	CONSTRAINT CKSalario CHECK (Salario BETWEEN 0 AND 15000),
	CONSTRAINT CKCodiceFiscale CHECK (CF ~* '^[A-Z]{3}[A-Z]{3}[0-9]{2}[A-EHLMPR-T][0-9]{2}[A-Z][0-9]{3}[A-Z]$'),
	CONSTRAINT CKDataN CHECK (EXTRACT(YEAR from DataN) <= EXTRACT(YEAR from current_date) - 18),
	CONSTRAINT CKIndirizzo CHECK (),
	CONSTRAINT CKEmail CHECK (Email ~* '^[A-Za-z0-9._%-]+@[A-Za-z0-9.-]+[.][A-Za-z]+$'),
	CONSTRAINT CKGrado CHECK (Grado IN ("Dirigente", "Vice-Dirigente", "Stagista", "CEO", "Direttore Risorse Umane", "Impiegato", "Ricercatore"))
)

CREATE TABLE Skill(
	idSkill INTEGER PRIMARY KEY,
	Descrizione VARCHAR(1000),
	TipoSkill VARCHER(10) NOT NULL,
	
	CONSTRAINT CKTipoSkill (TipoSkill IN ("Soft-Skill", "Hard-Skill"))
)

CREATE TABLE CompSkill(
	CF VARCHAR(255) NOT NULL,
	idSkill INTEGER NOT NULL,
	
	CONSTRAINT FKCodiceFiscale FOREIGN KEY CF REFERENCES Impiegato(CF),
	CONSTRAINT FKSkill FOREIGN KEY idSkill REFERENCES Skill(idSkill),
	CONSTRAINT UniqueCFSkill UNIQUE (CF, idSkill)
)

-- meglio inserire le regex lato client (un controllo sugli inserimenti in Java) o lato server (tramite vincoli intrarelazionali)?
-- vogliamo creare una classe a parte per l'indirizzo oppure una regex oppure lasciamo con nessun vincolo?