package sk.tuke.gamestudio.server.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.context.WebApplicationContext;
import sk.tuke.gamestudio.entity.User;
import sk.tuke.gamestudio.service.GameStudioException;
import sk.tuke.gamestudio.service.UserService;

@Controller
@Scope(WebApplicationContext.SCOPE_SESSION)
@RequestMapping("/block_puzzle")
public class UserController {

    @Autowired
    private UserService userService;

    @GetMapping("/signUp")
    public String signUpPage() {
        return "sign_up";
    }

    @PostMapping("/signUp")
    public String signUp(@RequestParam String username, @RequestParam String password, Model model) {
        try {
            User user = userService.addUser(username, password);
            model.addAttribute("User", user);
            return "redirect:/block_puzzle";
        } catch (GameStudioException e) {
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
    public String login(@RequestParam String username, @RequestParam String password, Model model) {
        try {
            User user = userService.logIn(username, password);
            model.addAttribute("User", user);
            return "redirect:/block_puzzle";
        } catch (GameStudioException e) {
            if (e.getMessage().equals("Bad login or password!"))
                model.addAttribute("login_error", e.getMessage());
            else
                model.addAttribute("login_error", "Error logging in!");
            return "login";
        }
    }
}
