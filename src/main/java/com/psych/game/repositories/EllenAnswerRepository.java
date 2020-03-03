package com.psych.game.repositories;

import com.psych.game.model.EllenAnswer;
import com.psych.game.model.Question;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface EllenAnswerRepository extends JpaRepository<EllenAnswer, Long> {

    @Query(value = "select * from ellenanswers limit 1", nativeQuery = true) // todo
    EllenAnswer getRandomAnswer(Question question);
}
