\c currency
begin;
create table currencies (
                            id serial PRIMARY KEY ,
                            code varchar unique ,
                            fullName varchar,
                            sign varchar
);
CREATE TABLE exchangerates(
                              id serial PRIMARY KEY,
                              baseCurrencyId int not null,
                              targetCurrencyId int not null,
                              rate decimal(6,2),
                              FOREIGN KEY (baseCurrencyId) REFERENCES Currencies(id),
                              FOREIGN KEY (targetCurrencyId) REFERENCES Currencies(id)
);
commit;
begin;
INSERT INTO currencies (code, fullname, sign) VALUES('USD', 'Международный доллар', '$');
INSERT INTO currencies (code, fullname, sign) VALUES('RUB', 'Российский рубль', '₽');
INSERT INTO currencies (code, fullname, sign) VALUES('EUR', 'Немецкий евро', '€');
INSERT INTO currencies (code, fullname, sign) VALUES('UAH', 'Украинская гривна', '₴');
INSERT INTO currencies (code, fullname, sign) VALUES('CNY', 'Китайский юань', '¥');
commit;
begin;
INSERT INTO exchangerates (basecurrencyid, targetcurrencyid, rate) VALUES(2, 1, 2.00);
INSERT INTO exchangerates (basecurrencyid, targetcurrencyid, rate) VALUES(2, 3, 2.12);
INSERT INTO exchangerates (basecurrencyid, targetcurrencyid, rate) VALUES(4, 5, 1.00);
INSERT INTO exchangerates (basecurrencyid, targetcurrencyid, rate) VALUES(5, 4, 1.00);
commit;


