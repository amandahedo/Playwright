package utils;

import com.qa.opencart.pages.Setup.setUp;
import org.apache.log4j.Logger;
import org.yaml.snakeyaml.Yaml;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Map;

public class ConfigFile {

    private static Logger Log = Logger.getLogger(setUp.class);
    static Map<?, ?> loadApplicationConfigs(String name) throws IOException {
        Yaml yaml = new Yaml();
        Log.info("\t Getting endpoints from the environment "+ name + " config");
         InputStream is = new FileInputStream(System.getProperty("user.dir")+"/config/"+ name + "/environment.yml");
        Map<?, ?> stream = yaml.load(is);

        return stream;
    }

    public static String getUrl(String page) throws IOException {
        String env = System.getProperty("test.env");
//         LogHelpers.info("\t Name of the environment is: " + env);
        Map<?, ?> configs = loadApplicationConfigs(env);
        @SuppressWarnings("unchecked")
        Map<String, Object> endpoint = (Map<String, Object>) configs.get(env);
        String url = endpoint.get(page).toString() ;//+endpoint.get(page).toString();
        return url;
    }

    public static String getConfigDetails(String key) throws Exception {
        // load the environment config yml file and return the map matching the passed key
        Map<?, ?> configs = loadEnvironmentConfig();
        Map<String, Object> endpoint = (Map<String, Object>) configs.get(System.getProperty("test.env"));
        Log.info("\t configs: "+endpoint.get(key).toString());
        return endpoint.get(key).toString();
    }

    static Map<String, Object> loadEnvironmentConfig() throws Exception {
        InputStream is = new FileInputStream(System.getProperty("user.dir")+"/config/"+ System.getProperty("test.env") + "/environment.yml");
        return new Yaml().load(is);
    }
}
