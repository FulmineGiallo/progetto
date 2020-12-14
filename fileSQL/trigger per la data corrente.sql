CREATE TRIGGER DataValutazione
BEFORE INSERT ON Valutazione
FOR EACH ROW
BEGIN
	NEW.DataV = current_date();
END