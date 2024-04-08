package sk.tuke.gamestudio.server.webservice;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import sk.tuke.gamestudio.entity.User;
import sk.tuke.gamestudio.service.UserService;

@RestController
@RequestMapping("api/user")
public class UserServiceRest {
    @Autowired
    private UserService userService;

    @PostMapping("/addUser/{login}")
    public User addUser(@PathVariable String login, @RequestBody String password) {
        return userService.addUser(login, password);
    }

    @PostMapping("/logIn/{login}")
    public User logIn(@PathVariable String login, @RequestBody String password) {
        return userService.logIn(login, password);
    }
}
