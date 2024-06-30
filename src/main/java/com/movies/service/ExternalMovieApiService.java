package com.movies.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.movies.errors.ExternalApiException;
import com.movies.model.MovieResponse;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;

@Service
public class ExternalMovieApiService {

    private final RestTemplate restTemplate;
    private final String moviesApiUrl;
    private final ObjectMapper objectMapper;

    public ExternalMovieApiService(RestTemplate restTemplate, @Value("${movies.api.url}") String moviesApiUrl, ObjectMapper objectMapper) {
        this.restTemplate = restTemplate;
        this.moviesApiUrl = moviesApiUrl;
        this.objectMapper = objectMapper;
    }

    public MovieResponse fetchMovies(int page) throws JsonProcessingException {
        String url = String.format("%s?page=%d", moviesApiUrl, page);
        try {
            String response = restTemplate.getForObject(url, String.class);
            return objectMapper.readValue(response, MovieResponse.class);
        } catch (RestClientException e) {
            throw new ExternalApiException("Error occurred when calling external API", e);
        }
    }
}

