package glfp.glfp.controller;


import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
@CrossOrigin(origins = "*")
@Controller     //TODO: 추후 restController로 수정
public class MainController {

    @GetMapping("/main")
    public String getMain(){
        return "main";          //TODO: 추후 프론트와 연동시 메인페이지(랜딩페이지) url로 redirect
    }

    //로그인
    @GetMapping("/login")   //security config 에 있는 loginPage 인자와 동일
    public String getLoginForm() {
        return "loginPage";         //TODO: 프론트와 연결시 로그인페이지 url로 redirect
    }
}
