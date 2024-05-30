package com.example.songrecommend.dto;

import com.example.songrecommend.entity.SongEntity;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class SongDto {

    private String title; // 노래이름
    private String artist; // 작곡가
    private String gender; // 성별
    private String lowest_Note; // 최저음
    private String highest_Note; // 최고음
    private Long lowest_Int; // 최저음 정수값
    private Long highest_Int; // 최고음 정수값
    private double similarity;//유사도

    public static SongDto toDto(SongEntity entity) {
        SongDto dto = new SongDto();
        dto.setTitle(entity.getTitle());
        dto.setArtist(entity.getArtist());
        dto.setGender(entity.getGender());
        dto.setLowest_Note(entity.getLowest_note());
        dto.setHighest_Note(entity.getHighest_note());
        dto.setLowest_Int(entity.getLowest_int());
        dto.setHighest_Int(entity.getHighest_int());
        return dto;
    }

//    public static SongEntity toEntity(SongDto dto) { //db에 저장하는 기능이 없음.
//        SongEntity entity = new SongEntity();
//        entity.setTitle(dto.getTitle());
//        entity.setArtist(dto.getArtist());
//        entity.setGender(dto.getGender());
//        entity.setLowest_note(dto.getLowest_Note());
//        entity.setHighest_note(dto.getHighest_Note());
//        entity.setLowest_int(dto.getLowest_Int());
//        entity.setHighest_int(dto.getHighest_Int());
//        return entity;
//    }

}


