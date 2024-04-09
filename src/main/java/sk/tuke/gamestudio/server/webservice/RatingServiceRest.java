package sk.tuke.gamestudio.server.webservice;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import sk.tuke.gamestudio.entity.Rating;
import sk.tuke.gamestudio.service.RatingService;

@RestController
@RequestMapping("/api/rating")
public class RatingServiceRest {

    @Autowired
    private RatingService ratingService;

    @PostMapping("/setRating")
    public void setRating(@RequestBody Rating rating) {
        ratingService.setRating(rating);
    }

    @GetMapping("/getAverageRating")
    public int getAverageRating() {
        return ratingService.getAverageRating();
    }

    @GetMapping("/getRating/{username}")
    public int getRating(@PathVariable String username) {
        return ratingService.getRating(username);
    }
}
