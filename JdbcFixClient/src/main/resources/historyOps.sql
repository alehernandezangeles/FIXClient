-- FixClientDB
SELECT 'NOS', count(*) FROM FixClientDB.NOS
union
SELECT 'OCR', count(*) FROM FixClientDB.OCR
union
SELECT 'ACK1', count(*) FROM FixClientDB.ACK1
union
SELECT 'ACK2', count(*) FROM FixClientDB.ACK2
union
SELECT 'ER', count(*) FROM FixClientDB.ER
union
SELECT 'OtrosMsjFix', count(*) FROM FixClientDB.OtrosMsjFix
union
SELECT 'Errores', count(*) FROM FixClientDB.Errores;

-- FixClientHistDB
SELECT 'NOS', count(*) FROM FixClientHistDB.NOS
union
SELECT 'OCR', count(*) FROM FixClientHistDB.OCR
union
SELECT 'ACK1', count(*) FROM FixClientHistDB.ACK1
union
SELECT 'ACK2', count(*) FROM FixClientHistDB.ACK2
union
SELECT 'ER', count(*) FROM FixClientHistDB.ER
union
SELECT 'OtrosMsjFix', count(*) FROM FixClientHistDB.OtrosMsjFix
union
SELECT 'Errores', count(*) FROM FixClientHistDB.Errores;

-- Make op tables candidates for historical removal
UPDATE FixClientDB.NOS set TransactTime = DATE_SUB(CURDATE(), INTERVAL 1 DAY);
UPDATE FixClientDB.OCR set TransactTime = DATE_SUB(CURDATE(), INTERVAL 1 DAY);
UPDATE FixClientDB.ACK1 set FechaInsercion = DATE_SUB(CURDATE(), INTERVAL 1 DAY);
UPDATE FixClientDB.ACK2 set FechaInsercion = DATE_SUB(CURDATE(), INTERVAL 1 DAY);
UPDATE FixClientDB.ER set TransactTime = DATE_SUB(CURDATE(), INTERVAL 1 DAY);
UPDATE FixClientDB.OtrosMsjFix set FechaInsercion = DATE_SUB(CURDATE(), INTERVAL 1 DAY);
UPDATE FixClientDB.Errores set FechaInsercion = DATE_SUB(CURDATE(), INTERVAL 1 DAY);

-- Make op tables NOT candidates for historical removal
UPDATE FixClientDB.NOS set TransactTime = CURDATE();
UPDATE FixClientDB.OCR set TransactTime = CURDATE();
UPDATE FixClientDB.ACK1 set FechaInsercion = CURDATE();
UPDATE FixClientDB.ACK2 set FechaInsercion = CURDATE();
UPDATE FixClientDB.ER set TransactTime = CURDATE();
UPDATE FixClientDB.OtrosMsjFix set FechaInsercion = CURDATE();
UPDATE FixClientDB.Errores set FechaInsercion = CURDATE();

-- Delete Hist
DELETE FROM FixClientHistDB.NOS;
DELETE FROM FixClientHistDB.OCR;
DELETE FROM FixClientHistDB.ACK1;
DELETE FROM FixClientHistDB.ACK2;
DELETE FROM FixClientHistDB.ER;
DELETE FROM FixClientHistDB.OtrosMsjFix;
DELETE FROM FixClientHistDB.Errores;

-- Copy from Hist back to op db
INSERT INTO FixClientDB.NOS (
  SELECT * FROM FixClientHistDB.NOS
);
INSERT INTO FixClientDB.OCR (
  SELECT * FROM FixClientHistDB.OCR
);
INSERT INTO FixClientDB.ACK1 (
  SELECT * FROM FixClientHistDB.ACK1
);
INSERT INTO FixClientDB.ACK2 (
  SELECT * FROM FixClientHistDB.ACK2
);
INSERT INTO FixClientDB.ER (
  SELECT * FROM FixClientHistDB.ER
);
INSERT INTO FixClientDB.OtrosMsjFix (
  SELECT * FROM FixClientHistDB.OtrosMsjFix
);
INSERT INTO FixClientDB.Errores (
  SELECT * FROM FixClientHistDB.Errores
);
