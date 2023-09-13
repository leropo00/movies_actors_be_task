package org.moviesdata.controller;

import io.quarkus.panache.common.Page;
import jakarta.inject.Inject;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.DELETE;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.PUT;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.PathParam;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.QueryParam;
import jakarta.ws.rs.core.Context;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.core.UriBuilder;
import jakarta.ws.rs.core.UriInfo;
import org.eclipse.microprofile.metrics.annotation.Counted;
import org.moviesdata.constants.ErrorResponseCode;
import org.moviesdata.domain.Actor;
import org.moviesdata.response.ActorResponse;
import org.moviesdata.response.ResponseMetadata;
import org.moviesdata.response.errors.EntityError;
import org.moviesdata.utils.ResponseGenerator;
import org.moviesdata.service.ActorService;

import java.util.List;
import java.util.Optional;

@Path("/actors")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class ActorResource {

    @Inject
    ActorService actorService;

    @GET
    @Counted(name = "getAllActors", description = "count for: /actors")
    public Response getAllActors(@QueryParam ("page_index") Optional<@Min (0) Integer> pageIndex,
                                  @QueryParam ("page_size") Optional<@Min (1) Integer> pageSize) {
        if(pageIndex.isPresent() && pageSize.isEmpty()) return ResponseGenerator.pageSizeMissing();

        final ActorResponse response;
        if(pageSize.isPresent()) {
            Page pagination = Page.of(pageIndex.orElse(0), pageSize.get());
            List<Actor>actors = actorService.listAllActors(pagination);
            ResponseMetadata metadata = new ResponseMetadata(actorService.allActorsCount(), actors.size(), pagination);
            if(metadata.outsidePaginationBoundaries()) return ResponseGenerator.paginationOutsideBounds(metadata);

            response = new ActorResponse(actors, metadata);
        }
        else {
            List<Actor>actors = actorService.listAllActors();
            response = new ActorResponse(actors, new ResponseMetadata(actors.size()));
        }
        return Response.ok(response).build();
    }

    @GET
    @Path("/{id}")
    @Counted(name = "getSingleActors", description = "count for: /actors/{id}")
    public Response getSingleActor(@PathParam("id") String actorId) {
        Optional<Actor> actor = actorService.findActorById(actorId);
        if(actor.isEmpty()) {
            return Response.status(Response.Status.NOT_FOUND).entity(
                    new EntityError(ErrorResponseCode.ENTITY_NOT_FOUND,
                            "Actor with specified id does not exists",
                            actorId)).build();
        }
        return Response.ok(actor.get()).build();
    }

    @POST
    @Counted(name = "createActor", description = "count for: POST /actors/")
    public Response createActor(@Valid @NotNull Actor actor, @Context UriInfo uriInfo) {
        if(actorService.findActorById(actor.getImdbID()).isPresent()) {
            return Response.status(Response.Status.BAD_REQUEST).entity(
                  new EntityError(ErrorResponseCode.ENTITY_ALREADY_EXISTS,
                          "Actor with specified id already exists",
                          actor.getImdbID())).build();
        }
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
            return Response.status(Response.Status.NOT_FOUND).entity(
                    new EntityError(ErrorResponseCode.ENTITY_NOT_FOUND,
                            "Actor with specified id does not exists, use POST /actors to create actor",
                            actorId)).build();
        }
         actorService.updateActor(actor, actorId);
        return Response.ok().build();
    }

    @DELETE
    @Path("/{id}")
    @Counted(name = "deleteActor", description = "count for: DELETE /actors/{id}")
    public Response deleteActor(@PathParam("id") String actorId) {
        if(actorService.findActorById(actorId).isEmpty()) {
            return Response.status(Response.Status.NOT_FOUND).entity(
                 new EntityError(ErrorResponseCode.ENTITY_NOT_FOUND,
                    "Actor with specified id does not exists, it either never existed or was already deleted",
                        actorId)).build();
        }
        boolean success = actorService.deleteActorById(actorId);
        if(!success) {
            return Response.serverError().entity(
                    new EntityError(ErrorResponseCode.DELETE_OPERATION_FAILED,
                            "Delete operation failed for unknown reasons",
                            actorId)).build();
        }
        return Response.noContent().build();
    }
}
