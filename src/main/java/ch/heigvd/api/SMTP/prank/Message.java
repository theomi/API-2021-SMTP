package ch.heigvd.api.SMTP.prank;

public class Message {

    private String subject;
    private String content;

    public Message(String subject, String content) {
        this.subject = subject;
        this.content = content;
    }
}
