package org.moviesdata;

import io.quarkus.test.junit.QuarkusTest;
import org.json.JSONObject;
import org.junit.jupiter.api.Test;

import static io.restassured.RestAssured.given;
import static org.junit.jupiter.api.Assertions.*;
import org.moviesdata.domain.Actor;
import org.moviesdata.domain.Movie;

@QuarkusTest
public class ReadOperationsOnEndpointsTest {

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

        assertTrue(dataLength > 0);
        assertFalse(json.getJSONObject("metadata").getBoolean("has_pagination"));
        assertEquals(dataLength, json.getJSONObject("metadata").getInt("total"));
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
    public void testPaginationOnMoviess() {
        String response = given()
                .when().get("/movies?page_size=5&page_index=1")
                .then()
                .statusCode(200)
                .extract()
                .asString();

        JSONObject json = new JSONObject(response);

        JSONObject metadata = json.getJSONObject("metadata");

        final int dataLength = json.getJSONArray("data").length();

        assertTrue(dataLength < metadata.getInt("total")) ;
        assertEquals(dataLength, metadata.getInt("results_count"));

        assertTrue(metadata.getBoolean("has_pagination"));
        assertTrue(metadata.getBoolean("has_next_page"));
        assertTrue(metadata.getBoolean("has_previous_page"));
    }


    @Test
    public void testPaginationOverTheLimit() {
        given()
                .when().get("/actors?page_size=10&page_index=10")
                .then()
                .statusCode(400);
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