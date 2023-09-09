DROP TABLE  IF EXISTS  movies;

CREATE TABLE movies
(
    imdbID       VARCHAR(20) NOT NULL PRIMARY KEY,
    created_at   TIMESTAMP NOT NULL,
    updated_at   TIMESTAMP NOT NULL
);


INSERT INTO movies(imdbID, created_at, updated_at)
 VALUES('tt5027774', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP);
