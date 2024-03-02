package main.java.sk.tuke.gamestudio.entity;

public class User {
    private final String login;
    private int levelsCompleted;

    public User(String login, int levelsCompleted) {
        this.login = login;
        this.levelsCompleted = levelsCompleted;
    }

    public String getLogin() {
        return login;
    }
    public int getLevelsCompleted() {
        return levelsCompleted;
    }
    public void setLevelsCompleted(int levelsCompleted) {
        this.levelsCompleted = levelsCompleted;
    }
}
