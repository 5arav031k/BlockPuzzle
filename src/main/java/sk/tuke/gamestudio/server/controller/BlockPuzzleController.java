package sk.tuke.gamestudio.server.controller;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.context.WebApplicationContext;
import sk.tuke.gamestudio.game.block_puzzle.core.Field;

@Controller
@Scope(WebApplicationContext.SCOPE_SESSION)
@RequestMapping("/block_puzzle")
public class BlockPuzzleController {
    private Field field = new Field(5, 4);

    @GetMapping
    public String blockPuzzle() {
        return "start_menu";
    }

    @GetMapping("/game_menu")
    public String newGame() {
        return "game_menu";
    }

    private String getHtmlField() {
        StringBuilder html = new StringBuilder();
        html.append("<table class='block_puzzle_field'>\n");
        for (int col = 0; col < field.getMapHeight(); col++) {
            html.append("<tr>\n");
            for (int row = 0; row < field.getMapWidth(); row++) {
                html.append(String.format("<td>%s</td>\n", field.getMap()[row][col].getValue()));
            }
            html.append("</tr>\n");
        }
        html.append("</table>\n");

        return html.toString();
    }

    private void prepareModel(Model model) {
        model.addAttribute("field", field);
        model.addAttribute("htmlField", getHtmlField());
    }
}
