package org.moviesdata.service;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;
import org.moviesdata.domain.Movie;
import org.moviesdata.model.ActorEntity;
import org.moviesdata.model.MovieEntity;
import org.moviesdata.repository.MovieRepository;

import java.util.*;
import java.util.stream.Collectors;

@ApplicationScoped
public class MovieService {
    @Inject
    ActorService actorService;

    @Inject
    MovieRepository movieRepository;

    public List<Movie> listAllMovies() {
       return movieRepository.findAll()
                .list().stream().map(Movie::fromEntity).collect(Collectors.toList());
    }

    public Optional<Movie> findMovieById(String movieId) {
        Optional<MovieEntity> movieEntity = movieRepository.findByIdOptional(movieId);
        if(movieEntity.isEmpty()) return Optional.empty();
        return Optional.of(Movie.fromEntity(movieEntity.get()));
    }

    @Transactional
    public void createMovie(Movie data) {
        MovieEntity entity = MovieEntity.fromDomain(data, true);
        setMovieActors(data, entity);
        movieRepository.persistAndFlush(entity);
    }

    @Transactional
    public void updateMovie(Movie data, String movieId) {
        MovieEntity entity = movieRepository.findById(movieId);
        entity.setTitle(data.getTitle());
        entity.setDescription(data.getDescription());
        entity.setReleaseYear(data.getReleaseYear());
        entity.setUpdatedDate(new Date());
        setMovieActors(data, entity);
        movieRepository.persistAndFlush(entity);
    }

    private void setMovieActors(Movie data, MovieEntity entity) {
        // not setting actors means they will not be changed
        if(data.getActors() == null) return;

        // empty list removes all the actors from movie
        if(data.getActors().isEmpty()) {
            entity.setActors(new HashSet<>());
        }
        else {
            Set<ActorEntity> actors = actorService.findActorEntities(data.getActors()).stream().collect(Collectors.toSet());
            entity.setActors(actors);
        }
    }

    @Transactional
    public boolean deleteMovieById(String movieId) {
       return movieRepository.deleteById(movieId);
    }
}
