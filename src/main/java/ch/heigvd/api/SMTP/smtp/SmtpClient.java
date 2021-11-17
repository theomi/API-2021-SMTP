package ch.heigvd.api.SMTP.smtp;

import ch.heigvd.api.SMTP.mail.Mail;

import java.io.*;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.logging.Level;
import java.util.logging.Logger;

public class SmtpClient {

    private static final Logger LOG = Logger.getLogger(SmtpClient.class.getName());

    private final String host;
    private final int port;
    private final boolean auth;
    private final String username;
    private final String password;
    private Socket clientSocket;

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
            clientSocket = new Socket(host, port);
            BufferedWriter out = new BufferedWriter(new OutputStreamWriter(clientSocket.getOutputStream()));
            BufferedReader in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));

            String line;

            // Shows the welcome line
            line = in.readLine();
            if(debug)
                LOG.log(Level.INFO, line);

            // Outputs EHLO line
            line = "EHLO " + SmtpClient.class.getName();
            out.write(line + "\r\n");
            out.flush();

            while ((line = in.readLine()).startsWith("250-")) {
                if(debug)
                    LOG.log(Level.INFO, line);
            }

            // SMTP Authentication
            if(auth) {
                out.write("AUTH LOGIN\r\n");
                out.flush();
                in.readLine();
                out.write(username + "\r\n");
                out.flush();
                in.readLine();
                out.write(password + "\r\n");
                out.flush();
            }

            line = in.readLine();
            if(debug)
                LOG.log(Level.INFO, line);

            return line.startsWith("235");


        } catch (IOException e) {
            if(debug)
                LOG.log(Level.SEVERE, e.toString(), e);
            return false;
        }
    }

    public void sendMail(Mail mail) {

    }

    public void enableDebug() {
        this.debug = true;
    }


}
