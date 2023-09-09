package org.moviesdata.controller;

import jakarta.inject.Inject;
import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.PathParam;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import org.eclipse.microprofile.metrics.annotation.Counted;
import org.moviesdata.domain.Movie;
import org.moviesdata.service.MovieService;

import java.util.Optional;

@Path("/movies")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class MovieResource {

    @Inject
    MovieService movieService;

    @GET
    @Counted(name = "getAllMovies", description = "count for: /movies")
    public Response getAllMovies() {

        return Response.ok(movieService.listAllMovies()).build();
    }

    @GET
    @Path("/{id}")
    @Counted(name = "getSingleMovie", description = "count for: /movies/{id}")
    public Response getMovie(@PathParam("id") String movieId) {
        Optional<Movie> movie = movieService.findMovieById(movieId);
        if(movie.isEmpty()) return Response.status(Response.Status.NOT_FOUND).build();
        return Response.ok(movie.get()).build();
    }
}
