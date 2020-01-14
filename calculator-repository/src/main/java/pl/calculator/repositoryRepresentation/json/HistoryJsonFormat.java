package pl.calculator.repositoryRepresentation.json;

import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.HashMap;

public class HistoryJsonFormat {
    public String getString(HashMap<Integer, ArrayList<String>> history) {
        return new Gson().toJson(history);
    }
}
