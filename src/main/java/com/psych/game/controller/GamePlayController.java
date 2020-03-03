package com.psych.game.controller;

import com.psych.game.exceptions.InvalidGameActionException;
import com.psych.game.model.Game;
import com.psych.game.model.Player;
import com.psych.game.repositories.PlayerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/play")
public class GamePlayController {

    @Autowired
    private PlayerRepository playerRepository;

    @GetMapping("/")
    public String play(Authentication auth) {
        return auth.getName();
    }

    @GetMapping("/submit-answer/{answer}")
    public void submitAnswer(Authentication authentication, @PathVariable(name = "answer") String answer) throws InvalidGameActionException {
        Player player = playerRepository.findByEmail(authentication.getName()).orElseThrow(null);
        Game currentGame = player.getCurrentGame();
        currentGame.submitAnswer(player, answer);
    }
}
