package Server.Commands;

import Common.Response;
import Server.Managers.CollectionManager;
import Common.Utilites.Console;
import Common.Utilites.StandartConsole;

public class Remove_greater extends Command{
    private final Console console;
    private final CollectionManager collectionManager;
    public Remove_greater(Console console, CollectionManager collectionManager){
        super("remove_greater", "удаляет все эелемента из коллекции, чьи id превышают заданный.");
        this.console = console;
        this.collectionManager = collectionManager;
    }

    @Override
    public void execute(String[] args, Response response){
        Console console1 = new StandartConsole();
        try{
            collectionManager.removeGreaterElem(args[1], response);
        }catch (Exception e){
            response.setAnswer(e.getMessage());
        }
    }
}
