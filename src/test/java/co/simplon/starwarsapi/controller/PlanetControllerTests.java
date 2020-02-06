package co.simplon.starwarsapi.controller;

import co.simplon.starwarsapi.model.planet.Planet;
import co.simplon.starwarsapi.repository.PlanetRepository;
import org.junit.jupiter.api.Test;
//import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
//import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.*;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
//import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

//@ExtendWith(SpringExtension.class)
@WebMvcTest
public class PlanetControllerTests {

    @Autowired
    MockMvc mockMvc;

    @MockBean
    PlanetRepository planetRepository;

    @Test
    // Get Planets list from getPlanetList
    public void getPlanetListShouldReturnListNotNull() throws Exception {
        when(this.planetRepository.findAll()).thenReturn(new ArrayList<>());

        this.mockMvc.perform(get("/api/planets")).andExpect(status().isOk());
    }

    @Test
    // Get Planet from getPlanet
    public void getPlanetShouldReturnPlanet() throws Exception {
        when(this.planetRepository.findById(anyLong())).thenReturn(Optional.of(new Planet()));

        this.mockMvc.perform(get("/api/planets/1")).andExpect(status().isOk());
    }

    @Test
    // Get Planet from getPlanet
    public void getNoExistingPlanetShouldReturnNotFound() throws Exception {
        when(this.planetRepository.findById(anyLong())).thenReturn(Optional.empty());

        this.mockMvc.perform(get("/api/planets/1")).andExpect(status().isNotFound());
    }

    @Test
    // Create Planet from createPlanet
    public void postPlanetShouldReturnOK() throws Exception {
        String namePlanet = "Terre";
        Long idPlanet = 1L;

        when(this.planetRepository.save(any())).thenReturn(new Planet(idPlanet, namePlanet));

        this.mockMvc.perform(
                post("/api/planets")
                .contentType(MediaType.APPLICATION_JSON)        // Définit le format de la donnée envoyée
                .content("{\"name\": \"Terre\"}")               // Le JSON contenant la donnée
        )
                .andExpect(status().isOk())
                .andExpect(jsonPath("name").value(namePlanet))
                .andExpect(jsonPath("id").value(idPlanet));
    }

}
