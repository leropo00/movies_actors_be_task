package org.moviesdata;

import io.quarkus.test.junit.QuarkusTest;
import io.restassured.http.ContentType;
import jakarta.inject.Inject;
import org.junit.jupiter.api.*;
import org.moviesdata.domain.Actor;
import org.moviesdata.domain.Movie;
import org.moviesdata.model.ActorEntity;
import org.moviesdata.model.MovieEntity;
import org.moviesdata.repository.ActorRepository;
import org.moviesdata.repository.MovieRepository;
import org.moviesdata.service.MovieService;

import java.util.GregorianCalendar;
import java.util.List;
import java.util.Optional;

import static io.restassured.RestAssured.given;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.containsString;
import static org.junit.jupiter.api.Assertions.*;

@QuarkusTest
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class WriteOperationsOnEndpointsTest {

    @Inject
    MovieRepository movieRepository;

    @Inject
    ActorRepository actorRepository;

    @Inject
    MovieService movieService;

    @Test
    @Order(1)
    public void testNonExistingMovie() {
        Optional<MovieEntity> movie = movieRepository.findByIdOptional("tt0169547");
        assertFalse(movie.isPresent());
    }

    @Test
    @Order(2)
    public void createActorForMovie() {
        Actor actor = new Actor();
        actor.setImdbID("nm0000148");
        actor.setFirstName("Harrison");
        actor.setLastName("Ford");
        actor.setGender("male");
        actor.setBirthDate(new GregorianCalendar(1942, 6, 13).getTime());

        given().body(actor).contentType(ContentType.JSON)
                .when().post("/actors").then()
                .statusCode(201);
    }

    @Test
    @Order(3)
    public void createMovie() {
        Movie movie = new Movie();
        movie.setImdbID("tt0169547");
        movie.setTitle("Blade Runner");
        movie.setDescription("A blade runner must pursue and terminate four replicants who stole a ship in space and have returned to Earth to find their creator");
        movie.setReleaseYear(1982);
        movie.setActors(List.of("nm0000148"));

        given().body(movie).contentType(ContentType.JSON)
                .when().post("/movies").then()
                .statusCode(201);
    }

    @Test
    @Order(4)
    public void checkMovieDataMapping() {
        Optional<MovieEntity> movieEntityOptional = movieService.findMovieEntityWithActors("tt0169547");
        assertTrue(movieEntityOptional.isPresent());

        Movie movie = Movie.fromEntityWithActors(movieEntityOptional.get());
        MovieEntity movieEntity = movieEntityOptional.get();

        assertEquals(movie.getImdbID(), movieEntity.getImdbID());
        assertEquals(movie.getTitle(), movieEntity.getTitle());
        assertEquals(movie.getReleaseYear(), movieEntity.getReleaseYear());
        assertEquals(List.of("nm0000148"), movie.getActors());
    }

    @Test
    @Order(5)
    public void createActorForUpdate() {
        Actor actor = new Actor();
        actor.setImdbID("nm0001140");
        actor.setFirstName("Philip");
        actor.setLastName("Dick");
        actor.setGender("male");
        actor.setBirthDate(new GregorianCalendar(1912, 11, 16).getTime());

        given().body(actor).contentType(ContentType.JSON)
                .when().post("/actors").then()
                .statusCode(201);
    }


    @Test
    @Order(6)
    public void updateMovie() {
        Movie movie = new Movie();
        movie.setImdbID("tt0169547");
        movie.setTitle("Do Androids Dream of Electric Sheep?");
        movie.setDescription("Rick Deckard, a bounty hunter, plans to kill enough errant androids (replicants) so he can replace his robotic sheep with a real one. In the process of hunting down these slave pseudo-humans, Rick Deckard falls in love with an android and learns about himself and what it means to be human--and inhuman.");
        movie.setReleaseYear(1968);
        movie.setActors(List.of("nm0001140"));
        given().body(movie).contentType(ContentType.JSON)
                .when().put("/movies/tt0169547").then()
                .statusCode(200);
    }

    @Test
    @Order(7)
    public void checkMovieDataChanged() {
        Optional<MovieEntity> movieEntityOptional = movieRepository.findByIdOptional("tt0169547");
        assertTrue(movieEntityOptional.isPresent());

        Movie movie = Movie.fromEntityWithActors(movieEntityOptional.get());

        assertEquals(movie.getImdbID(), "tt0169547");
        assertEquals(movie.getTitle(), "Do Androids Dream of Electric Sheep?");
        assertEquals(movie.getReleaseYear(), 1968);
        assertThat(movie.getDescription(), containsString("sheep"));
        assertEquals(List.of("nm0001140"), movie.getActors());
    }

    @Test
    @Order(8)
    public void deleteActor() {
        given()
                .when().delete("/actors/nm0001140")
                .then()
                .statusCode(204);

        Optional<ActorEntity> actor = actorRepository.findByIdOptional("nm0001140");
        assertTrue(actor.isEmpty());

        Optional<MovieEntity> movieEntityOptional = movieService.findMovieEntityWithActors("tt0169547");
        assertTrue(movieEntityOptional.isPresent());
        MovieEntity movieEntity = movieEntityOptional.get();
        assertTrue(movieEntity.getActors().size() == 0);
    }


    @Test
    @Order(9)
    public void deleteMovie() {
        given()
                .when().delete("/movies/tt0169547")
                .then()
                .statusCode(204);

        Optional<MovieEntity> movie = movieRepository.findByIdOptional("tt0169547");
        assertFalse(movie.isPresent());
    }

    @Test
    @Order(10)
    public void originalActorStillExists() {
        Optional<ActorEntity> actor = actorRepository.findByIdOptional("nm0000148");
        assertTrue(actor.isPresent());
    }
}