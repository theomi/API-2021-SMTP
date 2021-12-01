package ch.heigvd.api.SMTP;

import ch.heigvd.api.SMTP.configuration.ConfigurationManager;
import ch.heigvd.api.SMTP.mail.Mail;
import ch.heigvd.api.SMTP.prank.PrankGenerator;
import ch.heigvd.api.SMTP.smtp.SmtpClient;

public class App {

    public static void main(String... args) {

        ConfigurationManager cm = new ConfigurationManager();

        if(args.length != 1) {
            System.out.println("Veuillez spécifier un nombre de groupes");
            return;
        }

        cm.setNumberOfGroups(args[0]);

        SmtpClient client = new SmtpClient(cm.getSmtpServerAddress(), cm.getSmtpServerPort(), cm.isSmtpAuthEnabled(), cm.getSmtpUsername(), cm.getSmtpPassword());
        PrankGenerator pg = new PrankGenerator(cm.getNumberOfGroups());

        if(client.connect()) {
            System.out.println("Start sending emails...");
            for (Mail mail : pg.generateMails()) {
                client.sendMail(mail);
            }
            client.disconnect();
            System.out.println("Emails sent");
        } else {
            System.out.println("Erreur lors de la connexion au serveur SMTP");
        }

    }
}
