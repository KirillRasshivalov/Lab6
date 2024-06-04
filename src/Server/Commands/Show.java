package Server.Commands;

import Common.Response;
import Server.Managers.CollectionManager;
import Client.Models.Vehicle;
import Common.Utilites.Console;
import Common.Utilites.StandartConsole;

import java.util.LinkedHashMap;

public class Show extends Command{
    private final Console console;
    private final CollectionManager collectionManager;
    public Show (Console console, CollectionManager collectionManager){
        super("show", "выводит введенные транспортные средства.");
        this.console = console;
        this.collectionManager = collectionManager;
    }
    @Override
    public void execute(String[] args, Response response){
        Console console1 = new StandartConsole();
        if (collectionManager.getTable().keySet().size() == 0){
            response.setAnswer("Коллекция пуста.");
        }
        else {
            LinkedHashMap<String, Vehicle> table = CollectionManager.getTable();
            String answer = "";
            for (String key : table.keySet()) {
                answer += table.get(key).toString() + "\n";
            }
            response.setAnswer(answer);
        }
    }
}
