package ch.heigvd.api.SMTP.mail;

import java.util.LinkedList;

public class Group {

    private final LinkedList<Person> members = new LinkedList<>();

    public void addMember(Person person) {
        members.add(person);
    }

    public LinkedList<Person> getMembers() {
        return members;
    }
}
