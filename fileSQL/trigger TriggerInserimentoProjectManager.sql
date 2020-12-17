CREATE FUNCTION CKRegistrazione()
RETURNS trigger

AS '
BEGIN			   
		
		IF NEW.ruolo='Project Manager' THEN
		
			IF EXISTS (SELECT *
				   		   FROM Registrazione R
				   		   WHERE R.idProgetto = NEW.idProgetto
				   		   AND R.ruolo = 'Project Manager') THEN
				RAISE NOTICE 'questo utente non puo essere registrato a questo progetto perche 	questo progetto ha già un project manager';
				RETURN NULL;
			ELSE
				RETURN NEW;
			END IF;
		ELSE 
			IF NOT EXISTS (SELECT *
				   		   FROM Registrazione R
				   		   WHERE R.idProgetto = NEW.idProgetto
				   		   AND R.ruolo = 'Project Manager') THEN
				RAISE NOTICE 'questo utente non puo essere registrato a questo progetto perche 	questo progetto non ha ancora un project manager';
				RETURN NULL;
			ELSE
				RETURN NEW;
		END IF
	END IF	
	END
'LANGUAGE 'plpgsql';

CREATE TRIGGER TriggerInserimentoProjectManager
BEFORE INSERT ON Registrazione
FOR EACH ROW
EXECUTE PROCEDURE CKRegistrazione();
