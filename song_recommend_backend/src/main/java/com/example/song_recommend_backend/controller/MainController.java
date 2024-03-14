package com.example.song_recommend_backend.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class MainController {

    @GetMapping("/test")
    public String test(){
        System.out.println("yes");
        return "main/main";
    }
}
