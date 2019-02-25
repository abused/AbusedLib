package abused_master.abusedlib.utils;

import abused_master.abusedlib.AbusedLib;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class JsonConfig {

    private JsonObject config;
    private File configFile;
    private InputStream defaultConfigFile;
    private File configFolderFile = new File(System.getProperty("user.dir") + "/config");

    public JsonConfig(String modid, Class mainModClass) {
        this(modid, mainModClass, "", false);
    }

    public JsonConfig(String modid, Class mainModClass, String name) {
        this(modid, mainModClass, name, false);
    }

    public JsonConfig(String modid, Class mainModClass, boolean createModConfigFolder) {
        this(modid, mainModClass, "", createModConfigFolder);
    }

    public JsonConfig(String modid, Class mainModClass, String name, boolean createModConfigFolder) {
        this.defaultConfigFile = mainModClass.getClassLoader().getResourceAsStream("assets/" + modid + "/config.json");
        this.runConfigSetup(modid, createModConfigFolder, name.equals("") ? false : true, name);
    }

    private void runConfigSetup(String modid, boolean createModConfigFolder, boolean hasCustomName, String customConfigName) {
        if(!configFolderFile.exists()) {
            configFolderFile.mkdir();
        }

        if(!createModConfigFolder) {
            this.configFile = new File(configFolderFile.getPath() + "/" + (hasCustomName ? customConfigName : modid) + ".json");
        }else {
            this.configFile = new File(configFolderFile.getPath() + "/" + modid + "/" + (hasCustomName ? customConfigName : modid) + ".json");
        }

        if (defaultConfigFile == null) {
            AbusedLib.LOGGER.fatal("Unable to find the default config.json for mod ", modid, " in assets/", modid);
            System.exit(-1);
            return;
        }

        if(!configFile.exists()) {
            AbusedLib.LOGGER.info("Creating config file for " + modid);

            BufferedWriter bufferedWriter = null;
            PrintWriter printWriter = null;

            try {
                configFile.createNewFile();

                List<String> linesList = new ArrayList<>();
                BufferedReader reader = new BufferedReader(new InputStreamReader(defaultConfigFile));

                while (reader.ready()) {
                    linesList.add(reader.readLine());
                }

                bufferedWriter = new BufferedWriter(new FileWriter(configFile));
                printWriter = new PrintWriter(bufferedWriter);

                for (String string : linesList) {
                    printWriter.println(string);
                }

                printWriter.flush();
                printWriter.close();
                this.loadConfigFile();
            } catch (IOException e) {
                e.printStackTrace();
            } finally {
                try {
                    bufferedWriter.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }else {
            AbusedLib.LOGGER.info("Loading config file for {}", modid);
            this.loadConfigFile();
        }
    }

    public void loadConfigFile() {
        try {
            config = new JsonObject();
            JsonParser parser = new JsonParser();
            config = parser.parse(new FileReader(configFile)).getAsJsonObject();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }

    public String getString(String name) {
        return getJsonConfig().get(name).getAsString();
    }

    public int getInt(String name) {
        return getJsonConfig().get(name).getAsInt();
    }

    public double getDouble(String name) {
        return getJsonConfig().get(name).getAsDouble();
    }

    public long getLong(String name) {
        return getJsonConfig().get(name).getAsLong();
    }

    public boolean getBoolean(String name) {
        return getJsonConfig().get(name).getAsBoolean();
    }

    public JsonArray getList(String name) {
        return getJsonConfig().get(name).getAsJsonArray();
    }

    public JsonObject getJsonConfig() {
        return config;
    }
}
