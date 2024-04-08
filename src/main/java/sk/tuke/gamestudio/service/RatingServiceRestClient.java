package sk.tuke.gamestudio.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.client.RestTemplate;
import sk.tuke.gamestudio.entity.Rating;

public class RatingServiceRestClient implements RatingService{
    private final String url = "http://localhost:8080/api/rating";
    @Autowired
    private RestTemplate restTemplate;

    @Override
    public void setRating(Rating rating) throws GameStudioException {
        restTemplate.postForEntity(url+"/setRating", rating, Rating.class);
    }

    @Override
    public int getAverageRating() throws GameStudioException {
        return restTemplate.getForObject(url+"/averageRating", Integer.class);
    }

    @Override
    public int getRating(String username) throws GameStudioException {
        return restTemplate.getForObject(url+"/getRating/"+username, Integer.class);
    }

    @Override
    public void reset() throws GameStudioException {
        throw new UnsupportedOperationException("Not supported via web service");
    }
}
