--le recensioni sono valide se e solo se recensore e recensito hanno partecipato allo stesso progetto
--le recensioni possono essere effettuate solo da impiegati che hanno avuto ruolo di project manager
 
CREATE VIEW ProgettoRecensoreRecensito (idProgetto, Impiegato, Ruolo) AS
(SELECT R.idProgetto, I.CF, R.Ruolo
 FROM Impiegato I JOIN Registrazione R ON I.CF = R.CF)
 
CREATE FUNCTION CKRecensione()
RETURNS trigger
LANGUAGE 'plpgsql'
AS $$
BEGIN			
	IF EXISTS (SELECT *
			   FROM ProgettoRecensoreRecensito PRR1, ProgettoRecensoreRecensito PRR2
			   WHERE PRR1.Impiegato <> PRR2.Impiegato
			   AND PRR1.idProgetto = PRR2.idProgetto
			   AND PRR1.Impiegato = NEW.Recensore
			   AND PRR2.Impiegato = NEW.Recensito) THEN --recensore e recensito hanno lavorato allo stesso progetto
		IF NOT EXISTS (SELECT *
			   		   FROM ProgettoRecensoreRecensito PRR1, ProgettoRecensoreRecensito PRR2
			   		   WHERE PRR1.Impiegato <> PRR2.Impiegato
			   		   AND PRR1.idProgetto = PRR2.idProgetto
			   		   AND PRR1.Impiegato = NEW.Recensore
			   		   AND PRR2.Impiegato = NEW.Recensito
					   AND PRR1.Ruolo = 'Project Manager') THEN
			RAISE NOTICE 'questo recensore non può scrivere recensioni perché non è un project manager del progetto';
		ELSE
			RETURN NEW;
		END IF;
	ELSE
		RAISE NOTICE 'recensore e recensito non hanno lavorato allo stesso progetto';
	END IF;
END

CREATE TRIGGER TriggerInserimentoRecensione
BEFORE INSERT ON Valutazione
FOR EACH ROW
EXECUTE PROCEDURE CKRecensione();