package ch.heigvd.api.SMTP;

import ch.heigvd.api.SMTP.configuration.ConfigurationManager;
import ch.heigvd.api.SMTP.smtp.SmtpClient;

public class App {

    public static void main(String... args) {

        ConfigurationManager cm = new ConfigurationManager();
        SmtpClient client = new SmtpClient(cm.getSmtpServerAddress(), cm.getSmtpServerPort(), cm.isSmtpAuthEnabled(), cm.getSmtpUsername(), cm.getSmtpPassword());

        // client.connect();
        // client.send()
    }
}
