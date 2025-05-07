package com.example.demo.dto;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Data
public class LectureDTO {

	private int lectureid;
	private String lectname;
	private String grade;
	private String department;
	private String classroom;
	private String timetable;
	private String lectcategory;
	private String professorname;
}
