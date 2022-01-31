package com.taa.teacherlib.dao;

import com.taa.teacherlib.business.Appointment;
import com.taa.teacherlib.business.Student;
import com.taa.teacherlib.business.Teacher;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import javax.transaction.Transactional;
import java.util.Date;
import java.util.List;
import java.util.Optional;

@Transactional
public interface AppointmentDao extends JpaRepository<Appointment,Long> {

    @Query("select a from Appointment as a where a.teacher = :teacher and a.start > :today")
    List<Appointment> getAppointmentByTeacherAndStartIsAfter(Teacher teacher, Date today);

    @Query("select a from Appointment as a where a.teacher = :student and a.start > :today")
    List<Appointment> getAppointmentByStudentAndStartIsAfter(Student student, Date today);

    List<Appointment> getAppointmentByTeacher(Teacher teacher);

    List<Appointment> getAppointmentByStudent(Student student);


}
