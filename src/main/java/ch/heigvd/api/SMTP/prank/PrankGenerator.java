package ch.heigvd.api.SMTP.prank;

import ch.heigvd.api.SMTP.mail.Group;
import ch.heigvd.api.SMTP.mail.Mail;
import ch.heigvd.api.SMTP.mail.Person;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.Collections;
import java.util.LinkedList;
import java.util.Random;

/*
 ATTENTION : Classe selon wsadaigi qui contient le plus de logique
 Pour chaque groupe on crée une nouvelle prank
 */
public class PrankGenerator {

    private int nbrGroups;

    public PrankGenerator(int nbrGroups) {
        this.nbrGroups = nbrGroups;
    }

    public LinkedList<Mail> generateMails() {
        LinkedList<Mail> mails = new LinkedList<>();
        LinkedList<Person> victims = generateVictimsList();

        if(victims.size() / nbrGroups < 3) {
            throw new RuntimeException("Il n'y pas assez de victimes pour le nombre de groupes spécifiés");
        }

        // Parse victims and generate groups
        LinkedList<Message> messages = generateMessages();
        LinkedList<Prank> pranks = generatePranks(victims, messages, nbrGroups);

        for (Prank prank : pranks) {
            mails.add(prank.generateMail());
        }

        return mails;
    }

    private LinkedList<Person> generateVictimsList() {
        BufferedReader isr = null;
        LinkedList<Person> victims = new LinkedList<>();
        String line;

        try {
            isr = new BufferedReader(new FileReader("./src/main/resources/victims.utf8"));

            String[] names = new String[2];
            String mail = "";

            while ((line = isr.readLine()) != null) {
                if(line.equals("--")) {
                    victims.add(new Person(names[0], names[1], mail));
                    continue;
                }

                // Parse name
                names = line.split(" ");
                mail = isr.readLine();
            }

        } catch (IOException e) {
            System.out.println(e.getMessage());
        }

        Collections.shuffle(victims);
        return victims;
    }

    private LinkedList<Message> generateMessages() {
        BufferedReader isr = null;
        String line;
        LinkedList<Message> messages = new LinkedList<>();

        try {
            isr = new BufferedReader(new FileReader("./src/main/resources/messages.utf8"));
            String subject = null;
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

        } catch (IOException e) {
            System.out.println(e.getMessage());
        }

        Collections.shuffle(messages);
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
                group.addMember(victims.remove(i));
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
}
