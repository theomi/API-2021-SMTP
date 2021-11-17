package ch.heigvd.api.SMTP;

import ch.heigvd.api.SMTP.configuration.ConfigurationManager;
import ch.heigvd.api.SMTP.mail.Mail;
import ch.heigvd.api.SMTP.prank.PrankGenerator;
import ch.heigvd.api.SMTP.smtp.SmtpClient;

public class App {

    public static void main(String... args) {

        ConfigurationManager cm = new ConfigurationManager();
        SmtpClient client = new SmtpClient(cm.getSmtpServerAddress(), cm.getSmtpServerPort());

        PrankGenerator pg = new PrankGenerator();

        // client.connect();
        // client.send()
    }
}
