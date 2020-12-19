ALTER TABLE impiegato
ADD genere varchar(2) CHECK (genere = 'M' OR genere = 'F');