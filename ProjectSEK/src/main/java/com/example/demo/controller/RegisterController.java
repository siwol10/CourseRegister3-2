package com.example.demo.controller;

import java.util.List;

import org.springframework.dao.DuplicateKeyException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.example.demo.dto.LectureDTO;
import com.example.demo.dto.UserDTO;
import com.example.demo.model.User;
import com.example.demo.service.LectureService;

import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;

@Controller
@RequiredArgsConstructor
public class RegisterController {
	private final LectureService lectureService;

	@GetMapping("/register")
	public String searchLectures(
	        @RequestParam(value = "issue-category", required = false) String issueCategory,
	        @RequestParam(value = "lectname", required = false) String courseName,
	        @RequestParam(value = "department", required = false) String department,
	        @RequestParam(value = "target-year", required = false) String targetYear,
	        @RequestParam(value = "classroom", required = false) String classroom,
	        @RequestParam(value = "professorname", required = false) String professorName,
	        @RequestParam(name = "page", defaultValue = "1") int page,
	        @RequestParam(name = "size", defaultValue = "5") int size,
	        Model model,
	        HttpSession session) {

	    // 검색 결과 처리
	    List<LectureDTO> lectureList = null;
	    int totalRecords = 0;
	    if (issueCategory != null || courseName != null || department != null ||
	        targetYear != null || classroom != null || professorName != null) {

	        lectureList = lectureService.getLectures(
	        		issueCategory, courseName, department, targetYear, classroom, professorName, page, size);
	        totalRecords = lectureService.getSearchCount(
	                issueCategory, courseName, department, targetYear, classroom, professorName);
	    }

	    // 현재 로그인한 사용자의 신청 강의 조회
	    User user = (User) session.getAttribute("user"); // 세션에서 User 객체를 가져옴
	    if (user == null) {
	        return "redirect:/login"; // 세션에 유저 정보가 없으면 로그인 페이지로 리다이렉트
	    }
	    UserDTO userDTO = new UserDTO();
	    userDTO.setUserid(user.getUserid());
	    userDTO.setUsername(user.getUsername());
	    userDTO.setGrade(user.getGrade());
	    userDTO.setDepartment(user.getDepartment());

	    List<LectureDTO> registeredLectures = lectureService.getRegisteredLectures(user.getUserid());

	    // 모델에 데이터 추가
	    model.addAttribute("lectures", lectureList);
	    model.addAttribute("currentPage", page);
	    model.addAttribute("pageSize", size);
	    model.addAttribute("totalPages", (int) Math.ceil((double) totalRecords / size));
	    model.addAttribute("registeredLectures", registeredLectures);
	    model.addAttribute("user", userDTO); // UserDTO 객체를 모델에 추가

	    return "register";
	}

	
	@PostMapping("/apply")
	@ResponseBody
	public ResponseEntity<String> registerCourse(@RequestParam(name = "userid") int userid, @RequestParam(name = "lectureid") int lectureid) {
	    try {
	        lectureService.registerCourse(userid, lectureid);
	        return ResponseEntity.ok("수강신청 성공");
	    } 
	    catch (DuplicateKeyException e) {
	        // 중복 데이터 삽입 예외 처리
	        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("이미 수강 신청한 강의입니다.");}
	    catch (Exception e) {
	    	e.printStackTrace();
	        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("수강신청 실패");
	    }
	}



}
