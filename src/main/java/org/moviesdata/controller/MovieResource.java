package org.moviesdata.controller;

import jakarta.inject.Inject;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.*;
import org.eclipse.microprofile.metrics.annotation.Counted;
import org.moviesdata.domain.Movie;
import org.moviesdata.domain.MovieQueryParams;
import org.moviesdata.service.MovieService;

import java.util.List;
import java.util.Optional;

@Path("/movies")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class MovieResource {

    @Inject
    MovieService movieService;

    @GET
    @Counted(name = "getAllMovies", description = "count for: GET /movies")
    public Response getAllMovies(
            @QueryParam ("title") Optional<String> title,
            @QueryParam ("description") Optional<String> description,
            @QueryParam ("release_year") Optional<Integer> releaseYear
                                             ) {
        List<Movie> movies;
        if(title.isPresent() || description.isPresent() || releaseYear.isPresent()) {
            MovieQueryParams queryParams = new MovieQueryParams(title, description, releaseYear);
            movies = movieService.searchMovies(queryParams);
        }
        else {
            movies = movieService.listAllMovies();
        }
        return Response.ok(movies).build();
    }

    @GET
    @Path("/{id}")
    @Counted(name = "getSingleMovie", description = "count for: GET /movies/{id}")
    public Response getSingleMovie(@PathParam("id") String movieId) {
        Optional<Movie> movie = movieService.findMovieById(movieId);
        if(movie.isEmpty()) return Response.status(Response.Status.NOT_FOUND).build();
        return Response.ok(movie.get()).build();
    }

    @POST
    @Counted(name = "createMovie", description = "count for: POST /movies/")
    public Response createMovie(@Valid @NotNull Movie movie, @Context UriInfo uriInfo) {
        if(movieService.findMovieById(movie.getImdbID()).isPresent()) {
            return Response.status(Response.Status.BAD_REQUEST).build();
        }
        movieService.createMovie(movie);
        UriBuilder uriBuilder = uriInfo.getAbsolutePathBuilder();
        uriBuilder.path(movie.getImdbID());
        return Response.created(uriBuilder.build()).build();
    }

    @PUT
    @Path("/{id}")
    @Counted(name = "updateMovie", description = "count for: PUT /movies/{id}")
    public Response updateMovie(@PathParam("id") String movieId, @Valid @NotNull Movie data) {
        if(movieService.findMovieById(movieId).isEmpty()) {
            return Response.status(Response.Status.NOT_FOUND).build();
        }
        movieService.updateMovie(data, movieId);
        return Response.ok().build();
    }

    @DELETE
    @Path("/{id}")
    @Counted(name = "deleteMovie", description = "count for: DELETE /movies/{id}")
    public Response deleteMovie(@PathParam("id") String movieId) {
        if(movieService.findMovieById(movieId).isEmpty()) {
            return Response.status(Response.Status.NOT_FOUND).build();
        }
        boolean success = movieService.deleteMovieById(movieId);
        if(!success) return Response.serverError().build();
        return Response.noContent().build();
    }
}
