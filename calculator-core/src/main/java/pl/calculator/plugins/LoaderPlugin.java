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
    private ArrayList<String> op;
    public LoaderPlugin(ArrayList<String> o) {
        op=new ArrayList<>(o);
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
                Operation ic = (Operation) constructor.newInstance();

                try {
                    for(String sign: op){
                        if(sign.equals(ic.getSign())){
                            throw new IllegalStateException("Istnieje juz taki znak");
                        }
                    }
                    System.out.print("Pomyślnie załadowano klasę z pluginu: "+file.getName());
                    hp.put(ic.getSign(),ic);
                }catch (Exception e){
                    System.out.print(e.getMessage()+"! Nie wczytano klasy z: "+file.getName());

                }
                System.out.println(", nazwa klasy: "+constructor.getName()+", znak: "+ic.getSign());

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
