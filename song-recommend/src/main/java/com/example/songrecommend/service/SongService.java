package com.example.songrecommend.service;


import com.example.songrecommend.dto.SongDto;
import com.example.songrecommend.entity.SongEntity;
import com.example.songrecommend.repository.SongRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class SongService {

    private final SongRepository songRepository;

    public SongDto change_int(SongDto songDto) {
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


    //가장 비슷한 dto값 리턴
    public SongDto recommendSimilarVocal(long inputLowNoteInt, long inputHighNoteInt) {
        List<SongEntity> allVocals = songRepository.findAll();
        SongDto best_songdto = null;
        double highestSimilarity = -1;

        for (SongEntity vocal : allVocals) {//비교해서 가장 유사한값 찾기
            long[] vectorA = {inputLowNoteInt, inputHighNoteInt};//비교하려는 대상
            long[] vectorB = { vocal.getLowest_int(), vocal.getHighest_int()};//db에 저장된 값들

            double similarity = CosineSimilarity.calculateCosineSimilarity(vectorA, vectorB);

            if (similarity > highestSimilarity) {
                highestSimilarity = similarity;
                best_songdto = SongDto.toDto(vocal);
            }
        }

        return best_songdto;
    }




}
