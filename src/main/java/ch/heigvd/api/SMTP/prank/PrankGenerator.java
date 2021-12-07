package ch.heigvd.api.SMTP.prank;

import ch.heigvd.api.SMTP.mail.Group;
import ch.heigvd.api.SMTP.mail.Mail;
import ch.heigvd.api.SMTP.mail.Person;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.Collections;
import java.util.LinkedList;
import java.util.Random;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class PrankGenerator {

    private final int nbGroups;
    private final LinkedList<Mail> mails;

    public PrankGenerator(int nbGroups) {
        this.nbGroups = nbGroups;
        mails = new LinkedList<>();
    }

    public void generateMails() {
        LinkedList<Person> victims = generateVictimsList();

        if(victims.isEmpty()) {
            throw new RuntimeException("Erreur lors de la génération de la liste des victimes - il n'y a aucune victime :(");
        }

        if(victims.size() / nbGroups < 3) {
            throw new RuntimeException("Il n'y pas assez de victimes pour le nombre de groupes spécifiés");
        }

        // Parse victims and generate groups
        LinkedList<Message> messages = generateMessages();

        if(messages.isEmpty()) {
            throw new RuntimeException("Erreur lors de la génération de la liste des messages - il n'y a aucun message :(");
        }

        LinkedList<Prank> pranks = generatePranks(victims, messages, nbGroups);

        for (Prank prank : pranks) {
            mails.add(prank.generateMail());
        }
    }

    private LinkedList<Person> generateVictimsList() {
        BufferedReader isr;
        LinkedList<Person> victims = new LinkedList<>();
        String line;
        String regex = "^[a-zA-Z0-9_!#$%&'*+/=?`{|}~^.-]+@[a-zA-Z0-9.-]+$";

        try {
            isr = new BufferedReader(new FileReader(System.getProperty("user.dir") + "/config/victims.utf8", StandardCharsets.UTF_8));

            String[] names = new String[2];
            String mail = "";

            while ((line = isr.readLine()) != null) {
                if(line.equals("--")) {
                    victims.add(new Person(names[0], names[1], mail));
                    continue;
                }

                // Parse name
                names = line.split(" ", 2);
                mail = isr.readLine();

                // Parse email
                Pattern pattern = Pattern.compile(regex, Pattern.CASE_INSENSITIVE);
                Matcher matcher = pattern.matcher(mail);
                if(!matcher.find()) {
                    throw new RuntimeException("L'adresse email suviante n'est pas valide : " + mail);
                }
            }

            Collections.shuffle(victims);
            isr.close();
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }

        return victims;
    }

    private LinkedList<Message> generateMessages() {
        BufferedReader isr;
        String line;
        LinkedList<Message> messages = new LinkedList<>();

        try {
            isr = new BufferedReader(new FileReader(System.getProperty("user.dir") + "/config/messages.utf8", StandardCharsets.UTF_8));
            String subject;
            StringBuilder content = new StringBuilder();

            while ((line = isr.readLine()) != null) {
                // Parse subject
                subject = line.split(" : ")[1];

                // parse content
                while (!((line = isr.readLine()).equals("--"))) {
                    content.append(line).append("\r\n");
                }
                messages.add(new Message(subject, content.toString()));
                content.setLength(0);
            }

            Collections.shuffle(messages);
            isr.close();
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }

        return messages;
    }

    private LinkedList<Prank> generatePranks(LinkedList<Person> victims, LinkedList<Message> messages, int numberOfGroups) {
        LinkedList<Prank> pranks = new LinkedList<>();
        LinkedList<Group> groups = new LinkedList<>();
        Collections.shuffle(victims);

        for(int i = 0; i < numberOfGroups; ++i) {
            Group group = new Group();
            groups.add(group);
        }

        for(Group group : groups) {
            Prank prank = new Prank();
            // Set prank sender and remove from the list
            prank.setSender(victims.remove(0));

            // Set prank group victims
            for(int i = 0; i < 2; ++i) {
                group.addMember(victims.remove(0));
            }
            prank.setVictims(group.getMembers());

            Random rand = new Random();
            Message message = messages.get(rand.nextInt(messages.size()));
            prank.setSubject(message.getSubject());
            prank.setMessage(message.getContent());
            pranks.add(prank);
        }

        return pranks;
    }

    public LinkedList<Mail> getMails() {
        return mails;
    }
}
