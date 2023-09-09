DROP TABLE  IF EXISTS  movies_cast;
DROP TABLE  IF EXISTS  actors;
DROP TABLE  IF EXISTS  movies;

CREATE TABLE movies
(
    imdbID       VARCHAR(20) NOT NULL PRIMARY KEY,
    title        VARCHAR(255) NOT NULL,
    description  VARCHAR(2000) NOT NULL,
    release_year INTEGER,
    created_at   TIMESTAMP NOT NULL,
    updated_at   TIMESTAMP NOT NULL
);

CREATE TABLE actors
(
    imdbID      VARCHAR(20)  NOT NULL PRIMARY KEY,
    first_name  VARCHAR(255) NOT NULL,
    last_name   VARCHAR(255) NOT NULL,
    gender      ENUM('male', 'female') NOT NULL,
    birth_date  DATE,
    created_at  TIMESTAMP NOT NULL,
    updated_at  TIMESTAMP NOT NULL
);

CREATE TABLE movies_cast
(
    movie_id VARCHAR(20) NOT NULL,
    actor_id VARCHAR(20) NOT NULL,
    PRIMARY KEY(movie_id, actor_id),
    FOREIGN KEY (movie_id) REFERENCES movies(imdbID) ,
    FOREIGN KEY (actor_id) REFERENCES actors(imdbID)
);
