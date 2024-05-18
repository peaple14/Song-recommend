package com.example.songrecommend.service;

import com.example.songrecommend.dto.SongDto;
import com.example.songrecommend.entity.SongEntity;
import com.example.songrecommend.repository.SongRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;

@Component
@RequiredArgsConstructor
@Slf4j
public class InitSongData {

    private final SongRepository songRepository;
    private final SongService songService;

    @PostConstruct
    public void init() {
        if (songRepository.count() == 0) { // 데이터가 없을 경우에만 초기화
            insertVocal("꿈속에 너", "전상근", "남성", "A2", "A4");
            insertVocal("찰나가 영원이 될 때", "마크툽", "남성", "A#2", "D#5");
            insertVocal("흔들리는 꽃들 속에서 네 샴푸향이 느껴진 거야", "장범준", "남성", "G#2", "E4");
            insertVocal("나는 나비", "YB(윤도현밴드)", "남성", "F3", "G#4");
            insertVocal("민물장어의 꿈", "하현우", "남성", "F#2", "A4");
            insertVocal("헤어지자 말해요", "박재정", "남성", "C3", "C#5");
            insertVocal("그대가 내 안에 박혔다", "황치열", "남성", "B2", "A#4");
            insertVocal("사랑에 연습이 있었다면", "임재현", "남성", "C#2", "A#4");
            insertVocal("조금 취했어", "임재현", "남성", "A2", "C5");
            insertVocal("사랑이란 멜로는 없어", "전상근", "남성", "B2", "C#5");
            insertVocal("걱정말아요 그대", "이적", "남성", "F2", "G4");
            insertVocal("I AM", "IVE(아이브)", "여성", "F#3", "G5");
            insertVocal("밤양갱", "비비", "여성", "F3", "F#4");
            insertVocal("사랑하게 될 줄 알았어", "전미도", "여성", "G3", "E4");
            insertVocal("후라이의 꿈", "이수현", "여성", "E2", "E4");

            System.out.println("노래 데이터 추가 완료");
        } else {
            System.out.println("노래 데이터가 이미 존재함.");
        }
    }

    private void insertVocal(String title, String artist, String gender, String lowNote, String highNote) {
        SongEntity songEntity = new SongEntity();
        songEntity.setTitle(title);
        songEntity.setGender(gender);
        songEntity.setArtist(artist);
        songEntity.setHighest_note(highNote);
        songEntity.setLowest_note(lowNote);

        SongDto songDto = new SongDto();
        songDto.setHighest_Note(highNote);
        songDto.setLowest_Note(lowNote);
        songEntity.setHighest_int(songService.change_int(songDto).getHighest_Int());
        songEntity.setLowest_int(songService.change_int(songDto).getLowest_Int());

        songRepository.save(songEntity);
    }


}
