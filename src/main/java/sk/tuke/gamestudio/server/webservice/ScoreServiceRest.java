package sk.tuke.gamestudio.server.webservice;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import sk.tuke.gamestudio.entity.Score;
import sk.tuke.gamestudio.service.ScoreService;

import java.util.List;

@RestController
@RequestMapping("/api/score")
public class ScoreServiceRest {

    @Autowired
    private ScoreService scoreService;

    @PostMapping("/addCompletedLevel/{level}")
    public void addCompletedLevel(@RequestBody Score score, @PathVariable int level) {
        scoreService.addCompletedLevel(score, level);
    }

    @PostMapping("/addScore/{username}")
    public void addScore(@PathVariable String username) {
        scoreService.addScore(username);
    }

    @GetMapping("/getScore/{username}")
    public Score getScore(@PathVariable String username) {
        return scoreService.getScore(username);
    }

    @GetMapping("/getTopScores")
    public List<Score> getTopScores() {
        return scoreService.getTopScores();
    }
}
