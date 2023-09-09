package org.moviesdata.service;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import org.moviesdata.model.ActorEntity;
import org.moviesdata.repository.ActorRepository;

import java.util.List;

@ApplicationScoped
public class ActorService {

    @Inject
    ActorRepository actorRepository;

    public List<ActorEntity> listAllActors() {

        return actorRepository.findAll().list();
    }
}
