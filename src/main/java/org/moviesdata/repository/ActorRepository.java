package org.moviesdata.repository;

import io.quarkus.hibernate.orm.panache.PanacheRepositoryBase;
import jakarta.enterprise.context.ApplicationScoped;
import org.moviesdata.model.ActorEntity;
import org.moviesdata.model.MovieEntity;

@ApplicationScoped
public class ActorRepository implements PanacheRepositoryBase<ActorEntity, String> {

}