package com.taa.teacherlib.mail;

import com.taa.teacherlib.business.Appointment;
import com.taa.teacherlib.business.Student;
import com.taa.teacherlib.business.Teacher;
import com.taa.teacherlib.service.DateUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Component;

@Component
public class SendMailService {

    private static final String FROM = "paul.borie@etudiant.univ-rennes1.fr";
    private static final String SUBJECT = "Rendez-Vous TeacherLib";

    @Autowired
    private JavaMailSender emailSender;

    @Autowired
    @Qualifier("templateStudentMail")
    private SimpleMailMessage studentTemplateMail;

    @Autowired
    @Qualifier("templateTeacherMail")
    private SimpleMailMessage teacherTemplateMail;

    public void sendSimpleMessage(String to, String text) {
        SimpleMailMessage message = new SimpleMailMessage();
        message.setFrom(FROM);
        message.setTo(to);
        message.setSubject(SUBJECT);
        message.setText(text);
        emailSender.send(message);
    }

    public void sendConfirmationMails(Student student, Teacher teacher, Appointment appointment) {
        String studentName = student.getName();
        String teacherName = teacher.getName();
        String date = DateUtils.getInstance().prettyPrintDate(appointment.getStart());
        String hour = DateUtils.getInstance().prettyPrintHour(appointment.getStart());
        long duration = appointment.calculateAppointmentDuration();
        String durationString = DateUtils.getInstance().longDurationToString(duration);
        String appointmentName = appointment.getName();
        String appointmentDesc = appointment.getDescription();
        String fullDateStart = DateUtils.getInstance().prettyPrintFullDate(appointment.getStart());
        String fullDateEnd = DateUtils.getInstance().prettyPrintFullDate(appointment.getEnd());

        String studentConfirmationMail = String.format(studentTemplateMail.getText(), studentName, teacherName, date, hour, durationString, appointmentName, appointmentDesc, fullDateStart, fullDateEnd );
        String teacherConfirmationMail = String.format(teacherTemplateMail.getText(), teacherName, studentName, date, hour, durationString,appointmentName, appointmentDesc,fullDateStart, fullDateEnd );

        this.sendSimpleMessage(student.getMail(), studentConfirmationMail);
        this.sendSimpleMessage(teacher.getMail(), teacherConfirmationMail);

    }
}
