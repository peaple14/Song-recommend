package com.song_recommend_backend.song_recommend_backend.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


@RestController
@RequestMapping("/api/fruits")
public class FruitController {

    @GetMapping("/getFruitName")
    public String getFruitName() {
        return "Apple";
    }
}