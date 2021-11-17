package ch.heigvd.api.SMTP.configuration;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public class ConfigurationManager {

    private String smtpServerAddress;
    private String smtpServerPort;
    private String numberOfGroups;

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
            this.smtpServerPort = prop.getProperty("smtpServerPort");
            this.numberOfGroups = prop.getProperty("numberOfGroups");

        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    public String getSmtpServerAddress() {
        return smtpServerAddress;
    }

    public String getSmtpServerPort() {
        return smtpServerPort;
    }

    public String getNumberOfGroups() {
        return numberOfGroups;
    }

    public static void main(String... args) {
        ConfigurationManager cm = new ConfigurationManager();
        System.out.println(cm.getNumberOfGroups());
        System.out.println(cm.getSmtpServerAddress());
        System.out.println(cm.getSmtpServerPort());
    }
}
