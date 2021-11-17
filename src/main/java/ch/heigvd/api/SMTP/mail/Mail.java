package ch.heigvd.api.SMTP.mail;

public class Mail {

    private String from;
    private String to;
    private String cc;

    private String subject;
    private String body;

    public Mail(String from, String to, String cc, String subject, String body) {
        this.from = from;
        this.to = to;
        this.cc = cc;
        this.subject = subject;
        this.body = body;
    }

    public String getFrom() {
        return from;
    }

    public String getTo() {
        return to;
    }

    public String getCc() {
        return cc;
    }

    public String getSubject() {
        return subject;
    }

    public String getBody() {
        return body;
    }
}
