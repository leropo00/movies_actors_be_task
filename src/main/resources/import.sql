--  TABLE CREATION STATEMENTS --

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
    gender      ENUM('MALE', 'FEMALE') NOT NULL,
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

CREATE  INDEX reversed_pk_movies_cast ON movies_cast(actor_id, movie_id);

--  DATA INSERT STATEMENTS --

 -- test data is inserted in separate queries for easier debuging, if queries fail --

INSERT INTO actors(imdbID, first_name, last_name, gender, birth_date, created_at, updated_at)
VALUES('nm0000531','Frances', 'McDormand', 'FEMALE', '1957-06-23' , CURRENT_TIMESTAMP, CURRENT_TIMESTAMP);

INSERT INTO actors(imdbID, first_name, last_name, gender, birth_date, created_at, updated_at)
VALUES('nm0005377','Sam', 'Rockwell', 'MALE', '1968-11-05' , CURRENT_TIMESTAMP, CURRENT_TIMESTAMP);

INSERT INTO actors(imdbID, first_name, last_name, gender, birth_date, created_at, updated_at)
VALUES('nm0000437','Woody', 'Harrelson', 'MALE', '1961-07-23' , CURRENT_TIMESTAMP, CURRENT_TIMESTAMP);

INSERT INTO actors(imdbID, first_name, last_name, gender, birth_date, created_at, updated_at)
VALUES('nm0268199','Colin', 'Farrell', 'MALE', '1976-05-31' , CURRENT_TIMESTAMP, CURRENT_TIMESTAMP);

INSERT INTO actors(imdbID, first_name, last_name, gender, birth_date, created_at, updated_at)
VALUES('nm0322407','Brendan', 'Gleeson', 'MALE', '1955-03-29' , CURRENT_TIMESTAMP, CURRENT_TIMESTAMP);

INSERT INTO actors(imdbID, first_name, last_name, gender, birth_date, created_at, updated_at)
VALUES('nm0842770','Tilda', 'Swinton', 'FEMALE', '1960-11-05' , CURRENT_TIMESTAMP, CURRENT_TIMESTAMP);

INSERT INTO actors(imdbID, first_name, last_name, gender, birth_date, created_at, updated_at)
VALUES('nm0000114','Steve', 'Buscemi', 'MALE', '1957-12-13' , CURRENT_TIMESTAMP, CURRENT_TIMESTAMP);

INSERT INTO actors(imdbID, first_name, last_name, gender, birth_date, created_at, updated_at)
VALUES('nm0000195','Bill', 'Murray', 'MALE', '1950-09-21' , CURRENT_TIMESTAMP, CURRENT_TIMESTAMP);

INSERT INTO movies(imdbID, title, description, release_year, created_at, updated_at)
 VALUES('tt5027774','Three Billboards Outside Ebbing, Missouri',
'A mother personally challenges the local authorities to solve her daughters murder when they fail to catch the culprit.',
 2017 , CURRENT_TIMESTAMP, CURRENT_TIMESTAMP);

INSERT INTO movies_cast(movie_id, actor_id)
VALUES ('tt5027774', 'nm0000531');

INSERT INTO movies_cast(movie_id, actor_id)
VALUES ('tt5027774', 'nm0005377');

INSERT INTO movies_cast(movie_id, actor_id)
VALUES ('tt5027774', 'nm0000437');

INSERT INTO movies(imdbID, title, description, release_year, created_at, updated_at)
VALUES('tt0116282','Fargo',
'Minnesota car salesman Jerry Lundegaards inept crime falls apart due to his and his henchmens bungling and the persistent police work of the quite pregnant Marge Gunderson.',
1996 , CURRENT_TIMESTAMP, CURRENT_TIMESTAMP);

INSERT INTO movies_cast(movie_id, actor_id)
VALUES ('tt0116282', 'nm0000531');

INSERT INTO movies_cast(movie_id, actor_id)
VALUES ('tt0116282', 'nm0000114');

INSERT INTO movies(imdbID, title, description, release_year, created_at, updated_at)
 VALUES('tt11813216','The Banshees of Inisherin',
'Two lifelong friends find themselves at an impasse when one abruptly ends their relationship, with alarming consequences for both of them.',
 2022 , CURRENT_TIMESTAMP, CURRENT_TIMESTAMP);

INSERT INTO movies_cast(movie_id, actor_id)
VALUES ('tt11813216', 'nm0322407');

INSERT INTO movies_cast(movie_id, actor_id)
VALUES ('tt11813216', 'nm0268199');

INSERT INTO movies(imdbID, title, description, release_year, created_at, updated_at)
 VALUES('tt0780536','In Bruges',
'Guilt-stricken after a job gone wrong, hitman Ray and his partner await orders from their ruthless boss in Bruges, Belgium, the last place in the world Ray wants to be.',
 2008 , CURRENT_TIMESTAMP, CURRENT_TIMESTAMP);

