package me.namutree0345.minevillage_plugin;

import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.configuration.file.YamlConfiguration;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

public class Configuration {

    public File conf;
    public ArrayList<String> initedPlayers;

    public Configuration() throws IOException {
        conf = new File("minevil.yml");
        initedPlayers = new ArrayList<>();
        if(!conf.exists()) {
            conf.createNewFile();
        }
    }

    public String translatePrefix(String prefix) {
        switch (prefix) {
            case "rich":
                return "부자";
            case "builder":
                return "건축가";
            case "shimin":
                return "시민";
            default:
                return prefix;
        }
    }

    public void initUserConfiguration(String uuid) throws IOException {
        YamlConfiguration yConf = loadConfiguration();
        ConfigurationSection usersSection;
        if(!yConf.isConfigurationSection("users")) {
            usersSection = yConf.createSection("users");
            yConf.save(conf);
        } else {
            usersSection = yConf.getConfigurationSection("users");
        }
        if(usersSection.contains(uuid)) {
            return;
        }
        ConfigurationSection section = usersSection.createSection(uuid);
        // rich: 부자
        // builder: 건축가
        // shimin: 시민
        section.set("prefix", "shimin");
        section.set("coin", 0);
        yConf.save(conf);
        if(!initedPlayers.contains(uuid)) {
            initedPlayers.add(uuid);
        }
    }

    public ConfigurationSection getUserInfo(String uuid) throws NotInitiallizedConfigurationException, IOException {
        /*
        if(!initedPlayers.contains(uuid)) {
            throw new NotInitiallizedConfigurationException("Not Initiallized Player Configuration!!");
        }

         */
        YamlConfiguration yConf = loadConfiguration();
        ConfigurationSection usersSection;
        if(!yConf.isConfigurationSection("users")) {
            usersSection = yConf.createSection("users");
        } else {
            usersSection = yConf.getConfigurationSection("users");
        }
        if(!usersSection.contains(uuid)) {
            throw new NotInitiallizedConfigurationException("No Player Configuration in Users Section!! (Please init player configuration)");
        }

        return (ConfigurationSection) usersSection.get(uuid);
    }

    public YamlConfiguration loadConfiguration() {
        return YamlConfiguration.loadConfiguration(conf);
    }

}

class NotInitiallizedConfigurationException extends Exception {
    public NotInitiallizedConfigurationException(String message) {
        super(message);
    }
    public NotInitiallizedConfigurationException(String message, Throwable throwable) {
        super(message, throwable);
    }
}
