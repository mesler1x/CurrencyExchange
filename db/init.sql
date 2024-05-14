CREATE DATABASE currency;
GRANT ALL PRIVILEGES ON DATABASE currency TO postgres;
create table Currencies (
                            id serial PRIMARY KEY ,
                            code varchar unique ,
                            fullName varchar,
                            sign varchar
);
CREATE TABLE ExchangeRates(
                              id serial PRIMARY KEY,
                              baseCurrencyId int not null,
                              targetCurrencyId int not null,
                              rate decimal(6,2),
                              FOREIGN KEY (baseCurrencyId) REFERENCES Currencies(id),
                              FOREIGN KEY (targetCurrencyId) REFERENCES Currencies(id)
);
INSERT INTO currencies (code, fullname, sign) VALUES('USD', 'Международный доллар', '$');
INSERT INTO currencies (code, fullname, sign) VALUES('RUB', 'Российский рубль', '₽');
INSERT INTO currencies (code, fullname, sign) VALUES('EUR', 'Немецкий евро', '€');
INSERT INTO currencies (code, fullname, sign) VALUES('UAH', 'Украинская гривна', '₴');
INSERT INTO currencies (code, fullname, sign) VALUES('CNY', 'Китайский юань', '¥');
INSERT INTO exchangerates (basecurrencyid, targetcurrencyid, rate) VALUES(2, 1, 2.00);
INSERT INTO exchangerates (basecurrencyid, targetcurrencyid, rate) VALUES(2, 3, 2.12);
INSERT INTO exchangerates (basecurrencyid, targetcurrencyid, rate) VALUES(4, 5, 1.00);
INSERT INTO exchangerates (basecurrencyid, targetcurrencyid, rate) VALUES(5, 4, 1.00);

