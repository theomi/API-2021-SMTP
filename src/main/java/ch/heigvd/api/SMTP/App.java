package ch.heigvd.api.SMTP;

import ch.heigvd.api.SMTP.configuration.ConfigurationManager;
import ch.heigvd.api.SMTP.mail.Mail;
import ch.heigvd.api.SMTP.prank.PrankGenerator;
import ch.heigvd.api.SMTP.smtp.SmtpClient;

public class App {

    public static void main(String... args) {

        ConfigurationManager cm = new ConfigurationManager();
        SmtpClient client = new SmtpClient(cm.getSmtpServerAddress(), cm.getSmtpServerPort(), cm.isSmtpAuthEnabled(), cm.getSmtpUsername(), cm.getSmtpPassword());
        PrankGenerator pg = new PrankGenerator(cm.getNumberOfGroups());

        if(client.connect()) {
            for (Mail mail : pg.generateMails()) {
                System.out.println("Send mail");
                client.sendMail(mail);
            }
            client.disconnect();
        } else {
            System.out.println("Erreur lors de la connexion au serveur SMTP");
        }

    }
}
