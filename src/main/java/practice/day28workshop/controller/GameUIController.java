package practice.day28workshop.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import practice.day28workshop.model.Game;
import practice.day28workshop.model.GameWithRating;
import practice.day28workshop.model.RatingDisplay;
import practice.day28workshop.service.RestService;

@Controller
@RequestMapping("/")
public class GameUIController {

    @Autowired
    private RestService restService;

    @GetMapping("/games")
    public ModelAndView getGameList() {
        List<Game> gameList = restService.getGameList();

        ModelAndView mav = new ModelAndView();
        mav.setViewName("gamelist");
        mav.addObject("gameList", gameList);

        return mav;
    }

    @GetMapping("/games/sorted")
    public ModelAndView getGameListSorted() {
        List<GameWithRating> gameList = restService.getGameListSorted();

        ModelAndView mav = new ModelAndView();
        mav.setViewName("gamelistsorted");
        mav.addObject("gameList", gameList);

        return mav;
    }
}
