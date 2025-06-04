CREATE TABLE users (
       id SERIAL PRIMARY KEY,
       name VARCHAR(100) NOT NULL,
       email VARCHAR(255) UNIQUE NOT NULL,
       password VARCHAR(255) NOT NULL,
       role VARCHAR(50) NOT NULL,
       balance NUMERIC NOT NULL
);

CREATE TABLE transactions (
     id SERIAL PRIMARY KEY,
     user_id SERIAL REFERENCES users,
     symbol TEXT,
     type TEXT CHECK (type IN ('BUY', 'SELL')),
     price NUMERIC,
     quantity NUMERIC,
     timestamp TIMESTAMP
 );

CREATE TABLE assets (
    user_id SERIAL,
    symbol TEXT,
    quantity NUMERIC,
    avg_price NUMERIC,
    PRIMARY KEY (user_id, symbol)
);