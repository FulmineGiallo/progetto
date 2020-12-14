CREATE TABLE ambito(
idAmbito	INTEGER	PRIMARY KEY,
TipoAmbito	VARCHAR(255),

CONSTRAINT CKTipoAmbito CHECK (TipoAmbito IN ('Economica', 'Medicina', 'Informatica', 'Fisica', 'Matematica', 'Chimica', 'Biologia', 'Musica'))
)