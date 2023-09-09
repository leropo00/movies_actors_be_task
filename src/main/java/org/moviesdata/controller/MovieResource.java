package org.moviesdata.controller;

import jakarta.inject.Inject;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.*;
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
    @Counted(name = "getAllMovies", description = "count for: GET /movies")
    public Response getAllMovies() {

        return Response.ok(movieService.listAllMovies()).build();
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
        UriBuilder uriBuilder = uriInfo.getAbsolutePathBuilder();
        uriBuilder.path(movie.getImdbID());
        return Response.created(uriBuilder.build()).build();
    }

    @PUT
    @Path("/{id}")
    @Counted(name = "updateMovie", description = "count for: PUT /movies/{id}")
    public Response updateMovie(@PathParam("id") String movieId, @Valid @NotNull Movie movie) {
        return Response.ok().build();
    }

    @DELETE
    @Path("/{id}")
    @Counted(name = "deleteMovie", description = "count for: DELETE /movies/{id}")
    public Response deleteMovie(@PathParam("id") String movieId) {
        return Response.noContent().build();
    }
}
