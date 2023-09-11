package org.moviesdata;

import io.quarkus.test.junit.QuarkusTest;
import io.restassured.http.ContentType;
import org.junit.jupiter.api.*;
import org.moviesdata.domain.Movie;

import static io.restassured.RestAssured.given;
import static org.junit.jupiter.api.Assertions.*;

@QuarkusTest
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class WriteOperationsOnEndpointsTest {

    @Test
    @Order(1)
    public void testNonExistingMovie() {
        given()
                .when().get("/movies/tt0169547")
                .then()
                .statusCode(404);
    }

    @Test
    @Order(2)
    public void createMovie() {
        Movie movie = new Movie();
        movie.setImdbID("tt0169547");
        movie.setTitle("Blade Runner");
        movie.setDescription("A blade runner must pursue and terminate four replicants who stole a ship in space and have returned to Earth to find their creator");
        movie.setReleaseYear(1982);
        given().body(movie).contentType(ContentType.JSON)
                .when().post("/movies").then()
                .statusCode(201);
    }

    @Test
    @Order(3)
    public void updateMovie() {
        Movie movie = new Movie();
        movie.setImdbID("tt0169547");
        movie.setTitle("Do Androids Dream of Electric Sheep?");
        movie.setDescription("Rick Deckard, a bounty hunter, plans to kill enough errant androids (replicants) so he can replace his robotic sheep with a real one. In the process of hunting down these slave pseudo-humans, Rick Deckard falls in love with an android and learns about himself and what it means to be human--and inhuman.");
        movie.setReleaseYear(1968);
        given().body(movie).contentType(ContentType.JSON)
                .when().put("/movies/tt0169547").then()
                .statusCode(200);
    }

    @Test
    @Order(4)
    public void rereadMovie() {
            Movie movie =
            given()
                    .when().get("/movies/tt0169547")
                    .then()
                    .statusCode(200)
                    .extract()
                    .body()
                    .as(Movie.class);

            assertEquals(1968, movie.getReleaseYear());

    }

    @Test
    @Order(5)
    public void deleteMovies() {
        given()
                .when().delete("/movies/tt0169547")
                .then()
                .statusCode(204);
    }

    @Test
    @Order(6)
    public void movieWasDeleted() {
        given()
                .when().get("/movies/tt0169547")
                .then()
                .statusCode(404);
    }
}