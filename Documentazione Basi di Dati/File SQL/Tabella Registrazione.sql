CREATE TABLE Registrazione(
Ruolo	VARCHAR(255),
CF		VARCHAR(16),
idAmbito 	INTEGER,

CONSTRAINT Fk1FOREIGN KEY (CF) REFERENCES Impiegato (CF) ON UPDATE CASCADE, ON DELETE CASCADE,
CONSTRAINT Fk2 FOREIGN KEY (idAmbito) REFERENCES Ambiti (idAmbito) ON UPDATE CASCADE, ON DELETE CASCADE,
CONSTRAINT CKRuolo CHECK (Ruolo IN ('Project Manager', 'Partecipante', 'Programmatore', 'Grafico', 'Analista', 'Tecnico', 'Consulente'))
CONSTRAINT uq1 UNIQUE(cf, idprogetto)
)	