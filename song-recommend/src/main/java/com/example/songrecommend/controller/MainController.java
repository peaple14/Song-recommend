package com.example.songrecommend.controller;

import com.example.songrecommend.dto.SongDto;
import com.example.songrecommend.service.SongService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequiredArgsConstructor
public class MainController {

    private final SongService songService;

    @GetMapping("/")
    public String start(){
        //재 테스트하기위해 보관
        SongDto songDto = new SongDto();
        songDto.setHighest_Note("F#3");
        songDto.setLowest_Note("C#5");
//        System.out.println("들어왔을때:" + songDto);
        songDto = songService.change_int(songDto);
        System.out.println("들어간값:" + songDto);
        songDto.setHighest_Int(songDto.getHighest_Int());
        System.out.println("노래추천테스트:" + songService.recommendSimilarVocal(songDto.getLowest_Int(), songDto.getHighest_Int()));
        return "/vocal/index";
    }

    @GetMapping("/first-step")
    public String first() {
        System.out.println("1페이지");
        return "/vocal/first-step";
    }

    @GetMapping("/second-step")
    public String second() {
        System.out.println("2페이지");
        return "/vocal/second-step";
    }

    @GetMapping("/third-step")
    public String third() {
        System.out.println("3페이지");
        return "/vocal/third-step";
    }

    @GetMapping("/results")
    public String result() {
        System.out.println("결과값");
        return "/vocal/results";
    }

    @PostMapping("/getnote")
    @ResponseBody
    public SongDto getNote(@RequestBody SongDto songDto) {
        System.out.println("getnote 들어옴");
        System.out.println("들어왔을때:" + songDto);
        songDto = songService.change_int(songDto);
        System.out.println("나왔을때:" + songDto);
        System.out.println("노래추천테스트 result버전:" + songService.recommendSimilarVocal(songDto.getLowest_Int(), songDto.getHighest_Int()));
        songDto = songService.recommendSimilarVocal(songDto.getLowest_Int(), songDto.getHighest_Int());
        return songDto;
    }
}
