package main.java.sk.tuke.gamestudio.service;

import main.java.sk.tuke.gamestudio.entity.User;

public interface UserService {
    User addUser(String login, String password);
    User logIn(String login, String password);
    void deleteUser(String login, String password);
}
