CREATE TABLE IF NOT EXISTS a_game_saga (
     game_id SERIAL PRIMARY KEY,
     created timestamp DEFAULT now(),
     inviter bigint,
     waitlist bigint[],
     contributed bigint[],
     accepted timestamp,
     nextmove bigint,
     lastmove timestamp,
     canceled bool,
     finished timestamp,
     token varchar
);

CREATE TABLE IF NOT EXISTS a_game_disposition_dto (
      game_id SERIAL PRIMARY KEY,
      disposition varchar
);

CREATE TABLE IF NOT EXISTS a_game_move_dto (
      game_id SERIAL PRIMARY KEY,
      move varchar
);

CREATE TABLE IF NOT EXISTS a_game_state_dto (
      game_id SERIAL PRIMARY KEY,
      state varchar
);


CREATE TABLE IF NOT EXISTS gamer (
    id SERIAL PRIMARY KEY,
    name TEXT NOT NULL,
    token TEXT NOT NULL,
    busy boolean DEFAULT false,
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
    UNIQUE(player1),
    UNIQUE(player2),

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