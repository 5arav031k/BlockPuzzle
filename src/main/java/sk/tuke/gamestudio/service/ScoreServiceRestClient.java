package sk.tuke.gamestudio.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.client.RestTemplate;
import sk.tuke.gamestudio.entity.Score;

import java.util.Arrays;
import java.util.List;
import java.util.Objects;

public class ScoreServiceRestClient implements ScoreService{
    private final String url = "http://localhost:8080/api/score";

    @Autowired
    private RestTemplate restTemplate;

    @Override
    public void addCompletedLevel(Score score, int level) throws GameStudioException {
        restTemplate.postForEntity(url+"/addCompletedLevel/"+level, score, Score.class);
    }

    @Override
    public void addScore(String username) throws GameStudioException {
        restTemplate.postForObject(url+"/addScore", username, String.class);
    }

    @Override
    public Score getScore(String username) throws GameStudioException {
        return restTemplate.getForEntity(url+"/getScore/"+username, Score.class).getBody();
    }

    @Override
    public List<Score> getTopScores() throws GameStudioException {
        return Arrays.asList(Objects.requireNonNull(restTemplate.getForEntity(url + "/getTopScores", Score[].class).getBody()));
    }

    @Override
    public void reset() throws GameStudioException {
        throw new UnsupportedOperationException("Not supported via web service");
    }
}
