package sk.tuke.gamestudio.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import sk.tuke.gamestudio.entity.Level;

@Service
public class LevelServiceRestClient implements LevelService {
    private final String url = "http://localhost:8080/api/level";

    @Autowired
    private RestTemplate restTemplate;

    @Override
    public Level getLevel(int levelID) throws GameStudioException {
        return restTemplate.getForEntity(url+"/getLevel/"+levelID, Level.class).getBody();
    }
}
