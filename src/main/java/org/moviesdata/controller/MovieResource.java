package org.moviesdata.controller;

import io.quarkus.panache.common.Page;
import jakarta.inject.Inject;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.*;
import org.eclipse.microprofile.metrics.annotation.Counted;
import org.moviesdata.domain.Actor;
import org.moviesdata.domain.Movie;
import org.moviesdata.domain.MovieQueryParams;
import org.moviesdata.response.ActorResponse;
import org.moviesdata.response.MovieResponse;
import org.moviesdata.response.ResponseMetadata;
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
            @QueryParam ("release_year") Optional<Integer> releaseYear,
            @QueryParam ("page_index") Optional<@Min(0) Integer>  pageIndex,
            @QueryParam ("page_size") Optional<@Min (1) Integer> pageSize
    ) {
        if(pageIndex.isPresent() && pageSize.isEmpty()) return Response.status(Response.Status.BAD_REQUEST).build();
        final Optional<Page> pagination = pageSize.isPresent() ?
                Optional.of(Page.of(pageIndex.orElse(0), pageSize.get())) : Optional.empty();
        Optional<MovieQueryParams> queryParams = Optional.empty();
        if(title.isPresent() || description.isPresent() || releaseYear.isPresent()) {
            queryParams = Optional.of(new MovieQueryParams(title, description, releaseYear, pagination));
        }

        final List<Movie> movies;
        final int total;
        if(queryParams.isPresent()) {
            movies = movieService.searchMovies(queryParams.get());
            total = pagination.isPresent() ? movieService.searchMoviesCount(queryParams.get()) : movies.size();
        }
        else if (pagination.isPresent()) {
            movies = movieService.listAllMovies(pagination.get());
            total = movieService.allMoviesCount();
        }
        else {
            movies = movieService.listAllMovies();
            total = movies.size();
        }
        final ResponseMetadata metadata =  pagination.isPresent() ? new ResponseMetadata(total, movies.size(), pagination.get()) : new ResponseMetadata(total);
        return Response.ok(new MovieResponse(movies, metadata)).build();
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
