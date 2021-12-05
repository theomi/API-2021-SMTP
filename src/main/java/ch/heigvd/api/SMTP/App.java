package ch.heigvd.api.SMTP;

import ch.heigvd.api.SMTP.configuration.ConfigurationManager;
import ch.heigvd.api.SMTP.mail.Mail;
import ch.heigvd.api.SMTP.prank.PrankGenerator;
import ch.heigvd.api.SMTP.smtp.SmtpClient;

public class App {

    public static void main(String... args) {

        if(args.length != 1) {
            System.out.println("Veuillez spécifier un nombre de groupes");
            return;
        }

        ConfigurationManager cm;

        try {
            cm = new ConfigurationManager();
        } catch (Exception e) {
            System.out.println("Une erreur est survenue lors de la lecture de la config");
            return;
        }

        cm.setNumberOfGroups(args[0]);

        SmtpClient client = new SmtpClient(cm.getSmtpServerAddress(), cm.getSmtpServerPort(), cm.isSmtpAuthEnabled(), cm.getSmtpUsername(), cm.getSmtpPassword());
        PrankGenerator pg = new PrankGenerator(cm.getNumberOfGroups());

        try {
            pg.generateMails();
        } catch (RuntimeException e) {
            System.out.println(e.getMessage());
            return;
        }

        if(client.connect()) {
            System.out.println("Start sending emails...");
            for (Mail mail : pg.getMails()) {
                if(!client.sendMail(mail)) {
                    System.out.println("Un email ne s'est pas envoyé correctement");
                }
            }
            client.disconnect();
            System.out.println("Emails sent");
        } else {
            System.out.println("Erreur lors de la connexion au serveur SMTP");
        }

    }
}
