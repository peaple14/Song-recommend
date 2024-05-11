package com.example.songrecommend.controller;

import com.example.songrecommend.dto.VocalDto;
import com.example.songrecommend.service.VocalService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequiredArgsConstructor
public class MainController {

    private final VocalService vocalService;

    @GetMapping("/")
    public String start(){
        //재 테스트하기위해 보관
//        VocalDto vocalDto = new VocalDto();
//        vocalDto.setHighNote("G#2");
//        vocalDto.setLowNote("C1");
//        System.out.println("들어왔을때:" + vocalDto);
//        vocalDto = vocalService.change_int(vocalDto);
//        System.out.println("나왔을때:" + vocalDto);
        return "/vocal-range-detector-master/index";
    }

    @GetMapping("/first-step")
    public String first() {
        System.out.println("1페이지");
        return "/vocal-range-detector-master/first-step";
    }

    @GetMapping("/second-step")
    public String second() {
        System.out.println("2페이지");
        return "/vocal-range-detector-master/second-step";
    }

    @GetMapping("/third-step")
    public String third() {
        System.out.println("3페이지");
        return "/vocal-range-detector-master/third-step";
    }

    @GetMapping("/results")
    public String result() {
        System.out.println("결과값");
        return "/vocal-range-detector-master/results";
    }

    @PostMapping("/getnote")
    @ResponseBody
    public String getNote(@RequestBody VocalDto vocalDto) {
        System.out.println("들어왔을때:" + vocalDto);
        vocalDto = vocalService.change_int(vocalDto);
        System.out.println("나왔을때:" + vocalDto);
//        문제1.1회적으로 적용되게 해야한다.(점차 낮아지거나 높아지는게아니라 딱 거기)
//        문제2.페이지쪽으로 문제.노래 추천후 다시 적용하고 리턴
//        문제3.웹페이지 디자인 문제
        // 결과 반환
        return "Note received successfully";
    }
}
