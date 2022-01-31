package com.taa.teacherlib.dao;

import com.taa.teacherlib.business.Teacher;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import javax.transaction.Transactional;
import java.util.Date;
import java.util.List;

@Transactional
public interface TeacherDao extends JpaRepository<Teacher, Long> {

    boolean existsTeacherByName(String name);




}
