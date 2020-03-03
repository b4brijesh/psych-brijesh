package com.psych.game.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIdentityReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import lombok.Getter;
import lombok.Setter;
import net.minidev.json.annotate.JsonIgnore;

import javax.persistence.*;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "players")
public class Player extends User {
    @NotBlank
    @Getter @Setter
    private String alias;

    @Getter @Setter
    private String psychFaceUrl;

    @Getter @Setter
    private String picUrl;

    @OneToOne(cascade = CascadeType.ALL)
    @Getter @Setter
    @JsonManagedReference
    private Stat stat = new Stat();

    @ManyToMany(mappedBy = "players") //not players table but players attribute of Game
    @Getter @Setter
    @JsonIdentityReference // @JsonBackReference @JsonManagedReference @JsonIgnore
    private Set<Game> games = new HashSet<>();

    public Player() {
    } //violates Builder-Pattern but used to provide Spring access to Entity

    private Player(Builder builder) {
        setEmail(builder.email);
        setSaltedHashedPassword(builder.saltedHashedPassword);
        setAlias(builder.alias);
    }

    public Game getCurrentGame() {
        return new Game();
        // todo
    }

    public static final class Builder {
        private @Email @NotBlank String email;
        private @NotBlank String saltedHashedPassword;
        private @NotBlank String alias;

        public Builder() {
        }

        public Builder email(@Email @NotBlank String val) {
            email = val;
            return this;
        }

        public Builder saltedHashedPassword(@NotBlank String val) {
            saltedHashedPassword = val;
            return this;
        }

        public Builder alias(@NotBlank String val) {
            alias = val;
            return this;
        }

        public Player build() {
            return new Player(this);
        }
    }
}
