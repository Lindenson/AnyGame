CREATE TABLE IF NOT EXISTS gamer (
    id SERIAL PRIMARY KEY,
    name TEXT NOT NULL,
    token TEXT NOT NULL,
    created timestamp DEFAULT now(),
    UNIQUE(name)
);

CREATE TABLE IF NOT EXISTS saga (
    id SERIAL PRIMARY KEY,
    player1 TEXT NOT NULL,
    request  timestamp DEFAULT now(),
    player2 TEXT NOT NULL,
    created  timestamp,
    puzzle1 integer DEFAULT 0,
    puzzle2 integer DEFAULT 0,
    started  timestamp,
    nextstep integer DEFAULT 0,
    move1  timestamp,
    move2  timestamp,
    winner integer DEFAULT 0,
    canceled boolean DEFAULT false,

    CONSTRAINT FK_game1 FOREIGN KEY(player1)
    REFERENCES gamer(name) ON DELETE CASCADE,

    CONSTRAINT FK_game2 FOREIGN KEY(player2)
    REFERENCES gamer(name) ON DELETE CASCADE
);

CREATE TABLE IF NOT EXISTS game (
    id SERIAL PRIMARY KEY,
    player1 TEXT NOT NULL,
    move1 integer DEFAULT 0,
    bulls1 integer DEFAULT 0,
    caws1 integer  DEFAULT 0,
    player2 TEXT NOT NULL,
    move2 integer DEFAULT 0,
    bulls2 integer DEFAULT 0,
    caws2 integer  DEFAULT 0,
    saga integer NOT NULL,

    CONSTRAINT FK_game1 FOREIGN KEY(player1)
    REFERENCES gamer(name) ON DELETE CASCADE,

    CONSTRAINT FK_game2 FOREIGN KEY(player2)
    REFERENCES gamer(name) ON DELETE CASCADE,

    CONSTRAINT FK_saga3 FOREIGN KEY(saga)
    REFERENCES saga(id) ON DELETE CASCADE
);