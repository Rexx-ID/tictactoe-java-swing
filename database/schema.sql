
DROP TABLE IF EXISTS players;


CREATE TABLE players (
    id       SERIAL PRIMARY KEY,
    username VARCHAR(50)  NOT NULL UNIQUE,
    password VARCHAR(100) NOT NULL,
    wins     INT DEFAULT 0,
    losses   INT DEFAULT 0,
    draws    INT DEFAULT 0,
    score    INT DEFAULT 0
);


INSERT INTO players (username, password, wins, losses, draws, score)
VALUES ('Akbar', '12345', 0, 0, 0, 0);

INSERT INTO players (username, password, wins, losses, draws, score)
VALUES ('Fayyadh', '12345', 0, 0, 0, 0);

INSERT INTO players (username, password, wins, losses, draws, score)
VALUES ('Ahnaf', '12345', 0, 0, 0, 0);

INSERT INTO players (username, password, wins, losses, draws, score)
VALUES ('Axel', '12345', 0, 0, 0, 0);

INSERT INTO players (username, password, wins, losses, draws, score)
VALUES ('Danis', '12345', 0, 0, 0, 0);


SELECT * FROM players;