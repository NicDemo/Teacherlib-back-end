package com.taa.teacherlib.dao;

import com.taa.teacherlib.business.Student;
import org.springframework.data.jpa.repository.JpaRepository;
import javax.transaction.Transactional;


@Transactional
public interface StudentDao  extends JpaRepository<Student,Long> {

    boolean existsStudentByName(String name);


}
