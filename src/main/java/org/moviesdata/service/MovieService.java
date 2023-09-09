package org.moviesdata.service;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;
import org.moviesdata.domain.Movie;
import org.moviesdata.model.MovieEntity;
import org.moviesdata.repository.MovieRepository;

import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@ApplicationScoped
public class MovieService {

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
        movieRepository.persistAndFlush(entity);
    }

    @Transactional
    public void updateMovie(Movie data, String movieId) {
        MovieEntity entity = movieRepository.findById(movieId);
        entity.setTitle(data.getTitle());
        entity.setDescription(data.getDescription());
        entity.setReleaseYear(data.getReleaseYear());
        entity.setUpdatedDate(new Date());
        movieRepository.persistAndFlush(entity);
    }

    @Transactional
    public boolean deleteMovieById(String movieId) {
       return movieRepository.deleteById(movieId);
    }
}
