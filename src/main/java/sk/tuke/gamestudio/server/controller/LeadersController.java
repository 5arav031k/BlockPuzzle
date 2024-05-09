package sk.tuke.gamestudio.server.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.context.WebApplicationContext;
import sk.tuke.gamestudio.entity.Score;
import sk.tuke.gamestudio.service.ScoreService;

import java.text.SimpleDateFormat;
import java.util.List;

@Controller
@Scope(WebApplicationContext.SCOPE_SESSION)
@RequestMapping("/block_puzzle/leaders")
public class LeadersController {
    @Autowired
    private ScoreService scoreService;

    @GetMapping
    public String leaders(Model model) {
        model.addAttribute("htmlLeaders", getHtmlLeaders());
        return "leaders";
    }

    private String getHtmlLeaders() {
        StringBuilder html = new StringBuilder();
        List<Score> scores = scoreService.getTopScores();
        SimpleDateFormat dateFormat = new SimpleDateFormat("HH:mm:ss dd.MM.yyyy");
        for (Score score : scores) {
            int rank = scores.indexOf(score)+1;
            html.append("<tr"+getClassForTR(rank)+">")
                    .append("<td>")
                    .append(rank)
                    .append("</td>")
                    .append("<td>")
                    .append(score.getLogin())
                    .append("</td>")
                    .append("<td>")
                    .append(score.getLevelsCompleted())
                    .append("</td>")
                    .append("<td>")
                    .append(dateFormat.format(score.getCompletedAt()))
                    .append("</td>");
        }
        return html.toString();
    }

    private String getClassForTR(int rank) {
        return switch (rank) {
            case 1 -> " class='gold'";
            case 2 -> " class='silver'";
            case 3 -> " class='bronze'";
            default -> "";
        };
    }
}
