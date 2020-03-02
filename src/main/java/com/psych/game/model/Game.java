package com.psych.game.model;

import com.fasterxml.jackson.annotation.JsonIdentityReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.*;

@Entity
@Table(name = "games")
public class Game extends Auditable {
    @ManyToMany
    @Getter @Setter
    @JsonIdentityReference
    private Set<Player> players = new HashSet<>();

    @NotNull
    @Enumerated(EnumType.STRING)
    @Getter @Setter
    private GameMode gameMode;

    @OneToMany(mappedBy = "game", cascade =  CascadeType.ALL)
    @JsonManagedReference
    private List<Round> rounds = new ArrayList<Round>();

    @Getter @Setter
    private int numRounds = 10;

    @Getter @Setter
    private boolean hasEllen = false;

    @NotNull
    @Getter @Setter
    @ManyToOne
    @JsonIdentityReference
    private Player leader;

    @JsonIdentityReference
    @ManyToMany(cascade = CascadeType.ALL)
    @Getter @Setter
    Map<Player, Stat> playerStats = new HashMap<Player, Stat>();

    @Enumerated(EnumType.STRING)
    @Getter @Setter
    private GameStatus gameStatus;

    @Getter @Setter
    @ManyToMany
    @JsonIdentityReference
    private Set<Player> readyPlayers = new HashSet<Player>();

    public Game() {
    }

    private Game(Builder builder) {
        setPlayers(builder.players);
        setGameMode(builder.gameMode);
        setNumRounds(builder.numRounds);
        setLeader(builder.leader);
    }

    public static final class Builder {
        private Set<Player> players;
        private @NotNull GameMode gameMode;
        private int numRounds;
        private @NotNull Player leader;

        public Builder() {
        }

        public Builder players(Set<Player> val) {
            players = val;
            return this;
        }

        public Builder gameMode(@NotNull GameMode val) {
            gameMode = val;
            return this;
        }

        public Builder numRounds(int val) {
            numRounds = val;
            return this;
        }

        public Builder leader(@NotNull Player val) {
            leader = val;
            return this;
        }

        public Game build() {
            return new Game(this);
        }
    }
}
