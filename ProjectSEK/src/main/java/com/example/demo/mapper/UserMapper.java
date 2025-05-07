package com.example.demo.mapper;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import com.example.demo.model.User;

@Mapper
public interface UserMapper {
	User findByStudentIdAndPassword(
	        @Param("userid") int userid, 
	        @Param("userpw") String userpw
	    );

}
