package service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.movies.model.Movie;
import com.movies.model.MovieResponse;
import com.movies.service.ExternalMovieApiService;
import com.movies.service.MovieService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class MovieServiceTest {

    @Mock
    private ExternalMovieApiService externalMovieApiService;

    @InjectMocks
    private MovieService movieService;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testGetDirectors_Success() throws JsonProcessingException {
        int threshold = 1;
        int page = 1;
        MovieResponse movieResponsePage1 = new MovieResponse();
        movieResponsePage1.setTotalPages(2);
        movieResponsePage1.setData(List.of(
                new Movie("Title 1", "Director 1"),
                new Movie("Title 2", "Director 2")
        ));

        MovieResponse movieResponsePage2 = new MovieResponse();
        movieResponsePage2.setTotalPages(2);
        movieResponsePage2.setData(List.of(
                new Movie("Title 3", "Director 1"),
                new Movie("Title 4", "Director 3")
        ));

        when(externalMovieApiService.fetchMovies(page)).thenReturn(movieResponsePage1);
        when(externalMovieApiService.fetchMovies(page + 1)).thenReturn(movieResponsePage2);

        List<String> directors = movieService.getDirectors(threshold);

        assertEquals(List.of("Director 1"), directors);
        verify(externalMovieApiService, times(1)).fetchMovies(page);
        verify(externalMovieApiService, times(1)).fetchMovies(page + 1);
    }

    @Test
    void testGetDirectors_Threshold() throws JsonProcessingException {
        int threshold = 2;
        int page = 1;
        MovieResponse movieResponse = new MovieResponse();
        movieResponse.setTotalPages(1);
        movieResponse.setData(List.of(
                    new Movie("Title 1", "Director 1"),
                new Movie("Title 2", "Director 1"),
                new Movie("Title 3", "Director 2"),
                new Movie("Title 4", "Director 1")
        ));

        when(externalMovieApiService.fetchMovies(page)).thenReturn(movieResponse);

        List<String> directors = movieService.getDirectors(threshold);

        assertEquals(List.of("Director 1"), directors);
        verify(externalMovieApiService, times(1)).fetchMovies(page);
    }
}

