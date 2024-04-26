package sk.tuke.gamestudio.server.controller;

import lombok.Getter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.context.WebApplicationContext;
import sk.tuke.gamestudio.entity.Score;
import sk.tuke.gamestudio.entity.User;
import sk.tuke.gamestudio.service.GameStudioException;
import sk.tuke.gamestudio.service.ScoreService;
import sk.tuke.gamestudio.service.UserService;

import javax.servlet.http.HttpSession;

@Controller
@Scope(WebApplicationContext.SCOPE_SESSION)
@RequestMapping("/block_puzzle")
public class UserController {

    @Autowired
    private UserService userService;
    @Autowired
    private ScoreService scoreService;
    private User user;

    @GetMapping("/signUp")
    public String signUpPage() {
        return "sign_up";
    }

    @PostMapping("/signUp")
    public String signUp(@RequestParam String username, @RequestParam String password, Model model, HttpSession session) {
        try {
            user = userService.addUser(username, password);
            scoreService.addScore(username);
            Score score = scoreService.getScore(username);
            session.setAttribute("user", user);
            session.setAttribute("userScore", score);
            return "redirect:/block_puzzle/game_menu";
        } catch (GameStudioException e) {
            user = null;
            if (e.getMessage().equals("This login is already taken!"))
                model.addAttribute("signUp_error", e.getMessage());
            else
                model.addAttribute("signUp_error", "Error signing up");
            return "sign_up";
        }
    }

    @GetMapping("/login")
    public String loginPage() {
        return "login";
    }

    @PostMapping("/login")
    public String login(@RequestParam String username, @RequestParam String password, Model model, HttpSession session) {
        try {
            user = userService.logIn(username, password);
            Score score = scoreService.getScore(username);
            session.setAttribute("user", user);
            session.setAttribute("userScore", score);
            return "redirect:game_menu";
        } catch (GameStudioException e) {
            user = null;
            if (e.getMessage().equals("Bad login or password!"))
                model.addAttribute("login_error", e.getMessage());
            else
                model.addAttribute("login_error", "Error logging in!");
            return "login";
        }
    }

    public boolean isUserLoggedIn() {
        return user != null;
    }
}
