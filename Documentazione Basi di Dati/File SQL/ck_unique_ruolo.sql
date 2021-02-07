ALTER TABLE registrazione
ADD CONSTRAINT ck_unique_ruolo UNIQUE (ruolo, idprogetto);