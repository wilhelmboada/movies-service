package com.movies.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.movies.model.Movie;
import com.movies.model.MovieResponse;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class MovieService {

    private final ExternalMovieApiService externalMovieApiService;

    public MovieService(ExternalMovieApiService externalMovieApiService) {
        this.externalMovieApiService = externalMovieApiService;
    }

    public List<String> getDirectors(int threshold) throws JsonProcessingException {
        Map<String, Long> directorCount = new HashMap<>();
        int page = 1;
        int totalPages;

        do {
            MovieResponse movieResponse = fetchAndProcessMovies(page, directorCount);
            if (movieResponse != null) {
                totalPages = movieResponse.getTotalPages();
                page++;
            } else {
                totalPages = 0;
            }
        } while (page <= totalPages);

        return filterAndSortDirectors(directorCount, threshold);
    }

    private MovieResponse fetchAndProcessMovies(int page, Map<String, Long> directorCount) throws JsonProcessingException {
        MovieResponse movieResponse = externalMovieApiService.fetchMovies(page);
        if (movieResponse != null) {
            processMovies(movieResponse.getData(), directorCount);
        }
        return movieResponse;
    }

    private void processMovies(List<Movie> movies, Map<String, Long> directorCount) {
        movies.forEach(movie -> directorCount.merge(movie.getDirector(), 1L, Long::sum));
    }

    private List<String> filterAndSortDirectors(Map<String, Long> directorCount, int threshold) {
        return directorCount.entrySet().stream()
                .filter(entry -> entry.getValue() > threshold)
                .map(Map.Entry::getKey)
                .sorted()
                .toList();
    }
}