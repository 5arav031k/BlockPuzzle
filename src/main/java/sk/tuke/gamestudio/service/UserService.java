package sk.tuke.gamestudio.service;

import sk.tuke.gamestudio.entity.User;

public interface UserService {
    User addUser(String login, String password) throws GameStudioException;
    User logIn(String login, String password) throws GameStudioException;
    void deleteUser(User user) throws GameStudioException;
}