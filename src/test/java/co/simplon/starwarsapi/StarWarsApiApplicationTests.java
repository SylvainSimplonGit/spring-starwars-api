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
import org.springframework.http.HttpEntity;
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
        ResponseEntity<Planet> responseEntity = this.restTemplate.exchange("/api/planets/{PlanetId}", HttpMethod.GET, null, Planet.class, "1");
        Planet planet = responseEntity.getBody();
        // Then code 200 should returned
        assertThat(responseEntity.getStatusCode()).isEqualTo(HttpStatus.OK);
        // Then a planet not null should returned
        assertThat(planet).isNotNull();
    }

    @Test
//    Get non existing planet
//    Test de la requête GET sur /apî/planets/{planetId} sur un ID de planete absente
    public void getNotExistingPlanetShouldReturnNotFound() {
        // When getting on /api/planets/{planetId}
        ResponseEntity responseEntity = this.restTemplate.exchange("/api/planets/{PlanetId}", HttpMethod.GET, null, Planet.class, "9999999");
        // Then code 200 should returned
        assertThat(responseEntity.getStatusCode()).isEqualTo(HttpStatus.NOT_FOUND);
    }

    @Test
//    Create planet
//    Test de la requête POST sur /api/planets
    public void createNewPlanetShouldReturnOk() {
        // When putting on /api/planets/
        String planetName = "Terre";
        Planet planet = new Planet(planetName);

        HttpEntity<Planet> planetHttpEntity = new HttpEntity<>(planet, null);

//        ResponseEntity<Planet> responseEntity = this.restTemplate.exchange("/api/planets/", HttpMethod.POST, null, Planet.class, planet);
        ResponseEntity<Planet> responseEntity = this.restTemplate.postForEntity("/api/planets", planetHttpEntity, Planet.class);
        Planet createdPlanet = responseEntity.getBody();

        // Then code 200 should returned
        assertThat(responseEntity.getStatusCode()).isEqualTo(HttpStatus.OK);
        // Then Id Planet created should greater than 0
        assertThat(createdPlanet.getId()).isPositive();
        // Then Name Planet created is equal than new Planet
        assertThat(createdPlanet.getName()).isEqualTo(planetName);
    }

    @Test
//    Update existing planet
//    Test de la requête PUT sur /api/planets/{planetId} sur un ID de planete présent
    public void updateExistingPlanet() {
        // Given an existing valid Planet (with name modified)
        String newName = "Alderaaaaaaaaaan";
        Long newId = 2L;
        Planet existingPlanet = new Planet(newId, newName);

        HttpEntity<Planet> planetHttpEntity = new HttpEntity<>(existingPlanet, null);

        // When putting this existing planet to /api/planet/1
        ResponseEntity<Planet> responseEntity = this.restTemplate.exchange("/api/planets/{planetId}", HttpMethod.PUT, planetHttpEntity, Planet.class, newId);
        Planet updatedPlanet = responseEntity.getBody();

        // Then OK status code should be sent back and
        // the updated planet should be returned and should have the same ID and an updated name.
        assertThat(responseEntity.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(updatedPlanet.getId()).isEqualTo(newId);
        assertThat(updatedPlanet.getName()).isEqualTo(newName);
    }

    @Test
//    Update non existing planet
//    Test de la requête PUT sur /api/planets/{planetId} sur un ID de planete absente
    public void updateNonExistingPlanet() {
        // Given a non existing Planet
        String planetName = "NonExistingPlanet";
        Planet nonExistingPlanet = new Planet(planetName);

        HttpEntity<Planet> planetHttpEntity = new HttpEntity<>(nonExistingPlanet, null);

        // When putting this planet to /api/planet/2000
        ResponseEntity<Planet> responseEntity = this.restTemplate.exchange("/api/planets/{planetId}", HttpMethod.PUT, planetHttpEntity, Planet.class, 2000L);

        // Then NOT_FOUND status code should be sent back.
        assertThat(responseEntity.getStatusCode()).isEqualTo(HttpStatus.NOT_FOUND);
    }

    @Test
//    Delete  planet
//    Test de la requête DELETE sur /api/planets/{planetId}
    public void deletePlanet() {

        String planetName = "Terre";
        Long planetId = 9999L;
        Planet planet = new Planet(planetId, planetName);

        HttpEntity<Planet> planetHttpEntity = new HttpEntity<>(planet, null);

        ResponseEntity<Planet> responseEntityCreate = this.restTemplate.postForEntity("/api/planets", planetHttpEntity, Planet.class);

        // When deleting to /api/planet/9999
        ResponseEntity<Planet> responseEntity = this.restTemplate.exchange("/api/planets/{planetId}", HttpMethod.DELETE, null, Planet.class, planetId);

        // Then NO_CONTENT status code should be sent back.
        assertThat(responseEntity.getStatusCode()).isEqualTo(HttpStatus.NO_CONTENT);
    }

}
