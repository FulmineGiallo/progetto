CREATE VIEW Valutazioni (idValutazione, stelle, recensione, datav, CFRecensore, CFrecensito) AS(
SELECT idValutazione, stelle, recensione, datav, CFrecensore, CFrecensito
FROM valutazioneriunioneimpiegato

UNION

SELECT idValutazione, stelle, recensione, datav, CFrecensore, CFrecensito
FROM valutazioneprogettoimpiegato)