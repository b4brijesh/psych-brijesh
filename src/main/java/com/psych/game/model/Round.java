package com.psych.game.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIdentityReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.psych.game.exceptions.InvalidGameActionException;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.HashMap;
import java.util.Map;

@Entity
@Table(name = "rounds")
public class Round extends Auditable {

    @ManyToOne
    @Getter
    @Setter
    @JsonBackReference
    private Game game;

    @Getter @Setter
    @JsonIdentityReference
    private Question question;

    @Getter @Setter
    @ManyToMany(cascade = CascadeType.ALL)
    @JsonManagedReference
    private Map<Player, PlayerAnswer> submittedAnswers = new HashMap<>();

    @Getter @Setter
    @ManyToMany(cascade = CascadeType.ALL)
    @JsonManagedReference
    private Map<Player, PlayerAnswer> selectedAnswers = new HashMap<>();

    @ManyToOne
    @Getter @Setter
    @JsonIdentityReference
    private EllenAnswer ellenAnswer;

    @NotNull
    @Getter @Setter
    private int roundNumber;

    public Round(Game game, Question question, @NotNull int roundNumber) {
        this.game = game;
        this.question = question;
        this.roundNumber = roundNumber;
    }

    public void submitAnswer(Player player, String answer) throws InvalidGameActionException {
        if(submittedAnswers.containsKey(player))
            throw new InvalidGameActionException("Player has already submitted answer");
        for (PlayerAnswer existingAnswer: submittedAnswers.values()) {
            if (answer.equals(existingAnswer.getAnswer()))
                throw new InvalidGameActionException("Duplicate answer");
        }
        submittedAnswers.put(player, new PlayerAnswer(this, player, answer));
        //if player has already submitted answer then reject
        //if duplicate, reject
    }

    public boolean allAnswersSubmitted(int numPlayers) {
        return submittedAnswers.size() == numPlayers;
    }

    public void selectAnswer(Player player, PlayerAnswer selectedAnswer) throws InvalidGameActionException {
        if(selectedAnswers.containsKey(player))
            throw new InvalidGameActionException("Player has already selected answer");
        if(selectedAnswer.getPlayer().equals(player))
            throw new InvalidGameActionException("Can't select your own answer");
        selectedAnswers.put(player, selectedAnswer);
    }

    public boolean allAnswersSelected(int numPlayers) {
        return selectedAnswers.size() == numPlayers;
    }
}
