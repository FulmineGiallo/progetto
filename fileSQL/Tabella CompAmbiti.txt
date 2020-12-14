CREATE TABLE CompAmbiti(
idProgetto	INTEGER,
idAmbito 	INTEGER,

CONSTRAINT Fk1FOREIGN KEY (idProgetto) REFERENCES progetto (idProgetto),
CONSTRAINT Fk2 FOREIGN KEY (idAmbito) REFERENCES Ambiti (idAmbito),

)
