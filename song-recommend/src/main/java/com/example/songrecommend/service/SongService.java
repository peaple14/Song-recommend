package com.example.songrecommend.service;


import com.example.songrecommend.dto.SongDto;
import com.example.songrecommend.entity.SongEntity;
import com.example.songrecommend.repository.SongRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

@Service
@RequiredArgsConstructor
public class SongService {

    private final SongRepository songRepository;

    public SongDto change_int(SongDto songDto) { //int값으로 변경하는것
        songDto.setHighest_Int((long) octaveAndNoteToMidi(songDto.getHighest_Note()));
        songDto.setLowest_Int((long) octaveAndNoteToMidi(songDto.getLowest_Note()));
        return songDto;
    }

    public static int octaveAndNoteToMidi(String octaveAndNote) {
        char note = octaveAndNote.charAt(0);
        char accidental = ' ';
        if (octaveAndNote.length() == 3) {
            accidental = octaveAndNote.charAt(1);
        }
        int octave = Integer.parseInt(octaveAndNote.substring(octaveAndNote.length() - 1))+1;

        int noteValue = 0;
        switch (note) {
            case 'C':
                noteValue = 0;
                break;
            case 'D':
                noteValue = 2;
                break;
            case 'E':
                noteValue = 4;
                break;
            case 'F':
                noteValue = 5;
                break;
            case 'G':
                noteValue = 7;
                break;
            case 'A':
                noteValue = 9;
                break;
            case 'B':
                noteValue = 11;
                break;
            default:
                throw new IllegalArgumentException("Invalid note: " + note);
        }
        if (accidental == '#') {
            noteValue++;
        } else if (accidental == 'b') {
            noteValue--;
        }
        return (octave * 12) + noteValue;
    }


    //노래 추천
    public List<SongDto> recommendSong(long inputLowNoteInt, long inputHighNoteInt, long count) {
        List<SongEntity> allVocals = songRepository.findAll();
        List<SongDto> recommendedSongs = new ArrayList<>();

        // 모든 노래에 대해 유사도를 계산하고 SongDto에 추가
        for (SongEntity vocal : allVocals) {
            long[] vectorA = {inputLowNoteInt, inputHighNoteInt}; // 비교하려는 대상
            long[] vectorB = { vocal.getLowest_int(), vocal.getHighest_int()}; // DB에 저장된 값들
            double similarity = CosineSimilarity.calculateCosineSimilarity(vectorA, vectorB);
            SongDto songDto = SongDto.toDto(vocal);
            songDto.setSimilarity(similarity);
            recommendedSongs.add(songDto);
        }

        // 유사도에 따라 노래 정렬
        Collections.sort(recommendedSongs, Comparator.comparingDouble(SongDto::getSimilarity).reversed());

        // 상위 count 개의 노래만 추출
        return recommendedSongs.subList(0, (int)Math.min(recommendedSongs.size(), count));
    }




}
