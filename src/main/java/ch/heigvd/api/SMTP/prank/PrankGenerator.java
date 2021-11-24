package ch.heigvd.api.SMTP.prank;

import ch.heigvd.api.SMTP.configuration.ConfigurationManager;
import ch.heigvd.api.SMTP.mail.Group;
import ch.heigvd.api.SMTP.mail.Person;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.Collections;
import java.util.LinkedList;

/*
 ATTENTION : Classe selon wsadaigi qui contient le plus de logique
 Pour chaque groupe on crée une nouvelle prank
 */
public class PrankGenerator {

    private final ConfigurationManager cf;

    public PrankGenerator(ConfigurationManager cf) {
        this.cf = cf;
    }

    public void generatePranks() {
        int nbrGroups = cf.getNumberOfGroups();

        LinkedList<Person> victims = generateVictimsList();
        if(victims.size() / nbrGroups < 3) {
            throw new RuntimeException("Il n'y pas assez de victimes pour le nombre de groupes spécifiés");
        }

        // Parse victims and generate groups
        LinkedList<Group> groups = generateGroups(victims, nbrGroups);
        LinkedList<Message> messages = generateMessages();

        for (Group group : groups) {

        }

        // Set prank message
        // prank.setMessage();

    }

    public LinkedList<Person> generateVictimsList() {
        BufferedReader isr = null;
        LinkedList<Person> victims = new LinkedList<>();
        String line;
        int nbrVictims = 0;

        try {
            isr = new BufferedReader(new FileReader("./src/main/resources/victims.utf8"));

            String[] names = new String[2];
            String mail = "";

            while ((line = isr.readLine()) != null) {
                if(line.equals("--")) {
                    victims.add(new Person(names[0], names[1], mail));
                    nbrVictims++;
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

    public LinkedList<Message> generateMessages() {
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

    public LinkedList<Group> generateGroups(LinkedList<Person> victims, int numberOfGroups) {
        LinkedList<Group> groups = new LinkedList<>();
        Collections.shuffle(victims);

        for(int i = 0; i < numberOfGroups; ++i) {
            Group group = new Group();
            groups.add(group);
        }

        for(Group group : groups) {
            Prank prank = new Prank();
            // Set prank sender and remove from the list
            prank.setSender(victims.get(0));
            victims.remove(victims.get(0));

            // Set prank group victims
            for(int i = 0; i < 2; ++i) {
                group.addMember(victims.get(i));
                victims.remove(victims.get(i));
            }
            prank.setVictims(victims);
        }

        return groups;
    }

    public static void main(String[] args) {
        ConfigurationManager cm = new ConfigurationManager();
        PrankGenerator pg = new PrankGenerator(cm);
        pg.generatePranks();

    }

}
