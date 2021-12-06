package ch.heigvd.api.SMTP.configuration;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public class ConfigurationManager {

    private final String smtpServerAddress;
    private final int smtpServerPort;
    private final boolean smtpAuth;
    private int numberOfGroups;
    private String smtpUsername;
    private String smtpPassword;

    public ConfigurationManager() throws IOException {
        Properties prop = new Properties();

        InputStream inputStream = getClass().getClassLoader().getResourceAsStream("config.properties");

        if(inputStream != null) {
            prop.load(inputStream);
        } else {
            throw new FileNotFoundException("File not found");
        }

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

    public void setNumberOfGroups(int numberOfGroups) {
        this.numberOfGroups = numberOfGroups;
    }
}
