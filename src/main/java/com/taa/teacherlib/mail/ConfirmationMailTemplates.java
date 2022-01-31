package com.taa.teacherlib.mail;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.mail.SimpleMailMessage;



@Configuration
public class ConfirmationMailTemplates {

    @Bean(name="templateStudentMail")
    public SimpleMailMessage templateStudentConfirMail()  {
        SimpleMailMessage message = new SimpleMailMessage();
        String text = "Bonjour %s, \n\nNous vous confirmons que votre demande de rendez-vous avec le professeur %s le %s à %s pour une durée de %s a bien été effectuée. \n\nRécapitulatif du rendez-vous: \n\nNom: %s \nDescription: %s \nDébut: %s \nFin: %s";
        message.setText(text);
        return message;
    }

    @Bean(name="templateTeacherMail")
    public SimpleMailMessage templateTeacherConfirmMail()  {
        SimpleMailMessage message = new SimpleMailMessage();
        String text = "Bonjour %s, \n\nL'étudiant %s souhaite prendre un rendez-vous avec vous le %s à %s pour une durée de %s. \n\nRécapitulatif du rendez-vous: \n\nNom: %s \nDescription: %s \nDébut: %s \nFin: %s";
        message.setText(text);
        return message;
    }

}
