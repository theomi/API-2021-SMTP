package ch.heigvd.api.SMTP.mail;

import java.util.ArrayList;

public class Group {

    private final ArrayList<Person> members = new ArrayList<Person>();

    public void addMember(Person person) {
        members.add(person);
    }

    public ArrayList<Person> getMembers() {
        return members;
    }
}
