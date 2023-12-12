package com.song_recommend_backend.song_recommend_backend;

import org.springframework.web.bind.annotation.*;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import org.json.JSONObject;


@RestController
public class get_pitch {

    @GetMapping("/get_pitch")
    public String getPitch() {
        try {
            // 오디오 파일 경로 ->나중에는 이거없이 앱에서 음성값받아온후,이미만들어진 데이터프레임으로 결과값 대비후 보낼에정.
            String audioPath = "C:\\Song-recommend\\song_recommend_backend\\src\\main\\java\\com\\song_recommend_backend\\song_recommend_backend\\song\\시든 꽃에 물을 주듯-남성.mp3";

            // 파이썬 스크립트 경로 ->하드코딩으로 넣을예정.
            String pythonPath = "C:\\Song-recommend\\song_recommend_backend\\src\\main\\java\\com\\song_recommend_backend\\song_recommend_backend\\python\\test.py";

            // 파이썬 스크립트를 실행하여 결과 받아오기
            ProcessBuilder processBuilder = new ProcessBuilder("python", pythonPath, audioPath);
            Process process = processBuilder.start();

            // 프로세스의 출력 읽기
            BufferedReader reader = new BufferedReader(new InputStreamReader(process.getInputStream()));
            StringBuilder result = new StringBuilder();
            String line;
            while ((line = reader.readLine()) != null) {
                result.append(line).append("\n");
            }

            // 프로세스가 종료되기를 기다리고 결과 반환
            int exitCode = process.waitFor();
            if (exitCode == 0) {
                return result.toString();
            } else {
                return "파이썬 실행 실패";
            }
        } catch (IOException | InterruptedException e) {
            e.printStackTrace();
            return "Error: " + e.getMessage();
        }
    }
}