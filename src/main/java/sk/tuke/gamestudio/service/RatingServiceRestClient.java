package sk.tuke.gamestudio.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import sk.tuke.gamestudio.entity.Rating;

import java.util.Objects;

@Service
public class RatingServiceRestClient implements RatingService {

    @Autowired
    private RestTemplate restTemplate;
    private final String url = "http://localhost:8080/api/rating";

    @Override
    public void setRating(Rating rating) throws GameStudioException {
        restTemplate.postForEntity(url+"/setRating", rating, Rating.class);
    }

    @Override
    public int getAverageRating() throws GameStudioException {
        return Objects.requireNonNull(restTemplate.getForObject(url+"/getAverageRating", Integer.class));
    }

    @Override
    public int getRating(String username) throws GameStudioException {
        return Objects.requireNonNull(restTemplate.getForObject(url+"/getRating/"+username, Integer.class));
    }

    @Override
    public void reset() throws GameStudioException {
        throw new UnsupportedOperationException("Not supported via web service");
    }
}
