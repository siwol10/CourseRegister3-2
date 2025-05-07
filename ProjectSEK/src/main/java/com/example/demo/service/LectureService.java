package com.example.demo.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.example.demo.dto.LectureDTO;
import com.example.demo.repository.LectureRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class LectureService {

	private final LectureRepository lectureRepository;

	public List<LectureDTO> getList(int page, int size) {
		int offset = (page - 1) * size;
		return lectureRepository.getPagedList(offset, size);
	}

	public List<LectureDTO> getLectures(String issueCategory, String courseName, String department, String targetYear,
			String classroom, String professorName, int page, int size) {
		int offset = (page - 1) * size;
		return lectureRepository.getLectures(issueCategory, courseName, department, targetYear, classroom,
				professorName, size, offset);
	}

	public int getTotalCount() {
		return lectureRepository.getTotalCount();
	}

	public int getSearchCount(String issueCategory, String courseName, String department, String targetYear,
			String classroom, String professorName) {
		return lectureRepository.getSearchCount(issueCategory, courseName, department, targetYear, classroom,
				professorName);
	}

	public int getTotalCountForSearch(String issueCategory, String courseName, String department, String targetYear,
			String classroom, String professorName) {
		return lectureRepository.getTotalCountForSearch(issueCategory, courseName, department, targetYear, classroom,
				professorName);
	}
	
	public void registerCourse(int userid, int lectureid) {
        lectureRepository.registerCourse(userid, lectureid);
		
    }
	public List<LectureDTO> getRegisteredLectures(int userId) {
	    return lectureRepository.getRegisteredLectures(userId);
	}


}
