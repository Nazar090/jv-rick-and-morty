package mate.academy.rickandmorty.service;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.AllArgsConstructor;
import mate.academy.rickandmorty.model.Character;
import mate.academy.rickandmorty.repository.CharacterRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;
import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.ArrayList;
import java.util.List;

@Component
@AllArgsConstructor
public class DataInitializer implements CommandLineRunner {
    private static final String URL = "https://rickandmortyapi.com/api/character";
    private final CharacterRepository characterRepository;
    private final ObjectMapper objectMapper;

    @Override
    public void run(String... args) throws IOException, InterruptedException {
        HttpClient httpClient = HttpClient.newHttpClient();
        String nextPageUrl = URL;

        while (nextPageUrl != null) {
            HttpRequest httpRequest = HttpRequest.newBuilder()
                    .GET()
                    .uri(URI.create(nextPageUrl))
                    .build();
            HttpResponse<String> response = httpClient
                    .send(httpRequest, HttpResponse.BodyHandlers.ofString());

            JsonNode rootNode = objectMapper.readTree(response.body());
            List<Character> characters = new ArrayList<>();
            JsonNode results = rootNode.get("results");

            for (JsonNode result : results) {
                Character character = new Character();
                character.setExternalId(result.get("id").asText());
                character.setName(result.get("name").asText());
                character.setGender(result.get("gender").asText());
                character.setStatus(result.get("status").asText());

                characters.add(character);
            }
            characterRepository.saveAll(characters);
            nextPageUrl = rootNode.get("info").get("next").asText(null);
        }
    }
}
