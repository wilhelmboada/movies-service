package service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.movies.model.Movie;
import com.movies.model.MovieResponse;
import com.movies.service.ExternalMovieApiService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.web.client.RestTemplate;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(SpringExtension.class)
@TestPropertySource(properties = {
        "movies.api.url=http://example.com/api/movies"
})
class ExternalMovieApiServiceTest {

    @Mock
    private RestTemplate restTemplate;

    @Mock
    private ObjectMapper objectMapper;

    @InjectMocks
    private ExternalMovieApiService externalMovieApiService;

    @Value("${movies.api.url}")
    private String moviesApiUrl;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
        externalMovieApiService = new ExternalMovieApiService(restTemplate, moviesApiUrl, objectMapper);
        when(objectMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false)).thenReturn(objectMapper);
    }

    @Test
    void testFetchMovies_Success() throws JsonProcessingException {
        int page = 1;
        String url = String.format("%s?page=%d", moviesApiUrl, page);
        String jsonResponse = "{\"totalPages\":1,\"data\":[{\"title\":\"Title\",\"director\":\"Director 1\"}]}";
        MovieResponse expectedResponse = new MovieResponse();
        expectedResponse.setTotalPages(1);
        expectedResponse.setData(List.of(new Movie("Title", "Director 1")));

        when(restTemplate.getForObject(url, String.class)).thenReturn(jsonResponse);
        when(objectMapper.readValue(jsonResponse, MovieResponse.class)).thenReturn(expectedResponse);

        MovieResponse actualResponse = externalMovieApiService.fetchMovies(page);

        assertEquals(expectedResponse, actualResponse);
        verify(restTemplate, times(1)).getForObject(url, String.class);
        verify(objectMapper, times(1)).readValue(jsonResponse, MovieResponse.class);
    }

    @Test
    void testFetchMovies_JsonProcessingException() throws JsonProcessingException {
        int page = 1;
        String url = String.format("%s?page=%d", moviesApiUrl, page);
        String jsonResponse = "invalid json";

        when(restTemplate.getForObject(url, String.class)).thenReturn(jsonResponse);
        when(objectMapper.readValue(jsonResponse, MovieResponse.class)).thenThrow(new JsonProcessingException("Error") {});

        assertThrows(JsonProcessingException.class, () -> externalMovieApiService.fetchMovies(page));
        verify(restTemplate, times(1)).getForObject(url, String.class);
        verify(objectMapper, times(1)).readValue(jsonResponse, MovieResponse.class);
    }

    @Test
    void testFetchMovies_NullResponse() throws JsonProcessingException {
        int page = 1;
        String url = String.format("%s?page=%d", moviesApiUrl, page);

        when(restTemplate.getForObject(url, String.class)).thenReturn(null);

        MovieResponse actualResponse = externalMovieApiService.fetchMovies(page);

        assertNull(actualResponse);
        verify(restTemplate, times(1)).getForObject(url, String.class);
        verify(objectMapper, times(0)).readValue(anyString(), eq(MovieResponse.class));
    }
}


