package ch.heigvd.api.SMTP.prank;

import ch.heigvd.api.SMTP.mail.Mail;
import ch.heigvd.api.SMTP.mail.Person;

import java.util.ArrayList;
import java.util.LinkedList;

public class Prank {

    private LinkedList<Person> victims;
    private Person sender;
    private String message;
    private String subject;

    // génère le mail proprement avec les champs, la blague et le contenu de la blague
    public Prank(LinkedList<Person> victims, Person sender, String subject, String message) {
        this.victims = victims;
        this.sender = sender;
        this.message = message;
        this.subject = subject;
    }

    public Prank(){}

    private String getMailVictims() {
        StringBuilder victimsString = new StringBuilder();
        for (Person victim : victims) {
            if(victimsString.length() != 0) {
                victimsString.append(", ");
            }
            victimsString.append(victim.getFirstName() + " " + victim.getLastName() + "<" + victim.getMail() + ">");
        }
        return victimsString.toString();
    }

    private String getMailSender() {
        return sender.getFirstName() + " " + sender.getLastName() + "<" +  sender.getMail() + ">";
    }

    private String getMailMesage() {
        return message;
    }

    private String getMailSubject() {
        return subject;
    }

    public Mail generateMail() {
       return new Mail(getMailSender(), getMailVictims(), null, getMailSubject(), getMailMesage());
    }

    public void setSender(Person sender) {
        this.sender = sender;
    }

    public void setVictims(LinkedList<Person> victims) {
        this.victims = victims;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
