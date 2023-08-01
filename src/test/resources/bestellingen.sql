insert into bestellingen (werknemerId, omschrijving, bedrag, moment)
values ((select id from werknemers where voornaam = 'test1'), 'test1', 2.00, now());