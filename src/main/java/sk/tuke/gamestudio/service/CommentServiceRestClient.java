package sk.tuke.gamestudio.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import sk.tuke.gamestudio.entity.Comment;

import java.util.Arrays;
import java.util.List;
import java.util.Objects;

@Service
public class CommentServiceRestClient implements CommentService{
    private final String url = "http://localhost:8080/api/comment";

    @Autowired
    private RestTemplate restTemplate;

    @Override
    public void addComment(Comment comment) throws GameStudioException {
        restTemplate.postForEntity(url+"/addComment", comment, Comment.class);
    }

    @Override
    public List<Comment> getComments() throws GameStudioException {
        return Arrays.asList(Objects.requireNonNull(restTemplate.getForEntity(url + "/getComments", Comment[].class).getBody()));
    }

    @Override
    public void reset() throws GameStudioException {
        throw new UnsupportedOperationException("Not supported via web service");
    }
}
