package abused_master.abusedlib.utils;

import abused_master.abusedlib.AbusedLib;
import org.simpleyaml.configuration.file.YamlFile;
import org.simpleyaml.exceptions.InvalidConfigurationException;

import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;

/**
 * Create a new instance of this class, providing the modid and the main mod class
 */
public class Config {

    private YamlFile config;
    private InputStream defaultConfigFile;
    private File configFolderFile = new File(System.getProperty("user.dir") + "/config");

    public Config(String modid, Class mainModClass) {
        this(modid, mainModClass, "", false);
    }

    public Config(String modid, Class mainModClass, String name) {
        this(modid, mainModClass, name, false);
    }

    public Config(String modid, Class mainModClass, boolean createModConfigFolder) {
        this(modid, mainModClass, "", createModConfigFolder);
    }

    public Config(String modid, Class mainModClass, String name, boolean createModConfigFolder) {
        this.defaultConfigFile = mainModClass.getClassLoader().getResourceAsStream("assets/" + modid + "/config.yml");
        this.runConfigSetup(modid, createModConfigFolder, name.equals("") ? false : true, name);
    }

    private void runConfigSetup(String modid, boolean createModConfigFolder, boolean hasCustomName, String customConfigName) {
        if(!configFolderFile.exists()) {
            configFolderFile.mkdir();
        }

        if(!createModConfigFolder) {
            this.config = new YamlFile(configFolderFile.getPath() + "/" + (hasCustomName ? customConfigName : modid) + ".yml");
        }else {
            this.config = new YamlFile(configFolderFile.getPath() + "/" + modid + "/" + (hasCustomName ? customConfigName : modid) + ".yml");
        }

        if (defaultConfigFile == null) {
            AbusedLib.LOGGER.log(Level.SEVERE, "Unable to find the default config.yml for mod " + modid + " in assets/" + modid);
            System.exit(-1);
            return;
        }

        if (!config.exists()) {
            AbusedLib.LOGGER.info("Creating config file for " + modid);

            BufferedWriter bufferedWriter = null;
            PrintWriter printWriter = null;

            try {
                config.createNewFile(true);

                List<String> linesList = new ArrayList<>();
                BufferedReader reader = new BufferedReader(new InputStreamReader(defaultConfigFile));

                while (reader.ready()) {
                    linesList.add(reader.readLine());
                }

                bufferedWriter = new BufferedWriter(new FileWriter(config.getConfigurationFile()));
                printWriter = new PrintWriter(bufferedWriter);

                for (String string : linesList) {
                    printWriter.println(string);
                }

                printWriter.flush();
                printWriter.close();
                config.load();
            } catch (IOException e) {
                e.printStackTrace();
            } catch (InvalidConfigurationException e) {
                e.printStackTrace();
            } finally {
                try {
                    bufferedWriter.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        } else {
            AbusedLib.LOGGER.info("Loading config file for " + modid);

            try {
                config.load();
            } catch (InvalidConfigurationException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        try {
            config.saveWithComments();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public String getString(String name) {
        return getYamlConfigFile().getString(name);
    }

    public int getInt(String name) {
        return getYamlConfigFile().getInt(name);
    }

    public double getDouble(String name) {
        return getYamlConfigFile().getDouble(name);
    }

    public long getLong(String name) {
        return getYamlConfigFile().getLong(name);
    }

    public boolean getBoolean(String name) {
        return getYamlConfigFile().getBoolean(name);
    }

    public List getList(String name) {
        return getYamlConfigFile().getList(name);
    }

    public Object get(String name) {
        return getYamlConfigFile().get(name);
    }

    public YamlFile getYamlConfigFile() {
        return config;
    }
}
