package Server.Commands;

import Client.Models.Vehicle;
import Common.Response;
import Server.Managers.CollectionManager;
import Common.Utilites.Console;
import Common.Utilites.StandartConsole;

public class Replace_if_lower extends Command{
    private final Console console;
    private final CollectionManager collectionManager;
    public Replace_if_lower(Console console, CollectionManager collectionManager){
        super("replace_if_lower", "заменить значение по ключу, если новое значение id больше страого.");
        this.console = console;
        this.collectionManager = collectionManager;
    }
    @Override
    public void execute_add(String[] args, Response response, Vehicle vehicle){
        try{
            collectionManager.replaceIfLower(args[1], response, vehicle);
        } catch(Exception e){
            response.setAnswer(e.getMessage());
        }
    }
}
