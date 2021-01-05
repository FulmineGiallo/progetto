CREATE VIEW Account (CFAccount, Email, Password) AS
(SELECT CF, Email, Password
 FROM Impiegato);

SELECT COUNT(*)
FROM Account A
WHERE Email LIKE '?'
AND Password LIKE '?';