package controller;

import com.movies.controller.MovieController;
import com.movies.controller.RestExceptionHandler;
import com.movies.errors.ExternalApiException;
import com.movies.service.MovieService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.Arrays;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(SpringExtension.class)
class MovieControllerTest {

    private MockMvc mockMvc;

    @MockBean
    private MovieService movieService;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.openMocks(this);
        this.mockMvc = MockMvcBuilders.standaloneSetup(new MovieController(movieService))
                .setControllerAdvice(new RestExceptionHandler())
                .build();
    }

    @Test
    void testGetDirectors_Success() throws Exception {
        int threshold = 1;
        when(movieService.getDirectors(threshold)).thenReturn(Arrays.asList("Director 1", "Director 2"));

        mockMvc.perform(get("/api/directors")
                        .param("threshold", String.valueOf(threshold))
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0]").value("Director 1"))
                .andExpect(jsonPath("$[1]").value("Director 2"));

        verify(movieService, times(1)).getDirectors(threshold);
    }

    @Test
    void testGetDirectors_ExternalApiException() throws Exception {
        int threshold = 1;
        when(movieService.getDirectors(threshold)).thenThrow(new ExternalApiException("External API error"));

        mockMvc.perform(get("/api/directors")
                        .param("threshold", String.valueOf(threshold))
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadGateway())
                .andExpect(jsonPath("$.message").value("External API error"))
                .andExpect(jsonPath("$.details").exists());

        verify(movieService, times(1)).getDirectors(threshold);
    }
}
