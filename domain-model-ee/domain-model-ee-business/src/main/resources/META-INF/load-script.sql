INSERT INTO Discount (id, DTYPE, description, percentage, threshold) VALUES (1, 'NoDiscount', 'Sem desconto', 0, 0);
INSERT INTO Discount (id, DTYPE, description, percentage, threshold) VALUES (2, 'ThresholdPercentageDiscount', 'Percentagem do Total (acima de limiar)', 0.1, 50);
INSERT INTO Discount (id, DTYPE, description, percentage, threshold) VALUES (3, 'EligibleProductsDiscount', 'Percentagem do Total Elegivel', 0.1, 0);
INSERT INTO Unit (id, description, abbreviation) VALUES (1, 'Quilogramas', 'Kg');
INSERT INTO Unit (id, description, abbreviation) VALUES (2, 'Unidades', 'un');
INSERT INTO Product (id, prodCod, description, faceValue, qty, discountEligibility, unit_id) VALUES (1, 123, 'Prod 1', 100, 500, 0, 1);
INSERT INTO Product (id, prodCod, description, faceValue, qty, discountEligibility, unit_id) VALUES (2, 124, 'Prod 2', 35, 1000, 1, 2);