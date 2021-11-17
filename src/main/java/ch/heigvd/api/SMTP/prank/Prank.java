package ch.heigvd.api.SMTP.prank;

import ch.heigvd.api.SMTP.mail.Mail;
import ch.heigvd.api.SMTP.mail.Person;

import java.util.ArrayList;

public class Prank {

    private ArrayList<Person> victims;
    private Person sender;
    private String message;
    private String subject;


    // génère le mail proprement avec les champs, la blague et le contenu de la blague
    public Prank(ArrayList<Person> victims, Person sender, String message) {
        this.victims = victims;
        this.sender = sender;
        this.message = message;
    }

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
}
