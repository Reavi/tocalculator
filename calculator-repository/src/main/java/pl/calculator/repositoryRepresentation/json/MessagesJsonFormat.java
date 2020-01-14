package pl.calculator.repositoryRepresentation.json;

import com.google.gson.Gson;
import pl.calculator.repository.messages.ErrorMessages;

public class MessagesJsonFormat {
    public String getString() {

        return new Gson().toJson(ErrorMessages.getMess());
    }
}
