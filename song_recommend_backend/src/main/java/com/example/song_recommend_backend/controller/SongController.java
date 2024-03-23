package com.example.song_recommend_backend.controller;

import com.example.song_recommend_backend.dto.SongDto;
import com.example.song_recommend_backend.service.SongService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
@RequiredArgsConstructor
public class SongController {

    private final SongService songService;

    //베이스노트 저장용
    @PostMapping("/save-base-note")
    public String saveBaseNote(@RequestBody String baseNote) {
        SongDto songDto = new SongDto();
        songDto.setBaseNote(baseNote);
        songService.saveSong(songDto);
        return "Base note 저장 완료.";
    }

    @PostMapping("/save-low-note")
    public ResponseEntity<String> saveLowNote(@RequestBody SongDto songDto) {
        songService.saveSong(songDto);
        return new ResponseEntity<>("Low note 저장 완료.", HttpStatus.OK);
    }

    //low-note 반환용
    @GetMapping("/get-low-note")
    public String getLowNote() {
        return songService.getLowNote();
    }
    //high-note 반환용
    @GetMapping("/get-high-note")
    public String getHighNote() {
        return songService.getHighNote();
    }

}
