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