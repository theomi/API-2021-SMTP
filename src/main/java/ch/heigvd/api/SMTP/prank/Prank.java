package ch.heigvd.api.SMTP.prank;

import ch.heigvd.api.SMTP.mail.Mail;
import ch.heigvd.api.SMTP.mail.Person;

import java.util.LinkedList;

public class Prank {

    private LinkedList<Person> victims;
    private final StringBuilder victimsWithNames = new StringBuilder();
    private Person sender;
    private String message;
    private String subject;

    private String getMailVictims() {
        StringBuilder victimsString = new StringBuilder();
        for (Person victim : victims) {
            if(victimsString.length() != 0) {
                victimsString.append(", ");
                victimsWithNames.append(", ");
            }
            victimsWithNames.append(victim.getFirstName() + " " + victim.getLastName() + "<" + victim.getMail() + ">");
            victimsString.append("<" + victim.getMail() + ">");
        }
        return victimsString.toString();
    }


    private String getVictimsWithNames() {
        return victimsWithNames.toString();
    }

    private String getMailSender() {
        return "<" +  sender.getMail() + ">";
    }

    private String getMailSenderWithName() {
        return sender.getFirstName() + " " + sender.getLastName() + getMailSender();
    }

    private String getMailMesage() {
        return message;
    }

    private String getMailSubject() {
        return subject;
    }

    public Mail generateMail() {
       return new Mail(getMailSender(), getMailSenderWithName(), getMailVictims(), getVictimsWithNames(), null, getMailSubject(), getMailMesage());
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

    public void setSubject(String subject){ this.subject = subject; }
}
