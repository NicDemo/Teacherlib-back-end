package com.taa.teacherlib.service;

import com.taa.teacherlib.business.*;
import com.taa.teacherlib.dao.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;

import java.text.ParseException;
import java.util.Date;

@Component
public class DataLoader implements ApplicationRunner {

    private TeacherAccountDao teacherAccountRepo;
    private StudentAccountDao studentAccountRepo;
    private TeacherDao teacherRepo;
    private StudentDao studentRepo;
    private AppointmentDao appointmentRepo;




    @Autowired
    public DataLoader(TeacherAccountDao teacherAccountRepo, StudentAccountDao studentAccountRepo, TeacherDao teacherRepo, StudentDao studentRepo, AppointmentDao appointmentRepo) {
        this.teacherAccountRepo = teacherAccountRepo;
        this.studentAccountRepo = studentAccountRepo;
        this.teacherRepo = teacherRepo;
        this.studentRepo = studentRepo;
        this.appointmentRepo = appointmentRepo;

    }

    public void run(ApplicationArguments args) throws ParseException {

        if (!teacherAccountRepo.existsTeacherAccountByUsername("olivier")) {


            //add teacher Barais
            Teacher olivier = new Teacher("Olivier Barais", "nicolas.demongeot@etudiant.univ-rennes1.fr");
            TeacherAccount olivierAccount = new TeacherAccount("olivier", "pass", olivier);
            LoaderUtils.getInstance().setAvailabilitiesNextDaysBeforeWeekForTeacher(olivier);
            LoaderUtils.getInstance().setFullWeekForTeacher(4, olivier);
            teacherAccountRepo.save(olivierAccount);

            //add teacher Grazon
            Teacher grazon = new Teacher("Anne Grazon", "nicolas.demongeot@etudiant.univ-rennes1.fr");
            TeacherAccount grazonAccount = new TeacherAccount("anne", "pass", grazon);
            LoaderUtils.getInstance().setAvailabilitiesNextDaysBeforeWeekForTeacher(grazon);
            LoaderUtils.getInstance().setFullWeekForTeacher(4, grazon);
            teacherAccountRepo.save(grazonAccount);

            //add student Paul
            Student paul = new Student("Paul Borie", "paul.borie@etudiant.univ-rennes1.fr");
            StudentAccount paulAccount = new StudentAccount("paul", "pass", paul);
            studentAccountRepo.save(paulAccount);


            //add student Jean
            Student jean = new Student("Jean Derieux", "jean.derieux@etudiant.univ-rennes1.fr");
            StudentAccount jeanAccount = new StudentAccount("jean", "pass", jean);
            studentAccountRepo.save(jeanAccount);

            //add appointments to Paul
            Date start2 = new DateUtils().getInstance().dateFromString("10-12-2021 16:00");
            Date end2 = new DateUtils().getInstance().dateFromString("10-12-2021 17:00");
            Appointment appt2 = new Appointment("TLC", "Docker and Kubernetes", start2, end2, olivier, paul);
            paul.addAppointment(appt2);

            Date start4 = new DateUtils().getInstance().dateFromString("06-12-2021 17:00");
            Date end4 = new DateUtils().getInstance().dateFromString("06-12-2021 18:00");
            Appointment appt4 = new Appointment("MMM", "Flutter explanation", start4, end4, grazon, paul);
            paul.addAppointment(appt4);
            studentRepo.save(paul);

            //add appointments to Jean
            Date start = new DateUtils().getInstance().dateFromString("09-12-2021 10:00");
            Date end = new DateUtils().getInstance().dateFromString("09-12-2021 11:00");
            Appointment appt = new Appointment("TAA", "soutien individuel", start, end, olivier, jean);
            jean.addAppointment(appt);

            Date start1 = new DateUtils().getInstance().dateFromString("11-12-2021 10:00");
            Date end1 = new DateUtils().getInstance().dateFromString("11-12-2021 11:00");
            Appointment appt1 = new Appointment("TLC", "Quarkus", start1, end1, grazon, jean);
            jean.addAppointment(appt1);
            studentRepo.save(jean);

        }
    }
}
