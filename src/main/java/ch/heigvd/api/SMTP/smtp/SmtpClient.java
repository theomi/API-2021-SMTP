package ch.heigvd.api.SMTP.smtp;

import ch.heigvd.api.SMTP.mail.Mail;

import java.io.*;
import java.net.Socket;
import java.nio.charset.StandardCharsets;
import java.util.Base64;
import java.util.logging.Level;
import java.util.logging.Logger;

public class SmtpClient {

    private static final Logger LOG = Logger.getLogger(SmtpClient.class.getName());

    private final String host;
    private final int port;
    private final boolean auth;
    private final String username;
    private final String password;
    private BufferedWriter out;
    private BufferedReader in;
    private boolean connected;

    // For debug purposes
    private boolean debug = false;

    public SmtpClient(String host, int port, boolean auth, String username, String password) {
        this.host = host;
        this.port = port;
        this.auth = auth;
        this.username = username;
        this.password = password;
    }

    public boolean connect() {
        try {
            // Creates the sockets and the stream
            Socket clientSocket = new Socket(host, port);
            out = new BufferedWriter(new OutputStreamWriter(clientSocket.getOutputStream()));
            in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));

            String line;

            // Shows the welcome line
            readLine();

            // Outputs EHLO line
            line = "EHLO " + SmtpClient.class.getName();
            writeLine(line);

            while ((readLine()).startsWith("250-"));

            // SMTP Authentication
            if (auth) {
                writeLine("AUTH LOGIN");
                readLine();
                writeLine(Base64.getEncoder().encodeToString(username.getBytes()));
                readLine();
                writeLine(Base64.getEncoder().encodeToString(password.getBytes()));
            }

            if (readLine().startsWith("235")) {
                connected = true;
                return true;
            }else return false;

        } catch (IOException e) {
            if(debug)
                LOG.log(Level.SEVERE, e.toString(), e);
            return false;
        }
    }

    public void disconnect() {

        if (!connected) {
            LOG.log(Level.SEVERE, "The SMTP client is not connected. You must use connect() method before.");
            return;
        }

        try {
            writeLine("QUIT");
            if(readLine().startsWith("221")) {
                connected = false;
            }

            in.close();
            out.close();

        } catch (IOException e) {
            LOG.log(Level.SEVERE, e.toString(), e);
        }

    }

    public boolean sendMail(Mail mail) {
        if (!connected) {
            LOG.log(Level.SEVERE, "The SMTP client is not connected. You must use connect() method before.");
            return false;
        }

        try {
            // Mail sender
            writeLine("MAIL FROM: <" + mail.getFrom() + ">");
            readLine();

            // Mail recipient
            writeLine("RCPT TO: <"   + mail.getTo()   + ">");
            readLine();

            // Mail content
            writeLine("DATA");
            readLine();

                // Headers
                writeLine("From: "    + mail.getFrom());
                writeLine("To: "      + mail.getTo());
                writeLine("Cc: "      + mail.getCc());
                writeLine("Subject: " + mail.getSubject());
                writeLine("");

                // Body
                writeLine(mail.getBody());

            writeLine(".");

            return readLine().startsWith("250");


        } catch (IOException e) {
            if(debug)
                LOG.log(Level.SEVERE, e.toString(), e);
            return false;
        }
    }

    /**
     * Enables logging everything for debug purposes
     */
    public void enableDebug() {
        this.debug = true;
    }

    /**
     * Reads a line from the input reader and logs it if debug is enabled
     * @return The line as a String
     * @throws IOException if the read failed
     */
    private String readLine() throws IOException {
        String line = in.readLine();
        if (debug)
            System.out.println("\u001B[34m" + line + "\u001B[0m");
        return line;
    }

    private void writeLine(String line) throws IOException {
        out.write(line + "\r\n");
        out.flush();
        if (debug)
            System.out.println(line);
    }


}
