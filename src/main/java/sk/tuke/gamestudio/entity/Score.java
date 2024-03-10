package main.java.sk.tuke.gamestudio.entity;

import java.util.Date;

public record Score(String login, int levelsCompleted, Date date) {
}
