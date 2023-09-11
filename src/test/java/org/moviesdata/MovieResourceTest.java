package org.moviesdata;

import io.quarkus.test.junit.QuarkusTest;
import org.junit.jupiter.api.Test;

import static io.restassured.RestAssured.given;
import static org.junit.jupiter.api.Assertions.*;
import org.moviesdata.domain.Actor;
import org.moviesdata.domain.Movie;

@QuarkusTest
public class MovieResourceTest {

    @Test
    public void testAllMoviesEndpoint() {
        given()
                .when().get("/movies")
                .then()
                .statusCode(200);
    }

    @Test
    public void testAllActorsEndpoint() {
        given()
                .when().get("/actors")
                .then()
                .statusCode(200);
    }

    @Test
    public void testGetMovieEndpoint() {
        Movie movie =
                given()
                        .when().get("/movies/tt5027774")
                        .then()
                        .statusCode(200)
                        .extract()
                        .body()
                        .as(Movie.class);

        assertEquals("tt5027774", movie.getImdbID());

    }

    @Test
    public void testGetActorEndpoint() {
        Actor actor =
                given()
                        .when().get("/actors/nm0000531")
                        .then()
                        .statusCode(200)
                        .extract()
                        .body()
                        .as(Actor.class);

        assertEquals("nm0000531", actor.getImdbID());

    }


    @Test
    public void testNonExistingMovie() {

        given()
                .when().get("/movies/tt0180093")
                .then()
                .statusCode(404);
    }

    @Test
    public void testNonExistingActor() {
        given()
                .when().get("/actors/nm0001467")
                .then()
                .statusCode(404);
    }
}