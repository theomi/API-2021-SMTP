package ch.heigvd.api.SMTP.smtp;

import ch.heigvd.api.SMTP.mail.Mail;

import java.net.Socket;

public class SmtpClient {

    private final String host;
    private final int port;

    public SmtpClient(String host, int port) {
        this.host = host;
        this.port = port;
    }

    public void sendMail(Mail mail) {
        // send mail
    }


}
