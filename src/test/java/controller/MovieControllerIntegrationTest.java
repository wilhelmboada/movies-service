package controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.movies.MovieApplication;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.web.client.RestTemplate;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest(classes = MovieApplication.class)
@AutoConfigureMockMvc
@TestPropertySource(properties = {
        "movies.api.url=http://external-api-url/movies"
})
class MovieControllerIntegrationTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private RestTemplate restTemplate;

    @Autowired
    private ObjectMapper objectMapper;

    @BeforeEach
    public void setUp() {
        String mockResponse = "{\"data\":[{\"Director\":\"Director 1\",\"Title\":\"Title 1\"},{\"Director\":\"Director 2\",\"Title\":\"title 2\"},{\"Director\":\"Director 1\",\"Title\":\"Title 3\"}],\"totalPages\":1}";
        when(restTemplate.getForObject("http://external-api-url/movies?page=1", String.class)).thenReturn(mockResponse);
    }

    @Test
    void testGetDirectors_Success() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/api/directors")
                        .param("threshold", "1")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$").isArray())
                .andExpect(MockMvcResultMatchers.jsonPath("$[0]").value("Director 1"));
    }
}