INSERT INTO movies_cast(movie_id, actor_id)
VALUES ('tt0780536', 'nm0322407');

INSERT INTO movies_cast(movie_id, actor_id)
VALUES ('tt0780536', 'nm0268199');

INSERT INTO movies(imdbID, title, description, release_year, created_at, updated_at)
 VALUES('tt3799694','The Nice Guys',
'In 1970s Los Angeles, a mismatched pair of private eyes investigate a missing girl and the mysterious death of a porn star.',
 2016 , CURRENT_TIMESTAMP, CURRENT_TIMESTAMP);

INSERT INTO movies(imdbID, title, description, release_year, created_at, updated_at)
 VALUES('tt0420223','Stranger Than Fiction',
'I.R.S. auditor Harold Crick suddenly finds his mundane Chicago life to be the subject of narration only he can hear: narration that begins to affect his entire existence, from his work to his love life to his death.',
 2006 , CURRENT_TIMESTAMP, CURRENT_TIMESTAMP);

INSERT INTO movies(imdbID, title, description, release_year, created_at, updated_at)
 VALUES('tt0335266','Lost in Translation',
'A faded movie star and a neglected young woman form an unlikely bond after crossing paths in Tokyo.',
 2003 , CURRENT_TIMESTAMP, CURRENT_TIMESTAMP);

INSERT INTO movies(imdbID, title, description, release_year, created_at, updated_at)
 VALUES('tt4686844','Death of Stalin',
'Moscow, 1953. After being in power for nearly 30 years, Soviet dictator Joseph Vissarionovich Stalin takes ill and quickly dies. Now the members of the Council of Ministers scramble for power.',
 2017 , CURRENT_TIMESTAMP, CURRENT_TIMESTAMP);

 INSERT INTO movies_cast(movie_id, actor_id)
 VALUES ('tt4686844', 'nm0000114');

INSERT INTO movies(imdbID, title, description, release_year, created_at, updated_at)
  VALUES('tt3416742','What We Do in the Shadows',
 'Viago, Deacon, and Vladislav are vampires who are struggling with the mundane aspects of modern life, like paying rent, keeping up with the chore wheel, trying to get into nightclubs, and overcoming flatmate conflicts.',
  2014 , CURRENT_TIMESTAMP, CURRENT_TIMESTAMP);

INSERT INTO movies(imdbID, title, description, release_year, created_at, updated_at)
 VALUES('tt1821549','Nebraska',
'An aging, booze-addled father makes the trip from Montana to Nebraska with his estranged son in order to claim a million-dollar Mega Sweepstakes Marketing prize.',
 2013 , CURRENT_TIMESTAMP, CURRENT_TIMESTAMP);

INSERT INTO movies(imdbID, title, description, release_year, created_at, updated_at)
 VALUES('tt1748122','Moonrise Kingdom',
'A pair of young lovers flee their New England town, which causes a local search party to fan out to find them..',
 2012 , CURRENT_TIMESTAMP, CURRENT_TIMESTAMP);

INSERT INTO movies_cast(movie_id, actor_id)
VALUES ('tt1748122', 'nm0000531');

INSERT INTO movies_cast(movie_id, actor_id)
VALUES ('tt1748122', 'nm0842770');

INSERT INTO movies_cast(movie_id, actor_id)
VALUES ('tt1748122', 'nm0000195');

INSERT INTO movies(imdbID, title, description, release_year, created_at, updated_at)
 VALUES('tt0128445','Rushmore',
'A teenager at Rushmore Academy falls for a much older teacher and befriends a middle-aged industrialist. Later, he finds out that his love interest and his friend are having an affair, which prompts him to begin a vendetta.',
 1998 , CURRENT_TIMESTAMP, CURRENT_TIMESTAMP);

INSERT INTO movies_cast(movie_id, actor_id)
VALUES ('tt0128445', 'nm0000195');

INSERT INTO movies(imdbID, title, description, release_year, created_at, updated_at)
 VALUES('tt8847712','The French Dispatch',
'A love letter to journalists set in an outpost of an American newspaper in a fictional twentieth century French city that brings to life a collection of stories published in "The French Dispatch Magazine".',
 2021 , CURRENT_TIMESTAMP, CURRENT_TIMESTAMP);

INSERT INTO movies_cast(movie_id, actor_id)
VALUES ('tt8847712', 'nm0000531');

INSERT INTO movies_cast(movie_id, actor_id)
VALUES ('tt8847712', 'nm0842770');

INSERT INTO movies_cast(movie_id, actor_id)
VALUES ('tt8847712', 'nm0000195');
