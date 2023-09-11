package org.moviesdata;

import io.quarkus.test.junit.QuarkusTest;
import org.json.JSONObject;
import org.junit.jupiter.api.Test;

import static io.restassured.RestAssured.given;
import static org.junit.jupiter.api.Assertions.*;
import org.moviesdata.domain.Actor;
import org.moviesdata.domain.Movie;

@QuarkusTest
public class MovieResourceTest {

    /*
        TEMPORARY WORKAROUND:
            certain tests use JSON parsing to parse output
            parsing of ActorResponse and MovieResponse
            results in stack overflow from jackson parsing
                 at com.fasterxml.jackson.databind.ser.BeanSerializer.serialize(BeanSerializer.java:178)
                 at com.fasterxml.jackson.databind.ser.BeanPropertyWriter.serializeAsField(BeanPropertyWriter.java:732)
                 at com.fasterxml.jackson.databind.ser.std.BeanSerializerBase.serializeFields(BeanSerializerBase.java:772)

            when tested via Postman /actors and /movies endpoints work correctly
     */

    @Test
    public void testAllMoviesEndpoint() {
        String response = given()
                    .when().get("/movies")
                    .then()
                    .statusCode(200)
                    .extract()
                    .asString();

        JSONObject json = new JSONObject(response);
        final int dataLength = json.getJSONArray("data").length();
        final int metadataCount = json.getJSONObject("metadata").getInt("total");

        assertTrue(dataLength > 0);
        assertEquals(dataLength, metadataCount);
    }

    @Test
    public void testAllActorsEndpoint() {
        String response = given()
                .when().get("/actors")
                .then()
                .extract()
                .asString();

        JSONObject json = new JSONObject(response);
        final int dataLength = json.getJSONArray("data").length();
        final int metadataCount = json.getJSONObject("metadata").getInt("total");

        assertTrue(dataLength > 0);
        assertEquals(dataLength, metadataCount);
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