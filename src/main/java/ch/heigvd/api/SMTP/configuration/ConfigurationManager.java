package ch.heigvd.api.SMTP.configuration;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.util.Properties;

public class ConfigurationManager {

    private final String smtpServerAddress;
    private final int smtpServerPort;
    private final boolean smtpAuth;
    private final int numberOfGroups;
    private String smtpUsername;
    private String smtpPassword;

    public ConfigurationManager() throws IOException {
        Properties prop = new Properties();

        BufferedReader inputStream = new BufferedReader(new FileReader(System.getProperty("user.dir") + "/config/config.properties", StandardCharsets.UTF_8));

        prop.load(inputStream);

        this.smtpServerAddress = prop.getProperty("smtpServerAddress");
        this.smtpServerPort = Integer.parseInt(prop.getProperty("smtpServerPort"));
        this.numberOfGroups = Integer.parseInt(prop.getProperty("numberOfGroups"));
        this.smtpAuth = Boolean.parseBoolean(prop.getProperty("smtpAuth"));
        if(smtpAuth) {
            this.smtpUsername = prop.getProperty("smtpUsername");
            this.smtpPassword = prop.getProperty("smtpPassword");
        }
    }

    public String getSmtpServerAddress() {
        return smtpServerAddress;
    }

    public int getSmtpServerPort() { return smtpServerPort;}

    public boolean isSmtpAuthEnabled() { return smtpAuth; }

    public String getSmtpUsername() {return smtpUsername; }

    public String getSmtpPassword() {return smtpPassword; }

    public int getNumberOfGroups() {
        return numberOfGroups;
    }
}
