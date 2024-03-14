package main.java.sk.tuke.gamestudio.service;

import main.java.sk.tuke.gamestudio.entity.Comment;
import main.java.sk.tuke.gamestudio.entity.User;

import java.util.List;

public interface CommentService {
    void addComment(Comment comment) throws GameStudioException;
    List<Comment> getComments(User user) throws GameStudioException;
    void reset() throws GameStudioException;
}
