package pl.calculator.plugins;

import pl.calculator.Calculator;
import pl.calculator.Operation;

import java.io.File;
import java.lang.reflect.Constructor;
import java.net.URL;
import java.net.URLClassLoader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class LoaderPlugin {
    //Coś co przechowuje ile pluginow jest załadowanych / nazwy? licbza?
    private ArrayList<URL[]> urls=new ArrayList<>();
    public LoaderPlugin() {
    }
    public Map<String,Operation> load() throws Exception{
        File [] listOfFiles=loadFromFile();
        URL[] classLoaderUrls = new URL[listOfFiles.length];
        Map<String,Operation> hp=new HashMap<>();
        for(File file : listOfFiles){
            classLoaderUrls = new URL[]{new URL("file:///d:/plugin/"+file.getName())};
            URLClassLoader urlClassLoader = new URLClassLoader(classLoaderUrls,Thread.currentThread().getContextClassLoader());
            //System.out.println(file.getName().substring(0,file.getName().length()-4));
            Class cl = urlClassLoader.loadClass(file.getName().substring(0,file.getName().length()-4));
            Constructor constructor = cl.getConstructor();
            System.out.println("Plugin class name: "+constructor.getName());
            Operation ic = (Operation) constructor.newInstance();
            System.out.println("Plugin  operand: "+ic.getSign());
            hp.put(ic.getSign(),ic);


        }
        /*
        // Create a new URLClassLoader
        URLClassLoader urlClassLoader = new URLClassLoader(classLoaderUrls,Thread.currentThread().getContextClassLoader());


        // z refleksją
        Class cl = urlClassLoader.loadClass("Mul");
        Constructor constructor = cl.getConstructor();
        System.out.println("Plugin class name: "+constructor.getName());
        Operation ic = (Operation) constructor.newInstance();
        System.out.println("Plugin  operand: "+ic.getSign());
*/

        return hp;
    }
    private File[] loadFromFile(){
        File folder = new File("d:/plugin/");
        return folder.listFiles(
                (dir, name) -> name.toLowerCase().endsWith(".jar"));
    }
}
