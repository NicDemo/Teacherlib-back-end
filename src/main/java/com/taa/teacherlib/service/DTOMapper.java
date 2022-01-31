package com.taa.teacherlib.service;

import com.taa.teacherlib.business.Appointment;
import com.taa.teacherlib.business.Student;
import com.taa.teacherlib.business.Teacher;
import com.taa.teacherlib.dto.AppointmentDTO;
import com.taa.teacherlib.dto.TeacherAppointmentDTO;

import java.util.ArrayList;
import java.util.List;

public class DTOMapper {

    private static DTOMapper instance = null;

    public static DTOMapper getInstance(){
        if (instance == null)
            instance = new DTOMapper();
        return instance;
    }

    public Appointment appointmentDTOToAppointment(AppointmentDTO appointmentDto, Teacher teacher, Student student) {
        return new Appointment(appointmentDto.getName(), appointmentDto.getDescription(), appointmentDto.getStartDate(), appointmentDto.getEndDate(), teacher, student);
    }

    public TeacherAppointmentDTO appointmentToTeacherAppointmentDTO(Appointment appointment) {
        return new TeacherAppointmentDTO(appointment.getId(), appointment.getName(), appointment.getDescription(), appointment.getStart(), appointment.getEnd(), appointment.getStudent());
    }

    public List<TeacherAppointmentDTO> getListAppointmentDTO(List<Appointment> appointments){
        List<TeacherAppointmentDTO> res = new ArrayList<>();
        for (Appointment a : appointments){
            res.add(appointmentToTeacherAppointmentDTO(a));
        }
        return res;
    }

}
