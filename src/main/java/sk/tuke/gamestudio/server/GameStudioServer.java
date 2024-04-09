package sk.tuke.gamestudio.server;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.FilterType;
import org.springframework.security.crypto.argon2.Argon2PasswordEncoder;
import sk.tuke.gamestudio.service.*;

@SpringBootApplication
@Configuration
@EntityScan("sk.tuke.gamestudio.entity")
@ComponentScan(includeFilters = @ComponentScan.Filter(type = FilterType.REGEX,
        pattern = "sk.tuke.gamestudio.service"))
public class GameStudioServer {
    public static void main(String[] args) {
        SpringApplication.run(GameStudioServer.class, args);
    }

    @Bean
    public ScoreService scoreService() {
        return new ScoreServiceJPA();
    }
    @Bean
    public CommentService commentService() {
        return new CommentServiceJPA();
    }
    @Bean
    public UserService userService() {
        return new UserServiceJPA();
    }
    @Bean
    public RatingService ratingService() {
        return new RatingServiceJPA();
    }
    @Bean
    public LevelService levelService() {
        return new LevelServiceJPA();
    }
    @Bean
    public Argon2PasswordEncoder passwordEncoder() {
        return new Argon2PasswordEncoder(16, 32, 1, 65536, 1);
    }
}