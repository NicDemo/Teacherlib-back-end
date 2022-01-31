package com.taa.teacherlib.rest;

import com.taa.teacherlib.dto.LoginFormDTO;
import com.taa.teacherlib.business.Student;
import com.taa.teacherlib.business.StudentAccount;
import com.taa.teacherlib.business.Teacher;
import com.taa.teacherlib.business.TeacherAccount;
import com.taa.teacherlib.dao.StudentAccountDao;
import com.taa.teacherlib.dao.TeacherAccountDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import javax.servlet.http.HttpServletRequest;
import java.util.Collections;
import java.util.Optional;

@Controller
@RequestMapping("/api/session")
public class SessionController {


    @Autowired
    StudentAccountDao studentAccountRepo;

    @Autowired
    TeacherAccountDao teacherAccountRepo;


    @PostMapping("/student/register")
    public ResponseEntity<Object> studentRegister(@RequestBody LoginFormDTO loginFormDto, HttpServletRequest request) {
        try {
            //Check if username already in db
            if(!studentAccountRepo.existsStudentAccountByUsername(loginFormDto.getUsername())) {
                Student newstudent = new Student(loginFormDto.getName(), loginFormDto.getMail());
                StudentAccount newstudentAccount = new StudentAccount(loginFormDto.getUsername(), loginFormDto.getPassword(), newstudent);
                studentAccountRepo.save(newstudentAccount);
                return new ResponseEntity<>(HttpStatus.OK);
            } else {
                return new ResponseEntity<>(Collections.singletonMap("error", "username already exist"), HttpStatus.CONFLICT);
            }
        } catch(Exception e) {
            System.out.println(e);
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PostMapping("/student/login")
    public ResponseEntity<Object> studentLogin(@RequestParam("username") String username, @RequestParam("password") String password, HttpServletRequest request) {
        Optional<StudentAccount> studentAccountOpt = studentAccountRepo.getByUsername(username);
        if (studentAccountOpt.isPresent()) {
            StudentAccount studentAccount = studentAccountOpt.get();
            if (studentAccount.getPassword().equals(password)) {
                request.getSession().setAttribute("STUDENT_ID", studentAccount.getStudent().getId());
                return new ResponseEntity<>(HttpStatus.OK);
            } else {
                return new ResponseEntity<>(Collections.singletonMap("error", "wrong username or password"), HttpStatus.FORBIDDEN);
            }
        } else {
            return new ResponseEntity<>(Collections.singletonMap("error", "wrong username or password"), HttpStatus.FORBIDDEN);
        }
    }

    @PostMapping("/teacher/register")
    public ResponseEntity<Object> teacherRegister(@RequestBody LoginFormDTO loginFormDto, HttpServletRequest request) {

        if(!teacherAccountRepo.existsTeacherAccountByUsername(loginFormDto.getUsername())) {
            Teacher newteacher = new Teacher(loginFormDto.getName(), loginFormDto.getMail());
            TeacherAccount newteacherAccount = new TeacherAccount(loginFormDto.getUsername(), loginFormDto.getPassword(), newteacher);
            teacherAccountRepo.save(newteacherAccount);
            return new ResponseEntity<>(HttpStatus.OK);
        } else {
            return new ResponseEntity<>(Collections.singletonMap("error", "username already exist"), HttpStatus.CONFLICT);
        }
    }

    @PostMapping("/teacher/login")
    public ResponseEntity<Object> teacherLogin(@RequestParam("username") String username, @RequestParam("password") String password, HttpServletRequest request) {
        Optional<TeacherAccount> teacherAccountOpt = teacherAccountRepo.getByUsername(username);
        if (teacherAccountOpt.isPresent()) {
            TeacherAccount teacherAccount = teacherAccountOpt.get();
            if (teacherAccount.getPassword().equals(password)) {
                request.getSession().setAttribute("TEACHER_ID", teacherAccount.getTeacher().getId());
                return new ResponseEntity<>(HttpStatus.OK);
            } else {
                return new ResponseEntity<>(Collections.singletonMap("error", "wrong username or password"), HttpStatus.FORBIDDEN);
            }
        } else {
            return new ResponseEntity<>(Collections.singletonMap("error", "wrong username or password"), HttpStatus.FORBIDDEN);
        }
    }

    @PostMapping("/logout")
    public ResponseEntity<Object> destroySession(HttpServletRequest request) {
        request.getSession().invalidate();
        return new ResponseEntity<>(HttpStatus.OK);
    }

}
