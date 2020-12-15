--le recensioni possono essere effettuate solo da impiegati che hanno avuto ruolo di project manager
--le recensioni sono valide sse recensore e recensito hanno partecipato allo stesso progetto

CREATE VIEW ProgettoRecensore (idProgetto, Recensore, RuoloRecensore) AS
(SELECT R.idProgetto, V.Recensore, R.Ruolo
 FROM Valutazione V JOIN Impiegato I ON V.Recensore = I.CF JOIN Registrazione R ON I.CF = R.CF)

CREATE VIEW ProgettoRecensito (idProgetto, Recensito) AS
(SELECT R.idProgetto, V.Recensito
 FROM Valutazione V JOIN Impiegato I ON V.Recensito = I.CF JOIN Registrazione R ON I.CF = R.CF)
 
CREATE FUNCTION CKRecensione()
RETURNS trigger
LANGUAGE 'plpgsql'
AS $$
BEGIN			
	IF EXISTS (SELECT *
			   FROM ProgettoRecensore NATURAL JOIN ProgettoRecensito
			   WHERE Recensore = NEW.Recensore
			   AND Recensito = NEW.Recensito) THEN --recensore e recensito hanno lavorato allo stesso progetto
		IF NOT EXISTS (SELECT *
					   FROM ProgettoRecensore NATURAL JOIN ProgettoRecensito
					   WHERE Recensore = NEW.Recensore
					   AND Recensito = NEW.Recensito
					   AND RuoloRecensore = 'Project Manager') THEN
			RAISE NOTICE 'questo recensore non può scrivere recensioni perché non è un project manager del progetto';
		ELSE
			RETURN NEW;
		END IF;
	ELSE
		RAISE NOTICE 'recensore e recensito non hanno lavorato allo stesso progetto';
	END IF;
END
$$;

CREATE TRIGGER TriggerInserimentoRecensione
BEFORE INSERT ON Valutazione
FOR EACH ROW
EXECUTE PROCEDURE CKRecensione();