package com.psych.game.controller;

import com.psych.game.model.*;
import com.psych.game.repositories.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController

@RequestMapping("/dev-test")
public class DevTestController {

    @Autowired
    private PlayerRepository playerRepository;
    @Autowired
    private QuestionRepository questionRepository;
    @Autowired
    private GameRepository gameRepository;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private GameModeRepository gameModeRepository;

    /*@Autowired //dependency injection/inversion in Spring
    public DevTestController(PlayerRepository playerRepository) {
        this.playerRepository = playerRepository;
    }*/

    @GetMapping("/")
    public String hello() {
        return "Hello World!";
    }

    @GetMapping("/questions")
    public List<Question> getAllQuestions() {
        return questionRepository.findAll();
    }

    @GetMapping("/question/{id}")
    public Question getQuestionById(@PathVariable(name = "id") Long id) {
        return questionRepository.findById(id).orElseThrow(null);
    }

    @GetMapping("/players")
    public List<Player> getAllPlayers() {
        return playerRepository.findAll();
    }

    @GetMapping("/player/{id}")
    public Player getPlayerById(@PathVariable(name = "id") Long id) {
        return playerRepository.findById(id).orElseThrow(null);
    }

    @GetMapping("/games")
    public List<Game> getAllGames() {
        return gameRepository.findAll();
    }

    @GetMapping("/game/{id}")
    public Game getGameById(@PathVariable(name = "id") Long id) {
        return gameRepository.findById(id).orElseThrow(null);
    }

    @GetMapping("/users")
    public List<User> getAllUsers() {
        return userRepository.findAll();
    }

    @GetMapping("/user/{id}")
    public User getUserById(@PathVariable(name = "id") Long id) {
        return userRepository.findById(id).orElseThrow(null);
    }
    //Rounds
    //Content-Writers

    @GetMapping("/populate")
    public String populateDB() {
        gameRepository.deleteAll();
        playerRepository.deleteAll();
        questionRepository.deleteAll();
        gameModeRepository.deleteAll();

        Player luffy = new Player.Builder()
                .alias("Monkey D. Luffy")
                .email("luffy@psych.com")
                .saltedHashedPassword("strawhat")
                .build();
        playerRepository.save(luffy);
        Player robin = new Player.Builder()
                .alias("Nico Robin")
                .email("robin@psych.com")
                .saltedHashedPassword("poneglyph")
                .build();
        playerRepository.save(robin);

        GameMode isThisAFact = new GameMode("Is This A Fact?", "https://irs.www.warnerbros.com/mobile-app-games-square-jpeg/psych_mobile_keyart.jpg", "is this a fact description");
        gameModeRepository.save(isThisAFact);
        gameModeRepository.save(new GameMode("Word Up", "https://irs.www.warnerbros.com/mobile-app-games-square-jpeg/psych_mobile_keyart.jpg", "word up description"));
        gameModeRepository.save(new GameMode("Un-Scramble", "https://irs.www.warnerbros.com/mobile-app-games-square-jpeg/psych_mobile_keyart.jpg", "un-scramble description"));
        gameModeRepository.save(new GameMode("Movie Buff", "https://irs.www.warnerbros.com/mobile-app-games-square-jpeg/psych_mobile_keyart.jpg", "movie buff description"));

        questionRepository.save(new Question("What is the most important Poneglyph?",
                "Rio Poneglyph",
                isThisAFact
        ));

        questionRepository.save(new Question("How far can you stretch?",
                "56",
                isThisAFact
        ));

        Game game = new Game();
        game.setGameMode(isThisAFact);
        game.setLeader(luffy);
        game.getPlayers().add(luffy);
        gameRepository.save(game);

        return "populated";
    }
}
