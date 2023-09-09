package org.moviesdata.controller;

import jakarta.inject.Inject;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.*;
import org.eclipse.microprofile.metrics.annotation.Counted;
import org.moviesdata.domain.Actor;
import org.moviesdata.domain.Movie;
import org.moviesdata.service.ActorService;
import org.moviesdata.service.MovieService;

import java.util.Optional;

@Path("/actors")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class ActorResource {

    @Inject
    ActorService actorService;

    @GET
    @Counted(name = "getAllActors", description = "count for: /actors")
    public Response getAllActors() {
        return Response.ok(actorService.listAllActors()).build();
    }

    @GET
    @Path("/{id}")
    @Counted(name = "getSingleActors", description = "count for: /actors/{id}")
    public Response getAllActors(@PathParam("id") String actorId) {
        Optional<Actor> actor = actorService.findActorById(actorId);
        if(actor.isEmpty()) return Response.status(Response.Status.NOT_FOUND).build();
        return Response.ok(actor.get()).build();
    }

    @POST
    @Counted(name = "createActor", description = "count for: POST /actors/")
    public Response createMovie(Movie movie, @Context UriInfo uriInfo) {
        UriBuilder uriBuilder = uriInfo.getAbsolutePathBuilder();
        uriBuilder.path(movie.getImdbID());
        return Response.created(uriBuilder.build()).build();
    }

    @PUT
    @Path("/{id}")
    @Counted(name = "updateActor", description = "count for: PUT /actors/{id}")
    public Response updateMovie(@PathParam("id") String ActorId, Actor actor) {
        return Response.ok().build();
    }

    @DELETE
    @Path("/{id}")
    @Counted(name = "deleteActor", description = "count for: DELETE /actors/{id}")
    public Response deleteActor(@PathParam("id") String ActorId, Actor actor) {
        return Response.noContent().build();
    }
}
