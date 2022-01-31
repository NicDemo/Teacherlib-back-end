package com.taa.teacherlib.rest;

import com.taa.teacherlib.dao.AppointmentDao;
import com.taa.teacherlib.dao.AvailabilityDao;
import com.taa.teacherlib.dto.AvailabilityDTO;
import com.taa.teacherlib.business.Appointment;
import com.taa.teacherlib.business.Availability;
import com.taa.teacherlib.business.Teacher;
import com.taa.teacherlib.dao.TeacherDao;
import com.taa.teacherlib.dto.TeacherAppointmentDTO;
import com.taa.teacherlib.service.DTOMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.*;

@RestController
@RequestMapping("/api/teacher")
public class TeacherController {

    @Autowired
    private TeacherDao teacherRepo;

    @Autowired
    private AppointmentDao appointmentRepo;

    @Autowired
    private AvailabilityDao availabilityRepo;
    //This request has no Authentification mechanism involved we estimate that the list of the teachers and their availabilities can be publicly accessible in the application, anyone can access this uri

    @GetMapping("/teachers")
    public ResponseEntity<List<Teacher>> getTeachers(HttpServletRequest request) {
        Optional<List<Teacher>> teachersOpt = Optional.ofNullable(this.teacherRepo.findAll());
        if (teachersOpt.isPresent()) {
            List<Teacher> teachers = teachersOpt.get();
            for(Teacher t : teachers){
                t.removeOutDatedAvailabilities();
            }
            return new ResponseEntity<>(teachers, HttpStatus.OK);
        } else {
            List<Teacher> teachers = new ArrayList<>();
            return new ResponseEntity<>(teachers, HttpStatus.OK);
        }
    }

    @GetMapping("/getavailabilities")
    public ResponseEntity<Object> getTeacherAvailabilities(HttpServletRequest request, @RequestParam("id") String id){
        HttpSession session = request.getSession();
        Long teacherId = Long.valueOf(id);
        Date now = new Date();
        Optional<List<Availability>> availabilities = availabilityRepo.getAvailabilityByTeacherIdAndStartIsAfter(teacherId, now);
        if (availabilities.isPresent()){
            return new ResponseEntity<>(availabilities.get(), HttpStatus.OK);
        } else return new ResponseEntity<>(Collections.singletonMap("error", "you must provide the id of an existing teacher" ), HttpStatus.BAD_REQUEST);
    }

    @GetMapping("/infos")
    public ResponseEntity<Object> getTeacherInfos(HttpServletRequest request) {
        HttpSession session = request.getSession();
        Long teacher_id = (Long) session.getAttribute("TEACHER_ID");
        if (teacher_id != null) {
            Optional<Teacher> teacherOpt = this.teacherRepo.findById(teacher_id);
            if (teacherOpt.isPresent()) {
                Teacher currentTeacher = teacherOpt.get();
                return new ResponseEntity<>(currentTeacher, HttpStatus.OK);
            } else {
                return new ResponseEntity<>(Collections.singletonMap("error", "you must login as a teacher before accessing your teacher infos"), HttpStatus.UNAUTHORIZED);
            }
        } else {
            return new ResponseEntity<>(Collections.singletonMap("error", "you must login as a teacher before accessing your teacher infos"), HttpStatus.UNAUTHORIZED);
        }
    }

    @GetMapping(value = "/appointments")
    public ResponseEntity<List<TeacherAppointmentDTO>> getAppointments(HttpServletRequest request){
        HttpSession session = request.getSession();
        Long teacher_id = (Long) session.getAttribute("TEACHER_ID");
        if (teacher_id != null) {
            Optional<Teacher> teacherOpt = this.teacherRepo.findById(teacher_id);
            if (teacherOpt.isPresent()){
                Teacher currentTeacher = teacherOpt.get();
                Date now = new Date();
                List<Appointment> appointments = appointmentRepo.getAppointmentByTeacher(currentTeacher);
                List<TeacherAppointmentDTO> teacherAppointmentDTOS = DTOMapper.getInstance().getListAppointmentDTO(appointments);
                return new ResponseEntity<>(teacherAppointmentDTOS, HttpStatus.OK);
            }else return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
        }else {
            return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
        }
    }

    @PostMapping("/availabilities")
    public ResponseEntity<Object> postAvailabilities(@RequestBody List<AvailabilityDTO> availabilityDTOS, HttpServletRequest request) {
        HttpSession session = request.getSession();
        Long teacher_id = (Long) session.getAttribute("TEACHER_ID");
        if (teacher_id != null) {
            // c'est bien un teacher qui fait la requete, allons chercher le teacher correspondant à cet id dans la base
            Teacher currentTeacher = this.teacherRepo.getById(teacher_id);

            for (AvailabilityDTO availabilityDto : availabilityDTOS) {
                Availability availability = new Availability(availabilityDto.getStartDate(), availabilityDto.getEndDate());
                //on check si l'Availability est bien valide c'est à dire qu'elle n'est pas passée
                if ( availability.isValid()){
                    //on check si il n'existe pas déjà une Availability à cette date.
                    if (!currentTeacher.getAvailabilities().contains(availability)){
                        availability.setTeacher(currentTeacher);
                        currentTeacher.addAvailability(availability);
                        this.teacherRepo.save(currentTeacher);
                    }
                } else return new ResponseEntity<>(Collections.singletonMap("error", "you must post availabilities that are not outdated"), HttpStatus.CONFLICT);
            }
            return new ResponseEntity<>(HttpStatus.OK);
        } else {
            return new ResponseEntity<>(Collections.singletonMap("error", "you must login as a teacher before adding availabilities to your schedule"), HttpStatus.UNAUTHORIZED);
        }
    }
}