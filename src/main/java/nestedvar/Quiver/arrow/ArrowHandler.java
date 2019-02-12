package nestedvar.Quiver.arrow;

import java.io.File;
import java.net.URL;
import java.net.URLClassLoader;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.jar.JarEntry;
import java.util.jar.JarFile;

import nestedvar.Quiver.Quiver;
import nestedvar.Quiver.util.Logger;

public class ArrowHandler {
    public static URLClassLoader loader;
    public static ArrayList<String> arrows = new ArrayList<String>();
    public static HashMap<String, HashMap<String, String>> arrowInfo = new HashMap<String, HashMap<String, String>>();
    public static ArrayList<Object> arrowClasses = new ArrayList<Object>();
    public static ArrayList<Object[]> listeners = new ArrayList<Object[]>();

    /**
     * Checks if the Arrows 
     * folder is empty
     */
    void createFolder() {
        File file = new File("arrows");
        if (file.isDirectory()) {
            if (file.list().length > 0) load();
        }
        else {    
            try {
                File arrowsDir = new File("arrows");
                arrowsDir.mkdir();
            }
            catch (Exception e) {
                new Logger(1, e);
            }
        }
    }

    /**
     * Loads all Arrows
     */
    void load() {
        File dir = new File("arrows");
        File[] files = dir.listFiles((d, file) -> file.endsWith(".jar"));

        for (File file : files) {
            try {
                URL[] urls = { 
                    file.toURI().toURL()
                };
                loader = URLClassLoader.newInstance(urls);
                JarFile jar = new JarFile(file);
                Enumeration<JarEntry> entries = jar.entries();

                while (entries.hasMoreElements()) {
                    try {
                        JarEntry jarEntry = entries.nextElement();
                        if (jarEntry.isDirectory() || !jarEntry.getName().endsWith(".class")) {
                            continue;
                        }
                        String className = jarEntry.getName().substring(0, jarEntry.getName().length() - 6).replace('/', '.');
                        Class<?> clazz = loader.loadClass(className);
                        Object obj = clazz.newInstance();
                    
                        if (obj instanceof Arrow) {
                            Arrow arrow = (Arrow) obj;
                            arrow.load();
                        }
                    }
                    catch (Exception e) {
                        e.printStackTrace();
                        continue;
                    }
                }
                jar.close();
            }
            catch (Exception e) {new Logger(1, e);}
        }
    }

    /**
     * Unloads all loaded Arrows
     */
    void unload() {
        try {
            loader.close();
            for (Object[] obj : listeners) {
                Quiver.builder.removeEventListeners(obj);
            }
        }
        catch (Exception e) {
            new Logger(1, e);
        }
    }

    /** 
     * Reloads the arrows that are currently 
     * loaded and loads new arrows
     */
    void reload() {
        unload();
        load();
    }

    public void addListeners(Object listener) {
        System.out.println(listener);
    }
}