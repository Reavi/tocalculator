package pl.calculator.plugins;


import pl.calculator.Operation;

import java.io.*;
import java.lang.reflect.Constructor;
import java.net.URL;
import java.net.URLClassLoader;
import java.util.*;
import java.util.jar.JarEntry;
import java.util.jar.JarFile;

public class LoaderPlugin {
    private ArrayList<URL[]> urls=new ArrayList<>();
    public LoaderPlugin() {
    }
    public Map<String,Operation> load() throws Exception{
        File [] listOfFiles=loadFromFile();
        Map<String,Operation> hp=new HashMap<>();

        for(File file : listOfFiles){
            ArrayList<String> names=getNamesClass(file);
            for(String name : names){
                URL[] classLoaderUrls = new URL[]{new URL("file:///d:/plugin/"+file.getName())};
                URLClassLoader urlClassLoader = new URLClassLoader(classLoaderUrls,Thread.currentThread().getContextClassLoader());
                Class<?> cl = urlClassLoader.loadClass(name);
                Constructor<?> constructor = cl.getConstructor();
                System.out.print("Plugin class name: "+constructor.getName());
                Operation ic = (Operation) constructor.newInstance();
                System.out.println(", operand: "+ic.getSign());
                hp.put(ic.getSign(),ic);
            }
        }
        return hp;
    }
    private File[] loadFromFile(){
        File folder = new File("d:/plugin/");
        return folder.listFiles(
                (dir, name) -> name.toLowerCase().endsWith(".jar"));
    }
    private ArrayList<String> getNamesClass(File file) throws IOException {
        JarFile jf = new JarFile(file);
        Enumeration<JarEntry> entries = jf.entries();
        String name = "";
        ArrayList<String> names = new ArrayList<>();
        while(entries.hasMoreElements()) {
            name = entries.nextElement().getName();
            if(name.endsWith(".class")){
                name = name.substring(0,name.length()-6);
                names.add(name);
            }
        }
        return names;
    }
}
