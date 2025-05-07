package com.example.demo.controller;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.example.demo.dto.LectureDTO;
import com.example.demo.model.User;
import com.example.demo.service.LectureService;

import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;

@Controller
@RequiredArgsConstructor
public class RetrieveController {

	private final LectureService lectureService;

	// 기본 조회
	@GetMapping("/retrieve")
	public String getList(@RequestParam(value = "page", defaultValue = "1") int page,
			@RequestParam(value = "size", defaultValue = "5") int size, HttpSession session,Model model) {
		User user = (User) session.getAttribute("user"); // 세션에서 User 객체를 가져옴
	    if (user == null) {
	        return "redirect:/login"; // 세션에 유저 정보가 없으면 로그인 페이지로 리다이렉트
	    }
		
		List<LectureDTO> lectureList = lectureService.getList(page, size);
		int totalRecords = lectureService.getTotalCount();

		model.addAttribute("lectureList", lectureList);
		model.addAttribute("currentPage", page);
		model.addAttribute("pageSize", size);
		model.addAttribute("totalPages", (int) Math.ceil((double) totalRecords / size));
		return "retrieve";
	}

	@PostMapping("/retrieve")
    public String searchLectures(
            @RequestParam(value = "issueCategory", required = false) String issueCategory,
            @RequestParam(value = "lectname", required = false) String lectname,
            @RequestParam(value = "department", required = false) String department,
            @RequestParam(value = "targetYear", required = false) String targetYear,
            @RequestParam(value = "classroom", required = false) String classroom,
            @RequestParam(value = "professorName", required = false) String professorName,
            RedirectAttributes redirectAttributes) {
        
        // 검색 조건 RedirectAttributes로 전달
        redirectAttributes.addAttribute("page", 1);
        redirectAttributes.addAttribute("size", 5);
        redirectAttributes.addAttribute("issueCategory", issueCategory);
        redirectAttributes.addAttribute("lectname", lectname);
        redirectAttributes.addAttribute("department", department);
        redirectAttributes.addAttribute("targetYear", targetYear);
        redirectAttributes.addAttribute("classroom", classroom);
        redirectAttributes.addAttribute("professorName", professorName);

        return "redirect:/search"; // 검색 결과 페이지로 리다이렉트
    }

    // 검색 결과 페이지 처리
    @GetMapping("/search")
    public String searchResults(
            @RequestParam(value = "page", defaultValue = "1") int page,
            @RequestParam(value = "size", defaultValue = "5") int size,
            @RequestParam(value = "issueCategory", required = false) String issueCategory,
            @RequestParam(value = "lectname", required = false) String lectname,
            @RequestParam(value = "department", required = false) String department,
            @RequestParam(value = "targetYear", required = false) String targetYear,
            @RequestParam(value = "classroom", required = false) String classroom,
            @RequestParam(value = "professorName", required = false) String professorName,
            Model model) {
        
        // 검색 조건에 따른 강의 리스트 및 검색 결과 수 가져오기
        List<LectureDTO> lectureList = lectureService.getLectures(
                issueCategory, lectname, department, targetYear, classroom, professorName, page, size);
        int totalRecords = lectureService.getSearchCount(
                issueCategory, lectname, department, targetYear, classroom, professorName);
        int totalPages = (int) Math.ceil((double) totalRecords / size);

        // 모델에 데이터 추가
        model.addAttribute("lectureList", lectureList);
        model.addAttribute("currentPage", page);
        model.addAttribute("pageSize", size);
        model.addAttribute("totalPages", totalPages);

        // 검색 조건 유지 (뷰에서 폼 필드 값 채우기)
        model.addAttribute("issueCategory", issueCategory);
        model.addAttribute("lectname", lectname);
        model.addAttribute("department", department);
        model.addAttribute("targetYear", targetYear);
        model.addAttribute("classroom", classroom);
        model.addAttribute("professorName", professorName);

        return "searchResults"; // searchResults.html 렌더링
    }




}
