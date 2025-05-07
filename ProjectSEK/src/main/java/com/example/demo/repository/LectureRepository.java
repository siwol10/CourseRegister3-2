package com.example.demo.repository;

import java.util.List;
import java.util.Map;

import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.stereotype.Repository;

import com.example.demo.dto.LectureDTO;
import com.example.demo.mapper.LectureMapper;

import lombok.RequiredArgsConstructor;

@Repository
@RequiredArgsConstructor
public class LectureRepository {

	private final SqlSessionTemplate sql;
	private final LectureMapper lectureMapper;
	
	public List<LectureDTO> getLectures(String issueCategory, String courseName, String department, String targetYear,
			String classroom, String professorName, int pageSize, int offset) {
		return lectureMapper.findLectures(issueCategory, courseName, department, targetYear, classroom, professorName,
				pageSize, offset);
	}
	public List<LectureDTO> getPagedList(int offset, int size) {
		return lectureMapper.getList(size, offset);
	}

	public int getTotalCount() {
		return lectureMapper.getTotalCount();
	}

	public int getSearchCount(String issueCategory, String courseName, String department, String targetYear,
			String classroom, String professorName) {
		return lectureMapper.countLectures(issueCategory, courseName, department, targetYear, classroom, professorName);
	}

	public int getTotalCountForSearch(String issueCategory, String courseName, String department, String targetYear,
			String classroom, String professorName) {
		return lectureMapper.getTotalCountForSearch(issueCategory, courseName, department, targetYear, classroom, professorName);
	}
	public void registerCourse(int userid, int lectureid) {
		lectureMapper.registerCourse(userid,lectureid);
	}
	public List<LectureDTO> getRegisteredLectures(int userId) {
	    return lectureMapper.getRegisteredLectures(userId);
	}

}
