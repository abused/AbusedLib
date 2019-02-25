package abused_master.abusedlib.utils;

import abused_master.abusedlib.AbusedLib;
import io.netty.util.internal.StringUtil;
import net.fabricmc.loader.api.FabricLoader;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Create a new instance of this class, providing the modid and the main mod class
 */
public class Config {

    private YamlFile config;
    private InputStream defaultConfigFile;
    private File configFolderFile = FabricLoader.getInstance().getConfigDirectory();
    private String modid;

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
        this.config = new YamlFile(configFolderFile.getPath() + "/" + (createModConfigFolder ? modid + "/" : "") + (StringUtil.isNullOrEmpty(name) ? modid : name) + ".yml");
        this.modid = modid;
        this.runConfigSetup();
    }

    private void runConfigSetup() {
        if (defaultConfigFile == null) {
            AbusedLib.LOGGER.fatal("Unable to find the default config.yml for mod {} in assets/{}",  modid, modid);
            System.exit(-1);
            return;
        }

        if (!config.exists()) {
            AbusedLib.LOGGER.info("Creating config file for {}", modid);

            try (PrintWriter printWriter = new PrintWriter(new FileWriter(config.getConfigFile()))) {
                List<String> linesList = new ArrayList<>();
                BufferedReader reader = new BufferedReader(new InputStreamReader(defaultConfigFile));

                while (reader.ready()) {
                    linesList.add(reader.readLine());
                }

                for (String string : linesList) {
                    printWriter.println(string);
                }

            } catch (IOException e) {
                AbusedLib.LOGGER.fatal("You done borked something with your config", e);
            }

        }

        AbusedLib.LOGGER.info("Loading config file for {}", modid);
        if(config != null) {
            config.load();
        }
    }

    public String getString(String name) {
        return getConfig().getString(name);
    }

    public int getInt(String name) {
        return getConfig().getInt(name);
    }

    public double getDouble(String name) {
        return getConfig().getDouble(name);
    }

    public long getLong(String name) {
        return getConfig().getLong(name);
    }

    public boolean getBoolean(String name) {
        return getConfig().getBoolean(name);
    }

    public Object get(String name) {
        return getConfig().get(name);
    }

    public boolean contains(String name) {
        return getConfig().doesValueExist(name);
    }

    public YamlFile getConfig() {
        return config;
    }
}
