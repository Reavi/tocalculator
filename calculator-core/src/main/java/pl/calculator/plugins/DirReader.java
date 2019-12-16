package pl.calculator.plugins;

import java.io.File;

class DirReader {

    static File[] loadFromDirJarFile(){
        File folder = new File(System.getProperty("user.dir")+"/plugin/");
        return folder.listFiles(
                (dir, name) -> name.toLowerCase().endsWith(".jar"));
    }
}
