package sk.tuke.gamestudio.server.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.context.WebApplicationContext;
import sk.tuke.gamestudio.entity.Level;
import sk.tuke.gamestudio.entity.Score;
import sk.tuke.gamestudio.game.block_puzzle.core.Field;
import sk.tuke.gamestudio.service.GameStudioException;
import sk.tuke.gamestudio.service.LevelService;

import javax.servlet.http.HttpSession;

@Controller
@Scope(WebApplicationContext.SCOPE_SESSION)
@RequestMapping("/block_puzzle/level_menu")
public class LevelController {
    @Autowired
    private LevelService levelService;
    private int completedLevels;

    @GetMapping
    public String levelMenuPage(HttpSession session, Model model) {
        session.removeAttribute("level");
        session.removeAttribute("field");
        try {
            completedLevels = ((Score) session.getAttribute("userScore")).getLevelsCompleted();
            model.addAttribute("htmlLevels", getHtmlLevels());
            return "level_menu";
        } catch (Exception e) {
            return "redirect:/block_puzzle/login";
        }
    }

    @PostMapping
    public String levelMenu(@RequestParam int levelID, HttpSession session) {
        session.removeAttribute("level");
        session.removeAttribute("field");
        try {
            Level level = levelService.getLevel(levelID);
            session.setAttribute("level", level);
            session.setAttribute("field", new Field(5, 4));
            return "redirect:/block_puzzle/play";
        } catch (GameStudioException e) {
            return "level_menu";
        }
    }

    private String getHtmlLevels() {
        StringBuilder htmlLevels = new StringBuilder();
        htmlLevels.append("<tr>");
        for (int level = 1; level <= 6; level++) {
            htmlLevels.append("<td id="+getId(level)+">")
                    .append("<button type='"+getButtonType(level)+"' name='levelID' value='"+level+"'>")
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

    private String getButtonType(int level) {
        return completedLevels + 1 >= level ? "submit" : "button";
    }
}
