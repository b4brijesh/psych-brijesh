package com.psych.game.controller;

import com.psych.game.exceptions.InvalidGameActionException;
import com.psych.game.model.Game;
import com.psych.game.model.GameMode;
import com.psych.game.model.Player;
import com.psych.game.repositories.GameModeRepository;
import com.psych.game.repositories.GameRepository;
import com.psych.game.repositories.PlayerRepository;
import com.sun.org.apache.xpath.internal.operations.Bool;
import net.minidev.json.JSONArray;
import net.minidev.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/play")
public class GamePlayAPI {

    @Autowired
    private PlayerRepository playerRepository;
    @Autowired
    private GameModeRepository gameModeRepository;
    @Autowired
    private GameRepository gameRepository;

    private Player getCurrentPlayer(Authentication authentication) {
        return playerRepository.findByEmail(authentication.getName()).orElseThrow(null);
    }

    private JSONObject getData(Player player) {
        Game currentGame = player.getCurrentGame();

        JSONObject response = new JSONObject();
        response.put("playerAlias", player.getAlias());
        response.put("currentGame", currentGame==null?null:currentGame.getId());

        if (currentGame == null) {
            JSONArray gameModes = new JSONArray();
            for (GameMode mode: gameModeRepository.findAll()) {
                JSONObject gameMode = new JSONObject();
                gameMode.put("title", mode.getName());
                gameMode.put("image", mode.getPicture());
                gameMode.put("description", mode.getDescription());
                gameModes.add(gameMode);
            }
            response.put("gameModes", gameModes);
        } else {
            response.put("gameState", currentGame.getGameState());
        }
        return response;
    }

    @GetMapping(name = "")
    public JSONObject play(Authentication authentication) {
        Player player = getCurrentPlayer(authentication);
        return getData(player);
    }

    //@GetMapping("/")
    /*public String play(Authentication auth) {
        return auth.getName();
    }*/

    @GetMapping("/submit-answer/{answer}")
    public void submitAnswer(Authentication authentication, @PathVariable(name = "answer") String answer) throws InvalidGameActionException {
        Player player = playerRepository.findByEmail(authentication.getName()).orElseThrow(null);
        Game currentGame = player.getCurrentGame();
        currentGame.submitAnswer(player, answer);
    }

    @GetMapping("/create-game")
    public JSONObject createGame(Authentication authentication,
                           @RequestParam(name = "mode") String gameMode,
                           @RequestParam(name = "rounds") Integer numRounds,
                           @RequestParam(name = "ellen") Boolean hasEllen) {
        Player leader = getCurrentPlayer(authentication);
        GameMode mode = gameModeRepository.findByName(gameMode).orElseThrow(null);
        try {
            gameRepository.save(new Game(mode, numRounds, hasEllen, leader));
        } catch (InvalidGameActionException e) {
            e.printStackTrace();
        }
        return getData(leader);
    }
}
