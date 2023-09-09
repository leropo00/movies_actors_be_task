package org.moviesdata;

import io.quarkus.test.junit.QuarkusTest;
import org.junit.jupiter.api.Test;

import static io.restassured.RestAssured.given;
import static org.hamcrest.CoreMatchers.is;

@QuarkusTest
public class MovieResourceTest {

    @Test
    public void testMoviesEndpoint() {
        given()
          .when().get("/movies")
          .then()
             .statusCode(200);
    }

    @Test
    public void testActorsEndpoint() {
        given()
                .when().get("/actors")
                .then()
                .statusCode(200);
    }

    @Test
    public void testGetMovieEndpoint() {
        given()
                .when().get("/movies/tt5027774")
                .then()
                .statusCode(200);

        given()
                .when().get("/movies/tt0180093")
                .then()
                .statusCode(404);

    }

    @Test
    public void testGetActorEndpoint() {
        given()
                .when().get("/actors/nm0000437")
                .then()
                .statusCode(200);

        given()
                .when().get("/actors/nm0001467")
                .then()
                .statusCode(404);
    }
}