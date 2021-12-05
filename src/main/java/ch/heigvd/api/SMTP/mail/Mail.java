package ch.heigvd.api.SMTP.mail;

public class Mail {

    private final String from;
    private final String fromWithName;
    private final String to;
    private final String toWithName;
    private final String cc;

    private final String subject;
    private final String body;

    public Mail(String from, String fromWithName, String to, String toWithName, String cc, String subject, String body) {
        this.from = from;
        this.fromWithName = fromWithName;
        this.to = to;
        this.toWithName = toWithName;
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

    public String getFromWithName() { return fromWithName; }

    public String getToWithName() { return toWithName; }
}
