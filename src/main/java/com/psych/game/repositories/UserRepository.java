package com.psych.game.repositories;

import com.psych.game.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    //this works even though User is abstract and not a concrete entity

    Optional<User> findByEmail(String email); //optional is a monad
}
