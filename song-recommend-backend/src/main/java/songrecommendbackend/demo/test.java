package songrecommendbackend.demo;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class test {
    //수정 테스트 입니다.
    @GetMapping
    public String test(){
        return "test";
    }
}
