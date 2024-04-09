package sk.tuke.gamestudio.server.webservice;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import sk.tuke.gamestudio.entity.Level;
import sk.tuke.gamestudio.service.LevelService;

@RestController
@RequestMapping("api/level")
public class LevelServiceRest {

    @Autowired
    private LevelService levelService;

    @GetMapping("/getLevel/{level}")
    public Level getLevel(@PathVariable int level) {
        return levelService.getLevel(level);
    }
}
