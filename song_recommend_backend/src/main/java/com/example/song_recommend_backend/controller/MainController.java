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
        System.out.println("1페이지");
        return "/vocal-range-detector-master/first-step";
    }

    @GetMapping("/second-step")
    public String second() {
        System.out.println("2페이지");
        return "/vocal-range-detector-master/second-step";
    }

    @GetMapping("/third-step")
    public String third() {
        System.out.println("3페이지");
        return "/vocal-range-detector-master/third-step";
    }

    @GetMapping("/result")
    public String result() {
        System.out.println("결과값");
        return "/vocal-range-detector-master/results";
    }
}
