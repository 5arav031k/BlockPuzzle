package sk.tuke.gamestudio.server.controller;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.context.WebApplicationContext;
import sk.tuke.gamestudio.entity.Level;
import sk.tuke.gamestudio.entity.User;
import sk.tuke.gamestudio.game.block_puzzle.core.*;

import javax.servlet.http.HttpSession;
import java.util.List;

@Controller
@Scope(WebApplicationContext.SCOPE_SESSION)
@RequestMapping("/block_puzzle")
public class BlockPuzzleController {
    private Field field;
    private Level level;
    private Shape currentShape;
    private int currentShapeIndex = 0;
    private boolean shapeIsMarked = false;

    @GetMapping
    public String blockPuzzle() {
        return "start_menu";
    }

    @GetMapping("/game_menu")
    public String gameMenu(HttpSession session) {
        User user = (User) session.getAttribute("user");
        if (user == null) {
            return "redirect:/block_puzzle";
        }
        return "game_menu";
    }

    @PostMapping("/exit")
    public String exit(HttpSession session) {
        session.invalidate();
        return "redirect:/block_puzzle";
    }

    @GetMapping("/play")
    public String playPage(HttpSession session, Model model) {
        level = (Level) session.getAttribute("level");
        field = (Field) session.getAttribute("field");
        if (level == null || field == null)
            return "redirect:/block_puzzle/level_menu";

        addShapesToMap();
        model.addAttribute("htmlField", getHtmlField());
        model.addAttribute("htmlShapes", getHtmlShapes());
        model.addAttribute("isSolved", field.isSolved());
        shapeIsMarked = false;
        field.clearMap();

        session.setAttribute("level", level);
        return "play";
    }

    @PostMapping("/play/placeShapeOnField")
    public String placeShapeOnField(@RequestParam int shapeNumber) {
        if (level == null)
            return "redirect:/block_puzzle/level_menu";

        currentShape = level.getShapes().get(shapeNumber-1);
        currentShapeIndex = shapeNumber;
        currentShape.placeShapeToField();

        return "redirect:/block_puzzle/play";
    }

    @PostMapping("/play/hideOrSelectShape")
    public String hideOrSelectShape(@RequestParam int shapeNumber) {
        if (level == null)
            return "redirect:/block_puzzle/level_menu";
        if (shapeNumber != currentShapeIndex) {
            currentShape = level.getShapes().get(shapeNumber-1);
            currentShapeIndex = shapeNumber;
            return "redirect:/block_puzzle/play";
        }
        currentShape.hideShape();
        currentShapeIndex = 0;
        return "redirect:/block_puzzle/play";
    }

    @GetMapping("/play/moveShape/{direction}")
    public String moveShape(@PathVariable String direction) {
        switch (direction) {
            case "W": currentShape.moveUp(); break;
            case "S": currentShape.moveDown(field); break;
            case "A": currentShape.moveLeft(); break;
            case "D": currentShape.moveRight(field); break;
        }
        return "redirect:/block_puzzle/play";
    }

    private String getHtmlField() {
        StringBuilder html = new StringBuilder();
        for (int col = 3; col < 3+field.getFieldHeight(); col++) {
            html.append("<tr>");
            for (int row = 8; row < 8+field.getFieldWidth()*2; row+=2) {
                String value = field.getMap()[row][col].getValue();
                TileState state = field.getMap()[row][col].getTileState();

                html.append("<td class='")
                        .append(getClassForTD(value))
                        .append(getColorForTD(value))
                        .append("'>");

                if (state == TileState.MARKED)
                    placeMarkedImage(html, row, col);
                else
                    placeButtonOnShapeOnField(html, value);

                html.append("</td>");
            }
            html.append("</tr>");
        }
        return html.toString();
    }

    private void placeMarkedImage(StringBuilder html, int row, int col) {
        html.append("<img class='marked-icon ")
                .append(getBackgroundColorForMarkedImage(row, col))
                .append("' src='/images/")
                .append(Color.getEnumByStringColor(currentShape.getShapeColor()).toString().toLowerCase())
                .append("-marked-icon.png' alt='marked-icon-img'/>");
    }

    private void placeButtonOnShapeOnField(StringBuilder html, String value) {
        String buttonType = getButtonType(value);
        if (buttonType != null) {
            html.append("<button type=").append(buttonType);
            if (shapeIsMarked && !currentShape.getShapeColor().equals(value)) {
                html.append(" disabled");
            }
            html.append("></button>");
        }
    }

    private String getHtmlShapes() {
        StringBuilder html = new StringBuilder();
        for (int col = 1; col < field.getMapHeight() - 1; col++) {
            html.append("<tr>");
            appendHtmlShapesForRange(html, col, 28, 36);
            html.append("<td></td>");
            appendHtmlShapesForRange(html, col, 42, 48);
            html.append("</tr>");
        }
        return html.toString();
    }

    private void appendHtmlShapesForRange(StringBuilder html, int col, int startRow, int endRow) {
        for (int row = startRow; row < endRow; row += 2) {
            String value = field.getMap()[row][col].getValue();
            html.append("<td class='").append(getColorForTD(value)).append("'>");
            String buttonType = getButtonType(value);
            if (buttonType != null) {
                html.append("<button type=").append(buttonType).append(shapeIsMarked ? " disabled" : "").append("></button>");
            }
            html.append("</td>");
        }
    }

    private String getBackgroundColorForMarkedImage(int row, int col) {
        List<Shape> shapes = level.getShapes();
        for (Shape shape : shapes) {
            for (ShapeTile shapeTile : shape.getShape()) {
                int tileX = shapeTile.getX();
                int tileY = shapeTile.getY();
                if (tileX == row && tileY == col && shape != currentShape) {
                    return Color.getEnumByStringColor(shape.getShapeColor()).toString();
                }
            }
        }
        return "";
    }

    private String getColorForTD(String cell) {
        ColorEnum color = Color.getEnumByStringColor(cell);
        if (color == null)
            return "";

        return " "+color;
    }

    private String getClassForTD(String cell) {
        if (currentShape == null || !currentShape.getShapeColor().equals(cell))
            return "";

        return "current-shape";
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
                    field.getMap()[tileX][tileY].setTileState(TileState.MARKED);
                    shapeIsMarked = true;
                }
                else {
                    field.getMap()[tileX][tileY].setValue(shape.getShapeColor());
                    field.getMap()[tileX][tileY].setTileState(TileState.NOT_MARKED);
                }
            }
        }
    }
}
