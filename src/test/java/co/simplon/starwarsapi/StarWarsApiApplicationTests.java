package co.simplon.starwarsapi;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.List;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.test.context.junit.jupiter.SpringExtension;

@ExtendWith(SpringExtension.class)
@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
class StarWarsApiApplicationTests {

    @Autowired
    private TestRestTemplate restTemplate;

    @Test
    public void getPlanets() {
		// When retrieving planets from /api/planets
		List<?> planets = this.restTemplate.getForObject("/api/planets", List.class);

		// Then a non null list should be returned
		assertThat(planets).isNotNull();
    }

}
