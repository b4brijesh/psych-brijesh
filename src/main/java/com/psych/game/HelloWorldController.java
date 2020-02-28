package com.psych.game;

import com.psych.game.model.GameMode;
import com.psych.game.model.Player;
import com.psych.game.model.Question;
import com.psych.game.repositories.PlayerRepository;
import com.psych.game.repositories.QuestionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.function.Supplier;

@RestController

@RequestMapping("/dev-test")
public class HelloWorldController {

    @Autowired
    private PlayerRepository playerRepository;
    @Autowired
    private QuestionRepository questionRepository;

    /*@Autowired //dependency injection/inversion in Spring
    public HelloWorldController(PlayerRepository playerRepository) {
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

    //Games
    //Admins
    //Rounds
    //Content-Writers

    @GetMapping("/populate")
    public String populateDB() {
        Player luffy = new Player.Builder()
                .alias("Monkey D. Luffy")
                .email("luffy@gmail.com")
                .saltedHashedPassword("strawhat")
                .build();
        playerRepository.save(luffy);
        Player robin = new Player.Builder()
                .alias("Nico Robin")
                .email("robin@gmail.com")
                .saltedHashedPassword("poneglyph")
                .build();
        playerRepository.save(robin);

        questionRepository.save(new Question("What is the most important Poneglyph?",
                "Rio Poneglyph",
                GameMode.IS_THIS_A_FACT
        ));
        return "populated";
    }
}
