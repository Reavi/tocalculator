package pl.calculator.plugins;


import pl.calculator.Operation;
import java.io.*;
import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.net.URL;
import java.net.URLClassLoader;
import java.nio.file.NoSuchFileException;
import java.util.*;
import java.util.jar.JarEntry;
import java.util.jar.JarFile;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class LoaderPlugin {
    private ArrayList<String> op;
    private PluginList lps;
    private static final Logger log = LoggerFactory.getLogger(LoaderPlugin.class);
    public LoaderPlugin(PluginList lps) {
        op=lps.getOperands();
        this.lps=lps;
    }
    public Map<String,Operation> load() {
        File [] listOfFiles= DirReader.loadFromDirJarFile();
        Map<String,Operation> hp=new HashMap<>();
        for(File file : listOfFiles){

            boolean d=false;
            for(String lf : lps.getListFile()){
                if(file.getName().equals(lf)){
                    d=true;
                }
            }
            if(d){
                continue;
            }
            lps.addToListFile(file.getName());
            try{
                ArrayList<String> names=getNamesClass(file);
                for(String name : names){
                    
                    URL[] classLoaderUrls = new URL[]{new URL("file:///"+DirReader.getPath()+file.getName())};
                    URLClassLoader urlClassLoader = new URLClassLoader(classLoaderUrls,Thread.currentThread().getContextClassLoader());
                    Class<?> cl = urlClassLoader.loadClass(name);
                    Constructor<?> constructor = cl.getConstructor();
                    Operation ic = (Operation) constructor.newInstance();

                    for(String sign: op){
                        if(sign.equals(ic.getSign())){
                            log.warn("Nie wczytano klasy z: "+file.getName()+", nazwa klasy: "+constructor.getName()+", znak: "+ic.getSign());
                            throw new IllegalStateException("Istnieje juz taki znak!");
                        }
                    }
                    log.info("Pomyślnie załadowano klasę z pluginu: "+file.getName());
                    hp.put(ic.getSign(),ic);
                    log.info(", nazwa klasy: "+constructor.getName()+", znak: "+ic.getSign());

                }

            } catch (NoSuchFileException e){
                log.error("No sucj file Exception:\n"+ e.getMessage());
            } catch (IllegalAccessException | InstantiationException| InvocationTargetException | ClassNotFoundException | NoSuchMethodException | IOException e) {
                log.error("IllegalAccess Instantion Exception etc:\n"+e.getMessage());
            }
            catch (IllegalStateException e) {
                log.error("IllegalState Exceptio:\n"+e.getMessage());
            } catch (ClassCastException e) {
                log.error("Znalezlismy niepasujacy plugin, \""+file.getName()+"\" zostanie on pominiety.\n INFO:"+e.getMessage());
            }

        }

        return hp;
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
