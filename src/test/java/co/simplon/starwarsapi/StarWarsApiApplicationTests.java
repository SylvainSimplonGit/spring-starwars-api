package co.simplon.starwarsapi;

import static org.assertj.core.api.Assertions.assertThat;

import java.net.http.HttpResponse;
import java.util.List;

import co.simplon.starwarsapi.model.planet.Planet;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit.jupiter.SpringExtension;

@ExtendWith(SpringExtension.class)
@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
class StarWarsApiApplicationTests {

    @Autowired
    private TestRestTemplate restTemplate;

    @Test
//    Get planet list
//    Test de la requête GET sur /apî/planets
    public void getPlanetsListShouldReturnNotNullList() {
		// When getting on /api/planets
//		List<?> planets = this.restTemplate.getForObject("/api/planets", List.class);
        ResponseEntity<List> responseEntity = this.restTemplate.exchange("/api/planets", HttpMethod.GET, null, List.class);
        List<?> planets = responseEntity.getBody();
        // Then a non null list should be returned
		assertThat(planets).isNotNull();
    }

    @Test
//    Get existing planet
//    Test de la requête GET sur /apî/planets/{planetId} sur un ID de planete présent
    public void getExistingPlanetShouldReturnPlanet() {
        // When getting on /api/planets/{planetId}
        ResponseEntity<Planet> responseEntity = this.restTemplate.exchange("/api/planets/1", HttpMethod.GET, null, Planet.class);
        Planet planet = responseEntity.getBody();
        // Then code 200 should returned
        assertThat(responseEntity.getStatusCode()).isEqualTo(HttpStatus.OK);
        // Then a planet not null should returned
        assertThat(planet).isNotNull();
    }


    @Test
//    Get non existing planet
//    Test de la requête GET sur /apî/planets/{planetId} sur un ID de planete absente
    public void getNotExistingPlanetShouldReturnPlanet() {
        // When getting on /api/planets/{planetId}
        ResponseEntity<Planet> responseEntity = this.restTemplate.exchange("/api/planets/99999999", HttpMethod.GET, null, Planet.class);
        // Then code 200 should returned
        assertThat(responseEntity.getStatusCode()).isEqualTo(HttpStatus.NOT_FOUND);
    }

//    @Test
//    Create planet
//    Test de la requête POST sur /api/planets

//    @Test
//    Update existing planet
//    Test de la requête PUT sur /api/planets/{planetId} sur un ID de planete présent

//    @Test
//    Update non existing planet
//    Test de la requête PUT sur /api/planets/{planetId} sur un ID de planete absente

//    @Test
//    Delete  planet
//    Test de la requête DELETE sur /api/planets/{planetId}


}
