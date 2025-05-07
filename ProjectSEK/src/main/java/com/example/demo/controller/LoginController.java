package com.example.demo.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.example.demo.model.User;
import com.example.demo.service.UserService;

import jakarta.servlet.http.HttpSession;

@Controller
public class LoginController {
    private final UserService userService;

    @Autowired
    public LoginController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/")
    public String index(HttpSession session,Model model) {
        // 세션에 "user" 속성이 있는지 확인
        User user = (User) session.getAttribute("user");
        if (user == null) {
            return "redirect:/login"; // 로그인 정보가 없으면 로그인 페이지로 리다이렉트
        }
        model.addAttribute("user", user);
        return "index"; // 로그인 정보가 있으면 index 페이지로 이동
    }

    @GetMapping("/login")
    public String loginPage() {
        return "login";
    }

    @PostMapping("/login")
    public String login(
            @RequestParam("userid") int userid,
            @RequestParam("userpw") String userpw,
            HttpSession session,
            Model model) {
        User user = userService.login(userid, userpw);
        if (user != null) {
            session.setAttribute("user", user);
            return "redirect:/"; // 로그인 성공 시 index로 리다이렉트
        } else {
            model.addAttribute("error", "Invalid student ID or password");
            return "login"; // 로그인 실패 시 로그인 페이지로 다시 이동
        }
    }
    @GetMapping("/logout")
    public String logout(HttpSession session) {
        session.invalidate(); // 세션 무효화
        return "redirect:/login"; // 로그아웃 후 로그인 페이지로 리다이렉트
    }

}
