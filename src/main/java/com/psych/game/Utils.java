package com.psych.game;

import com.psych.game.config.SpringConfiguration;
import com.psych.game.model.EllenAnswer;
import com.psych.game.model.GameMode;
import com.psych.game.model.Question;
import com.psych.game.repositories.EllenAnswerRepository;
import com.psych.game.repositories.QuestionRepository;

//can make this class as service or component and autowire repository interface objects
public class Utils {
    private static QuestionRepository questionRepository;
    private static EllenAnswerRepository ellenAnswerRepository;

    static {
        questionRepository = (QuestionRepository) SpringConfiguration
                .contextProvider()
                .getApplicationContext()
                .getBean("questionRepository");
        ellenAnswerRepository = (EllenAnswerRepository) SpringConfiguration
                .contextProvider()
                .getApplicationContext()
                .getBean("ellenAnswerRepository");
    }

    public static Question getRandomQuestion(GameMode gameMode) {
        return questionRepository.getRandomQuestion(gameMode);
    }

    public static EllenAnswer getRandomEllenAnswer(Question question) {
        return ellenAnswerRepository.getRandomAnswer(question);
    }
}
