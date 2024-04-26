package sk.tuke.gamestudio.server.controller;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.context.WebApplicationContext;
import sk.tuke.gamestudio.entity.Score;

import javax.servlet.http.HttpSession;

@Controller
@Scope(WebApplicationContext.SCOPE_SESSION)
@RequestMapping("/block_puzzle/level_menu")
public class LevelController {
    private int completedLevels;

    @GetMapping
    public String levelMenuPage(HttpSession session, Model model) {
        completedLevels = ((Score) session.getAttribute("userScore")).getLevelsCompleted();
        model.addAttribute("htmlLevels", getHtmlLevels());
        return "level_menu";
    }

    private String getHtmlLevels() {
        StringBuilder htmlLevels = new StringBuilder();
        htmlLevels.append("<tr>");
        for (int level = 1; level <= 6; level++) {
            htmlLevels.append("<td id="+getId(level)+">")
                    .append("<button type='button'"+getUrl(level)+">")
                    .append("Level"+level)
                    .append("</button>")
                    .append("</td>");
            if (level == 3) {
                htmlLevels.append("</tr>")
                        .append("<tr>");
            }
        }
        htmlLevels.append("</tr>");
        return htmlLevels.toString();
    }

    private String getId(int level) {
        if (completedLevels+1 > level)
            return "completed-level";
        else if (completedLevels+1 == level)
            return "open-level";

        return "closed-level";
    }

    private String getUrl(int level) {
        return completedLevels + 1 >= level ? " onclick=\"location.href='/block_puzzle/play'\"" : "";
    }
}
