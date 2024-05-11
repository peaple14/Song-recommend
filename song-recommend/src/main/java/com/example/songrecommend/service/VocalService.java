package com.example.songrecommend.service;


import com.example.songrecommend.dto.VocalDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class VocalService {

    public VocalDto change_int(VocalDto vocalDto) {
        vocalDto.setHighNote_int(octaveAndNoteToMidi(vocalDto.getHighNote()));
        vocalDto.setLowNote_int(octaveAndNoteToMidi(vocalDto.getLowNote()));
        return vocalDto;
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




}
