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

    @PostMapping("/addUser")
    public User addUser(@RequestBody User user) {
        return userService.addUser(user.getLogin(), user.getPassword());
    }

    @PostMapping("/logIn")
    public User logIn(@RequestBody User user) {
        return userService.logIn(user.getLogin(), user.getPassword());
    }
}
