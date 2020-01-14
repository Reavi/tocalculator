package pl.calculator;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import pl.calculator.factory.*;
import pl.calculator.plugins.DirReader;
import pl.calculator.plugins.PluginList;
import pl.calculator.plugins.LoaderPlugin;
import pl.calculator.plugins.Plugin;
import pl.calculator.repository.history.History;
import pl.calculator.repository.messages.ErrorMessages;
import pl.calculator.repositoryRepresentation.json.HistoryJsonFormat;
import pl.calculator.string.ProcessString;


import java.util.ArrayList;
import java.util.Map;

public class CalcCore {
    private double sum = 0;
    private PluginList listOfPluginLoaded = new PluginList();
    private Plugin observerListOfPLugin;
    private static final Logger log = LoggerFactory.getLogger(CalcCore.class);
    private History his = new History();

    public CalcCore(String name) {
        DirReader.setName(name);
        prepare();
        observerListOfPLugin = new Plugin(listOfPluginLoaded);
        loadPlugins();
    }

    private void prepare() {
        ArrayList<Operation> op = new ArrayList<>();
        op.add(new AddFactory().CreateOperation());
        op.add(new SubFactory().CreateOperation());
        op.add(new MulFactory().CreateOperation());
        op.add(new DivFactory().CreateOperation());
        op.add(new SqrtFactory().CreateOperation());
        for (Operation o : op) {
            listOfPluginLoaded.addOb(o);
        }
    }

    public void updateMods() {
        observerListOfPLugin.check();
    }

    private void loadPlugins() {
        Map<String, Operation> tmp = new LoaderPlugin(listOfPluginLoaded).load();
        for (Map.Entry<String, Operation> entry : tmp.entrySet()) {
            listOfPluginLoaded.addOb(entry.getValue());
        }
    }

    public double result() {
        return sum;
    }

    public void read(String s) {
        try {
            log.info("Przyszło wyrażenie " + s);
            updateMods();
            ProcessString str = new ProcessString(s, listOfPluginLoaded);
            this.sum = str.getSum();
            log.info("wynik: " + this.sum);
            his.add(s, Double.toString(this.sum));
        } catch (IllegalStateException | IllegalArgumentException | NullPointerException e) {
            log.warn(e.getMessage());
            ErrorMessages.addMess("ERROR", e.getMessage());
        }

    }

    public Map<String, Operation> getPLuginList() {
        return listOfPluginLoaded.getOb();

    }

    public void clear() {
        DirReader.delete();
    }

    public String getHistory() {
        return new HistoryJsonFormat().getString(his.getHistory());
    }
}
