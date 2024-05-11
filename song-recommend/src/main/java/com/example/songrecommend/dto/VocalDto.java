package com.example.songrecommend.dto;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class VocalDto {
    //영어+숫자값
    private String lowNote;
    private String highNote;

    //정수값으로 변환
    private int lowNote_int;
    private int highNote_int;

}
