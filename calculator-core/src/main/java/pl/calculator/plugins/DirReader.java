package pl.calculator.plugins;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
public class DirReader {
    private static File folder;
    private static final Logger log = LoggerFactory.getLogger(DirReader.class);
    private static String path;
    public static void setName(String s){
        if(!s.equals("")){
            s="/"+s;
        }
        String name = s + "/";

        path = System.getProperty("user.dir") + "/plugins" + name;
        System.out.println(path);
        log.info("path ustawiony na: "+path);
        folder = new File(path);
        if (!folder.exists()) {
            if (folder.mkdirs()) {
                log.info("Directory is created!");
            } else {
                log.error("Failed to create directory!");
            }
        }
    }
    static String getPath(){
        return path;
    }
    static File[] loadFromDirJarFile(){
        return folder.listFiles(
                (dir, name) -> name.toLowerCase().endsWith(".jar"));
    }
    public static void delete(){
        File index = new File(path);
        String[]entries = index.list();
        for(String s: entries){
            File currentFile = new File(index.getPath(),s);
            currentFile.delete();
        }
    }
}
