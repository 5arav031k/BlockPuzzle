package sk.tuke.gamestudio.server.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.context.WebApplicationContext;
import sk.tuke.gamestudio.entity.Comment;
import sk.tuke.gamestudio.entity.Rating;
import sk.tuke.gamestudio.entity.User;
import sk.tuke.gamestudio.service.CommentService;
import sk.tuke.gamestudio.service.GameStudioException;
import sk.tuke.gamestudio.service.RatingService;

import javax.servlet.http.HttpSession;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

@Controller
@Scope(WebApplicationContext.SCOPE_SESSION)
@RequestMapping("/block_puzzle/community")
public class CommunityController {
    @Autowired
    private CommentService commentService;
    @Autowired
    private RatingService ratingService;
    private User user;

    @GetMapping
    public String community(HttpSession session, Model model) {
        user = (User) session.getAttribute("user");
        if (user == null) {
            return "redirect:/block_puzzle";
        }
        model.addAttribute("htmlCommentsAndRating", getHtmlCommentsAndRating());
        return "community";
    }

    @PostMapping("/addCommentAndRating")
    public String addComment(@RequestParam String comment, @RequestParam String rating) {
        try {
            Date date = new Date();
            commentService.addComment(new Comment(user.getLogin(), comment.trim(), date));
            if (rating != null && !rating.isEmpty()) {
                ratingService.setRating(new Rating(user.getLogin(), Integer.parseInt(rating), date));
            }
        } catch (GameStudioException e) {
            return "redirect:/block_puzzle/community";
        }
        return "redirect:/block_puzzle/community";
    }

    private String getHtmlCommentsAndRating() {
        StringBuilder html = new StringBuilder();
        List<Comment> comments = commentService.getComments();
        SimpleDateFormat dateFormat = new SimpleDateFormat("HH:mm:ss dd.MM.yyyy");
        for (Comment comment : comments) {
            int rating = ratingService.getRating(comment.getLogin());
            html.append("<tr>")
                    .append("<td>")
                    .append(comment.getLogin())
                    .append("</td>")
                    .append("<td>")
                    .append(comment.getComment())
                    .append("</td>")
                    .append("<td>")
                    .append(dateFormat.format(comment.getCommentedOn()))
                    .append("</td>")
                    .append("<td>")
                    .append(rating == 0 ? "" : rating)
                    .append("</td>")
                    .append("</tr>");
        }
        return html.toString();
    }
}
