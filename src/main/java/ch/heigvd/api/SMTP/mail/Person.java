package ch.heigvd.api.SMTP.mail;

public class Person {

    private final String firstName;
    private final String lastName;
    private final String mail;

    public Person(String firstName, String lastName, String address) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.mail = address;
    }

    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public String getMail() {
        return mail;
    }
}
