package sk.tuke.gamestudio.service;

import sk.tuke.gamestudio.entity.Comment;
import sk.tuke.gamestudio.entity.User;

import java.util.List;

public interface CommentService {
    void addComment(Comment comment) throws GameStudioException;
    List<Comment> getComments(User user) throws GameStudioException;
    void reset(User user) throws GameStudioException;
}
