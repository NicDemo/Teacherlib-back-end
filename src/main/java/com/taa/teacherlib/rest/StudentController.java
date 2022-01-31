package com.taa.teacherlib.rest;

import com.taa.teacherlib.business.Appointment;
import com.taa.teacherlib.business.Availability;
import com.taa.teacherlib.business.Student;
import com.taa.teacherlib.business.Teacher;
import com.taa.teacherlib.dao.AppointmentDao;
import com.taa.teacherlib.dao.StudentDao;
import com.taa.teacherlib.dao.TeacherDao;
import com.taa.teacherlib.dto.AppointmentDTO;
import com.taa.teacherlib.service.DTOMapper;
import com.taa.teacherlib.mail.SendMailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.Optional;


@RestController
@RequestMapping("/api/student")
public class StudentController {

    @Autowired
    private StudentDao studentRepo;

    @Autowired
    private TeacherDao teacherRepo;

    @Autowired
    private AppointmentDao appointmentRepo;

    @Autowired
    private SendMailService sendMailService;


    @GetMapping("/infos")
    public ResponseEntity<Object> getStudentInfos(HttpServletRequest request) {
        HttpSession session = request.getSession();
        Long student_id = (Long) session.getAttribute("STUDENT_ID");
        if (student_id != null) {
            Optional<Student> studentOpt = this.studentRepo.findById(student_id);
            if (studentOpt.isPresent()) {
                Student currentStudent = studentOpt.get();
                return new ResponseEntity<>(currentStudent, HttpStatus.OK);
            } else {
                return new ResponseEntity<>(Collections.singletonMap("error", "you must be login to see your student infos"), HttpStatus.UNAUTHORIZED);
            }
        } else {
            return new ResponseEntity<>(Collections.singletonMap("error", "you must be login to see your student infos"), HttpStatus.UNAUTHORIZED);
        }
    }

    @GetMapping("/appointments")
    public ResponseEntity<Object> getAppointments(HttpServletRequest request){
        HttpSession session = request.getSession();;
        Long student_id = (Long) session.getAttribute("STUDENT_ID");
        //First check if the student exists
        if (student_id != null) {
            Optional<Student> studentOpt = this.studentRepo.findById(student_id);
            if(studentOpt.isPresent()) {
                Student currentStudent = studentOpt.get();
                Date today = new Date();
                List<Appointment> appointments = appointmentRepo.getAppointmentByStudentAndStartIsAfter(currentStudent, today);
                return new ResponseEntity<>(appointments, HttpStatus.OK);
            } else return new ResponseEntity<>(Collections.singletonMap("error", "you must be login as a student before taking an appointment") ,HttpStatus.UNAUTHORIZED);
        } else return new ResponseEntity<>(Collections.singletonMap("error", "you must be login as a student before taking an appointment"), HttpStatus.UNAUTHORIZED);
    }

    @PostMapping("/bookappointment")
    public ResponseEntity<Object> bookAppointment(@RequestBody AppointmentDTO appointmentDto, HttpServletRequest request){
        HttpSession session = request.getSession();
        Long student_id = (Long) session.getAttribute("STUDENT_ID");
        //First check if student exist
        if (student_id != null) {
            Optional<Student> studentOpt = this.studentRepo.findById(student_id);
            if(studentOpt.isPresent()) {
                Student currentStudent = studentOpt.get();
                //Check if there is the availability posted is well an availability of the teacher
                Availability postedAvailability = new Availability(appointmentDto.getStartDate(), appointmentDto.getEndDate());
                Long teacherId = appointmentDto.getTeacherId();
                Optional<Teacher> teacherOpt  = teacherRepo.findById(teacherId);
                if(teacherOpt.isPresent()) {
                    Teacher currentTeacher = teacherOpt.get();
                    List<Availability> teacherAvailabilities = currentTeacher.getAvailabilities();
                    if (teacherAvailabilities.contains(postedAvailability)) {
                        //On crééer un nouvel appointment
                        Appointment newAppointment = DTOMapper.getInstance().appointmentDTOToAppointment(appointmentDto, currentTeacher, currentStudent);
                        //on ajoute l'appointment au student et au teacher (que add au student car l'ajout est en cascade
                        currentStudent.addAppointment(newAppointment);
                        studentRepo.save(currentStudent);
                        //On supprime l'availability des availabilities du teacher
                        currentTeacher.getAvailabilities().remove(postedAvailability);
                        teacherRepo.save(currentTeacher);
                        //On envoie un mail au teacher et au student concerné par l'appointment
                        this.sendMailService.sendConfirmationMails(currentStudent, currentTeacher, newAppointment);

                        return new ResponseEntity<>(HttpStatus.OK);
                    } else {
                        return  new ResponseEntity<>(Collections.singletonMap("error", "you must enter a valid availability to take an appointment with this teacher"),HttpStatus.BAD_REQUEST); }

                } else return new ResponseEntity<>(Collections.singletonMap("error", "you must take an appointment with an existing teacher"), HttpStatus.BAD_REQUEST);

            } else return new ResponseEntity<>(Collections.singletonMap("error", "you must be login as a student before taking an appointment") ,HttpStatus.UNAUTHORIZED);

        } else return new ResponseEntity<>(Collections.singletonMap("error", "you must be login as a student before taking an appointment"), HttpStatus.UNAUTHORIZED);
    }

}
