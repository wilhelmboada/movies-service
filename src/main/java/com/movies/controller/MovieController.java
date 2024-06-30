package com.movies.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.movies.service.MovieService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class MovieController {

    private final MovieService movieService;

    public MovieController(MovieService movieService) {
        this.movieService = movieService;
    }

    @GetMapping("/api/directors")
    public ResponseEntity<List<String>> getDirectors(@RequestParam int threshold) throws JsonProcessingException {
        List<String> directors = movieService.getDirectors(threshold);
        return ResponseEntity.ok(directors);
    }
}
