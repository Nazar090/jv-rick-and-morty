package mate.academy.rickandmorty.service;

import lombok.AllArgsConstructor;
import mate.academy.rickandmorty.model.Character;
import mate.academy.rickandmorty.repository.CharacterRepository;
import org.json.JSONArray;
import org.json.JSONObject;
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

    private CharacterRepository characterRepository;

    @Override
    public void run(String... args) throws IOException, InterruptedException {
        HttpClient httpClient = HttpClient.newHttpClient();

        HttpRequest httpRequest = HttpRequest.newBuilder()
                .GET()
                .uri(URI.create(URL))
                .build();
        HttpResponse<String> response = httpClient
                .send(httpRequest, HttpResponse.BodyHandlers.ofString());

        JSONObject jsonResponse = new JSONObject(response.body());
        JSONArray resultsArray = jsonResponse.getJSONArray("results");

        List<Character> characters = new ArrayList<>();

        for (int i = 0; i < resultsArray.length(); i++) {
            JSONObject characterJson = resultsArray.getJSONObject(i);

            Character character = new Character();
            character.setExternalId(String.valueOf(characterJson.getInt("id")));
            character.setName(characterJson.getString("name"));
            character.setStatus(characterJson.getString("status"));
            character.setGender(characterJson.getString("gender"));

            characters.add(character);
        }
        characterRepository.saveAll(characters);
    }
}
