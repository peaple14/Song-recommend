package com.example.song_recommend_backend.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class MainController {

    @GetMapping("/")
    public String start(){
        return "/vocal-range-detector-master/index";
    }


    @GetMapping("/first-step")
    public String first() {
        return "/vocal-range-detector-master/first-step";
    }

//    @GetMapping("/first-step")
//    public String result() {
//        return "/vocal-range-detector-master/result";
//    }
//
//    @GetMapping("/first-step")
//    public String test() {
//        return "/vocal-range-detector-master/first-step";
//    }
}
