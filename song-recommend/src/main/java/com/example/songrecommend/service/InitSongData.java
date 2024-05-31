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
            insertVocal("찰나가 영원이 될 때", "마크툽", "남성", "A#2", "A#5");
            insertVocal("흔들리는 꽃들 속에서 네 샴푸향이 느껴진 거야", "장범준", "남성", "G#2", "E4");
            insertVocal("나는 나비", "YB(윤도현밴드)", "남성", "F3", "G#4");
            insertVocal("민물장어의 꿈", "하현우", "남성", "F#2", "A4");
            insertVocal("헤어지자 말해요", "박재정", "남성", "C3", "C#5");
            insertVocal("그대가 내 안에 박혔다", "황치열", "남성", "B2", "A#4");
            insertVocal("사랑에 연습이 있었다면", "임재현", "남성", "C#2", "A#4");
            insertVocal("조금 취했어", "임재현", "남성", "A2", "C5");
            insertVocal("사랑이란 멜로는 없어", "전상근", "남성", "B2", "C#5");
            insertVocal("걱정말아요 그대", "이적", "남성", "F2", "B4");
            insertVocal("I AM", "IVE(아이브)", "여성", "F#3", "G5");
            insertVocal("밤양갱", "비비", "여성", "G#3", "C#5");
            insertVocal("사랑하게 될 줄 알았어", "전미도", "여성", "G3", "E5");
            insertVocal("후라이의 꿈", "이수현", "여성", "E3", "E5");
            insertVocal("기억의 습작", "전람회", "남성", "G2", "G♯4");
            insertVocal("Sweet Dream", "장나라", "여성", "A3", "C5");
            insertVocal("인연", "이선희", "여성", "E3", "C♯5");
            insertVocal("가리워진 길", "안지영", "여성", "G3", "B4");
            insertVocal("Good Parts", "LE SSERAFIM", "여성", "G3", "A4");
            insertVocal("You&Me", "제니", "여성", "B3", "C#5");
            insertVocal("Ditto", "뉴진스", "여성", "F#3", "C#5");
            insertVocal("스물다섯,스물하나", "김윤아(자우림)", "여성", "G3", "C5");
            insertVocal("홀로", "이하이", "여성", "D#3", "B4");
            insertVocal("Hard to Love", "BLACKPINK", "여성", "C4", "C5");
            insertVocal("꺼내먹어요", "자이언티", "남성", "C#3", "C#4");
            insertVocal("잊어야한다는마음으로", "김광석", "남성", "D3", "D4");
            insertVocal("24시간이모자라", "선미", "여성", "C4", "C5");
            insertVocal("우리가헤어져야했던이유", "BIBI", "여성", "G3", "C5");
            insertVocal("badguy", "Billie Eilish", "여성", "F#3", "D4");
            insertVocal("옛사랑", "이문세", "남성", "E2", "C#4");
            insertVocal("Vancouver", "서동현", "남성", "A#3", "F4");
            insertVocal("바보에게바보가", "박명수", "남성", "B2", "F♯4");
            insertVocal("미스코리아", "이효리", "여성", "F3", "A#4");
            insertVocal("STAY", "BLACKPINK", "여성", "G#3", "A#4");
            insertVocal("스토커", "10CM", "남성", "C♯3", "G♯4");
            insertVocal("겨울을 걷는다", "윤딴딴", "남성", "B2", "G♯4");
            insertVocal("까만 안경", "이루", "남성", "B2", "G♯4");
            insertVocal("눈물꽃", "이승철", "남성", "F3", "G4");
            insertVocal("안녕", "폴킴", "남성", "G2", "G4");
            insertVocal("너는 나의 봄이다", "성시경", "남성", "B2", "F♯4");
            insertVocal("바람이 불어오는 곳", "김광석", "남성", "D♯3", "D♯4");
            insertVocal("냉면", "박명수(명카드라이브)", "남성", "C3", "C4");
            insertVocal("Chitty Chitty Bang Bang", "이효리", "여성", "A♯2", "A♯4");
            insertVocal("연예인", "싸이", "남성", "A3", "G4");
            insertVocal("사랑합니다", "팀", "남성", "C♯3", "G4");
            insertVocal("먼지가 되어", "김광석", "남성", "A2", "G4");
            insertVocal("어떻게 사랑이 그래요", "이승환", "남성", "A♯2", "G♯4");
            insertVocal("I'm In Love", "라디(Ra.D)", "남성", "D3", "G4");
            insertVocal("그게 뭐라고", "어쿠루브", "남성", "D3", "G4");
            insertVocal("안아줘", "정준일", "남성", "G2", "G4");
            insertVocal("왈왈", "김승민", "남성", "C3", "G4");
            insertVocal("Youth", "Dasutt", "남성", "F♯3", "F♯4");
            insertVocal("잘 살고 있지롱", "윤딴딴", "남성", "D3", "E4");
            insertVocal("싸구려 커피", "장기하와 얼굴들", "남성", "C3", "D4");
            insertVocal("Fabulous", "태연", "여성", "F#3", "C♯5");
            insertVocal("친구", "안재욱", "남성", "C3", "G♯4");
            insertVocal("별", "이수", "남성", "C3", "G♯4");
            insertVocal("꽃", "이승환", "남성", "F♯2", "G♯4");
            insertVocal("가족사진", "김진호", "남성", "G4", "C3");
            insertVocal("어떻게 지내", "크러쉬", "남성", "D3", "F♯4");
            insertVocal("사랑.. 그 놈", "바비킴", "남성", "A2", "G4");
            insertVocal("여수 밤바다", "버스커 버스커", "남성", "B2", "G4");
            insertVocal("노래", "자이언티", "남성", "G3", "G4");
            insertVocal("Change", "(여자)아이들", "여성", "F♯3", "B4");



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
