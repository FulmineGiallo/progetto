CREATE FUNCTION public.ckregistrazione()
    RETURNS trigger
    LANGUAGE 'plpgsql'
    COST 100
    VOLATILE NOT LEAKPROOF
AS $BODY$
BEGIN			
		IF NOT EXISTS (SELECT *
			   		   FROM Registrazione R
			   		   WHERE R.idProgetto = NEW.idProgetto
			   		   AND R.ruolo = 'Project Manager') THEN
			RAISE NOTICE 'questo utente non puo essere registrato a questo progetto perche 	questo progetto non ha ancora un project manager';
			RETURN NULL;
		ELSE
			RETURN NEW;
		END IF;
END
$BODY$;

ALTER FUNCTION public.ckregistrazione()
    OWNER TO postgres;
