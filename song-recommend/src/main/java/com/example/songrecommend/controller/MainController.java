package com.example.songrecommend.controller;

import com.example.songrecommend.dto.SongDto;
import com.example.songrecommend.service.SongService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

@Controller
@RequiredArgsConstructor
public class MainController {

    private final SongService songService;

    @GetMapping("/")
    public String start(){
        //재테스트하기위해 보관
        SongDto songDto = new SongDto();
        songDto.setHighest_Note("F#3");
        songDto.setLowest_Note("C#5");
        System.out.println("들어왔을때:" + songDto);
        songDto = songService.change_int(songDto);
        System.out.println("들어간값:" + songDto);
        songDto.setHighest_Int(songDto.getHighest_Int());
        System.out.println("노래추천테스트:" + songService.recommendSong(songDto.getLowest_Int(), songDto.getHighest_Int(),1));
        return "/vocal/index";
    }

    @GetMapping("/first-step")
    public String first() {
        return "/vocal/first-step";
    }

    @GetMapping("/second-step")
    public String second() {
        return "/vocal/second-step";
    }

    @GetMapping("/results")
    public String result() {
        return "/vocal/results";
    }

    @PostMapping("/getnote")
    @ResponseBody
    public List<SongDto> getNote(@RequestBody SongDto songDto) {
        System.out.println("받은값" + songDto);
        songService.change_int(songDto);
        List<SongDto> songList = songService.recommendSong(songDto.getLowest_Int(), songDto.getHighest_Int(),5); // 노래추천받기
        System.out.println(songList);
        return songList;
    }
}
