package org.moviesdata;

import io.quarkus.test.junit.QuarkusTest;
import jakarta.inject.Inject;
import org.hamcrest.beans.HasPropertyWithValue;
import org.hamcrest.core.Every;
import org.junit.jupiter.api.Test;
import org.moviesdata.domain.Movie;
import org.moviesdata.domain.MovieQueryParams;
import org.moviesdata.service.MovieService;

import java.util.List;
import java.util.Optional;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;
import static org.hamcrest.Matchers.empty;
import static org.hamcrest.core.Is.is;
import static org.junit.jupiter.api.Assertions.assertEquals;

@QuarkusTest
public class MovieServiceSearchTest {

    @Inject
    MovieService movieService;

    @Test
    public void testTitleSearch() {
        final String title = "the";
        MovieQueryParams params = new MovieQueryParams();
        params.setTitle(Optional.of(title));

        List<Movie> movieList = movieService.searchMovies(params);

        assertThat(movieList, is(not(empty())));
        assertThat(movieList,
                Every.everyItem(
                    HasPropertyWithValue.hasProperty("title", startsWithIgnoringCase(title))));
        checkCountSearchQueryMatches(movieList, params);
    }

    @Test
    public void testDescriptionSearch() {
        final String description = "LOVE";
        MovieQueryParams params = new MovieQueryParams();
        params.setDescription(Optional.of(description));

        List<Movie> movieList = movieService.searchMovies(params);

        assertThat(movieList, is(not(empty())));
        assertThat(movieList,
                Every.everyItem(
                        HasPropertyWithValue.hasProperty("description", containsStringIgnoringCase(description))));
        checkCountSearchQueryMatches(movieList, params);
    }

    @Test
    public void testYearSearch() {
        final int releaseYear = 2017;
        MovieQueryParams params = new MovieQueryParams();
        params.setReleaseYear(Optional.of(releaseYear));

        List<Movie> movieList = movieService.searchMovies(params);

        assertThat(movieList, is(not(empty())));
        assertThat(movieList,
                Every.everyItem(
                        HasPropertyWithValue.hasProperty("releaseYear", equalTo(releaseYear))));
        checkCountSearchQueryMatches(movieList, params);
    }

    @Test
    public void testMultipleConditionsSearch() {
        final String title = "Three Billboards";
        final String description = "challenges the local authorities";
        final int releaseYear = 2017;
        MovieQueryParams params = new MovieQueryParams();
        params.setTitle(Optional.of(title));
        params.setDescription(Optional.of(description));
        params.setReleaseYear(Optional.of(releaseYear));

        List<Movie> movieList = movieService.searchMovies(params);

        assertThat(movieList, is(not(empty())));
        assertThat(movieList,
                Every.everyItem(
                        HasPropertyWithValue.hasProperty("title", startsWithIgnoringCase(title))));
        assertThat(movieList,
                Every.everyItem(
                        HasPropertyWithValue.hasProperty("description", containsStringIgnoringCase(description))));
        assertThat(movieList,
                Every.everyItem(
                        HasPropertyWithValue.hasProperty("releaseYear", equalTo(releaseYear))));
        checkCountSearchQueryMatches(movieList, params);
    }

    @Test
    public void testNoResults() {
        MovieQueryParams params = new MovieQueryParams();
        params.setTitle(Optional.of("Requiem for a dream"));

        List<Movie> movieList = movieService.searchMovies(params);

        assertThat(movieList, is(empty()));
        checkCountSearchQueryMatches(movieList, params);
    }

    private void checkCountSearchQueryMatches(List<Movie> movieList, MovieQueryParams params) {
        assertEquals(movieList.size(), movieService.searchMoviesCount(params));
    }
}
