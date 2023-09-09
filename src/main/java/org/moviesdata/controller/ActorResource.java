package org.moviesdata.controller;

import jakarta.inject.Inject;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
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
    public Response getSingleActor(@PathParam("id") String actorId) {
        Optional<Actor> actor = actorService.findActorById(actorId);
        if(actor.isEmpty()) return Response.status(Response.Status.NOT_FOUND).build();
        return Response.ok(actor.get()).build();
    }

    @POST
    @Counted(name = "createActor", description = "count for: POST /actors/")
    public Response createActor(@Valid @NotNull Actor actor, @Context UriInfo uriInfo) {
        if(actorService.findActorById(actor.getImdbID()).isPresent())
            return Response.status(Response.Status.BAD_REQUEST).build();
        actorService.createActor(actor);
        UriBuilder uriBuilder = uriInfo.getAbsolutePathBuilder();
        uriBuilder.path(actor.getImdbID());
        return Response.created(uriBuilder.build()).build();
    }

    @PUT
    @Path("/{id}")
    @Counted(name = "updateActor", description = "count for: PUT /actors/{id}")
    public Response updateActor(@PathParam("id") String actorId, @Valid @NotNull Actor actor) {
        if(actorService.findActorById(actorId).isEmpty()) {
            return Response.status(Response.Status.NOT_FOUND).build();
        }
         actorService.updateActor(actor, actorId);
        return Response.ok().build();
    }

    @DELETE
    @Path("/{id}")
    @Counted(name = "deleteActor", description = "count for: DELETE /actors/{id}")
    public Response deleteActor(@PathParam("id") String actorId) {
        if(actorService.findActorById(actorId).isEmpty()) {
            return Response.status(Response.Status.NOT_FOUND).build();
        }
        boolean success = actorService.deleteActorById(actorId);
        if(!success) return Response.serverError().build();
        return Response.noContent().build();
    }
}
