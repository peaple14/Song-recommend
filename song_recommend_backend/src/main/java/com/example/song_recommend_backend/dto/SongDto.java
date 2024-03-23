package com.example.song_recommend_backend.dto;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Data
@NoArgsConstructor
@ToString
public class SongDto {

    private String baseNote;
    private String lowNote;
    private String highNote;


}
