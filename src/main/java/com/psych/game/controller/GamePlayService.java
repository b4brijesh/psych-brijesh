package com.psych.game.controller;

import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Service
@RequestMapping(name = "/")
public class GamePlayService {

    @GetMapping(name = "")
    public String index() {
        return "index.html";
    }
}
