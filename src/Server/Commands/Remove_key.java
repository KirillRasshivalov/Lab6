package Server.Commands;

import Common.Response;
import Server.Managers.CollectionManager;
import Common.Utilites.Console;
import Common.Utilites.StandartConsole;

public class Remove_key extends Command{
    private final Console console;
    private final CollectionManager collectionManager;
    public Remove_key(Console console, CollectionManager collectionManager){
        super("remove_key", "удаляет значение элемента по заданному ключу.");
        this.console = console;
        this.collectionManager = collectionManager;
    }
    @Override
    public void execute(String[] args, Response response){
        Console console1 = new StandartConsole();
        try{
            collectionManager.deleteElementByKey(args[1], response);
        } catch (Exception e){
            response.setAnswer(e.getMessage());
        }
    }
}
