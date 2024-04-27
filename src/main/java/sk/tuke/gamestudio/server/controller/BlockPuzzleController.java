package sk.tuke.gamestudio.server.controller;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.context.WebApplicationContext;
import sk.tuke.gamestudio.entity.Level;
import sk.tuke.gamestudio.game.block_puzzle.core.*;

import javax.servlet.http.HttpSession;
import java.util.List;

@Controller
@Scope(WebApplicationContext.SCOPE_SESSION)
@RequestMapping("/block_puzzle")
public class BlockPuzzleController {

    private final Field field = new Field(5, 4);
    private Level level;
    private Shape currentShape;
    private int currentShapeIndex;
    private boolean shapeIsMarked;

    @GetMapping
    public String blockPuzzle() {
        return "start_menu";
    }

    @GetMapping("/game_menu")
    public String gameMenu() {
        return "game_menu";
    }

    @GetMapping("/play")
    public String playPage(HttpSession session, Model model) {
        if (field.isSolved())
            return null;
        level = (Level) session.getAttribute("level");
        if (level == null)
            return "redirect:/block_puzzle/level_menu";

        addShapesToMap();
        model.addAttribute("htmlField", getHtmlField());
        model.addAttribute("htmlShapes", getHtmlShapes());
        session.setAttribute("level", level);
        return "play";
    }

    @PostMapping("/play/placeShapeOnField")
    public String placeShapeOnField(@RequestParam int shapeNumber) {
        currentShape = level.getShapes().get(shapeNumber-1);
        currentShapeIndex = level.getShapes().indexOf(currentShape)+1;
        currentShape.placeShapeToField();
        return "redirect:/block_puzzle/play";
    }

    private String getHtmlField() {
        StringBuilder html = new StringBuilder();
        for (int col = 3; col < 3+field.getFieldHeight(); col++) {
            html.append("<tr>");
            for (int row = 8; row < 8+field.getFieldWidth()*2; row+=2) {
                String value = field.getMap()[row][col].getValue();
                html.append("<td"+getId(value)+">");
                String buttonType = getButtonType(value);
                if (buttonType != null) {
                    html.append("<button type=").append(buttonType).append("></button>");
                }
                html.append("</td>");
            }
            html.append("</tr>");
        }
        return html.toString();
    }

    private String getHtmlShapes() {
        StringBuilder html = new StringBuilder();
        for (int col = 1; col < field.getMapHeight() - 1; col++) {
            html.append("<tr>");
            appendHtmlForRange(html, col, 28, 36);
            html.append("<td></td>");
            appendHtmlForRange(html, col, 42, 48);
            html.append("</tr>");
        }
        return html.toString();
    }

    private void appendHtmlForRange(StringBuilder html, int col, int startRow, int endRow) {
        for (int row = startRow; row < endRow; row += 2) {
            String value = field.getMap()[row][col].getValue();
            html.append("<td").append(getId(value)).append(">");
            String buttonType = getButtonType(value);
            if (buttonType != null) {
                html.append("<button type=").append(buttonType).append("></button>");
            }
            html.append("</td>");
        }
    }


    private String getId(String cell) {
        String color = Color.getStringByColor(cell);
        if (color == null)
            return "";

        return " id="+color;
    }

    private String getButtonType(String cell) {
        int shapeNumber = -1;
        for (Shape shape : level.getShapes()) {
            if (shape.getShapeColor().equals(cell)) {
                shapeNumber = shape.getShapeNumber();
                break;
            }
        }
        if (shapeNumber == -1)
            return null;

        return "'submit' name='shapeNumber' value='"+shapeNumber+"'";
    }

    private void addShapesToMap() {
        List<Shape> shapes = level.getShapes();
        for (Shape shape : shapes) {
            for (ShapeTile shapeTile : shape.getShape()) {
                int tileX = shapeTile.getX();
                int tileY = shapeTile.getY();
                if (!field.getMap()[tileX][tileY].getValue().equals(" ")
                        && tileX >= 8 && tileX <= (7+field.getFieldWidth()*2)
                        && tileY >= 3 && tileY <= (2+field.getFieldHeight()))
                {
                    field.getMap()[tileX][tileY].setValue(Color.MARKED);
                    shapeIsMarked = true;
                }
                else
                    field.getMap()[shapeTile.getX()][shapeTile.getY()].setValue(shape.getShapeColor());
            }
        }
    }
}
