package ch.heigvd.api.SMTP.prank;

public class Message {

    private final String subject;
    private final String content;

    public Message(String subject, String content) {
        this.subject = subject;
        this.content = content;
    }

    public String getSubject() {
        return subject;
    }

    public String getContent() {
        return content;
    }
}
