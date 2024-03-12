package main.java.sk.tuke.gamestudio.service;

import main.java.sk.tuke.gamestudio.entity.User;

public interface CommentService {
    void addComment(User user, String comment);
    void reset();
}
