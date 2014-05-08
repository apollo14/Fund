package pl.js.fund.config;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.nio.MappedByteBuffer;
import java.nio.channels.FileChannel;
import java.nio.charset.Charset;

import javax.xml.transform.Source;
import javax.xml.transform.stream.StreamSource;

import org.apache.commons.configuration.Configuration;
import org.apache.commons.configuration.ConfigurationException;
import org.apache.commons.configuration.XMLConfiguration;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * At first tries to retriew external configuration from config.xml
 * If not present, takes configuration enclosed with module.
 */
public final class ConfigManager
{

    /** searched in module resources */
    private static final String  INTERNAL_XML_CONFIG_LOCATION = "config/siri-config.xml";

    /** default for most common configuration */
    private static final String  EXTERNAL_XML_CONFIG_LOCATION = "/deploy/siri-config.xml";
    /** suffix added to the file location path */
    private static final String  EXTERNAL_PROPERTY_SUFFIX     = ".external";

    private static final String  INTERNAL_PROPERTY_SUFFIX     = ".internal";

    private static ConfigManager instance                     = new ConfigManager();

    private static Logger        log                          = LoggerFactory.getLogger(ConfigManager.class);

    private static Configuration config;

    private ConfigManager()
    {
        String serverHome = System.getProperty("jboss.server.home.dir");
        String configLocation = serverHome + EXTERNAL_XML_CONFIG_LOCATION;
        File f = new File(configLocation);

        if (!f.exists())
        {
            configLocation = INTERNAL_XML_CONFIG_LOCATION;
        }
        try
        {
            config = new XMLConfiguration(configLocation);

        }
        catch (ConfigurationException e)
        {
            ConfigException ex = new ConfigException(e);
            log.error(ex.getKey(), ex);
            throw ex;
        }
    }

    /**
     * Returns content of the file. CAUTION: the file might be located
     * externally (but relative to jboss.server.home.dir !!) or file might be
     * located inside siri-util !! If external file exists than it is used, if
     * not internal is used
     * 
     * @param prop
     * @return
     */
    public static Source getSource(ConfigProperty prop)
    {
        String externalPath = config.getString(prop.getName() + EXTERNAL_PROPERTY_SUFFIX);
        String internalPath = config.getString(prop.getName() + INTERNAL_PROPERTY_SUFFIX);

        File f = new File(externalPath);
        InputStream is = null;

        if (f.exists())
        {
            try
            {
                is = new FileInputStream(f);
            }
            catch (FileNotFoundException e)
            {
                ConfigException ex = new ConfigException(e);
                log.error(ex.getKey(), ex);
                throw ex;
            }
        }
        else
        {
            is = instance.getClass().getClassLoader().getResourceAsStream(internalPath);
        }

        Source source = new StreamSource(is);

        return source;
    }

    /**
     * Returns string content of the file
     * 
     * @param prop
     * @return
     */
    public static String getFileContent(ConfigProperty prop)
    {
        String path = getString(prop);

        return getFileContent(path);
    }

    public static String getFileContent(String path)
    {
        FileInputStream stream = null;
        try
        {
            stream = new FileInputStream(new File(path));
            FileChannel fc = stream.getChannel();
            MappedByteBuffer bb = fc.map(FileChannel.MapMode.READ_ONLY, 0, fc.size());
            return Charset.defaultCharset().decode(bb).toString();
        }
        catch (Exception e)
        {
            ConfigException ex = new ConfigException(e);
            log.error(ex.getKey(), ex);

        }
        finally
        {
            try
            {
                stream.close();
            }
            catch (Exception e)
            {
                ConfigException ex = new ConfigException(e);
                log.error(ex.getKey(), ex);
            }
        }

        return null;

    }

    public static boolean getBoolean(ConfigProperty prop)
    {
        return config.getBoolean(prop.getName());
    }

    public static String getString(ConfigProperty prop)
    {
        String str = config.getString(prop.getName());

        if (null == str)
        {
            ConfigException ex = new ConfigException();
            log.error(ex.getKey(), ex);
            throw ex;
        }

        return str;
    }

    public static String[] getStringArray(ConfigProperty code)
    {
        String[] str = config.getStringArray(code.getName());

        if (null == str)
        {
            ConfigException ex = new ConfigException();
            log.error(ex.getKey(), ex);
            throw ex;
        }

        return str;
    }
}
