package Server.Commands;

import Common.Response;
import Server.Managers.CollectionManager;
import Common.Utilites.Console;
import Common.Utilites.StandartConsole;

/**
 * Команда которая очищает коллекцию
 */
public class Clear extends Command{
    private final CollectionManager collectionManager;
    public Clear(CollectionManager collectionManager){
        super("clear", "очищает коллекцию.");
        this.collectionManager = collectionManager;
    }

    @Override
    public void execute(String[] args, Response response){
        try{
            collectionManager.clearCollection(response);
        }catch (Exception e){
            response.setAnswer(e.getMessage());
        }
    }
}
