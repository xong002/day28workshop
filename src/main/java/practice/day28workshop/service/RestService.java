package practice.day28workshop.service;

import java.util.List;

import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import practice.day28workshop.model.Game;
import practice.day28workshop.model.GameWithRating;

@Service
public class RestService {

    RestTemplate restTemplate = new RestTemplate();

    public List<Game> getGameList() {
        ResponseEntity<List<Game>> result = restTemplate.exchange("http://localhost:8080/api/games", HttpMethod.GET,
                new HttpEntity<String>("parameters"), new ParameterizedTypeReference<List<Game>>() {
                });
        return result.getBody();
    }

    public List<GameWithRating> getGameListSorted() {
        ResponseEntity<List<GameWithRating>> result = restTemplate.exchange("http://localhost:8080/api/games/highest",
                HttpMethod.GET, new HttpEntity<String>("parameters"),
                new ParameterizedTypeReference<List<GameWithRating>>() {
                });
        return result.getBody();
    }
}
