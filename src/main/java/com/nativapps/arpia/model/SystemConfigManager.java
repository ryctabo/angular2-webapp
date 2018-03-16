package com.nativapps.arpia.model;

import com.google.gson.Gson;
import com.nativapps.arpia.model.exception.FileOperationException;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;
import java.util.logging.Logger;

/**
 *
 * @author Luis Alfonso Lenes Salas <luislenes02@gmail.com>
 * @version 1.0
 */
public class SystemConfigManager {

    private static final SystemConfigManager INSTANCE = new SystemConfigManager();

    private static final String PATH = "com/nativapps/arpia/config/system_config.json";

    private final Gson gson;

    private static final Logger LOG = Logger.getLogger(SystemConfigManager.class.getName());

    private SystemConfigManager() {
        this.gson = new Gson();
    }

    /**
     * Gets the System Configurations from the json file
     *
     * @return System configurations
     */
    public static SystemConfig get() {
        try (BufferedReader reader = new BufferedReader(
                new InputStreamReader(INSTANCE.getConfigFile().openStream()))){
            String json = "", line;
            while ((line = reader.readLine()) != null) {
                json += line;
            }
            return INSTANCE.getGson().fromJson(json, SystemConfig.class);
        } catch (IOException ex) {
            LOG.info(ex.getCause().getMessage());
            throw new FileOperationException(ex.getCause().getMessage(), ex);
        }
    }

    /**
     * Updates the system configurations' json file
     *
     * @param config
     */
    public static void set(SystemConfig config) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(INSTANCE
                .getConfigFile().getPath()))) {
            writer.write(INSTANCE.getGson().toJson(config));
        } catch (IOException ex) {
            LOG.info(ex.getCause().getMessage());
            throw new FileOperationException(ex.getCause().getMessage(), ex);
        }
    }

    /**
     * Get the URL that content the system configurations' json file
     *
     * @return
     */
    private URL getConfigFile() {
        return getClass().getClassLoader().getResource(PATH);
    }

    private Gson getGson() {
        return gson;
    }
}
