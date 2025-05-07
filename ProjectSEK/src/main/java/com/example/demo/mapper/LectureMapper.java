package com.example.demo.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import com.example.demo.dto.LectureDTO;

@Mapper
public interface LectureMapper {
    List<LectureDTO> getList(@Param("pageSize") int pageSize, @Param("offset") int offset);

    List<LectureDTO> findLectures(@Param("issueCategory") String issueCategory, @Param("courseName") String courseName,
                                  @Param("department") String department, @Param("targetYear") String targetYear,
                                  @Param("classroom") String classroom, @Param("professorName") String professorName,
                                  @Param("pageSize") int pageSize, @Param("offset") int offset);

    int getTotalCount();

    int countLectures(@Param("issueCategory") String issueCategory, @Param("courseName") String courseName,
                      @Param("department") String department, @Param("targetYear") String targetYear,
                      @Param("classroom") String classroom, @Param("professorName") String professorName);
    
    int getTotalCountForSearch(@Param("issueCategory") String issueCategory,
            @Param("courseName") String courseName,
            @Param("department") String department,
            @Param("targetYear") String targetYear,
            @Param("classroom") String classroom,
            @Param("professorName") String professorName);

	void registerCourse(@Param("userid") int userid, @Param("lectureid") int lectureid);
	List<LectureDTO> getRegisteredLectures(@Param("userId") int userId);

    
}
