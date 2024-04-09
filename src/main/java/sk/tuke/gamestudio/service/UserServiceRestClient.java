package sk.tuke.gamestudio.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import sk.tuke.gamestudio.entity.User;

@Service
public class UserServiceRestClient implements UserService {
    private final String url = "http://localhost:8080/api/user";

    @Autowired
    private RestTemplate restTemplate;

    @Override
    public User addUser(String login, String password) throws GameStudioException {
        return restTemplate.postForEntity(url+"/addUser", new User(login, password), User.class).getBody();
    }

    @Override
    public User logIn(String login, String password) throws GameStudioException {
        return restTemplate.postForEntity(url+"/logIn", new User(login, password), User.class).getBody();
    }

    @Override
    public void deleteUser(User user) throws GameStudioException {
        throw new UnsupportedOperationException("Not supported via web service");
    }
}
