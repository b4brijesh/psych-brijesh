package com.psych.game.repositories;

import com.psych.game.model.GameMode;
import com.psych.game.model.Question;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface QuestionRepository extends JpaRepository<Question, Long> {
    //JPA has its own query language - native query is not interpreted by JPA
    //JPA provides ORM so that we can code DB stuff in Java
    //JPA abstracts out the differences between different DB providers
    // todo
    @Query(value = "select * from questions where gameMode=:gameMode order by RAND() limit 1", nativeQuery = true)
    Question getRandomQuestion(GameMode gameMode);
}
