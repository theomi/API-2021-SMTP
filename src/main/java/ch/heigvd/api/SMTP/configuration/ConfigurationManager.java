package ch.heigvd.api.SMTP.configuration;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public class ConfigurationManager {

    private String smtpServerAddress;
    private int smtpServerPort;
    private String numberOfGroups;
    private boolean smtpAuth;
    private String smtpUsername;
    private String smtpPassword;

    public ConfigurationManager() {
        try {
            Properties prop = new Properties();

            InputStream inputStream = getClass().getClassLoader().getResourceAsStream("config.properties");

            if(inputStream != null) {
                prop.load(inputStream);
            } else {
                throw new FileNotFoundException("File not found");
            }

            this.smtpServerAddress = prop.getProperty("smtpServerAddress");
            this.smtpServerPort = Integer.parseInt(prop.getProperty("smtpServerPort"));
            this.numberOfGroups = prop.getProperty("numberOfGroups");
            this.smtpAuth = Boolean.parseBoolean(prop.getProperty("smtpAuth"));
            if(smtpAuth) {
                this.smtpUsername = prop.getProperty("smtpUsername");
                this.smtpPassword = prop.getProperty("smtpPassword");
            }

        } catch (IOException e) {
            e.printStackTrace();
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
        return Integer.parseInt(numberOfGroups);
    }

    public void setNumberOfGroups(String numberOfGroups) {
        this.numberOfGroups = numberOfGroups;
    }
}
