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

 -- test data is inserted in separate queries for easier debuging, if queries fail --

INSERT INTO movies(imdbID, title, description, release_year, created_at, updated_at)
 VALUES('tt5027774','Three Billboards Outside Ebbing, Missouri',
'A mother personally challenges the local authorities to solve her daughters murder when they fail to catch the culprit.',
 2017 , CURRENT_TIMESTAMP, CURRENT_TIMESTAMP);


INSERT INTO actors(imdbID, first_name, last_name, gender, birth_date, created_at, updated_at)
VALUES('nm0000531','Frances', 'McDormand', 'female', '1957-06-23' , CURRENT_TIMESTAMP, CURRENT_TIMESTAMP);

INSERT INTO actors(imdbID, first_name, last_name, gender, birth_date, created_at, updated_at)
VALUES('nm0005377','Sam', 'Rockwell', 'male', '1968-11-05' , CURRENT_TIMESTAMP, CURRENT_TIMESTAMP);

INSERT INTO actors(imdbID, first_name, last_name, gender, birth_date, created_at, updated_at)
VALUES('nm0000437','Woody', 'Harrelson', 'male', '1961-07-23' , CURRENT_TIMESTAMP, CURRENT_TIMESTAMP);

INSERT INTO movies_cast(movie_id, actor_id)
VALUES ('tt5027774', 'nm0000531');

INSERT INTO movies_cast(movie_id, actor_id)
VALUES ('tt5027774', 'nm0005377');

INSERT INTO movies_cast(movie_id, actor_id)
VALUES ('tt5027774', 'nm0000437');
