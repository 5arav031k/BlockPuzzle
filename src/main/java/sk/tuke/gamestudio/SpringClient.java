package sk.tuke.gamestudio;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.WebApplicationType;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.FilterType;
import org.springframework.security.crypto.argon2.Argon2PasswordEncoder;
import org.springframework.web.client.RestTemplate;
import sk.tuke.gamestudio.game.block_puzzle.consoleui.ConsoleUI;
import sk.tuke.gamestudio.game.block_puzzle.consoleui.LevelMenuConsoleUI;
import sk.tuke.gamestudio.game.block_puzzle.consoleui.StartMenuConsoleUI;
import sk.tuke.gamestudio.game.block_puzzle.core.Field;
import sk.tuke.gamestudio.service.*;

@SpringBootApplication
@ComponentScan(excludeFilters = @ComponentScan.Filter(type = FilterType.REGEX,
		pattern = "sk.tuke.gamestudio.server.*"))

public class SpringClient {
	public static void main(String[] args) {
//		SpringApplication.run(SpringClient.class, args);
		new SpringApplicationBuilder(SpringClient.class).web(WebApplicationType.NONE).run(args);
	}

	@Bean
	public CommandLineRunner runner(ConsoleUI ui) {
		return args -> ui.play();
	}
	@Bean
	public ConsoleUI consoleUI() {
		return new ConsoleUI();
	}
	@Bean
	public StartMenuConsoleUI startMenuConsoleUI() {
		return new StartMenuConsoleUI();
	}
	@Bean
	public LevelMenuConsoleUI levelMenuConsoleUI() {
		return new LevelMenuConsoleUI();
	}
	@Bean
	public RestTemplate restTemplate() {
		return new RestTemplate();
	}
	@Bean
	public ScoreService scoreService() {
		return new ScoreServiceRestClient();
	}
	@Bean
	public RatingService ratingService() {
		return new RatingServiceRestClient();
	}
	@Bean
	public CommentService commentService() {
		return new CommentServiceRestClient();
	}
	@Bean
	public UserService userService() {
		return new UserServiceRestClient();
	}
	@Bean
	public LevelService levelService() {
		return new LevelServiceRestClient();
	}
	@Bean
	public Field field() {
		return new Field(5, 4);
	}
	@Bean
	public Argon2PasswordEncoder passwordEncoder() {
		return new Argon2PasswordEncoder(16, 32, 1, 65536, 1);
	}
}
