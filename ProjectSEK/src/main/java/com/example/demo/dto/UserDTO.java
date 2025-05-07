package com.example.demo.dto;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Data
public class UserDTO {

	private int userid;
	private String username;
	private String grade;
	private String department;
}
