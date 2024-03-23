package com.example.song_recommend_backend.service;

import com.example.song_recommend_backend.dto.SongDto;
import org.springframework.stereotype.Service;

@Service
public class SongService {

    private SongDto songDto;

    public void saveSong(SongDto baseNote) {
        this.songDto = baseNote;
    }

    public String getLowNote() {
        if (songDto != null) {
            return songDto.getLowNote();
        } else {
            return null;
        }
    }

    public String getHighNote() {
        if (songDto != null) {
            return songDto.getHighNote();
        } else {
            return null;
        }
    }
}
