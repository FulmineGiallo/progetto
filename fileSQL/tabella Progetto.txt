CREATE TABLE Progetto(
idProgetto	INTEGER(255) PRIMARY KEY,
Titolo 		VARCHAR(255) NOT NULL,
Descrizione VARCHAR(255),
DataInizio	DATE,
DataFine	DATE,
Scadenza 	DATE,
tipoProgetto VARCHAR(255)
CONSTRAINT CKProgetto (CHECK IN ("Ricerca di Base", "Ricerca Industriale", "Ricerca Sperimentale", "Sviluppo Sperimentale", "Ricerca Medica", "Sperimentazione Fisica", "Sviluppo Software", "Ricerca Musicale", "Ricerca Economica", "Analisi Statistica", "Ricerca Ambientale", "Sperimentazione Chimica", "Ricerca Quantistica", "Sviluppo Algoritmi" ))
)

ALTER TABLE progetto
ADD CONSTRAINT CKDataInizio CHECK (DataInizio<Scadenza)

ALTER TABLE progetto
ADD CONSTRAINT CKDataFineInizio CHECK (DataInizio<DataFine)